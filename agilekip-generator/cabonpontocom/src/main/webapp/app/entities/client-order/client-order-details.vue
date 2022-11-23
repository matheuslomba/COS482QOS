<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="clientOrder">
        <h2 class="jh-entity-heading" data-cy="clientOrderDetailsHeading">
          <span v-text="$t('cabonpontocomApp.clientOrder.detail.title')">ClientOrder</span> {{ clientOrder.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('cabonpontocomApp.clientOrder.orderID')">Order ID</span>
          </dt>
          <dd>
            <span>{{ clientOrder.orderID }}</span>
          </dd>
          <dt>
            <span v-text="$t('cabonpontocomApp.clientOrder.orderDate')">Order Date</span>
          </dt>
          <dd>
            <span>{{ clientOrder.orderDate }}</span>
          </dd>
          <dt>
            <span v-text="$t('cabonpontocomApp.clientOrder.clientName')">Client Name</span>
          </dt>
          <dd>
            <span>{{ clientOrder.clientName }}</span>
          </dd>
          <dt>
            <span v-text="$t('cabonpontocomApp.clientOrder.clientEmail')">Client Email</span>
          </dt>
          <dd>
            <span>{{ clientOrder.clientEmail }}</span>
          </dd>
          <dt>
            <span v-text="$t('cabonpontocomApp.clientOrder.orderPrice')">Order Price</span>
          </dt>
          <dd>
            <span>{{ clientOrder.orderPrice }}</span>
          </dd>
          <dt>
            <span v-text="$t('cabonpontocomApp.clientOrder.numComponents')">Num Components</span>
          </dt>
          <dd>
            <span>{{ clientOrder.numComponents }}</span>
          </dd>
          <dt>
            <span v-text="$t('cabonpontocomApp.clientOrder.payment')">Payment</span>
          </dt>
          <dd>
            <span>{{ clientOrder.payment }}</span>
          </dd>
          <dt>
            <span v-text="$t('cabonpontocomApp.clientOrder.proceedToCheckout')">Proceed To Checkout</span>
          </dt>
          <dd>
            <span>{{ clientOrder.proceedToCheckout }}</span>
          </dd>
          <dt>
            <span v-text="$t('cabonpontocomApp.clientOrder.assemblyPC')">Assembly PC</span>
          </dt>
          <dd>
            <span>{{ clientOrder.assemblyPC }}</span>
          </dd>
          <dt>
            <span v-text="$t('cabonpontocomApp.clientOrder.deliveryAdd')">Delivery Add</span>
          </dt>
          <dd>
            <span>{{ clientOrder.deliveryAdd }}</span>
          </dd>
          <dt>
            <span v-text="$t('cabonpontocomApp.clientOrder.isCompatible')">Is Compatible</span>
          </dt>
          <dd>
            <span>{{ clientOrder.isCompatible }}</span>
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
