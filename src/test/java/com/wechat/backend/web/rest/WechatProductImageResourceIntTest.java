package com.wechat.backend.web.rest;

import com.wechat.backend.A5BackendApp;

import com.wechat.backend.domain.WechatProductImage;
import com.wechat.backend.repository.WechatProductImageRepository;
import com.wechat.backend.service.WechatProductImageService;
import com.wechat.backend.service.dto.WechatProductImageDTO;
import com.wechat.backend.service.mapper.WechatProductImageMapper;
import com.wechat.backend.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.wechat.backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WechatProductImageResource REST controller.
 *
 * @see WechatProductImageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = A5BackendApp.class)
public class WechatProductImageResourceIntTest {

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_DESC = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_CONTENT = "BBBBBBBBBB";

    @Autowired
    private WechatProductImageRepository wechatProductImageRepository;

    @Autowired
    private WechatProductImageMapper wechatProductImageMapper;

    @Autowired
    private WechatProductImageService wechatProductImageService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWechatProductImageMockMvc;

    private WechatProductImage wechatProductImage;
    private ResourceLoader resourceLoader;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WechatProductImageResource wechatProductImageResource = new WechatProductImageResource(wechatProductImageService,resourceLoader);
        this.restWechatProductImageMockMvc = MockMvcBuilders.standaloneSetup(wechatProductImageResource)
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
    public static WechatProductImage createEntity(EntityManager em) {
        WechatProductImage wechatProductImage = new WechatProductImage()
            .imageUrl(DEFAULT_IMAGE_URL)
            .imageDesc(DEFAULT_IMAGE_DESC)
            .imageContent(DEFAULT_IMAGE_CONTENT);
        return wechatProductImage;
    }

    @Before
    public void initTest() {
        wechatProductImage = createEntity(em);
    }

