package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ClientOrder;
import com.mycompany.myapp.repository.ClientOrderRepository;
import com.mycompany.myapp.service.dto.ClientOrderDTO;
import com.mycompany.myapp.service.mapper.ClientOrderMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ClientOrderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClientOrderResourceIT {

    private static final String DEFAULT_ORDER_ID = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ORDER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORDER_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CLIENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER_PRICE = 1;
    private static final Integer UPDATED_ORDER_PRICE = 2;

    private static final Integer DEFAULT_NUM_COMPONENTS = 1;
    private static final Integer UPDATED_NUM_COMPONENTS = 2;

    private static final String DEFAULT_PAYMENT = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PROCEED_TO_CHECKOUT = false;
    private static final Boolean UPDATED_PROCEED_TO_CHECKOUT = true;

    private static final Boolean DEFAULT_ASSEMBLY_PC = false;
    private static final Boolean UPDATED_ASSEMBLY_PC = true;

    private static final String DEFAULT_DELIVERY_ADD = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_ADD = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_COMPATIBLE = false;
    private static final Boolean UPDATED_IS_COMPATIBLE = true;

    private static final String ENTITY_API_URL = "/api/client-orders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClientOrderRepository clientOrderRepository;

    @Autowired
    private ClientOrderMapper clientOrderMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientOrderMockMvc;

    private ClientOrder clientOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientOrder createEntity(EntityManager em) {
        ClientOrder clientOrder = new ClientOrder()
            .orderID(DEFAULT_ORDER_ID)
            .orderDate(DEFAULT_ORDER_DATE)
            .clientName(DEFAULT_CLIENT_NAME)
            .orderPrice(DEFAULT_ORDER_PRICE)
            .numComponents(DEFAULT_NUM_COMPONENTS)
            .payment(DEFAULT_PAYMENT)
            .proceedToCheckout(DEFAULT_PROCEED_TO_CHECKOUT)
            .assemblyPC(DEFAULT_ASSEMBLY_PC)
            .deliveryAdd(DEFAULT_DELIVERY_ADD)
            .isCompatible(DEFAULT_IS_COMPATIBLE);
        return clientOrder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientOrder createUpdatedEntity(EntityManager em) {
        ClientOrder clientOrder = new ClientOrder()
            .orderID(UPDATED_ORDER_ID)
            .orderDate(UPDATED_ORDER_DATE)
            .clientName(UPDATED_CLIENT_NAME)
            .orderPrice(UPDATED_ORDER_PRICE)
            .numComponents(UPDATED_NUM_COMPONENTS)
            .payment(UPDATED_PAYMENT)
            .proceedToCheckout(UPDATED_PROCEED_TO_CHECKOUT)
            .assemblyPC(UPDATED_ASSEMBLY_PC)
            .deliveryAdd(UPDATED_DELIVERY_ADD)
            .isCompatible(UPDATED_IS_COMPATIBLE);
        return clientOrder;
    }

    @BeforeEach
    public void initTest() {
        clientOrder = createEntity(em);
    }

    @Test
    @Transactional
    void getAllClientOrders() throws Exception {
        // Initialize the database
        clientOrderRepository.saveAndFlush(clientOrder);

        // Get all the clientOrderList
        restClientOrderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderID").value(hasItem(DEFAULT_ORDER_ID)))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].clientName").value(hasItem(DEFAULT_CLIENT_NAME)))
            .andExpect(jsonPath("$.[*].orderPrice").value(hasItem(DEFAULT_ORDER_PRICE)))
            .andExpect(jsonPath("$.[*].numComponents").value(hasItem(DEFAULT_NUM_COMPONENTS)))
            .andExpect(jsonPath("$.[*].payment").value(hasItem(DEFAULT_PAYMENT)))
            .andExpect(jsonPath("$.[*].proceedToCheckout").value(hasItem(DEFAULT_PROCEED_TO_CHECKOUT.booleanValue())))
            .andExpect(jsonPath("$.[*].assemblyPC").value(hasItem(DEFAULT_ASSEMBLY_PC.booleanValue())))
            .andExpect(jsonPath("$.[*].deliveryAdd").value(hasItem(DEFAULT_DELIVERY_ADD)))
            .andExpect(jsonPath("$.[*].isCompatible").value(hasItem(DEFAULT_IS_COMPATIBLE.booleanValue())));
    }

    @Test
    @Transactional
    void getClientOrder() throws Exception {
        // Initialize the database
        clientOrderRepository.saveAndFlush(clientOrder);

        // Get the clientOrder
        restClientOrderMockMvc
            .perform(get(ENTITY_API_URL_ID, clientOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clientOrder.getId().intValue()))
            .andExpect(jsonPath("$.orderID").value(DEFAULT_ORDER_ID))
            .andExpect(jsonPath("$.orderDate").value(DEFAULT_ORDER_DATE.toString()))
            .andExpect(jsonPath("$.clientName").value(DEFAULT_CLIENT_NAME))
            .andExpect(jsonPath("$.orderPrice").value(DEFAULT_ORDER_PRICE))
            .andExpect(jsonPath("$.numComponents").value(DEFAULT_NUM_COMPONENTS))
            .andExpect(jsonPath("$.payment").value(DEFAULT_PAYMENT))
            .andExpect(jsonPath("$.proceedToCheckout").value(DEFAULT_PROCEED_TO_CHECKOUT.booleanValue()))
            .andExpect(jsonPath("$.assemblyPC").value(DEFAULT_ASSEMBLY_PC.booleanValue()))
            .andExpect(jsonPath("$.deliveryAdd").value(DEFAULT_DELIVERY_ADD))
            .andExpect(jsonPath("$.isCompatible").value(DEFAULT_IS_COMPATIBLE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingClientOrder() throws Exception {
        // Get the clientOrder
        restClientOrderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }
}
