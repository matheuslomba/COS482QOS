<template>
  <div class="row justify-content-center">
    <div class="col-10">
      <h2 id="page-heading" data-cy="TaskInstanceHeading">
        <span v-text="$t('cabonpontocomApp.taskInstance.details.title')" id="task-instance-heading">Task Details</span>
      </h2>
      <div v-if="taskContext.taskInstance">
        <akip-show-task-instance :taskInstance="taskContext.taskInstance">
          <template v-slot:body>
            <hr />
            <div class="form-group">
              <label class="form-control-label" v-text="$t('cabonpontocomApp.taskGetPaid.orderID')">orderID</label>
              <input
                readonly
                type="text"
                class="form-control"
                name="orderID"
                id="client-order-orderID"
                data-cy="orderID"
                v-model="taskContext.clientOrderProcess.clientOrder.orderID"
              />
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('cabonpontocomApp.taskGetPaid.orderDate')">orderDate</label>
              <input
                readonly
                type="text"
                class="form-control"
                name="orderDate"
                id="client-order-orderDate"
                data-cy="orderDate"
                v-model="taskContext.clientOrderProcess.clientOrder.orderDate"
              />
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('cabonpontocomApp.taskGetPaid.clientName')">clientName</label>
              <input
                readonly
                type="text"
                class="form-control"
                name="clientName"
                id="client-order-clientName"
                data-cy="clientName"
                v-model="taskContext.clientOrderProcess.clientOrder.clientName"
              />
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('cabonpontocomApp.taskGetPaid.deliveryAdd')">deliveryAdd</label>
              <input
                readonly
                type="text"
                class="form-control"
                name="deliveryAdd"
                id="client-order-deliveryAdd"
                data-cy="deliveryAdd"
                v-model="taskContext.clientOrderProcess.clientOrder.deliveryAdd"
              />
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('cabonpontocomApp.taskGetPaid.orderPrice')">orderPrice</label>
              <input
                readonly
                type="text"
                class="form-control"
                name="orderPrice"
                id="client-order-orderPrice"
                data-cy="orderPrice"
                v-model="taskContext.clientOrderProcess.clientOrder.orderPrice"
              />
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('cabonpontocomApp.taskGetPaid.payment')">payment</label>
              <input
                readonly
                type="text"
                class="form-control"
                name="payment"
                id="client-order-payment"
                data-cy="payment"
                v-model="taskContext.clientOrderProcess.clientOrder.payment"
              />
            </div>
          </template>
        </akip-show-task-instance>
        <br />
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>

        <router-link
          v-if="taskContext.taskInstance.status == 'NEW' || taskContext.taskInstance.status == 'ASSIGNED'"
          :to="`/process-definition/ClientOrderProcess/task/TaskGetPaid/${taskContext.taskInstance.id}/execute`"
          tag="button"
          class="btn btn-primary"
          data-cy="entityDetailsButton"
        >
          <font-awesome-icon icon="play"></font-awesome-icon>&nbsp;Execute
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./task-get-paid-details.component.ts"></script>
