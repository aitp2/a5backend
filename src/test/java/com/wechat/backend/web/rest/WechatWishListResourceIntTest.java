package com.wechat.backend.web.rest;

import com.wechat.backend.A5BackendApp;

import com.wechat.backend.domain.WechatWishList;
import com.wechat.backend.repository.WechatWishListRepository;
import com.wechat.backend.service.WechatWishListService;
import com.wechat.backend.service.dto.WechatWishListDTO;
import com.wechat.backend.service.mapper.WechatWishListMapper;
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
 * Test class for the WechatWishListResource REST controller.
 *
 * @see WechatWishListResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = A5BackendApp.class)
public class WechatWishListResourceIntTest {

    @Autowired
    private WechatWishListRepository wechatWishListRepository;

    @Autowired
    private WechatWishListMapper wechatWishListMapper;

    @Autowired
    private WechatWishListService wechatWishListService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWechatWishListMockMvc;

    private WechatWishList wechatWishList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WechatWishListResource wechatWishListResource = new WechatWishListResource(wechatWishListService);
        this.restWechatWishListMockMvc = MockMvcBuilders.standaloneSetup(wechatWishListResource)
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
    public static WechatWishList createEntity(EntityManager em) {
        WechatWishList wechatWishList = new WechatWishList();
        return wechatWishList;
    }

    @Before
    public void initTest() {
        wechatWishList = createEntity(em);
    }

    @Test
    @Transactional
    public void createWechatWishList() throws Exception {
        int databaseSizeBeforeCreate = wechatWishListRepository.findAll().size();

        // Create the WechatWishList
        WechatWishListDTO wechatWishListDTO = wechatWishListMapper.toDto(wechatWishList);
        restWechatWishListMockMvc.perform(post("/api/wechat-wish-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatWishListDTO)))
            .andExpect(status().isCreated());

        // Validate the WechatWishList in the database
        List<WechatWishList> wechatWishListList = wechatWishListRepository.findAll();
        assertThat(wechatWishListList).hasSize(databaseSizeBeforeCreate + 1);
        WechatWishList testWechatWishList = wechatWishListList.get(wechatWishListList.size() - 1);
    }

    @Test
    @Transactional
    public void createWechatWishListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wechatWishListRepository.findAll().size();

        // Create the WechatWishList with an existing ID
        wechatWishList.setId(1L);
        WechatWishListDTO wechatWishListDTO = wechatWishListMapper.toDto(wechatWishList);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWechatWishListMockMvc.perform(post("/api/wechat-wish-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatWishListDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WechatWishList in the database
        List<WechatWishList> wechatWishListList = wechatWishListRepository.findAll();
        assertThat(wechatWishListList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWechatWishLists() throws Exception {
        // Initialize the database
        wechatWishListRepository.saveAndFlush(wechatWishList);

        // Get all the wechatWishListList
        restWechatWishListMockMvc.perform(get("/api/wechat-wish-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wechatWishList.getId().intValue())));
    }

    @Test
    @Transactional
    public void getWechatWishList() throws Exception {
        // Initialize the database
        wechatWishListRepository.saveAndFlush(wechatWishList);

        // Get the wechatWishList
        restWechatWishListMockMvc.perform(get("/api/wechat-wish-lists/{id}", wechatWishList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(wechatWishList.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingWechatWishList() throws Exception {
        // Get the wechatWishList
        restWechatWishListMockMvc.perform(get("/api/wechat-wish-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWechatWishList() throws Exception {
        // Initialize the database
        wechatWishListRepository.saveAndFlush(wechatWishList);
        int databaseSizeBeforeUpdate = wechatWishListRepository.findAll().size();

        // Update the wechatWishList
        WechatWishList updatedWechatWishList = wechatWishListRepository.findOne(wechatWishList.getId());
        // Disconnect from session so that the updates on updatedWechatWishList are not directly saved in db
        em.detach(updatedWechatWishList);
        WechatWishListDTO wechatWishListDTO = wechatWishListMapper.toDto(updatedWechatWishList);

        restWechatWishListMockMvc.perform(put("/api/wechat-wish-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatWishListDTO)))
            .andExpect(status().isOk());

        // Validate the WechatWishList in the database
        List<WechatWishList> wechatWishListList = wechatWishListRepository.findAll();
        assertThat(wechatWishListList).hasSize(databaseSizeBeforeUpdate);
        WechatWishList testWechatWishList = wechatWishListList.get(wechatWishListList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingWechatWishList() throws Exception {
        int databaseSizeBeforeUpdate = wechatWishListRepository.findAll().size();

        // Create the WechatWishList
        WechatWishListDTO wechatWishListDTO = wechatWishListMapper.toDto(wechatWishList);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWechatWishListMockMvc.perform(put("/api/wechat-wish-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatWishListDTO)))
            .andExpect(status().isCreated());

        // Validate the WechatWishList in the database
        List<WechatWishList> wechatWishListList = wechatWishListRepository.findAll();
        assertThat(wechatWishListList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWechatWishList() throws Exception {
        // Initialize the database
        wechatWishListRepository.saveAndFlush(wechatWishList);
        int databaseSizeBeforeDelete = wechatWishListRepository.findAll().size();

        // Get the wechatWishList
        restWechatWishListMockMvc.perform(delete("/api/wechat-wish-lists/{id}", wechatWishList.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WechatWishList> wechatWishListList = wechatWishListRepository.findAll();
        assertThat(wechatWishListList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WechatWishList.class);
        WechatWishList wechatWishList1 = new WechatWishList();
        wechatWishList1.setId(1L);
        WechatWishList wechatWishList2 = new WechatWishList();
        wechatWishList2.setId(wechatWishList1.getId());
        assertThat(wechatWishList1).isEqualTo(wechatWishList2);
        wechatWishList2.setId(2L);
        assertThat(wechatWishList1).isNotEqualTo(wechatWishList2);
        wechatWishList1.setId(null);
        assertThat(wechatWishList1).isNotEqualTo(wechatWishList2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WechatWishListDTO.class);
        WechatWishListDTO wechatWishListDTO1 = new WechatWishListDTO();
        wechatWishListDTO1.setId(1L);
        WechatWishListDTO wechatWishListDTO2 = new WechatWishListDTO();
        assertThat(wechatWishListDTO1).isNotEqualTo(wechatWishListDTO2);
        wechatWishListDTO2.setId(wechatWishListDTO1.getId());
        assertThat(wechatWishListDTO1).isEqualTo(wechatWishListDTO2);
        wechatWishListDTO2.setId(2L);
        assertThat(wechatWishListDTO1).isNotEqualTo(wechatWishListDTO2);
        wechatWishListDTO1.setId(null);
        assertThat(wechatWishListDTO1).isNotEqualTo(wechatWishListDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(wechatWishListMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(wechatWishListMapper.fromId(null)).isNull();
    }
}
