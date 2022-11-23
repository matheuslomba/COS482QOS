<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="hd">
        <h2 class="jh-entity-heading" data-cy="hdDetailsHeading">
          <span v-text="$t('cabonpontocomApp.hd.detail.title')">Hd</span> {{ hd.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('cabonpontocomApp.hd.hdName')">Hd Name</span>
          </dt>
          <dd>
            <span>{{ hd.hdName }}</span>
          </dd>
          <dt>
            <span v-text="$t('cabonpontocomApp.hd.hdPrice')">Hd Price</span>
          </dt>
          <dd>
            <span>{{ hd.hdPrice }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="hd.id" :to="{ name: 'HdEdit', params: { hdId: hd.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./hd-details.component.ts"></script>
