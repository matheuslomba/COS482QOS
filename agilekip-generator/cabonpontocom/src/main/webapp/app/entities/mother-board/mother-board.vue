<template>
  <div>
    <h2 id="page-heading" data-cy="MotherBoardHeading">
      <span v-text="$t('cabonpontocomApp.motherBoard.home.title')" id="mother-board-heading">Mother Boards</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('cabonpontocomApp.motherBoard.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'MotherBoardCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-mother-board"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('cabonpontocomApp.motherBoard.home.createLabel')"> Create a new Mother Board </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && motherBoards && motherBoards.length === 0">
      <span v-text="$t('cabonpontocomApp.motherBoard.home.notFound')">No motherBoards found</span>
    </div>
    <div class="table-responsive" v-if="motherBoards && motherBoards.length > 0">
      <table class="table table-striped" aria-describedby="motherBoards">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('cabonpontocomApp.motherBoard.motherBoardName')">Mother Board Name</span></th>
            <th scope="row"><span v-text="$t('cabonpontocomApp.motherBoard.motherBoardPrice')">Mother Board Price</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="motherBoard in motherBoards" :key="motherBoard.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MotherBoardView', params: { motherBoardId: motherBoard.id } }">{{ motherBoard.id }}</router-link>
            </td>
            <td>{{ motherBoard.motherBoardName }}</td>
            <td>{{ motherBoard.motherBoardPrice }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'MotherBoardView', params: { motherBoardId: motherBoard.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'MotherBoardEdit', params: { motherBoardId: motherBoard.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(motherBoard)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="cabonpontocomApp.motherBoard.delete.question" data-cy="motherBoardDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-motherBoard-heading" v-text="$t('cabonpontocomApp.motherBoard.delete.question', { id: removeId })">
          Are you sure you want to delete this Mother Board?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-motherBoard"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeMotherBoard()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./mother-board.component.ts"></script>
