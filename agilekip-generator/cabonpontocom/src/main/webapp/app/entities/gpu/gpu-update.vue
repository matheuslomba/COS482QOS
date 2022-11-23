<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="cabonpontocomApp.gpu.home.createOrEditLabel"
          data-cy="GpuCreateUpdateHeading"
          v-text="$t('cabonpontocomApp.gpu.home.createOrEditLabel')"
        >
          Create or edit a Gpu
        </h2>
        <div>
          <div class="form-group" v-if="gpu.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="gpu.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('cabonpontocomApp.gpu.gpuName')" for="gpu-gpuName">Gpu Name</label>
            <input
              type="text"
              class="form-control"
              name="gpuName"
              id="gpu-gpuName"
              data-cy="gpuName"
              :class="{ valid: !$v.gpu.gpuName.$invalid, invalid: $v.gpu.gpuName.$invalid }"
              v-model="$v.gpu.gpuName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('cabonpontocomApp.gpu.gpuPrice')" for="gpu-gpuPrice">Gpu Price</label>
            <input
              type="number"
              class="form-control"
              name="gpuPrice"
              id="gpu-gpuPrice"
              data-cy="gpuPrice"
              :class="{ valid: !$v.gpu.gpuPrice.$invalid, invalid: $v.gpu.gpuPrice.$invalid }"
              v-model.number="$v.gpu.gpuPrice.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.gpu.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./gpu-update.component.ts"></script>
