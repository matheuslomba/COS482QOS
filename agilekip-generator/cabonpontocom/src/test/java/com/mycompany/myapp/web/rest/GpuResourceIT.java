package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Gpu;
import com.mycompany.myapp.repository.GpuRepository;
import com.mycompany.myapp.service.dto.GpuDTO;
import com.mycompany.myapp.service.mapper.GpuMapper;
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
 * Integration tests for the {@link GpuResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GpuResourceIT {

    private static final String DEFAULT_GPU_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GPU_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_GPU_PRICE = 1;
    private static final Integer UPDATED_GPU_PRICE = 2;

    private static final String ENTITY_API_URL = "/api/gpus";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GpuRepository gpuRepository;

    @Autowired
    private GpuMapper gpuMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGpuMockMvc;

    private Gpu gpu;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gpu createEntity(EntityManager em) {
        Gpu gpu = new Gpu().gpuName(DEFAULT_GPU_NAME).gpuPrice(DEFAULT_GPU_PRICE);
        return gpu;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gpu createUpdatedEntity(EntityManager em) {
        Gpu gpu = new Gpu().gpuName(UPDATED_GPU_NAME).gpuPrice(UPDATED_GPU_PRICE);
        return gpu;
    }

    @BeforeEach
    public void initTest() {
        gpu = createEntity(em);
    }

    @Test
    @Transactional
    void createGpu() throws Exception {
        int databaseSizeBeforeCreate = gpuRepository.findAll().size();
        // Create the Gpu
        GpuDTO gpuDTO = gpuMapper.toDto(gpu);
        restGpuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gpuDTO)))
            .andExpect(status().isCreated());

        // Validate the Gpu in the database
        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeCreate + 1);
        Gpu testGpu = gpuList.get(gpuList.size() - 1);
        assertThat(testGpu.getGpuName()).isEqualTo(DEFAULT_GPU_NAME);
        assertThat(testGpu.getGpuPrice()).isEqualTo(DEFAULT_GPU_PRICE);
    }

    @Test
    @Transactional
    void createGpuWithExistingId() throws Exception {
        // Create the Gpu with an existing ID
        gpu.setId(1L);
        GpuDTO gpuDTO = gpuMapper.toDto(gpu);

        int databaseSizeBeforeCreate = gpuRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGpuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gpuDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Gpu in the database
        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGpus() throws Exception {
        // Initialize the database
        gpuRepository.saveAndFlush(gpu);

        // Get all the gpuList
        restGpuMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gpu.getId().intValue())))
            .andExpect(jsonPath("$.[*].gpuName").value(hasItem(DEFAULT_GPU_NAME)))
            .andExpect(jsonPath("$.[*].gpuPrice").value(hasItem(DEFAULT_GPU_PRICE)));
    }

    @Test
    @Transactional
    void getGpu() throws Exception {
        // Initialize the database
        gpuRepository.saveAndFlush(gpu);

        // Get the gpu
        restGpuMockMvc
            .perform(get(ENTITY_API_URL_ID, gpu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gpu.getId().intValue()))
            .andExpect(jsonPath("$.gpuName").value(DEFAULT_GPU_NAME))
            .andExpect(jsonPath("$.gpuPrice").value(DEFAULT_GPU_PRICE));
    }

    @Test
    @Transactional
    void getNonExistingGpu() throws Exception {
        // Get the gpu
        restGpuMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGpu() throws Exception {
        // Initialize the database
        gpuRepository.saveAndFlush(gpu);

        int databaseSizeBeforeUpdate = gpuRepository.findAll().size();

        // Update the gpu
        Gpu updatedGpu = gpuRepository.findById(gpu.getId()).get();
        // Disconnect from session so that the updates on updatedGpu are not directly saved in db
        em.detach(updatedGpu);
        updatedGpu.gpuName(UPDATED_GPU_NAME).gpuPrice(UPDATED_GPU_PRICE);
        GpuDTO gpuDTO = gpuMapper.toDto(updatedGpu);

        restGpuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gpuDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gpuDTO))
            )
            .andExpect(status().isOk());

        // Validate the Gpu in the database
        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeUpdate);
        Gpu testGpu = gpuList.get(gpuList.size() - 1);
        assertThat(testGpu.getGpuName()).isEqualTo(UPDATED_GPU_NAME);
        assertThat(testGpu.getGpuPrice()).isEqualTo(UPDATED_GPU_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingGpu() throws Exception {
        int databaseSizeBeforeUpdate = gpuRepository.findAll().size();
        gpu.setId(count.incrementAndGet());

        // Create the Gpu
        GpuDTO gpuDTO = gpuMapper.toDto(gpu);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGpuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gpuDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gpuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gpu in the database
        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGpu() throws Exception {
        int databaseSizeBeforeUpdate = gpuRepository.findAll().size();
        gpu.setId(count.incrementAndGet());

        // Create the Gpu
        GpuDTO gpuDTO = gpuMapper.toDto(gpu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGpuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gpuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gpu in the database
        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGpu() throws Exception {
        int databaseSizeBeforeUpdate = gpuRepository.findAll().size();
        gpu.setId(count.incrementAndGet());

        // Create the Gpu
        GpuDTO gpuDTO = gpuMapper.toDto(gpu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGpuMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gpuDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gpu in the database
        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGpuWithPatch() throws Exception {
        // Initialize the database
        gpuRepository.saveAndFlush(gpu);

        int databaseSizeBeforeUpdate = gpuRepository.findAll().size();

        // Update the gpu using partial update
        Gpu partialUpdatedGpu = new Gpu();
        partialUpdatedGpu.setId(gpu.getId());

        partialUpdatedGpu.gpuPrice(UPDATED_GPU_PRICE);

        restGpuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGpu.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGpu))
            )
            .andExpect(status().isOk());

        // Validate the Gpu in the database
        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeUpdate);
        Gpu testGpu = gpuList.get(gpuList.size() - 1);
        assertThat(testGpu.getGpuName()).isEqualTo(DEFAULT_GPU_NAME);
        assertThat(testGpu.getGpuPrice()).isEqualTo(UPDATED_GPU_PRICE);
    }

    @Test
    @Transactional
    void fullUpdateGpuWithPatch() throws Exception {
        // Initialize the database
        gpuRepository.saveAndFlush(gpu);

        int databaseSizeBeforeUpdate = gpuRepository.findAll().size();

        // Update the gpu using partial update
        Gpu partialUpdatedGpu = new Gpu();
        partialUpdatedGpu.setId(gpu.getId());

        partialUpdatedGpu.gpuName(UPDATED_GPU_NAME).gpuPrice(UPDATED_GPU_PRICE);

        restGpuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGpu.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGpu))
            )
            .andExpect(status().isOk());

        // Validate the Gpu in the database
        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeUpdate);
        Gpu testGpu = gpuList.get(gpuList.size() - 1);
        assertThat(testGpu.getGpuName()).isEqualTo(UPDATED_GPU_NAME);
        assertThat(testGpu.getGpuPrice()).isEqualTo(UPDATED_GPU_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingGpu() throws Exception {
        int databaseSizeBeforeUpdate = gpuRepository.findAll().size();
        gpu.setId(count.incrementAndGet());

        // Create the Gpu
        GpuDTO gpuDTO = gpuMapper.toDto(gpu);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGpuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gpuDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gpuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gpu in the database
        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGpu() throws Exception {
        int databaseSizeBeforeUpdate = gpuRepository.findAll().size();
        gpu.setId(count.incrementAndGet());

        // Create the Gpu
        GpuDTO gpuDTO = gpuMapper.toDto(gpu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGpuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gpuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gpu in the database
        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGpu() throws Exception {
        int databaseSizeBeforeUpdate = gpuRepository.findAll().size();
        gpu.setId(count.incrementAndGet());

        // Create the Gpu
        GpuDTO gpuDTO = gpuMapper.toDto(gpu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGpuMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(gpuDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gpu in the database
        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGpu() throws Exception {
        // Initialize the database
        gpuRepository.saveAndFlush(gpu);

        int databaseSizeBeforeDelete = gpuRepository.findAll().size();

        // Delete the gpu
        restGpuMockMvc.perform(delete(ENTITY_API_URL_ID, gpu.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
