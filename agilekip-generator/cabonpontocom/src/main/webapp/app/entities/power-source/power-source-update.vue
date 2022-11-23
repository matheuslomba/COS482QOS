<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="cabonpontocomApp.powerSource.home.createOrEditLabel"
          data-cy="PowerSourceCreateUpdateHeading"
          v-text="$t('cabonpontocomApp.powerSource.home.createOrEditLabel')"
        >
          Create or edit a PowerSource
        </h2>
        <div>
          <div class="form-group" v-if="powerSource.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="powerSource.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('cabonpontocomApp.powerSource.powerSourceName')" for="power-source-powerSourceName"
              >Power Source Name</label
            >
            <input
              type="text"
              class="form-control"
              name="powerSourceName"
              id="power-source-powerSourceName"
              data-cy="powerSourceName"
              :class="{ valid: !$v.powerSource.powerSourceName.$invalid, invalid: $v.powerSource.powerSourceName.$invalid }"
              v-model="$v.powerSource.powerSourceName.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('cabonpontocomApp.powerSource.powerSourcePrice')"
              for="power-source-powerSourcePrice"
              >Power Source Price</label
            >
            <input
              type="number"
              class="form-control"
              name="powerSourcePrice"
              id="power-source-powerSourcePrice"
              data-cy="powerSourcePrice"
              :class="{ valid: !$v.powerSource.powerSourcePrice.$invalid, invalid: $v.powerSource.powerSourcePrice.$invalid }"
              v-model.number="$v.powerSource.powerSourcePrice.$model"
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
            :disabled="$v.powerSource.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./power-source-update.component.ts"></script>
