<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="clientOrder">
        <h2 class="jh-entity-heading" data-cy="clientOrderDetailsHeading">
          <span v-text="$t('cabunpontocomApp.clientOrder.detail.title')">ClientOrder</span> {{ clientOrder.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('cabunpontocomApp.clientOrder.orderID')">Order ID</span>
          </dt>
          <dd>
            <span>{{ clientOrder.orderID }}</span>
          </dd>
          <dt>
            <span v-text="$t('cabunpontocomApp.clientOrder.orderDate')">Order Date</span>
          </dt>
          <dd>
            <span>{{ clientOrder.orderDate }}</span>
          </dd>
          <dt>
            <span v-text="$t('cabunpontocomApp.clientOrder.clientName')">Client Name</span>
          </dt>
          <dd>
            <span>{{ clientOrder.clientName }}</span>
          </dd>
          <dt>
            <span v-text="$t('cabunpontocomApp.clientOrder.orderComponents')">Order Components</span>
          </dt>
          <dd>
            <span>{{ clientOrder.orderComponents }}</span>
          </dd>
          <dt>
            <span v-text="$t('cabunpontocomApp.clientOrder.orderPrice')">Order Price</span>
          </dt>
          <dd>
            <span>{{ clientOrder.orderPrice }}</span>
          </dd>
          <dt>
            <span v-text="$t('cabunpontocomApp.clientOrder.numItems')">Num Items</span>
          </dt>
          <dd>
            <span>{{ clientOrder.numItems }}</span>
          </dd>
          <dt>
            <span v-text="$t('cabunpontocomApp.clientOrder.assemblyPC')">Assembly PC</span>
          </dt>
          <dd>
            <span>{{ clientOrder.assemblyPC }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./client-order-details.component.ts"></script>
