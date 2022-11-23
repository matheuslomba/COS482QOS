<template>
  <div class="row justify-content-center">
    <div class="col-10">
      <h2 id="page-heading" data-cy="TaskInstanceHeading">
        <span v-text="$t('cabunpontocomApp.taskInstance.details.title')" id="task-instance-heading">Task Details</span>
      </h2>
      <div v-if="taskContext.taskInstance">
        <akip-show-task-instance :taskInstance="taskContext.taskInstance">
          <template v-slot:body>
            <hr />
            <div class="form-group">
              <label class="form-control-label" v-text="$t('cabunpontocomApp.taskChooseComponents.orderID')">orderID</label>
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
              <label class="form-control-label" v-text="$t('cabunpontocomApp.taskChooseComponents.orderDate')">orderDate</label>
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
              <label class="form-control-label" v-text="$t('cabunpontocomApp.taskChooseComponents.clientName')">clientName</label>
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
              <label class="form-control-label" v-text="$t('cabunpontocomApp.taskChooseComponents.orderComponents')">orderComponents</label>
              <input
                readonly
                type="text"
                class="form-control"
                name="orderComponents"
                id="client-order-orderComponents"
                data-cy="orderComponents"
                v-model="taskContext.clientOrderProcess.clientOrder.orderComponents"
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
          :to="`/process-definition/ClientOrderProcess/task/TaskChooseComponents/${taskContext.taskInstance.id}/execute`"
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

<script lang="ts" src="./task-choose-components-details.component.ts"></script>
