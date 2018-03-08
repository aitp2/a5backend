package com.wechat.backend.web.rest;

import com.wechat.backend.A5BackendApp;

import com.wechat.backend.domain.WechatOrder;
import com.wechat.backend.repository.WechatOrderRepository;
import com.wechat.backend.service.WechatOrderItemService;
import com.wechat.backend.service.WechatOrderService;
import com.wechat.backend.service.WechatProductService;
import com.wechat.backend.service.dto.WechatOrderDTO;
import com.wechat.backend.service.mapper.WechatOrderMapper;
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
 * Test class for the WechatOrderResource REST controller.
 *
 * @see WechatOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = A5BackendApp.class)
public class WechatOrderResourceIntTest {

    private static final String DEFAULT_STATUS = "AAA";
    private static final String UPDATED_STATUS = "BBB";

    private static final BigDecimal DEFAULT_ORDER_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_ORDER_AMOUNT = new BigDecimal(2);

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;

    private static final Long DEFAULT_SALER_ID = 1L;
    private static final Long UPDATED_SALER_ID = 2L;

    @Autowired
    private WechatOrderRepository wechatOrderRepository;

    @Autowired
    private WechatOrderMapper wechatOrderMapper;

    @Autowired
    private WechatOrderService wechatOrderService;

    @Autowired
    private WechatOrderItemService wechatOrderItemService;
    @Autowired
    private WechatProductService wechatProductService;
    @Autowired    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWechatOrderMockMvc;

    private WechatOrder wechatOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WechatOrderResource wechatOrderResource = new WechatOrderResource(wechatOrderService,wechatOrderItemService,wechatProductService);
        this.restWechatOrderMockMvc = MockMvcBuilders.standaloneSetup(wechatOrderResource)
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
    public static WechatOrder createEntity(EntityManager em) {
        WechatOrder wechatOrder = new WechatOrder()
            .status(DEFAULT_STATUS)
            .orderAmount(DEFAULT_ORDER_AMOUNT)
            .customerId(DEFAULT_CUSTOMER_ID)
            .salerId(DEFAULT_SALER_ID);
        return wechatOrder;
    }

