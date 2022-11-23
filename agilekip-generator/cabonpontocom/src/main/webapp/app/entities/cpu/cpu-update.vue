<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="cabonpontocomApp.cpu.home.createOrEditLabel"
          data-cy="CpuCreateUpdateHeading"
          v-text="$t('cabonpontocomApp.cpu.home.createOrEditLabel')"
        >
          Create or edit a Cpu
        </h2>
        <div>
          <div class="form-group" v-if="cpu.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="cpu.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('cabonpontocomApp.cpu.cpuName')" for="cpu-cpuName">Cpu Name</label>
            <input
              type="text"
              class="form-control"
              name="cpuName"
              id="cpu-cpuName"
              data-cy="cpuName"
              :class="{ valid: !$v.cpu.cpuName.$invalid, invalid: $v.cpu.cpuName.$invalid }"
              v-model="$v.cpu.cpuName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('cabonpontocomApp.cpu.cpuPrice')" for="cpu-cpuPrice">Cpu Price</label>
            <input
              type="number"
              class="form-control"
              name="cpuPrice"
              id="cpu-cpuPrice"
              data-cy="cpuPrice"
              :class="{ valid: !$v.cpu.cpuPrice.$invalid, invalid: $v.cpu.cpuPrice.$invalid }"
              v-model.number="$v.cpu.cpuPrice.$model"
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
            :disabled="$v.cpu.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./cpu-update.component.ts"></script>
