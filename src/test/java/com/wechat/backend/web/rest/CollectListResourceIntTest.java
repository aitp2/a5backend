package com.wechat.backend.web.rest;

import com.wechat.backend.A5BackendApp;

import com.wechat.backend.domain.CollectList;
import com.wechat.backend.repository.CollectListRepository;
import com.wechat.backend.service.CollectListService;
import com.wechat.backend.service.WechatProductService;
import com.wechat.backend.service.dto.CollectListDTO;
import com.wechat.backend.service.mapper.CollectListMapper;
import com.wechat.backend.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.wechat.backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CollectListResource REST controller.
 *
 * @see CollectListResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = A5BackendApp.class)
public class CollectListResourceIntTest {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    @Autowired
    private CollectListRepository collectListRepository;

    @Autowired
    private CollectListMapper collectListMapper;

    @Autowired
    private CollectListService collectListService;
    @Autowired
    private WechatProductService wechatProductService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCollectListMockMvc;

    private CollectList collectList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CollectListResource collectListResource = new CollectListResource(collectListService,wechatProductService);
        this.restCollectListMockMvc = MockMvcBuilders.standaloneSetup(collectListResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CollectList createEntity(EntityManager em) {
        CollectList collectList = new CollectList()
            .userId(DEFAULT_USER_ID);
        return collectList;
    }

    @Before
    public void initTest() {
        collectList = createEntity(em);
    }

    @Test
    @Transactional
    public void createCollectList() throws Exception {
        int databaseSizeBeforeCreate = collectListRepository.findAll().size();

        // Create the CollectList
        CollectListDTO collectListDTO = collectListMapper.toDto(collectList);
        restCollectListMockMvc.perform(post("/api/collect-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collectListDTO)))
            .andExpect(status().isCreated());

        // Validate the CollectList in the database
        List<CollectList> collectListList = collectListRepository.findAll();
        assertThat(collectListList).hasSize(databaseSizeBeforeCreate + 1);
        CollectList testCollectList = collectListList.get(collectListList.size() - 1);
        assertThat(testCollectList.getUserId()).isEqualTo(DEFAULT_USER_ID);
    }

    @Test
    @Transactional
    public void createCollectListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = collectListRepository.findAll().size();

        // Create the CollectList with an existing ID
        collectList.setId(1L);
        CollectListDTO collectListDTO = collectListMapper.toDto(collectList);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollectListMockMvc.perform(post("/api/collect-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collectListDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CollectList in the database
        List<CollectList> collectListList = collectListRepository.findAll();
        assertThat(collectListList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCollectLists() throws Exception {
        // Initialize the database
        collectListRepository.saveAndFlush(collectList);

        // Get all the collectListList
        restCollectListMockMvc.perform(get("/api/collect-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collectList.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));
    }

    @Test
    @Transactional
    public void getCollectList() throws Exception {
        // Initialize the database
        collectListRepository.saveAndFlush(collectList);

        // Get the collectList
        restCollectListMockMvc.perform(get("/api/collect-lists/{id}", collectList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(collectList.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCollectList() throws Exception {
        // Get the collectList
        restCollectListMockMvc.perform(get("/api/collect-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCollectList() throws Exception {
        // Initialize the database
        collectListRepository.saveAndFlush(collectList);
        int databaseSizeBeforeUpdate = collectListRepository.findAll().size();

        // Update the collectList
        CollectList updatedCollectList = collectListRepository.findOne(collectList.getId());
        // Disconnect from session so that the updates on updatedCollectList are not directly saved in db
        em.detach(updatedCollectList);
        updatedCollectList
            .userId(UPDATED_USER_ID);
        CollectListDTO collectListDTO = collectListMapper.toDto(updatedCollectList);

        restCollectListMockMvc.perform(put("/api/collect-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collectListDTO)))
            .andExpect(status().isOk());

        // Validate the CollectList in the database
        List<CollectList> collectListList = collectListRepository.findAll();
        assertThat(collectListList).hasSize(databaseSizeBeforeUpdate);
        CollectList testCollectList = collectListList.get(collectListList.size() - 1);
        assertThat(testCollectList.getUserId()).isEqualTo(UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingCollectList() throws Exception {
        int databaseSizeBeforeUpdate = collectListRepository.findAll().size();

        // Create the CollectList
        CollectListDTO collectListDTO = collectListMapper.toDto(collectList);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCollectListMockMvc.perform(put("/api/collect-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collectListDTO)))
            .andExpect(status().isCreated());

        // Validate the CollectList in the database
        List<CollectList> collectListList = collectListRepository.findAll();
        assertThat(collectListList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCollectList() throws Exception {
        // Initialize the database
        collectListRepository.saveAndFlush(collectList);
        int databaseSizeBeforeDelete = collectListRepository.findAll().size();

        // Get the collectList
        restCollectListMockMvc.perform(delete("/api/collect-lists/{id}", collectList.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CollectList> collectListList = collectListRepository.findAll();
        assertThat(collectListList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollectList.class);
        CollectList collectList1 = new CollectList();
        collectList1.setId(1L);
        CollectList collectList2 = new CollectList();
        collectList2.setId(collectList1.getId());
        assertThat(collectList1).isEqualTo(collectList2);
        collectList2.setId(2L);
        assertThat(collectList1).isNotEqualTo(collectList2);
        collectList1.setId(null);
        assertThat(collectList1).isNotEqualTo(collectList2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollectListDTO.class);
        CollectListDTO collectListDTO1 = new CollectListDTO();
        collectListDTO1.setId(1L);
        CollectListDTO collectListDTO2 = new CollectListDTO();
        assertThat(collectListDTO1).isNotEqualTo(collectListDTO2);
        collectListDTO2.setId(collectListDTO1.getId());
        assertThat(collectListDTO1).isEqualTo(collectListDTO2);
        collectListDTO2.setId(2L);
        assertThat(collectListDTO1).isNotEqualTo(collectListDTO2);
        collectListDTO1.setId(null);
        assertThat(collectListDTO1).isNotEqualTo(collectListDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(collectListMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(collectListMapper.fromId(null)).isNull();
    }
}
