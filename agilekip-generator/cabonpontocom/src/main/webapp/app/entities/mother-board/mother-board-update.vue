<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="cabonpontocomApp.motherBoard.home.createOrEditLabel"
          data-cy="MotherBoardCreateUpdateHeading"
          v-text="$t('cabonpontocomApp.motherBoard.home.createOrEditLabel')"
        >
          Create or edit a MotherBoard
        </h2>
        <div>
          <div class="form-group" v-if="motherBoard.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="motherBoard.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('cabonpontocomApp.motherBoard.motherBoardName')" for="mother-board-motherBoardName"
              >Mother Board Name</label
            >
            <input
              type="text"
              class="form-control"
              name="motherBoardName"
              id="mother-board-motherBoardName"
              data-cy="motherBoardName"
              :class="{ valid: !$v.motherBoard.motherBoardName.$invalid, invalid: $v.motherBoard.motherBoardName.$invalid }"
              v-model="$v.motherBoard.motherBoardName.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('cabonpontocomApp.motherBoard.motherBoardPrice')"
              for="mother-board-motherBoardPrice"
              >Mother Board Price</label
            >
            <input
              type="number"
              class="form-control"
              name="motherBoardPrice"
              id="mother-board-motherBoardPrice"
              data-cy="motherBoardPrice"
              :class="{ valid: !$v.motherBoard.motherBoardPrice.$invalid, invalid: $v.motherBoard.motherBoardPrice.$invalid }"
              v-model.number="$v.motherBoard.motherBoardPrice.$model"
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
            :disabled="$v.motherBoard.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./mother-board-update.component.ts"></script>
