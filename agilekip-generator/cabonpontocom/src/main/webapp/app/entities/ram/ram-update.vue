<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="cabonpontocomApp.ram.home.createOrEditLabel"
          data-cy="RamCreateUpdateHeading"
          v-text="$t('cabonpontocomApp.ram.home.createOrEditLabel')"
        >
          Create or edit a Ram
        </h2>
        <div>
          <div class="form-group" v-if="ram.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="ram.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('cabonpontocomApp.ram.ramName')" for="ram-ramName">Ram Name</label>
            <input
              type="text"
              class="form-control"
              name="ramName"
              id="ram-ramName"
              data-cy="ramName"
              :class="{ valid: !$v.ram.ramName.$invalid, invalid: $v.ram.ramName.$invalid }"
              v-model="$v.ram.ramName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('cabonpontocomApp.ram.ramPrice')" for="ram-ramPrice">Ram Price</label>
            <input
              type="number"
              class="form-control"
              name="ramPrice"
              id="ram-ramPrice"
              data-cy="ramPrice"
              :class="{ valid: !$v.ram.ramPrice.$invalid, invalid: $v.ram.ramPrice.$invalid }"
              v-model.number="$v.ram.ramPrice.$model"
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
            :disabled="$v.ram.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./ram-update.component.ts"></script>
