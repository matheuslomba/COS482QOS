<template>
  <div class="row justify-content-center">
    <div class="col-10">
      <div v-if="taskContext.taskInstance">
        <h2 id="page-heading" data-cy="TaskInstanceHeading">
          <span v-text="$t('cabonpontocomApp.taskInstance.execute.title')" id="task-instance-heading">Task Execution</span>
        </h2>
        <akip-show-task-instance :taskInstance="taskContext.taskInstance">
          <template v-slot:body>
            <hr />
            <div class="form-group">
              <label
                class="form-control-label"
                v-text="$t('cabonpontocomApp.taskChooseComponents.orderID')"
                for="task-choose-components-orderID"
                >Order ID</label
              >
              <input
                type="text"
                class="form-control"
                name="orderID"
                id="task-choose-components-orderID"
                readonly
                data-cy="orderID"
                :class="{
                  valid: !$v.taskContext.clientOrderProcess.clientOrder.orderID.$invalid,
                  invalid: $v.taskContext.clientOrderProcess.clientOrder.orderID.$invalid,
                }"
                v-model="$v.taskContext.clientOrderProcess.clientOrder.orderID.$model"
              />
            </div>
            <div class="form-group">
              <label
                class="form-control-label"
                v-text="$t('cabonpontocomApp.taskChooseComponents.orderDate')"
                for="task-choose-components-orderDate"
                >Order Date</label
              >
              <b-input-group class="mb-3">
                <b-form-input
                  id="task-choose-components-orderDate"
                  readonly
                  data-cy="orderDate"
                  type="text"
                  class="form-control"
                  name="orderDate"
                  :class="{
                    valid: !$v.taskContext.clientOrderProcess.clientOrder.orderDate.$invalid,
                    invalid: $v.taskContext.clientOrderProcess.clientOrder.orderDate.$invalid,
                  }"
                  v-model="$v.taskContext.clientOrderProcess.clientOrder.orderDate.$model"
                />
              </b-input-group>
            </div>
            <div class="form-group">
              <label
                class="form-control-label"
                v-text="$t('cabonpontocomApp.taskChooseComponents.clientName')"
                for="task-choose-components-clientName"
                >Client Name</label
              >
              <input
                type="text"
                class="form-control"
                name="clientName"
                id="task-choose-components-clientName"
                readonly
                data-cy="clientName"
                :class="{
                  valid: !$v.taskContext.clientOrderProcess.clientOrder.clientName.$invalid,
                  invalid: $v.taskContext.clientOrderProcess.clientOrder.clientName.$invalid,
                }"
                v-model="$v.taskContext.clientOrderProcess.clientOrder.clientName.$model"
              />
            </div>
            <div class="form-group">
              <label
                class="form-control-label"
                v-text="$t('cabonpontocomApp.taskChooseComponents.numComponents')"
                for="task-choose-components-numComponents"
                >Num Components</label
              >
              <input
                type="number"
                class="form-control"
                name="numComponents"
                id="task-choose-components-numComponents"
                data-cy="numComponents"
                :class="{
                  valid: !$v.taskContext.clientOrderProcess.clientOrder.numComponents.$invalid,
                  invalid: $v.taskContext.clientOrderProcess.clientOrder.numComponents.$invalid,
                }"
                v-model.number="$v.taskContext.clientOrderProcess.clientOrder.numComponents.$model"
              />
            </div>
          </template>
        </akip-show-task-instance>
        <br />
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <button type="submit" v-on:click.prevent="complete()" class="btn btn-success" data-cy="complete">
          <font-awesome-icon icon="check-circle"></font-awesome-icon>&nbsp;Complete
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./task-choose-components-execute.component.ts"></script>
