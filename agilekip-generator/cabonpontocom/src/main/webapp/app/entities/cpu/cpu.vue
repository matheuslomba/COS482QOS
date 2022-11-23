<template>
  <div>
    <h2 id="page-heading" data-cy="CpuHeading">
      <span v-text="$t('cabonpontocomApp.cpu.home.title')" id="cpu-heading">Cpus</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('cabonpontocomApp.cpu.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'CpuCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-cpu">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('cabonpontocomApp.cpu.home.createLabel')"> Create a new Cpu </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && cpus && cpus.length === 0">
      <span v-text="$t('cabonpontocomApp.cpu.home.notFound')">No cpus found</span>
    </div>
    <div class="table-responsive" v-if="cpus && cpus.length > 0">
      <table class="table table-striped" aria-describedby="cpus">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('cabonpontocomApp.cpu.cpuName')">Cpu Name</span></th>
            <th scope="row"><span v-text="$t('cabonpontocomApp.cpu.cpuPrice')">Cpu Price</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="cpu in cpus" :key="cpu.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CpuView', params: { cpuId: cpu.id } }">{{ cpu.id }}</router-link>
            </td>
            <td>{{ cpu.cpuName }}</td>
            <td>{{ cpu.cpuPrice }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CpuView', params: { cpuId: cpu.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CpuEdit', params: { cpuId: cpu.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(cpu)"
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
        ><span id="cabonpontocomApp.cpu.delete.question" data-cy="cpuDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-cpu-heading" v-text="$t('cabonpontocomApp.cpu.delete.question', { id: removeId })">
          Are you sure you want to delete this Cpu?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-cpu"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeCpu()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./cpu.component.ts"></script>
