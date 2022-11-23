package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.MotherBoard;
import com.mycompany.myapp.repository.MotherBoardRepository;
import com.mycompany.myapp.service.dto.MotherBoardDTO;
import com.mycompany.myapp.service.mapper.MotherBoardMapper;
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
 * Integration tests for the {@link MotherBoardResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MotherBoardResourceIT {

    private static final String DEFAULT_MOTHER_BOARD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MOTHER_BOARD_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_MOTHER_BOARD_PRICE = 1;
    private static final Integer UPDATED_MOTHER_BOARD_PRICE = 2;

    private static final String ENTITY_API_URL = "/api/mother-boards";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MotherBoardRepository motherBoardRepository;

    @Autowired
    private MotherBoardMapper motherBoardMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMotherBoardMockMvc;

    private MotherBoard motherBoard;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MotherBoard createEntity(EntityManager em) {
        MotherBoard motherBoard = new MotherBoard().motherBoardName(DEFAULT_MOTHER_BOARD_NAME).motherBoardPrice(DEFAULT_MOTHER_BOARD_PRICE);
        return motherBoard;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MotherBoard createUpdatedEntity(EntityManager em) {
        MotherBoard motherBoard = new MotherBoard().motherBoardName(UPDATED_MOTHER_BOARD_NAME).motherBoardPrice(UPDATED_MOTHER_BOARD_PRICE);
        return motherBoard;
    }

    @BeforeEach
    public void initTest() {
        motherBoard = createEntity(em);
    }

    @Test
    @Transactional
    void createMotherBoard() throws Exception {
        int databaseSizeBeforeCreate = motherBoardRepository.findAll().size();
        // Create the MotherBoard
        MotherBoardDTO motherBoardDTO = motherBoardMapper.toDto(motherBoard);
        restMotherBoardMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(motherBoardDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MotherBoard in the database
        List<MotherBoard> motherBoardList = motherBoardRepository.findAll();
        assertThat(motherBoardList).hasSize(databaseSizeBeforeCreate + 1);
        MotherBoard testMotherBoard = motherBoardList.get(motherBoardList.size() - 1);
        assertThat(testMotherBoard.getMotherBoardName()).isEqualTo(DEFAULT_MOTHER_BOARD_NAME);
        assertThat(testMotherBoard.getMotherBoardPrice()).isEqualTo(DEFAULT_MOTHER_BOARD_PRICE);
    }

    @Test
    @Transactional
    void createMotherBoardWithExistingId() throws Exception {
        // Create the MotherBoard with an existing ID
        motherBoard.setId(1L);
        MotherBoardDTO motherBoardDTO = motherBoardMapper.toDto(motherBoard);

        int databaseSizeBeforeCreate = motherBoardRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMotherBoardMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(motherBoardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MotherBoard in the database
        List<MotherBoard> motherBoardList = motherBoardRepository.findAll();
        assertThat(motherBoardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMotherBoards() throws Exception {
        // Initialize the database
        motherBoardRepository.saveAndFlush(motherBoard);

        // Get all the motherBoardList
        restMotherBoardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(motherBoard.getId().intValue())))
            .andExpect(jsonPath("$.[*].motherBoardName").value(hasItem(DEFAULT_MOTHER_BOARD_NAME)))
            .andExpect(jsonPath("$.[*].motherBoardPrice").value(hasItem(DEFAULT_MOTHER_BOARD_PRICE)));
    }

    @Test
    @Transactional
    void getMotherBoard() throws Exception {
        // Initialize the database
        motherBoardRepository.saveAndFlush(motherBoard);

        // Get the motherBoard
        restMotherBoardMockMvc
            .perform(get(ENTITY_API_URL_ID, motherBoard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(motherBoard.getId().intValue()))
            .andExpect(jsonPath("$.motherBoardName").value(DEFAULT_MOTHER_BOARD_NAME))
            .andExpect(jsonPath("$.motherBoardPrice").value(DEFAULT_MOTHER_BOARD_PRICE));
    }

    @Test
    @Transactional
    void getNonExistingMotherBoard() throws Exception {
        // Get the motherBoard
        restMotherBoardMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMotherBoard() throws Exception {
        // Initialize the database
        motherBoardRepository.saveAndFlush(motherBoard);

        int databaseSizeBeforeUpdate = motherBoardRepository.findAll().size();

        // Update the motherBoard
        MotherBoard updatedMotherBoard = motherBoardRepository.findById(motherBoard.getId()).get();
        // Disconnect from session so that the updates on updatedMotherBoard are not directly saved in db
        em.detach(updatedMotherBoard);
        updatedMotherBoard.motherBoardName(UPDATED_MOTHER_BOARD_NAME).motherBoardPrice(UPDATED_MOTHER_BOARD_PRICE);
        MotherBoardDTO motherBoardDTO = motherBoardMapper.toDto(updatedMotherBoard);

        restMotherBoardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, motherBoardDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(motherBoardDTO))
            )
            .andExpect(status().isOk());

        // Validate the MotherBoard in the database
        List<MotherBoard> motherBoardList = motherBoardRepository.findAll();
        assertThat(motherBoardList).hasSize(databaseSizeBeforeUpdate);
        MotherBoard testMotherBoard = motherBoardList.get(motherBoardList.size() - 1);
        assertThat(testMotherBoard.getMotherBoardName()).isEqualTo(UPDATED_MOTHER_BOARD_NAME);
        assertThat(testMotherBoard.getMotherBoardPrice()).isEqualTo(UPDATED_MOTHER_BOARD_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingMotherBoard() throws Exception {
        int databaseSizeBeforeUpdate = motherBoardRepository.findAll().size();
        motherBoard.setId(count.incrementAndGet());

        // Create the MotherBoard
        MotherBoardDTO motherBoardDTO = motherBoardMapper.toDto(motherBoard);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMotherBoardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, motherBoardDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(motherBoardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MotherBoard in the database
        List<MotherBoard> motherBoardList = motherBoardRepository.findAll();
        assertThat(motherBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMotherBoard() throws Exception {
        int databaseSizeBeforeUpdate = motherBoardRepository.findAll().size();
        motherBoard.setId(count.incrementAndGet());

        // Create the MotherBoard
        MotherBoardDTO motherBoardDTO = motherBoardMapper.toDto(motherBoard);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMotherBoardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(motherBoardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MotherBoard in the database
        List<MotherBoard> motherBoardList = motherBoardRepository.findAll();
        assertThat(motherBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMotherBoard() throws Exception {
        int databaseSizeBeforeUpdate = motherBoardRepository.findAll().size();
        motherBoard.setId(count.incrementAndGet());

        // Create the MotherBoard
        MotherBoardDTO motherBoardDTO = motherBoardMapper.toDto(motherBoard);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMotherBoardMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(motherBoardDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MotherBoard in the database
        List<MotherBoard> motherBoardList = motherBoardRepository.findAll();
        assertThat(motherBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMotherBoardWithPatch() throws Exception {
        // Initialize the database
        motherBoardRepository.saveAndFlush(motherBoard);

        int databaseSizeBeforeUpdate = motherBoardRepository.findAll().size();

        // Update the motherBoard using partial update
        MotherBoard partialUpdatedMotherBoard = new MotherBoard();
        partialUpdatedMotherBoard.setId(motherBoard.getId());

        partialUpdatedMotherBoard.motherBoardName(UPDATED_MOTHER_BOARD_NAME).motherBoardPrice(UPDATED_MOTHER_BOARD_PRICE);

        restMotherBoardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMotherBoard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMotherBoard))
            )
            .andExpect(status().isOk());

        // Validate the MotherBoard in the database
        List<MotherBoard> motherBoardList = motherBoardRepository.findAll();
        assertThat(motherBoardList).hasSize(databaseSizeBeforeUpdate);
        MotherBoard testMotherBoard = motherBoardList.get(motherBoardList.size() - 1);
        assertThat(testMotherBoard.getMotherBoardName()).isEqualTo(UPDATED_MOTHER_BOARD_NAME);
        assertThat(testMotherBoard.getMotherBoardPrice()).isEqualTo(UPDATED_MOTHER_BOARD_PRICE);
    }

    @Test
    @Transactional
    void fullUpdateMotherBoardWithPatch() throws Exception {
        // Initialize the database
        motherBoardRepository.saveAndFlush(motherBoard);

        int databaseSizeBeforeUpdate = motherBoardRepository.findAll().size();

        // Update the motherBoard using partial update
        MotherBoard partialUpdatedMotherBoard = new MotherBoard();
        partialUpdatedMotherBoard.setId(motherBoard.getId());

        partialUpdatedMotherBoard.motherBoardName(UPDATED_MOTHER_BOARD_NAME).motherBoardPrice(UPDATED_MOTHER_BOARD_PRICE);

        restMotherBoardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMotherBoard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMotherBoard))
            )
            .andExpect(status().isOk());

        // Validate the MotherBoard in the database
        List<MotherBoard> motherBoardList = motherBoardRepository.findAll();
        assertThat(motherBoardList).hasSize(databaseSizeBeforeUpdate);
        MotherBoard testMotherBoard = motherBoardList.get(motherBoardList.size() - 1);
        assertThat(testMotherBoard.getMotherBoardName()).isEqualTo(UPDATED_MOTHER_BOARD_NAME);
        assertThat(testMotherBoard.getMotherBoardPrice()).isEqualTo(UPDATED_MOTHER_BOARD_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingMotherBoard() throws Exception {
        int databaseSizeBeforeUpdate = motherBoardRepository.findAll().size();
        motherBoard.setId(count.incrementAndGet());

        // Create the MotherBoard
        MotherBoardDTO motherBoardDTO = motherBoardMapper.toDto(motherBoard);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMotherBoardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, motherBoardDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(motherBoardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MotherBoard in the database
        List<MotherBoard> motherBoardList = motherBoardRepository.findAll();
        assertThat(motherBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMotherBoard() throws Exception {
        int databaseSizeBeforeUpdate = motherBoardRepository.findAll().size();
        motherBoard.setId(count.incrementAndGet());

        // Create the MotherBoard
        MotherBoardDTO motherBoardDTO = motherBoardMapper.toDto(motherBoard);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMotherBoardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(motherBoardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MotherBoard in the database
        List<MotherBoard> motherBoardList = motherBoardRepository.findAll();
        assertThat(motherBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMotherBoard() throws Exception {
        int databaseSizeBeforeUpdate = motherBoardRepository.findAll().size();
        motherBoard.setId(count.incrementAndGet());

        // Create the MotherBoard
        MotherBoardDTO motherBoardDTO = motherBoardMapper.toDto(motherBoard);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMotherBoardMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(motherBoardDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MotherBoard in the database
        List<MotherBoard> motherBoardList = motherBoardRepository.findAll();
        assertThat(motherBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMotherBoard() throws Exception {
        // Initialize the database
        motherBoardRepository.saveAndFlush(motherBoard);

        int databaseSizeBeforeDelete = motherBoardRepository.findAll().size();

        // Delete the motherBoard
        restMotherBoardMockMvc
            .perform(delete(ENTITY_API_URL_ID, motherBoard.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MotherBoard> motherBoardList = motherBoardRepository.findAll();
        assertThat(motherBoardList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
