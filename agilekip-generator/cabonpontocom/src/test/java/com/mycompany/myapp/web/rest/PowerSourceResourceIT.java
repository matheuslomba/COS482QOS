package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PowerSource;
import com.mycompany.myapp.repository.PowerSourceRepository;
import com.mycompany.myapp.service.dto.PowerSourceDTO;
import com.mycompany.myapp.service.mapper.PowerSourceMapper;
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
 * Integration tests for the {@link PowerSourceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PowerSourceResourceIT {

    private static final String DEFAULT_POWER_SOURCE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_POWER_SOURCE_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_POWER_SOURCE_PRICE = 1;
    private static final Integer UPDATED_POWER_SOURCE_PRICE = 2;

    private static final String ENTITY_API_URL = "/api/power-sources";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PowerSourceRepository powerSourceRepository;

    @Autowired
    private PowerSourceMapper powerSourceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPowerSourceMockMvc;

    private PowerSource powerSource;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PowerSource createEntity(EntityManager em) {
        PowerSource powerSource = new PowerSource().powerSourceName(DEFAULT_POWER_SOURCE_NAME).powerSourcePrice(DEFAULT_POWER_SOURCE_PRICE);
        return powerSource;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PowerSource createUpdatedEntity(EntityManager em) {
        PowerSource powerSource = new PowerSource().powerSourceName(UPDATED_POWER_SOURCE_NAME).powerSourcePrice(UPDATED_POWER_SOURCE_PRICE);
        return powerSource;
    }

    @BeforeEach
    public void initTest() {
        powerSource = createEntity(em);
    }

    @Test
    @Transactional
    void createPowerSource() throws Exception {
        int databaseSizeBeforeCreate = powerSourceRepository.findAll().size();
        // Create the PowerSource
        PowerSourceDTO powerSourceDTO = powerSourceMapper.toDto(powerSource);
        restPowerSourceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(powerSourceDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PowerSource in the database
        List<PowerSource> powerSourceList = powerSourceRepository.findAll();
        assertThat(powerSourceList).hasSize(databaseSizeBeforeCreate + 1);
        PowerSource testPowerSource = powerSourceList.get(powerSourceList.size() - 1);
        assertThat(testPowerSource.getPowerSourceName()).isEqualTo(DEFAULT_POWER_SOURCE_NAME);
        assertThat(testPowerSource.getPowerSourcePrice()).isEqualTo(DEFAULT_POWER_SOURCE_PRICE);
    }

    @Test
    @Transactional
    void createPowerSourceWithExistingId() throws Exception {
        // Create the PowerSource with an existing ID
        powerSource.setId(1L);
        PowerSourceDTO powerSourceDTO = powerSourceMapper.toDto(powerSource);

        int databaseSizeBeforeCreate = powerSourceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPowerSourceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(powerSourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PowerSource in the database
        List<PowerSource> powerSourceList = powerSourceRepository.findAll();
        assertThat(powerSourceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPowerSources() throws Exception {
        // Initialize the database
        powerSourceRepository.saveAndFlush(powerSource);

        // Get all the powerSourceList
        restPowerSourceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(powerSource.getId().intValue())))
            .andExpect(jsonPath("$.[*].powerSourceName").value(hasItem(DEFAULT_POWER_SOURCE_NAME)))
            .andExpect(jsonPath("$.[*].powerSourcePrice").value(hasItem(DEFAULT_POWER_SOURCE_PRICE)));
    }

    @Test
    @Transactional
    void getPowerSource() throws Exception {
        // Initialize the database
        powerSourceRepository.saveAndFlush(powerSource);

        // Get the powerSource
        restPowerSourceMockMvc
            .perform(get(ENTITY_API_URL_ID, powerSource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(powerSource.getId().intValue()))
            .andExpect(jsonPath("$.powerSourceName").value(DEFAULT_POWER_SOURCE_NAME))
            .andExpect(jsonPath("$.powerSourcePrice").value(DEFAULT_POWER_SOURCE_PRICE));
    }

    @Test
    @Transactional
    void getNonExistingPowerSource() throws Exception {
        // Get the powerSource
        restPowerSourceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPowerSource() throws Exception {
        // Initialize the database
        powerSourceRepository.saveAndFlush(powerSource);

        int databaseSizeBeforeUpdate = powerSourceRepository.findAll().size();

        // Update the powerSource
        PowerSource updatedPowerSource = powerSourceRepository.findById(powerSource.getId()).get();
        // Disconnect from session so that the updates on updatedPowerSource are not directly saved in db
        em.detach(updatedPowerSource);
        updatedPowerSource.powerSourceName(UPDATED_POWER_SOURCE_NAME).powerSourcePrice(UPDATED_POWER_SOURCE_PRICE);
        PowerSourceDTO powerSourceDTO = powerSourceMapper.toDto(updatedPowerSource);

        restPowerSourceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, powerSourceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(powerSourceDTO))
            )
            .andExpect(status().isOk());

        // Validate the PowerSource in the database
        List<PowerSource> powerSourceList = powerSourceRepository.findAll();
        assertThat(powerSourceList).hasSize(databaseSizeBeforeUpdate);
        PowerSource testPowerSource = powerSourceList.get(powerSourceList.size() - 1);
        assertThat(testPowerSource.getPowerSourceName()).isEqualTo(UPDATED_POWER_SOURCE_NAME);
        assertThat(testPowerSource.getPowerSourcePrice()).isEqualTo(UPDATED_POWER_SOURCE_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingPowerSource() throws Exception {
        int databaseSizeBeforeUpdate = powerSourceRepository.findAll().size();
        powerSource.setId(count.incrementAndGet());

        // Create the PowerSource
        PowerSourceDTO powerSourceDTO = powerSourceMapper.toDto(powerSource);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPowerSourceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, powerSourceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(powerSourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PowerSource in the database
        List<PowerSource> powerSourceList = powerSourceRepository.findAll();
        assertThat(powerSourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPowerSource() throws Exception {
        int databaseSizeBeforeUpdate = powerSourceRepository.findAll().size();
        powerSource.setId(count.incrementAndGet());

        // Create the PowerSource
        PowerSourceDTO powerSourceDTO = powerSourceMapper.toDto(powerSource);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPowerSourceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(powerSourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PowerSource in the database
        List<PowerSource> powerSourceList = powerSourceRepository.findAll();
        assertThat(powerSourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPowerSource() throws Exception {
        int databaseSizeBeforeUpdate = powerSourceRepository.findAll().size();
        powerSource.setId(count.incrementAndGet());

        // Create the PowerSource
        PowerSourceDTO powerSourceDTO = powerSourceMapper.toDto(powerSource);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPowerSourceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(powerSourceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PowerSource in the database
        List<PowerSource> powerSourceList = powerSourceRepository.findAll();
        assertThat(powerSourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePowerSourceWithPatch() throws Exception {
        // Initialize the database
        powerSourceRepository.saveAndFlush(powerSource);

        int databaseSizeBeforeUpdate = powerSourceRepository.findAll().size();

        // Update the powerSource using partial update
        PowerSource partialUpdatedPowerSource = new PowerSource();
        partialUpdatedPowerSource.setId(powerSource.getId());

        restPowerSourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPowerSource.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPowerSource))
            )
            .andExpect(status().isOk());

        // Validate the PowerSource in the database
        List<PowerSource> powerSourceList = powerSourceRepository.findAll();
        assertThat(powerSourceList).hasSize(databaseSizeBeforeUpdate);
        PowerSource testPowerSource = powerSourceList.get(powerSourceList.size() - 1);
        assertThat(testPowerSource.getPowerSourceName()).isEqualTo(DEFAULT_POWER_SOURCE_NAME);
        assertThat(testPowerSource.getPowerSourcePrice()).isEqualTo(DEFAULT_POWER_SOURCE_PRICE);
    }

    @Test
    @Transactional
    void fullUpdatePowerSourceWithPatch() throws Exception {
        // Initialize the database
        powerSourceRepository.saveAndFlush(powerSource);

        int databaseSizeBeforeUpdate = powerSourceRepository.findAll().size();

        // Update the powerSource using partial update
        PowerSource partialUpdatedPowerSource = new PowerSource();
        partialUpdatedPowerSource.setId(powerSource.getId());

        partialUpdatedPowerSource.powerSourceName(UPDATED_POWER_SOURCE_NAME).powerSourcePrice(UPDATED_POWER_SOURCE_PRICE);

        restPowerSourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPowerSource.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPowerSource))
            )
            .andExpect(status().isOk());

        // Validate the PowerSource in the database
        List<PowerSource> powerSourceList = powerSourceRepository.findAll();
        assertThat(powerSourceList).hasSize(databaseSizeBeforeUpdate);
        PowerSource testPowerSource = powerSourceList.get(powerSourceList.size() - 1);
        assertThat(testPowerSource.getPowerSourceName()).isEqualTo(UPDATED_POWER_SOURCE_NAME);
        assertThat(testPowerSource.getPowerSourcePrice()).isEqualTo(UPDATED_POWER_SOURCE_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingPowerSource() throws Exception {
        int databaseSizeBeforeUpdate = powerSourceRepository.findAll().size();
        powerSource.setId(count.incrementAndGet());

        // Create the PowerSource
        PowerSourceDTO powerSourceDTO = powerSourceMapper.toDto(powerSource);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPowerSourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, powerSourceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(powerSourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PowerSource in the database
        List<PowerSource> powerSourceList = powerSourceRepository.findAll();
        assertThat(powerSourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPowerSource() throws Exception {
        int databaseSizeBeforeUpdate = powerSourceRepository.findAll().size();
        powerSource.setId(count.incrementAndGet());

        // Create the PowerSource
        PowerSourceDTO powerSourceDTO = powerSourceMapper.toDto(powerSource);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPowerSourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(powerSourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PowerSource in the database
        List<PowerSource> powerSourceList = powerSourceRepository.findAll();
        assertThat(powerSourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPowerSource() throws Exception {
        int databaseSizeBeforeUpdate = powerSourceRepository.findAll().size();
        powerSource.setId(count.incrementAndGet());

        // Create the PowerSource
        PowerSourceDTO powerSourceDTO = powerSourceMapper.toDto(powerSource);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPowerSourceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(powerSourceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PowerSource in the database
        List<PowerSource> powerSourceList = powerSourceRepository.findAll();
        assertThat(powerSourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePowerSource() throws Exception {
        // Initialize the database
        powerSourceRepository.saveAndFlush(powerSource);

        int databaseSizeBeforeDelete = powerSourceRepository.findAll().size();

        // Delete the powerSource
        restPowerSourceMockMvc
            .perform(delete(ENTITY_API_URL_ID, powerSource.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PowerSource> powerSourceList = powerSourceRepository.findAll();
        assertThat(powerSourceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
