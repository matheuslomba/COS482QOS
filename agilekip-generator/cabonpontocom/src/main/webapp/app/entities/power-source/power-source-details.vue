<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="powerSource">
        <h2 class="jh-entity-heading" data-cy="powerSourceDetailsHeading">
          <span v-text="$t('cabonpontocomApp.powerSource.detail.title')">PowerSource</span> {{ powerSource.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('cabonpontocomApp.powerSource.powerSourceName')">Power Source Name</span>
          </dt>
          <dd>
            <span>{{ powerSource.powerSourceName }}</span>
          </dd>
          <dt>
            <span v-text="$t('cabonpontocomApp.powerSource.powerSourcePrice')">Power Source Price</span>
          </dt>
          <dd>
            <span>{{ powerSource.powerSourcePrice }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="powerSource.id"
          :to="{ name: 'PowerSourceEdit', params: { powerSourceId: powerSource.id } }"
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

<script lang="ts" src="./power-source-details.component.ts"></script>
