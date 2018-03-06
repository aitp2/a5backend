package com.wechat.backend.web.rest;

import com.wechat.backend.A5BackendApp;

import com.wechat.backend.domain.WechatOrderItem;
import com.wechat.backend.repository.WechatOrderItemRepository;
import com.wechat.backend.service.WechatOrderItemService;
import com.wechat.backend.service.dto.WechatOrderItemDTO;
import com.wechat.backend.service.mapper.WechatOrderItemMapper;
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
import java.math.BigDecimal;
import java.util.List;

import static com.wechat.backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WechatOrderItemResource REST controller.
 *
 * @see WechatOrderItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = A5BackendApp.class)
public class WechatOrderItemResourceIntTest {

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final BigDecimal DEFAULT_RETAIL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_RETAIL_PRICE = new BigDecimal(2);

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;

    @Autowired
    private WechatOrderItemRepository wechatOrderItemRepository;

    @Autowired
    private WechatOrderItemMapper wechatOrderItemMapper;

    @Autowired
    private WechatOrderItemService wechatOrderItemService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWechatOrderItemMockMvc;

    private WechatOrderItem wechatOrderItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WechatOrderItemResource wechatOrderItemResource = new WechatOrderItemResource(wechatOrderItemService);
        this.restWechatOrderItemMockMvc = MockMvcBuilders.standaloneSetup(wechatOrderItemResource)
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
    public static WechatOrderItem createEntity(EntityManager em) {
        WechatOrderItem wechatOrderItem = new WechatOrderItem()
            .price(DEFAULT_PRICE)
            .quantity(DEFAULT_QUANTITY)
            .retailPrice(DEFAULT_RETAIL_PRICE)
            .productId(DEFAULT_PRODUCT_ID);
        return wechatOrderItem;
    }

