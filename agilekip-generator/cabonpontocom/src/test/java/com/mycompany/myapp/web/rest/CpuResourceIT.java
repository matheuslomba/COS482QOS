package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Cpu;
import com.mycompany.myapp.repository.CpuRepository;
import com.mycompany.myapp.service.dto.CpuDTO;
import com.mycompany.myapp.service.mapper.CpuMapper;
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
 * Integration tests for the {@link CpuResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CpuResourceIT {

    private static final String DEFAULT_CPU_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CPU_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_CPU_PRICE = 1;
    private static final Integer UPDATED_CPU_PRICE = 2;

    private static final String ENTITY_API_URL = "/api/cpus";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CpuRepository cpuRepository;

    @Autowired
    private CpuMapper cpuMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCpuMockMvc;

    private Cpu cpu;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cpu createEntity(EntityManager em) {
        Cpu cpu = new Cpu().cpuName(DEFAULT_CPU_NAME).cpuPrice(DEFAULT_CPU_PRICE);
        return cpu;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cpu createUpdatedEntity(EntityManager em) {
        Cpu cpu = new Cpu().cpuName(UPDATED_CPU_NAME).cpuPrice(UPDATED_CPU_PRICE);
        return cpu;
    }

    @BeforeEach
    public void initTest() {
        cpu = createEntity(em);
    }

    @Test
    @Transactional
    void createCpu() throws Exception {
        int databaseSizeBeforeCreate = cpuRepository.findAll().size();
        // Create the Cpu
        CpuDTO cpuDTO = cpuMapper.toDto(cpu);
        restCpuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cpuDTO)))
            .andExpect(status().isCreated());

        // Validate the Cpu in the database
        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeCreate + 1);
        Cpu testCpu = cpuList.get(cpuList.size() - 1);
        assertThat(testCpu.getCpuName()).isEqualTo(DEFAULT_CPU_NAME);
        assertThat(testCpu.getCpuPrice()).isEqualTo(DEFAULT_CPU_PRICE);
    }

    @Test
    @Transactional
    void createCpuWithExistingId() throws Exception {
        // Create the Cpu with an existing ID
        cpu.setId(1L);
        CpuDTO cpuDTO = cpuMapper.toDto(cpu);

        int databaseSizeBeforeCreate = cpuRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCpuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cpuDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cpu in the database
        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCpus() throws Exception {
        // Initialize the database
        cpuRepository.saveAndFlush(cpu);

        // Get all the cpuList
        restCpuMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cpu.getId().intValue())))
            .andExpect(jsonPath("$.[*].cpuName").value(hasItem(DEFAULT_CPU_NAME)))
            .andExpect(jsonPath("$.[*].cpuPrice").value(hasItem(DEFAULT_CPU_PRICE)));
    }

    @Test
    @Transactional
    void getCpu() throws Exception {
        // Initialize the database
        cpuRepository.saveAndFlush(cpu);

        // Get the cpu
        restCpuMockMvc
            .perform(get(ENTITY_API_URL_ID, cpu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cpu.getId().intValue()))
            .andExpect(jsonPath("$.cpuName").value(DEFAULT_CPU_NAME))
            .andExpect(jsonPath("$.cpuPrice").value(DEFAULT_CPU_PRICE));
    }

    @Test
    @Transactional
    void getNonExistingCpu() throws Exception {
        // Get the cpu
        restCpuMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCpu() throws Exception {
        // Initialize the database
        cpuRepository.saveAndFlush(cpu);

        int databaseSizeBeforeUpdate = cpuRepository.findAll().size();

        // Update the cpu
        Cpu updatedCpu = cpuRepository.findById(cpu.getId()).get();
        // Disconnect from session so that the updates on updatedCpu are not directly saved in db
        em.detach(updatedCpu);
        updatedCpu.cpuName(UPDATED_CPU_NAME).cpuPrice(UPDATED_CPU_PRICE);
        CpuDTO cpuDTO = cpuMapper.toDto(updatedCpu);

        restCpuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cpuDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cpuDTO))
            )
            .andExpect(status().isOk());

        // Validate the Cpu in the database
        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeUpdate);
        Cpu testCpu = cpuList.get(cpuList.size() - 1);
        assertThat(testCpu.getCpuName()).isEqualTo(UPDATED_CPU_NAME);
        assertThat(testCpu.getCpuPrice()).isEqualTo(UPDATED_CPU_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingCpu() throws Exception {
        int databaseSizeBeforeUpdate = cpuRepository.findAll().size();
        cpu.setId(count.incrementAndGet());

        // Create the Cpu
        CpuDTO cpuDTO = cpuMapper.toDto(cpu);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCpuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cpuDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cpuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cpu in the database
        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCpu() throws Exception {
        int databaseSizeBeforeUpdate = cpuRepository.findAll().size();
        cpu.setId(count.incrementAndGet());

        // Create the Cpu
        CpuDTO cpuDTO = cpuMapper.toDto(cpu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCpuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cpuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cpu in the database
        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCpu() throws Exception {
        int databaseSizeBeforeUpdate = cpuRepository.findAll().size();
        cpu.setId(count.incrementAndGet());

        // Create the Cpu
        CpuDTO cpuDTO = cpuMapper.toDto(cpu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCpuMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cpuDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cpu in the database
        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCpuWithPatch() throws Exception {
        // Initialize the database
        cpuRepository.saveAndFlush(cpu);

        int databaseSizeBeforeUpdate = cpuRepository.findAll().size();

        // Update the cpu using partial update
        Cpu partialUpdatedCpu = new Cpu();
        partialUpdatedCpu.setId(cpu.getId());

        partialUpdatedCpu.cpuName(UPDATED_CPU_NAME);

        restCpuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCpu.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCpu))
            )
            .andExpect(status().isOk());

        // Validate the Cpu in the database
        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeUpdate);
        Cpu testCpu = cpuList.get(cpuList.size() - 1);
        assertThat(testCpu.getCpuName()).isEqualTo(UPDATED_CPU_NAME);
        assertThat(testCpu.getCpuPrice()).isEqualTo(DEFAULT_CPU_PRICE);
    }

    @Test
    @Transactional
    void fullUpdateCpuWithPatch() throws Exception {
        // Initialize the database
        cpuRepository.saveAndFlush(cpu);

        int databaseSizeBeforeUpdate = cpuRepository.findAll().size();

        // Update the cpu using partial update
        Cpu partialUpdatedCpu = new Cpu();
        partialUpdatedCpu.setId(cpu.getId());

        partialUpdatedCpu.cpuName(UPDATED_CPU_NAME).cpuPrice(UPDATED_CPU_PRICE);

        restCpuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCpu.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCpu))
            )
            .andExpect(status().isOk());

        // Validate the Cpu in the database
        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeUpdate);
        Cpu testCpu = cpuList.get(cpuList.size() - 1);
        assertThat(testCpu.getCpuName()).isEqualTo(UPDATED_CPU_NAME);
        assertThat(testCpu.getCpuPrice()).isEqualTo(UPDATED_CPU_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingCpu() throws Exception {
        int databaseSizeBeforeUpdate = cpuRepository.findAll().size();
        cpu.setId(count.incrementAndGet());

        // Create the Cpu
        CpuDTO cpuDTO = cpuMapper.toDto(cpu);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCpuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cpuDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cpuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cpu in the database
        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCpu() throws Exception {
        int databaseSizeBeforeUpdate = cpuRepository.findAll().size();
        cpu.setId(count.incrementAndGet());

        // Create the Cpu
        CpuDTO cpuDTO = cpuMapper.toDto(cpu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCpuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cpuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cpu in the database
        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCpu() throws Exception {
        int databaseSizeBeforeUpdate = cpuRepository.findAll().size();
        cpu.setId(count.incrementAndGet());

        // Create the Cpu
        CpuDTO cpuDTO = cpuMapper.toDto(cpu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCpuMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cpuDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cpu in the database
        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCpu() throws Exception {
        // Initialize the database
        cpuRepository.saveAndFlush(cpu);

        int databaseSizeBeforeDelete = cpuRepository.findAll().size();

        // Delete the cpu
        restCpuMockMvc.perform(delete(ENTITY_API_URL_ID, cpu.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
