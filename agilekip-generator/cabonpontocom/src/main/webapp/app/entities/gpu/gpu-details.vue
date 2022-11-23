<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="gpu">
        <h2 class="jh-entity-heading" data-cy="gpuDetailsHeading">
          <span v-text="$t('cabonpontocomApp.gpu.detail.title')">Gpu</span> {{ gpu.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('cabonpontocomApp.gpu.gpuName')">Gpu Name</span>
          </dt>
          <dd>
            <span>{{ gpu.gpuName }}</span>
          </dd>
          <dt>
            <span v-text="$t('cabonpontocomApp.gpu.gpuPrice')">Gpu Price</span>
          </dt>
          <dd>
            <span>{{ gpu.gpuPrice }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="gpu.id" :to="{ name: 'GpuEdit', params: { gpuId: gpu.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./gpu-details.component.ts"></script>
