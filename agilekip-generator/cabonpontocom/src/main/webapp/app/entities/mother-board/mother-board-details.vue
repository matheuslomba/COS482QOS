<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="motherBoard">
        <h2 class="jh-entity-heading" data-cy="motherBoardDetailsHeading">
          <span v-text="$t('cabonpontocomApp.motherBoard.detail.title')">MotherBoard</span> {{ motherBoard.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('cabonpontocomApp.motherBoard.motherBoardName')">Mother Board Name</span>
          </dt>
          <dd>
            <span>{{ motherBoard.motherBoardName }}</span>
          </dd>
          <dt>
            <span v-text="$t('cabonpontocomApp.motherBoard.motherBoardPrice')">Mother Board Price</span>
          </dt>
          <dd>
            <span>{{ motherBoard.motherBoardPrice }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="motherBoard.id"
          :to="{ name: 'MotherBoardEdit', params: { motherBoardId: motherBoard.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./mother-board-details.component.ts"></script>
