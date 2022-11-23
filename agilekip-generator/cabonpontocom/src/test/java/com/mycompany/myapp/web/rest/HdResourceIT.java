package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Hd;
import com.mycompany.myapp.repository.HdRepository;
import com.mycompany.myapp.service.dto.HdDTO;
import com.mycompany.myapp.service.mapper.HdMapper;
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
 * Integration tests for the {@link HdResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HdResourceIT {

    private static final String DEFAULT_HD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_HD_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_HD_PRICE = 1;
    private static final Integer UPDATED_HD_PRICE = 2;

    private static final String ENTITY_API_URL = "/api/hds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HdRepository hdRepository;

    @Autowired
    private HdMapper hdMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHdMockMvc;

    private Hd hd;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hd createEntity(EntityManager em) {
        Hd hd = new Hd().hdName(DEFAULT_HD_NAME).hdPrice(DEFAULT_HD_PRICE);
        return hd;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hd createUpdatedEntity(EntityManager em) {
        Hd hd = new Hd().hdName(UPDATED_HD_NAME).hdPrice(UPDATED_HD_PRICE);
        return hd;
    }

    @BeforeEach
    public void initTest() {
        hd = createEntity(em);
    }

    @Test
    @Transactional
    void createHd() throws Exception {
        int databaseSizeBeforeCreate = hdRepository.findAll().size();
        // Create the Hd
        HdDTO hdDTO = hdMapper.toDto(hd);
        restHdMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hdDTO)))
            .andExpect(status().isCreated());

        // Validate the Hd in the database
        List<Hd> hdList = hdRepository.findAll();
        assertThat(hdList).hasSize(databaseSizeBeforeCreate + 1);
        Hd testHd = hdList.get(hdList.size() - 1);
        assertThat(testHd.getHdName()).isEqualTo(DEFAULT_HD_NAME);
        assertThat(testHd.getHdPrice()).isEqualTo(DEFAULT_HD_PRICE);
    }

    @Test
    @Transactional
    void createHdWithExistingId() throws Exception {
        // Create the Hd with an existing ID
        hd.setId(1L);
        HdDTO hdDTO = hdMapper.toDto(hd);

        int databaseSizeBeforeCreate = hdRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHdMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hdDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Hd in the database
        List<Hd> hdList = hdRepository.findAll();
        assertThat(hdList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHds() throws Exception {
        // Initialize the database
        hdRepository.saveAndFlush(hd);

        // Get all the hdList
        restHdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hd.getId().intValue())))
            .andExpect(jsonPath("$.[*].hdName").value(hasItem(DEFAULT_HD_NAME)))
            .andExpect(jsonPath("$.[*].hdPrice").value(hasItem(DEFAULT_HD_PRICE)));
    }

    @Test
    @Transactional
    void getHd() throws Exception {
        // Initialize the database
        hdRepository.saveAndFlush(hd);

        // Get the hd
        restHdMockMvc
            .perform(get(ENTITY_API_URL_ID, hd.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hd.getId().intValue()))
            .andExpect(jsonPath("$.hdName").value(DEFAULT_HD_NAME))
            .andExpect(jsonPath("$.hdPrice").value(DEFAULT_HD_PRICE));
    }

    @Test
    @Transactional
    void getNonExistingHd() throws Exception {
        // Get the hd
        restHdMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHd() throws Exception {
        // Initialize the database
        hdRepository.saveAndFlush(hd);

        int databaseSizeBeforeUpdate = hdRepository.findAll().size();

        // Update the hd
        Hd updatedHd = hdRepository.findById(hd.getId()).get();
        // Disconnect from session so that the updates on updatedHd are not directly saved in db
        em.detach(updatedHd);
        updatedHd.hdName(UPDATED_HD_NAME).hdPrice(UPDATED_HD_PRICE);
        HdDTO hdDTO = hdMapper.toDto(updatedHd);

        restHdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hdDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hdDTO))
            )
            .andExpect(status().isOk());

        // Validate the Hd in the database
        List<Hd> hdList = hdRepository.findAll();
        assertThat(hdList).hasSize(databaseSizeBeforeUpdate);
        Hd testHd = hdList.get(hdList.size() - 1);
        assertThat(testHd.getHdName()).isEqualTo(UPDATED_HD_NAME);
        assertThat(testHd.getHdPrice()).isEqualTo(UPDATED_HD_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingHd() throws Exception {
        int databaseSizeBeforeUpdate = hdRepository.findAll().size();
        hd.setId(count.incrementAndGet());

        // Create the Hd
        HdDTO hdDTO = hdMapper.toDto(hd);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hdDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hd in the database
        List<Hd> hdList = hdRepository.findAll();
        assertThat(hdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHd() throws Exception {
        int databaseSizeBeforeUpdate = hdRepository.findAll().size();
        hd.setId(count.incrementAndGet());

        // Create the Hd
        HdDTO hdDTO = hdMapper.toDto(hd);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hd in the database
        List<Hd> hdList = hdRepository.findAll();
        assertThat(hdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHd() throws Exception {
        int databaseSizeBeforeUpdate = hdRepository.findAll().size();
        hd.setId(count.incrementAndGet());

        // Create the Hd
        HdDTO hdDTO = hdMapper.toDto(hd);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHdMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hdDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hd in the database
        List<Hd> hdList = hdRepository.findAll();
        assertThat(hdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHdWithPatch() throws Exception {
        // Initialize the database
        hdRepository.saveAndFlush(hd);

        int databaseSizeBeforeUpdate = hdRepository.findAll().size();

        // Update the hd using partial update
        Hd partialUpdatedHd = new Hd();
        partialUpdatedHd.setId(hd.getId());

        partialUpdatedHd.hdPrice(UPDATED_HD_PRICE);

        restHdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHd.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHd))
            )
            .andExpect(status().isOk());

        // Validate the Hd in the database
        List<Hd> hdList = hdRepository.findAll();
        assertThat(hdList).hasSize(databaseSizeBeforeUpdate);
        Hd testHd = hdList.get(hdList.size() - 1);
        assertThat(testHd.getHdName()).isEqualTo(DEFAULT_HD_NAME);
        assertThat(testHd.getHdPrice()).isEqualTo(UPDATED_HD_PRICE);
    }

    @Test
    @Transactional
    void fullUpdateHdWithPatch() throws Exception {
        // Initialize the database
        hdRepository.saveAndFlush(hd);

        int databaseSizeBeforeUpdate = hdRepository.findAll().size();

        // Update the hd using partial update
        Hd partialUpdatedHd = new Hd();
        partialUpdatedHd.setId(hd.getId());

        partialUpdatedHd.hdName(UPDATED_HD_NAME).hdPrice(UPDATED_HD_PRICE);

        restHdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHd.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHd))
            )
            .andExpect(status().isOk());

        // Validate the Hd in the database
        List<Hd> hdList = hdRepository.findAll();
        assertThat(hdList).hasSize(databaseSizeBeforeUpdate);
        Hd testHd = hdList.get(hdList.size() - 1);
        assertThat(testHd.getHdName()).isEqualTo(UPDATED_HD_NAME);
        assertThat(testHd.getHdPrice()).isEqualTo(UPDATED_HD_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingHd() throws Exception {
        int databaseSizeBeforeUpdate = hdRepository.findAll().size();
        hd.setId(count.incrementAndGet());

        // Create the Hd
        HdDTO hdDTO = hdMapper.toDto(hd);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hdDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hd in the database
        List<Hd> hdList = hdRepository.findAll();
        assertThat(hdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHd() throws Exception {
        int databaseSizeBeforeUpdate = hdRepository.findAll().size();
        hd.setId(count.incrementAndGet());

        // Create the Hd
        HdDTO hdDTO = hdMapper.toDto(hd);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hd in the database
        List<Hd> hdList = hdRepository.findAll();
        assertThat(hdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHd() throws Exception {
        int databaseSizeBeforeUpdate = hdRepository.findAll().size();
        hd.setId(count.incrementAndGet());

        // Create the Hd
        HdDTO hdDTO = hdMapper.toDto(hd);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHdMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(hdDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hd in the database
        List<Hd> hdList = hdRepository.findAll();
        assertThat(hdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHd() throws Exception {
        // Initialize the database
        hdRepository.saveAndFlush(hd);

        int databaseSizeBeforeDelete = hdRepository.findAll().size();

        // Delete the hd
        restHdMockMvc.perform(delete(ENTITY_API_URL_ID, hd.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Hd> hdList = hdRepository.findAll();
        assertThat(hdList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
