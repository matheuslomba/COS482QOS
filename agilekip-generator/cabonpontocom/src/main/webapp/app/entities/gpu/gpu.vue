<template>
  <div>
    <h2 id="page-heading" data-cy="GpuHeading">
      <span v-text="$t('cabonpontocomApp.gpu.home.title')" id="gpu-heading">Gpus</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('cabonpontocomApp.gpu.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'GpuCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-gpu">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('cabonpontocomApp.gpu.home.createLabel')"> Create a new Gpu </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && gpus && gpus.length === 0">
      <span v-text="$t('cabonpontocomApp.gpu.home.notFound')">No gpus found</span>
    </div>
    <div class="table-responsive" v-if="gpus && gpus.length > 0">
      <table class="table table-striped" aria-describedby="gpus">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('cabonpontocomApp.gpu.gpuName')">Gpu Name</span></th>
            <th scope="row"><span v-text="$t('cabonpontocomApp.gpu.gpuPrice')">Gpu Price</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="gpu in gpus" :key="gpu.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'GpuView', params: { gpuId: gpu.id } }">{{ gpu.id }}</router-link>
            </td>
            <td>{{ gpu.gpuName }}</td>
            <td>{{ gpu.gpuPrice }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'GpuView', params: { gpuId: gpu.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'GpuEdit', params: { gpuId: gpu.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(gpu)"
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
        ><span id="cabonpontocomApp.gpu.delete.question" data-cy="gpuDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-gpu-heading" v-text="$t('cabonpontocomApp.gpu.delete.question', { id: removeId })">
          Are you sure you want to delete this Gpu?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-gpu"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeGpu()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./gpu.component.ts"></script>
