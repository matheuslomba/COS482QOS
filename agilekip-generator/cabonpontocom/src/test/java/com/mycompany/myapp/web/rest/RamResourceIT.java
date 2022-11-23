package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Ram;
import com.mycompany.myapp.repository.RamRepository;
import com.mycompany.myapp.service.dto.RamDTO;
import com.mycompany.myapp.service.mapper.RamMapper;
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
 * Integration tests for the {@link RamResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RamResourceIT {

    private static final String DEFAULT_RAM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RAM_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_RAM_PRICE = 1;
    private static final Integer UPDATED_RAM_PRICE = 2;

    private static final String ENTITY_API_URL = "/api/rams";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RamRepository ramRepository;

    @Autowired
    private RamMapper ramMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRamMockMvc;

    private Ram ram;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ram createEntity(EntityManager em) {
        Ram ram = new Ram().ramName(DEFAULT_RAM_NAME).ramPrice(DEFAULT_RAM_PRICE);
        return ram;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ram createUpdatedEntity(EntityManager em) {
        Ram ram = new Ram().ramName(UPDATED_RAM_NAME).ramPrice(UPDATED_RAM_PRICE);
        return ram;
    }

    @BeforeEach
    public void initTest() {
        ram = createEntity(em);
    }

    @Test
    @Transactional
    void createRam() throws Exception {
        int databaseSizeBeforeCreate = ramRepository.findAll().size();
        // Create the Ram
        RamDTO ramDTO = ramMapper.toDto(ram);
        restRamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ramDTO)))
            .andExpect(status().isCreated());

        // Validate the Ram in the database
        List<Ram> ramList = ramRepository.findAll();
        assertThat(ramList).hasSize(databaseSizeBeforeCreate + 1);
        Ram testRam = ramList.get(ramList.size() - 1);
        assertThat(testRam.getRamName()).isEqualTo(DEFAULT_RAM_NAME);
        assertThat(testRam.getRamPrice()).isEqualTo(DEFAULT_RAM_PRICE);
    }

    @Test
    @Transactional
    void createRamWithExistingId() throws Exception {
        // Create the Ram with an existing ID
        ram.setId(1L);
        RamDTO ramDTO = ramMapper.toDto(ram);

        int databaseSizeBeforeCreate = ramRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ramDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ram in the database
        List<Ram> ramList = ramRepository.findAll();
        assertThat(ramList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRams() throws Exception {
        // Initialize the database
        ramRepository.saveAndFlush(ram);

        // Get all the ramList
        restRamMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ram.getId().intValue())))
            .andExpect(jsonPath("$.[*].ramName").value(hasItem(DEFAULT_RAM_NAME)))
            .andExpect(jsonPath("$.[*].ramPrice").value(hasItem(DEFAULT_RAM_PRICE)));
    }

    @Test
    @Transactional
    void getRam() throws Exception {
        // Initialize the database
        ramRepository.saveAndFlush(ram);

        // Get the ram
        restRamMockMvc
            .perform(get(ENTITY_API_URL_ID, ram.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ram.getId().intValue()))
            .andExpect(jsonPath("$.ramName").value(DEFAULT_RAM_NAME))
            .andExpect(jsonPath("$.ramPrice").value(DEFAULT_RAM_PRICE));
    }

    @Test
    @Transactional
    void getNonExistingRam() throws Exception {
        // Get the ram
        restRamMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRam() throws Exception {
        // Initialize the database
        ramRepository.saveAndFlush(ram);

        int databaseSizeBeforeUpdate = ramRepository.findAll().size();

        // Update the ram
        Ram updatedRam = ramRepository.findById(ram.getId()).get();
        // Disconnect from session so that the updates on updatedRam are not directly saved in db
        em.detach(updatedRam);
        updatedRam.ramName(UPDATED_RAM_NAME).ramPrice(UPDATED_RAM_PRICE);
        RamDTO ramDTO = ramMapper.toDto(updatedRam);

        restRamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ramDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ramDTO))
            )
            .andExpect(status().isOk());

        // Validate the Ram in the database
        List<Ram> ramList = ramRepository.findAll();
        assertThat(ramList).hasSize(databaseSizeBeforeUpdate);
        Ram testRam = ramList.get(ramList.size() - 1);
        assertThat(testRam.getRamName()).isEqualTo(UPDATED_RAM_NAME);
        assertThat(testRam.getRamPrice()).isEqualTo(UPDATED_RAM_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingRam() throws Exception {
        int databaseSizeBeforeUpdate = ramRepository.findAll().size();
        ram.setId(count.incrementAndGet());

        // Create the Ram
        RamDTO ramDTO = ramMapper.toDto(ram);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ramDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ramDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ram in the database
        List<Ram> ramList = ramRepository.findAll();
        assertThat(ramList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRam() throws Exception {
        int databaseSizeBeforeUpdate = ramRepository.findAll().size();
        ram.setId(count.incrementAndGet());

        // Create the Ram
        RamDTO ramDTO = ramMapper.toDto(ram);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ramDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ram in the database
        List<Ram> ramList = ramRepository.findAll();
        assertThat(ramList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRam() throws Exception {
        int databaseSizeBeforeUpdate = ramRepository.findAll().size();
        ram.setId(count.incrementAndGet());

        // Create the Ram
        RamDTO ramDTO = ramMapper.toDto(ram);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRamMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ramDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ram in the database
        List<Ram> ramList = ramRepository.findAll();
        assertThat(ramList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRamWithPatch() throws Exception {
        // Initialize the database
        ramRepository.saveAndFlush(ram);

        int databaseSizeBeforeUpdate = ramRepository.findAll().size();

        // Update the ram using partial update
        Ram partialUpdatedRam = new Ram();
        partialUpdatedRam.setId(ram.getId());

        partialUpdatedRam.ramName(UPDATED_RAM_NAME);

        restRamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRam.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRam))
            )
            .andExpect(status().isOk());

        // Validate the Ram in the database
        List<Ram> ramList = ramRepository.findAll();
        assertThat(ramList).hasSize(databaseSizeBeforeUpdate);
        Ram testRam = ramList.get(ramList.size() - 1);
        assertThat(testRam.getRamName()).isEqualTo(UPDATED_RAM_NAME);
        assertThat(testRam.getRamPrice()).isEqualTo(DEFAULT_RAM_PRICE);
    }

    @Test
    @Transactional
    void fullUpdateRamWithPatch() throws Exception {
        // Initialize the database
        ramRepository.saveAndFlush(ram);

        int databaseSizeBeforeUpdate = ramRepository.findAll().size();

        // Update the ram using partial update
        Ram partialUpdatedRam = new Ram();
        partialUpdatedRam.setId(ram.getId());

        partialUpdatedRam.ramName(UPDATED_RAM_NAME).ramPrice(UPDATED_RAM_PRICE);

        restRamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRam.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRam))
            )
            .andExpect(status().isOk());

        // Validate the Ram in the database
        List<Ram> ramList = ramRepository.findAll();
        assertThat(ramList).hasSize(databaseSizeBeforeUpdate);
        Ram testRam = ramList.get(ramList.size() - 1);
        assertThat(testRam.getRamName()).isEqualTo(UPDATED_RAM_NAME);
        assertThat(testRam.getRamPrice()).isEqualTo(UPDATED_RAM_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingRam() throws Exception {
        int databaseSizeBeforeUpdate = ramRepository.findAll().size();
        ram.setId(count.incrementAndGet());

        // Create the Ram
        RamDTO ramDTO = ramMapper.toDto(ram);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ramDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ramDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ram in the database
        List<Ram> ramList = ramRepository.findAll();
        assertThat(ramList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRam() throws Exception {
        int databaseSizeBeforeUpdate = ramRepository.findAll().size();
        ram.setId(count.incrementAndGet());

        // Create the Ram
        RamDTO ramDTO = ramMapper.toDto(ram);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ramDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ram in the database
        List<Ram> ramList = ramRepository.findAll();
        assertThat(ramList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRam() throws Exception {
        int databaseSizeBeforeUpdate = ramRepository.findAll().size();
        ram.setId(count.incrementAndGet());

        // Create the Ram
        RamDTO ramDTO = ramMapper.toDto(ram);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRamMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ramDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ram in the database
        List<Ram> ramList = ramRepository.findAll();
        assertThat(ramList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRam() throws Exception {
        // Initialize the database
        ramRepository.saveAndFlush(ram);

        int databaseSizeBeforeDelete = ramRepository.findAll().size();

        // Delete the ram
        restRamMockMvc.perform(delete(ENTITY_API_URL_ID, ram.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ram> ramList = ramRepository.findAll();
        assertThat(ramList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
