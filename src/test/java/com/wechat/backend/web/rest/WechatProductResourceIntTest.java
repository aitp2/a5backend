package com.wechat.backend.web.rest;

import com.wechat.backend.A5BackendApp;

import com.wechat.backend.domain.WechatProduct;
import com.wechat.backend.repository.WechatProductRepository;
import com.wechat.backend.service.WechatProductService;
import com.wechat.backend.service.dto.WechatProductDTO;
import com.wechat.backend.service.mapper.WechatProductMapper;
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
 * Test class for the WechatProductResource REST controller.
 *
 * @see WechatProductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = A5BackendApp.class)
public class WechatProductResourceIntTest {

    private static final String DEFAULT_PRODUCT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_META_DESC = "AAAAAAAAAA";
    private static final String UPDATED_META_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_ORIGINAL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_ORIGINAL_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final Boolean DEFAULT_PLATFORM_PRODUCT = false;
    private static final Boolean UPDATED_PLATFORM_PRODUCT = true;

    private static final Boolean DEFAULT_SELL_OUT = false;
    private static final Boolean UPDATED_SELL_OUT = true;

    private static final Boolean DEFAULT_GO_LIVE = false;
    private static final Boolean UPDATED_GO_LIVE = true;

    private static final Integer DEFAULT_COLLECT_TIMES = 1;
    private static final Integer UPDATED_COLLECT_TIMES = 2;

    @Autowired
    private WechatProductRepository wechatProductRepository;

    @Autowired
    private WechatProductMapper wechatProductMapper;

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

    private MockMvc restWechatProductMockMvc;

    private WechatProduct wechatProduct;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WechatProductResource wechatProductResource = new WechatProductResource(wechatProductService);
        this.restWechatProductMockMvc = MockMvcBuilders.standaloneSetup(wechatProductResource)
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
    public static WechatProduct createEntity(EntityManager em) {
        WechatProduct wechatProduct = new WechatProduct()
            .productCode(DEFAULT_PRODUCT_CODE)
            .productName(DEFAULT_PRODUCT_NAME)
            .metaDesc(DEFAULT_META_DESC)
            .image(DEFAULT_IMAGE)
            .originalPrice(DEFAULT_ORIGINAL_PRICE)
            .price(DEFAULT_PRICE)
            .platformProduct(DEFAULT_PLATFORM_PRODUCT)
            .sellOut(DEFAULT_SELL_OUT)
            .goLive(DEFAULT_GO_LIVE)
            .collectTimes(DEFAULT_COLLECT_TIMES);
        return wechatProduct;
    }

    @Before
    public void initTest() {
        wechatProduct = createEntity(em);
    }