    @Before
    public void initTest() {
        wechatOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createWechatOrder() throws Exception {
        int databaseSizeBeforeCreate = wechatOrderRepository.findAll().size();

        // Create the WechatOrder
        WechatOrderDTO wechatOrderDTO = wechatOrderMapper.toDto(wechatOrder);
        restWechatOrderMockMvc.perform(post("/api/wechat-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the WechatOrder in the database
        List<WechatOrder> wechatOrderList = wechatOrderRepository.findAll();
        assertThat(wechatOrderList).hasSize(databaseSizeBeforeCreate + 1);
        WechatOrder testWechatOrder = wechatOrderList.get(wechatOrderList.size() - 1);
        assertThat(testWechatOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testWechatOrder.getOrderAmount()).isEqualTo(DEFAULT_ORDER_AMOUNT);
        assertThat(testWechatOrder.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testWechatOrder.getSalerId()).isEqualTo(DEFAULT_SALER_ID);
    }

    @Test
    @Transactional
    public void createWechatOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wechatOrderRepository.findAll().size();

        // Create the WechatOrder with an existing ID
        wechatOrder.setId(1L);
        WechatOrderDTO wechatOrderDTO = wechatOrderMapper.toDto(wechatOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWechatOrderMockMvc.perform(post("/api/wechat-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WechatOrder in the database
        List<WechatOrder> wechatOrderList = wechatOrderRepository.findAll();
        assertThat(wechatOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWechatOrders() throws Exception {
        // Initialize the database
        wechatOrderRepository.saveAndFlush(wechatOrder);

        // Get all the wechatOrderList
        restWechatOrderMockMvc.perform(get("/api/wechat-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wechatOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].orderAmount").value(hasItem(DEFAULT_ORDER_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].salerId").value(hasItem(DEFAULT_SALER_ID.intValue())));
    }

    @Test
    @Transactional
    public void getWechatOrder() throws Exception {
        // Initialize the database
        wechatOrderRepository.saveAndFlush(wechatOrder);

        // Get the wechatOrder
        restWechatOrderMockMvc.perform(get("/api/wechat-orders/{id}", wechatOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(wechatOrder.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.orderAmount").value(DEFAULT_ORDER_AMOUNT.intValue()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()))
            .andExpect(jsonPath("$.salerId").value(DEFAULT_SALER_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingWechatOrder() throws Exception {
        // Get the wechatOrder
        restWechatOrderMockMvc.perform(get("/api/wechat-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWechatOrder() throws Exception {
        // Initialize the database
        wechatOrderRepository.saveAndFlush(wechatOrder);
        int databaseSizeBeforeUpdate = wechatOrderRepository.findAll().size();

        // Update the wechatOrder
        WechatOrder updatedWechatOrder = wechatOrderRepository.findOne(wechatOrder.getId());
        // Disconnect from session so that the updates on updatedWechatOrder are not directly saved in db
        em.detach(updatedWechatOrder);
        updatedWechatOrder
            .status(UPDATED_STATUS)
            .orderAmount(UPDATED_ORDER_AMOUNT)
            .customerId(UPDATED_CUSTOMER_ID)
            .salerId(UPDATED_SALER_ID);
        WechatOrderDTO wechatOrderDTO = wechatOrderMapper.toDto(updatedWechatOrder);

        restWechatOrderMockMvc.perform(put("/api/wechat-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatOrderDTO)))
            .andExpect(status().isOk());

        // Validate the WechatOrder in the database
        List<WechatOrder> wechatOrderList = wechatOrderRepository.findAll();
        assertThat(wechatOrderList).hasSize(databaseSizeBeforeUpdate);
        WechatOrder testWechatOrder = wechatOrderList.get(wechatOrderList.size() - 1);
        assertThat(testWechatOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testWechatOrder.getOrderAmount()).isEqualTo(UPDATED_ORDER_AMOUNT);
        assertThat(testWechatOrder.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testWechatOrder.getSalerId()).isEqualTo(UPDATED_SALER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingWechatOrder() throws Exception {
        int databaseSizeBeforeUpdate = wechatOrderRepository.findAll().size();

        // Create the WechatOrder
        WechatOrderDTO wechatOrderDTO = wechatOrderMapper.toDto(wechatOrder);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWechatOrderMockMvc.perform(put("/api/wechat-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the WechatOrder in the database
        List<WechatOrder> wechatOrderList = wechatOrderRepository.findAll();
        assertThat(wechatOrderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWechatOrder() throws Exception {
        // Initialize the database
        wechatOrderRepository.saveAndFlush(wechatOrder);
        int databaseSizeBeforeDelete = wechatOrderRepository.findAll().size();

        // Get the wechatOrder
        restWechatOrderMockMvc.perform(delete("/api/wechat-orders/{id}", wechatOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WechatOrder> wechatOrderList = wechatOrderRepository.findAll();
        assertThat(wechatOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WechatOrder.class);
        WechatOrder wechatOrder1 = new WechatOrder();
        wechatOrder1.setId(1L);
        WechatOrder wechatOrder2 = new WechatOrder();
        wechatOrder2.setId(wechatOrder1.getId());
        assertThat(wechatOrder1).isEqualTo(wechatOrder2);
        wechatOrder2.setId(2L);
        assertThat(wechatOrder1).isNotEqualTo(wechatOrder2);
        wechatOrder1.setId(null);
        assertThat(wechatOrder1).isNotEqualTo(wechatOrder2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WechatOrderDTO.class);
        WechatOrderDTO wechatOrderDTO1 = new WechatOrderDTO();
        wechatOrderDTO1.setId(1L);
        WechatOrderDTO wechatOrderDTO2 = new WechatOrderDTO();
        assertThat(wechatOrderDTO1).isNotEqualTo(wechatOrderDTO2);
        wechatOrderDTO2.setId(wechatOrderDTO1.getId());
        assertThat(wechatOrderDTO1).isEqualTo(wechatOrderDTO2);
        wechatOrderDTO2.setId(2L);
        assertThat(wechatOrderDTO1).isNotEqualTo(wechatOrderDTO2);
        wechatOrderDTO1.setId(null);
        assertThat(wechatOrderDTO1).isNotEqualTo(wechatOrderDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(wechatOrderMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(wechatOrderMapper.fromId(null)).isNull();
    }
}
