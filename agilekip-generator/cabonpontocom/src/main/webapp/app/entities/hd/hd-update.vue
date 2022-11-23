<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="cabonpontocomApp.hd.home.createOrEditLabel"
          data-cy="HdCreateUpdateHeading"
          v-text="$t('cabonpontocomApp.hd.home.createOrEditLabel')"
        >
          Create or edit a Hd
        </h2>
        <div>
          <div class="form-group" v-if="hd.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="hd.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('cabonpontocomApp.hd.hdName')" for="hd-hdName">Hd Name</label>
            <input
              type="text"
              class="form-control"
              name="hdName"
              id="hd-hdName"
              data-cy="hdName"
              :class="{ valid: !$v.hd.hdName.$invalid, invalid: $v.hd.hdName.$invalid }"
              v-model="$v.hd.hdName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('cabonpontocomApp.hd.hdPrice')" for="hd-hdPrice">Hd Price</label>
            <input
              type="number"
              class="form-control"
              name="hdPrice"
              id="hd-hdPrice"
              data-cy="hdPrice"
              :class="{ valid: !$v.hd.hdPrice.$invalid, invalid: $v.hd.hdPrice.$invalid }"
              v-model.number="$v.hd.hdPrice.$model"
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
            :disabled="$v.hd.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./hd-update.component.ts"></script>