    @Test
    @Transactional
    public void createWechatProduct() throws Exception {
        int databaseSizeBeforeCreate = wechatProductRepository.findAll().size();

        // Create the WechatProduct
        WechatProductDTO wechatProductDTO = wechatProductMapper.toDto(wechatProduct);
        restWechatProductMockMvc.perform(post("/api/wechat-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatProductDTO)))
            .andExpect(status().isCreated());

        // Validate the WechatProduct in the database
        List<WechatProduct> wechatProductList = wechatProductRepository.findAll();
        assertThat(wechatProductList).hasSize(databaseSizeBeforeCreate + 1);
        WechatProduct testWechatProduct = wechatProductList.get(wechatProductList.size() - 1);
        assertThat(testWechatProduct.getProductCode()).isEqualTo(DEFAULT_PRODUCT_CODE);
        assertThat(testWechatProduct.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testWechatProduct.getMetaDesc()).isEqualTo(DEFAULT_META_DESC);
        assertThat(testWechatProduct.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testWechatProduct.getOriginalPrice()).isEqualTo(DEFAULT_ORIGINAL_PRICE);
        assertThat(testWechatProduct.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testWechatProduct.isPlatformProduct()).isEqualTo(DEFAULT_PLATFORM_PRODUCT);
        assertThat(testWechatProduct.isSellOut()).isEqualTo(DEFAULT_SELL_OUT);
        assertThat(testWechatProduct.isGoLive()).isEqualTo(DEFAULT_GO_LIVE);
        assertThat(testWechatProduct.getCollectTimes()).isEqualTo(DEFAULT_COLLECT_TIMES);
    }

    @Test
    @Transactional
    public void createWechatProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wechatProductRepository.findAll().size();

        // Create the WechatProduct with an existing ID
        wechatProduct.setId(1L);
        WechatProductDTO wechatProductDTO = wechatProductMapper.toDto(wechatProduct);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWechatProductMockMvc.perform(post("/api/wechat-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatProductDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WechatProduct in the database
        List<WechatProduct> wechatProductList = wechatProductRepository.findAll();
        assertThat(wechatProductList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkProductCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = wechatProductRepository.findAll().size();
        // set the field null
        wechatProduct.setProductCode(null);

        // Create the WechatProduct, which fails.
        WechatProductDTO wechatProductDTO = wechatProductMapper.toDto(wechatProduct);

        restWechatProductMockMvc.perform(post("/api/wechat-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatProductDTO)))
            .andExpect(status().isBadRequest());

        List<WechatProduct> wechatProductList = wechatProductRepository.findAll();
        assertThat(wechatProductList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProductNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = wechatProductRepository.findAll().size();
        // set the field null
        wechatProduct.setProductName(null);

        // Create the WechatProduct, which fails.
        WechatProductDTO wechatProductDTO = wechatProductMapper.toDto(wechatProduct);

        restWechatProductMockMvc.perform(post("/api/wechat-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatProductDTO)))
            .andExpect(status().isBadRequest());

        List<WechatProduct> wechatProductList = wechatProductRepository.findAll();
        assertThat(wechatProductList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWechatProducts() throws Exception {
        // Initialize the database
        wechatProductRepository.saveAndFlush(wechatProduct);

        // Get all the wechatProductList
        restWechatProductMockMvc.perform(get("/api/wechat-products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wechatProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].productCode").value(hasItem(DEFAULT_PRODUCT_CODE.toString())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME.toString())))
            .andExpect(jsonPath("$.[*].metaDesc").value(hasItem(DEFAULT_META_DESC.toString())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].originalPrice").value(hasItem(DEFAULT_ORIGINAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].platformProduct").value(hasItem(DEFAULT_PLATFORM_PRODUCT.booleanValue())))
            .andExpect(jsonPath("$.[*].sellOut").value(hasItem(DEFAULT_SELL_OUT.booleanValue())))
            .andExpect(jsonPath("$.[*].goLive").value(hasItem(DEFAULT_GO_LIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].collectTimes").value(hasItem(DEFAULT_COLLECT_TIMES)));
    }

    @Test
    @Transactional
    public void getWechatProduct() throws Exception {
        // Initialize the database
        wechatProductRepository.saveAndFlush(wechatProduct);

        // Get the wechatProduct
        restWechatProductMockMvc.perform(get("/api/wechat-products/{id}", wechatProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(wechatProduct.getId().intValue()))
            .andExpect(jsonPath("$.productCode").value(DEFAULT_PRODUCT_CODE.toString()))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME.toString()))
            .andExpect(jsonPath("$.metaDesc").value(DEFAULT_META_DESC.toString()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()))
            .andExpect(jsonPath("$.originalPrice").value(DEFAULT_ORIGINAL_PRICE.intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.platformProduct").value(DEFAULT_PLATFORM_PRODUCT.booleanValue()))
            .andExpect(jsonPath("$.sellOut").value(DEFAULT_SELL_OUT.booleanValue()))
            .andExpect(jsonPath("$.goLive").value(DEFAULT_GO_LIVE.booleanValue()))
            .andExpect(jsonPath("$.collectTimes").value(DEFAULT_COLLECT_TIMES));
    }

    @Test
    @Transactional
    public void getNonExistingWechatProduct() throws Exception {
        // Get the wechatProduct
        restWechatProductMockMvc.perform(get("/api/wechat-products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWechatProduct() throws Exception {
        // Initialize the database
        wechatProductRepository.saveAndFlush(wechatProduct);
        int databaseSizeBeforeUpdate = wechatProductRepository.findAll().size();

        // Update the wechatProduct
        WechatProduct updatedWechatProduct = wechatProductRepository.findOne(wechatProduct.getId());
        // Disconnect from session so that the updates on updatedWechatProduct are not directly saved in db
        em.detach(updatedWechatProduct);
        updatedWechatProduct
            .productCode(UPDATED_PRODUCT_CODE)
            .productName(UPDATED_PRODUCT_NAME)
            .metaDesc(UPDATED_META_DESC)
            .image(UPDATED_IMAGE)
            .originalPrice(UPDATED_ORIGINAL_PRICE)
            .price(UPDATED_PRICE)
            .platformProduct(UPDATED_PLATFORM_PRODUCT)
            .sellOut(UPDATED_SELL_OUT)
            .goLive(UPDATED_GO_LIVE)
            .collectTimes(UPDATED_COLLECT_TIMES);
        WechatProductDTO wechatProductDTO = wechatProductMapper.toDto(updatedWechatProduct);

        restWechatProductMockMvc.perform(put("/api/wechat-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatProductDTO)))
            .andExpect(status().isOk());

        // Validate the WechatProduct in the database
        List<WechatProduct> wechatProductList = wechatProductRepository.findAll();
        assertThat(wechatProductList).hasSize(databaseSizeBeforeUpdate);
        WechatProduct testWechatProduct = wechatProductList.get(wechatProductList.size() - 1);
        assertThat(testWechatProduct.getProductCode()).isEqualTo(UPDATED_PRODUCT_CODE);
        assertThat(testWechatProduct.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testWechatProduct.getMetaDesc()).isEqualTo(UPDATED_META_DESC);
        assertThat(testWechatProduct.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testWechatProduct.getOriginalPrice()).isEqualTo(UPDATED_ORIGINAL_PRICE);
        assertThat(testWechatProduct.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testWechatProduct.isPlatformProduct()).isEqualTo(UPDATED_PLATFORM_PRODUCT);
        assertThat(testWechatProduct.isSellOut()).isEqualTo(UPDATED_SELL_OUT);
        assertThat(testWechatProduct.isGoLive()).isEqualTo(UPDATED_GO_LIVE);
        assertThat(testWechatProduct.getCollectTimes()).isEqualTo(UPDATED_COLLECT_TIMES);
    }

    @Test
    @Transactional
    public void updateNonExistingWechatProduct() throws Exception {
        int databaseSizeBeforeUpdate = wechatProductRepository.findAll().size();

        // Create the WechatProduct
        WechatProductDTO wechatProductDTO = wechatProductMapper.toDto(wechatProduct);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWechatProductMockMvc.perform(put("/api/wechat-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatProductDTO)))
            .andExpect(status().isCreated());

        // Validate the WechatProduct in the database
        List<WechatProduct> wechatProductList = wechatProductRepository.findAll();
        assertThat(wechatProductList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWechatProduct() throws Exception {
        // Initialize the database
        wechatProductRepository.saveAndFlush(wechatProduct);
        int databaseSizeBeforeDelete = wechatProductRepository.findAll().size();

        // Get the wechatProduct
        restWechatProductMockMvc.perform(delete("/api/wechat-products/{id}", wechatProduct.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WechatProduct> wechatProductList = wechatProductRepository.findAll();
        assertThat(wechatProductList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WechatProduct.class);
        WechatProduct wechatProduct1 = new WechatProduct();
        wechatProduct1.setId(1L);
        WechatProduct wechatProduct2 = new WechatProduct();
        wechatProduct2.setId(wechatProduct1.getId());
        assertThat(wechatProduct1).isEqualTo(wechatProduct2);
        wechatProduct2.setId(2L);
        assertThat(wechatProduct1).isNotEqualTo(wechatProduct2);
        wechatProduct1.setId(null);
        assertThat(wechatProduct1).isNotEqualTo(wechatProduct2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WechatProductDTO.class);
        WechatProductDTO wechatProductDTO1 = new WechatProductDTO();
        wechatProductDTO1.setId(1L);
        WechatProductDTO wechatProductDTO2 = new WechatProductDTO();
        assertThat(wechatProductDTO1).isNotEqualTo(wechatProductDTO2);
        wechatProductDTO2.setId(wechatProductDTO1.getId());
        assertThat(wechatProductDTO1).isEqualTo(wechatProductDTO2);
        wechatProductDTO2.setId(2L);
        assertThat(wechatProductDTO1).isNotEqualTo(wechatProductDTO2);
        wechatProductDTO1.setId(null);
        assertThat(wechatProductDTO1).isNotEqualTo(wechatProductDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(wechatProductMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(wechatProductMapper.fromId(null)).isNull();
    }
}