    @Test
    @Transactional
    public void createWechatProductImage() throws Exception {
        int databaseSizeBeforeCreate = wechatProductImageRepository.findAll().size();

        // Create the WechatProductImage
        WechatProductImageDTO wechatProductImageDTO = wechatProductImageMapper.toDto(wechatProductImage);
        restWechatProductImageMockMvc.perform(post("/api/wechat-product-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatProductImageDTO)))
            .andExpect(status().isCreated());

        // Validate the WechatProductImage in the database
        List<WechatProductImage> wechatProductImageList = wechatProductImageRepository.findAll();
        assertThat(wechatProductImageList).hasSize(databaseSizeBeforeCreate + 1);
        WechatProductImage testWechatProductImage = wechatProductImageList.get(wechatProductImageList.size() - 1);
        assertThat(testWechatProductImage.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testWechatProductImage.getImageDesc()).isEqualTo(DEFAULT_IMAGE_DESC);
        assertThat(testWechatProductImage.getImageContent()).isEqualTo(DEFAULT_IMAGE_CONTENT);
    }

    @Test
    @Transactional
    public void createWechatProductImageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wechatProductImageRepository.findAll().size();

        // Create the WechatProductImage with an existing ID
        wechatProductImage.setId(1L);
        WechatProductImageDTO wechatProductImageDTO = wechatProductImageMapper.toDto(wechatProductImage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWechatProductImageMockMvc.perform(post("/api/wechat-product-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatProductImageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WechatProductImage in the database
        List<WechatProductImage> wechatProductImageList = wechatProductImageRepository.findAll();
        assertThat(wechatProductImageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWechatProductImages() throws Exception {
        // Initialize the database
        wechatProductImageRepository.saveAndFlush(wechatProductImage);

        // Get all the wechatProductImageList
        restWechatProductImageMockMvc.perform(get("/api/wechat-product-images?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wechatProductImage.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())))
            .andExpect(jsonPath("$.[*].imageDesc").value(hasItem(DEFAULT_IMAGE_DESC.toString())))
            .andExpect(jsonPath("$.[*].imageContent").value(hasItem(DEFAULT_IMAGE_CONTENT.toString())));
    }

    @Test
    @Transactional
    public void getWechatProductImage() throws Exception {
        // Initialize the database
        wechatProductImageRepository.saveAndFlush(wechatProductImage);

        // Get the wechatProductImage
        restWechatProductImageMockMvc.perform(get("/api/wechat-product-images/{id}", wechatProductImage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(wechatProductImage.getId().intValue()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL.toString()))
            .andExpect(jsonPath("$.imageDesc").value(DEFAULT_IMAGE_DESC.toString()))
            .andExpect(jsonPath("$.imageContent").value(DEFAULT_IMAGE_CONTENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWechatProductImage() throws Exception {
        // Get the wechatProductImage
        restWechatProductImageMockMvc.perform(get("/api/wechat-product-images/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWechatProductImage() throws Exception {
        // Initialize the database
        wechatProductImageRepository.saveAndFlush(wechatProductImage);
        int databaseSizeBeforeUpdate = wechatProductImageRepository.findAll().size();

        // Update the wechatProductImage
        WechatProductImage updatedWechatProductImage = wechatProductImageRepository.findOne(wechatProductImage.getId());
        // Disconnect from session so that the updates on updatedWechatProductImage are not directly saved in db
        em.detach(updatedWechatProductImage);
        updatedWechatProductImage
            .imageUrl(UPDATED_IMAGE_URL)
            .imageDesc(UPDATED_IMAGE_DESC)
            .imageContent(UPDATED_IMAGE_CONTENT);
        WechatProductImageDTO wechatProductImageDTO = wechatProductImageMapper.toDto(updatedWechatProductImage);

        restWechatProductImageMockMvc.perform(put("/api/wechat-product-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatProductImageDTO)))
            .andExpect(status().isOk());

        // Validate the WechatProductImage in the database
        List<WechatProductImage> wechatProductImageList = wechatProductImageRepository.findAll();
        assertThat(wechatProductImageList).hasSize(databaseSizeBeforeUpdate);
        WechatProductImage testWechatProductImage = wechatProductImageList.get(wechatProductImageList.size() - 1);
        assertThat(testWechatProductImage.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testWechatProductImage.getImageDesc()).isEqualTo(UPDATED_IMAGE_DESC);
        assertThat(testWechatProductImage.getImageContent()).isEqualTo(UPDATED_IMAGE_CONTENT);
    }

    @Test
    @Transactional
    public void updateNonExistingWechatProductImage() throws Exception {
        int databaseSizeBeforeUpdate = wechatProductImageRepository.findAll().size();

        // Create the WechatProductImage
        WechatProductImageDTO wechatProductImageDTO = wechatProductImageMapper.toDto(wechatProductImage);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWechatProductImageMockMvc.perform(put("/api/wechat-product-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatProductImageDTO)))
            .andExpect(status().isCreated());

        // Validate the WechatProductImage in the database
        List<WechatProductImage> wechatProductImageList = wechatProductImageRepository.findAll();
        assertThat(wechatProductImageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWechatProductImage() throws Exception {
        // Initialize the database
        wechatProductImageRepository.saveAndFlush(wechatProductImage);
        int databaseSizeBeforeDelete = wechatProductImageRepository.findAll().size();

        // Get the wechatProductImage
        restWechatProductImageMockMvc.perform(delete("/api/wechat-product-images/{id}", wechatProductImage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WechatProductImage> wechatProductImageList = wechatProductImageRepository.findAll();
        assertThat(wechatProductImageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WechatProductImage.class);
        WechatProductImage wechatProductImage1 = new WechatProductImage();
        wechatProductImage1.setId(1L);
        WechatProductImage wechatProductImage2 = new WechatProductImage();
        wechatProductImage2.setId(wechatProductImage1.getId());
        assertThat(wechatProductImage1).isEqualTo(wechatProductImage2);
        wechatProductImage2.setId(2L);
        assertThat(wechatProductImage1).isNotEqualTo(wechatProductImage2);
        wechatProductImage1.setId(null);
        assertThat(wechatProductImage1).isNotEqualTo(wechatProductImage2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WechatProductImageDTO.class);
        WechatProductImageDTO wechatProductImageDTO1 = new WechatProductImageDTO();
        wechatProductImageDTO1.setId(1L);
        WechatProductImageDTO wechatProductImageDTO2 = new WechatProductImageDTO();
        assertThat(wechatProductImageDTO1).isNotEqualTo(wechatProductImageDTO2);
        wechatProductImageDTO2.setId(wechatProductImageDTO1.getId());
        assertThat(wechatProductImageDTO1).isEqualTo(wechatProductImageDTO2);
        wechatProductImageDTO2.setId(2L);
        assertThat(wechatProductImageDTO1).isNotEqualTo(wechatProductImageDTO2);
        wechatProductImageDTO1.setId(null);
        assertThat(wechatProductImageDTO1).isNotEqualTo(wechatProductImageDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(wechatProductImageMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(wechatProductImageMapper.fromId(null)).isNull();
    }
}