    @Before
    public void initTest() {
        wechatOrderItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createWechatOrderItem() throws Exception {
        int databaseSizeBeforeCreate = wechatOrderItemRepository.findAll().size();

        // Create the WechatOrderItem
        WechatOrderItemDTO wechatOrderItemDTO = wechatOrderItemMapper.toDto(wechatOrderItem);
        restWechatOrderItemMockMvc.perform(post("/api/wechat-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatOrderItemDTO)))
            .andExpect(status().isCreated());

        // Validate the WechatOrderItem in the database
        List<WechatOrderItem> wechatOrderItemList = wechatOrderItemRepository.findAll();
        assertThat(wechatOrderItemList).hasSize(databaseSizeBeforeCreate + 1);
        WechatOrderItem testWechatOrderItem = wechatOrderItemList.get(wechatOrderItemList.size() - 1);
        assertThat(testWechatOrderItem.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testWechatOrderItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testWechatOrderItem.getRetailPrice()).isEqualTo(DEFAULT_RETAIL_PRICE);
        assertThat(testWechatOrderItem.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
    }

    @Test
    @Transactional
    public void createWechatOrderItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wechatOrderItemRepository.findAll().size();

        // Create the WechatOrderItem with an existing ID
        wechatOrderItem.setId(1L);
        WechatOrderItemDTO wechatOrderItemDTO = wechatOrderItemMapper.toDto(wechatOrderItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWechatOrderItemMockMvc.perform(post("/api/wechat-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatOrderItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WechatOrderItem in the database
        List<WechatOrderItem> wechatOrderItemList = wechatOrderItemRepository.findAll();
        assertThat(wechatOrderItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWechatOrderItems() throws Exception {
        // Initialize the database
        wechatOrderItemRepository.saveAndFlush(wechatOrderItem);

        // Get all the wechatOrderItemList
        restWechatOrderItemMockMvc.perform(get("/api/wechat-order-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wechatOrderItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].retailPrice").value(hasItem(DEFAULT_RETAIL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())));
    }

    @Test
    @Transactional
    public void getWechatOrderItem() throws Exception {
        // Initialize the database
        wechatOrderItemRepository.saveAndFlush(wechatOrderItem);

        // Get the wechatOrderItem
        restWechatOrderItemMockMvc.perform(get("/api/wechat-order-items/{id}", wechatOrderItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(wechatOrderItem.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.retailPrice").value(DEFAULT_RETAIL_PRICE.intValue()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingWechatOrderItem() throws Exception {
        // Get the wechatOrderItem
        restWechatOrderItemMockMvc.perform(get("/api/wechat-order-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWechatOrderItem() throws Exception {
        // Initialize the database
        wechatOrderItemRepository.saveAndFlush(wechatOrderItem);
        int databaseSizeBeforeUpdate = wechatOrderItemRepository.findAll().size();

        // Update the wechatOrderItem
        WechatOrderItem updatedWechatOrderItem = wechatOrderItemRepository.findOne(wechatOrderItem.getId());
        // Disconnect from session so that the updates on updatedWechatOrderItem are not directly saved in db
        em.detach(updatedWechatOrderItem);
        updatedWechatOrderItem
            .price(UPDATED_PRICE)
            .quantity(UPDATED_QUANTITY)
            .retailPrice(UPDATED_RETAIL_PRICE)
            .productId(UPDATED_PRODUCT_ID);
        WechatOrderItemDTO wechatOrderItemDTO = wechatOrderItemMapper.toDto(updatedWechatOrderItem);

        restWechatOrderItemMockMvc.perform(put("/api/wechat-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatOrderItemDTO)))
            .andExpect(status().isOk());

        // Validate the WechatOrderItem in the database
        List<WechatOrderItem> wechatOrderItemList = wechatOrderItemRepository.findAll();
        assertThat(wechatOrderItemList).hasSize(databaseSizeBeforeUpdate);
        WechatOrderItem testWechatOrderItem = wechatOrderItemList.get(wechatOrderItemList.size() - 1);
        assertThat(testWechatOrderItem.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testWechatOrderItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testWechatOrderItem.getRetailPrice()).isEqualTo(UPDATED_RETAIL_PRICE);
        assertThat(testWechatOrderItem.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingWechatOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = wechatOrderItemRepository.findAll().size();

        // Create the WechatOrderItem
        WechatOrderItemDTO wechatOrderItemDTO = wechatOrderItemMapper.toDto(wechatOrderItem);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWechatOrderItemMockMvc.perform(put("/api/wechat-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatOrderItemDTO)))
            .andExpect(status().isCreated());

        // Validate the WechatOrderItem in the database
        List<WechatOrderItem> wechatOrderItemList = wechatOrderItemRepository.findAll();
        assertThat(wechatOrderItemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWechatOrderItem() throws Exception {
        // Initialize the database
        wechatOrderItemRepository.saveAndFlush(wechatOrderItem);
        int databaseSizeBeforeDelete = wechatOrderItemRepository.findAll().size();

        // Get the wechatOrderItem
        restWechatOrderItemMockMvc.perform(delete("/api/wechat-order-items/{id}", wechatOrderItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WechatOrderItem> wechatOrderItemList = wechatOrderItemRepository.findAll();
        assertThat(wechatOrderItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WechatOrderItem.class);
        WechatOrderItem wechatOrderItem1 = new WechatOrderItem();
        wechatOrderItem1.setId(1L);
        WechatOrderItem wechatOrderItem2 = new WechatOrderItem();
        wechatOrderItem2.setId(wechatOrderItem1.getId());
        assertThat(wechatOrderItem1).isEqualTo(wechatOrderItem2);
        wechatOrderItem2.setId(2L);
        assertThat(wechatOrderItem1).isNotEqualTo(wechatOrderItem2);
        wechatOrderItem1.setId(null);
        assertThat(wechatOrderItem1).isNotEqualTo(wechatOrderItem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WechatOrderItemDTO.class);
        WechatOrderItemDTO wechatOrderItemDTO1 = new WechatOrderItemDTO();
        wechatOrderItemDTO1.setId(1L);
        WechatOrderItemDTO wechatOrderItemDTO2 = new WechatOrderItemDTO();
        assertThat(wechatOrderItemDTO1).isNotEqualTo(wechatOrderItemDTO2);
        wechatOrderItemDTO2.setId(wechatOrderItemDTO1.getId());
        assertThat(wechatOrderItemDTO1).isEqualTo(wechatOrderItemDTO2);
        wechatOrderItemDTO2.setId(2L);
        assertThat(wechatOrderItemDTO1).isNotEqualTo(wechatOrderItemDTO2);
        wechatOrderItemDTO1.setId(null);
        assertThat(wechatOrderItemDTO1).isNotEqualTo(wechatOrderItemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(wechatOrderItemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(wechatOrderItemMapper.fromId(null)).isNull();
    }
}
