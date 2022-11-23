<template>
  <div class="row justify-content-center">
    <div class="col-10">
      <div v-if="clientOrderProcess.processInstance">
        <h2 class="jh-entity-heading" data-cy="clientOrderProcessDetailsHeading">
          <span v-text="$t('cabonpontocomApp.clientOrderProcess.detail.title')">ClientOrderProcess</span> {{ clientOrderProcess.id }}
        </h2>
        <akip-show-process-instance :processInstance="clientOrderProcess.processInstance">
          <template v-slot:title>
            <span v-text="$t('cabonpontocomApp.clientOrderProcess.detail.title')">ClientOrderProcess</span> #{{ clientOrderProcess.id }}
          </template>
          <template v-slot:details>
            <div class="card" v-if="clientOrderProcess.clientOrder">
              <h5 class="card-header">
                <span class="title"> Client Order #{{ clientOrderProcess.clientOrder.id }} </span>
              </h5>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('cabonpontocomApp.clientOrderProcess.orderID')">orderID</label>
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="orderID"
                    id="client-order-orderID"
                    data-cy="orderID"
                    v-model="clientOrderProcess.clientOrder.orderID"
                  />
                </div>
              </div>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('cabonpontocomApp.clientOrderProcess.orderDate')">orderDate</label>
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="orderDate"
                    id="client-order-orderDate"
                    data-cy="orderDate"
                    v-model="clientOrderProcess.clientOrder.orderDate"
                  />
                </div>
              </div>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('cabonpontocomApp.clientOrderProcess.clientName')">clientName</label>
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="clientName"
                    id="client-order-clientName"
                    data-cy="clientName"
                    v-model="clientOrderProcess.clientOrder.clientName"
                  />
                </div>
              </div>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('cabonpontocomApp.clientOrderProcess.clientEmail')">clientEmail</label>
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="clientEmail"
                    id="client-order-clientEmail"
                    data-cy="clientEmail"
                    v-model="clientOrderProcess.clientOrder.clientEmail"
                  />
                </div>
              </div>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('cabonpontocomApp.clientOrderProcess.orderPrice')">orderPrice</label>
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="orderPrice"
                    id="client-order-orderPrice"
                    data-cy="orderPrice"
                    v-model="clientOrderProcess.clientOrder.orderPrice"
                  />
                </div>
              </div>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('cabonpontocomApp.clientOrderProcess.numComponents')">numComponents</label>
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="numComponents"
                    id="client-order-numComponents"
                    data-cy="numComponents"
                    v-model="clientOrderProcess.clientOrder.numComponents"
                  />
                </div>
              </div>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('cabonpontocomApp.clientOrderProcess.payment')">payment</label>
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="payment"
                    id="client-order-payment"
                    data-cy="payment"
                    v-model="clientOrderProcess.clientOrder.payment"
                  />
                </div>
              </div>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('cabonpontocomApp.clientOrderProcess.proceedToCheckout')"
                    >proceedToCheckout</label
                  >
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="proceedToCheckout"
                    id="client-order-proceedToCheckout"
                    data-cy="proceedToCheckout"
                    v-model="clientOrderProcess.clientOrder.proceedToCheckout"
                  />
                </div>
              </div>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('cabonpontocomApp.clientOrderProcess.assemblyPC')">assemblyPC</label>
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="assemblyPC"
                    id="client-order-assemblyPC"
                    data-cy="assemblyPC"
                    v-model="clientOrderProcess.clientOrder.assemblyPC"
                  />
                </div>
              </div>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('cabonpontocomApp.clientOrderProcess.deliveryAdd')">deliveryAdd</label>
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="deliveryAdd"
                    id="client-order-deliveryAdd"
                    data-cy="deliveryAdd"
                    v-model="clientOrderProcess.clientOrder.deliveryAdd"
                  />
                </div>
              </div>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('cabonpontocomApp.clientOrderProcess.isCompatible')">isCompatible</label>
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="isCompatible"
                    id="client-order-isCompatible"
                    data-cy="isCompatible"
                    v-model="clientOrderProcess.clientOrder.isCompatible"
                  />
                </div>
              </div>
            </div>
          </template>
        </akip-show-process-instance>
        <br />
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./client-order-process-details.component.ts"></script>
