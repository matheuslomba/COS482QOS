<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="cpu">
        <h2 class="jh-entity-heading" data-cy="cpuDetailsHeading">
          <span v-text="$t('cabonpontocomApp.cpu.detail.title')">Cpu</span> {{ cpu.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('cabonpontocomApp.cpu.cpuName')">Cpu Name</span>
          </dt>
          <dd>
            <span>{{ cpu.cpuName }}</span>
          </dd>
          <dt>
            <span v-text="$t('cabonpontocomApp.cpu.cpuPrice')">Cpu Price</span>
          </dt>
          <dd>
            <span>{{ cpu.cpuPrice }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="cpu.id" :to="{ name: 'CpuEdit', params: { cpuId: cpu.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./cpu-details.component.ts"></script>
