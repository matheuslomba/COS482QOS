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
                v-text="$t('cabonpontocomApp.taskProceedCheckout.orderID')"
                for="task-proceed-checkout-orderID"
                >Order ID</label
              >
              <input
                type="text"
                class="form-control"
                name="orderID"
                id="task-proceed-checkout-orderID"
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
                v-text="$t('cabonpontocomApp.taskProceedCheckout.orderDate')"
                for="task-proceed-checkout-orderDate"
                >Order Date</label
              >
              <b-input-group class="mb-3">
                <b-form-input
                  id="task-proceed-checkout-orderDate"
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
                v-text="$t('cabonpontocomApp.taskProceedCheckout.clientName')"
                for="task-proceed-checkout-clientName"
                >Client Name</label
              >
              <input
                type="text"
                class="form-control"
                name="clientName"
                id="task-proceed-checkout-clientName"
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
                v-text="$t('cabonpontocomApp.taskProceedCheckout.numComponents')"
                for="task-proceed-checkout-numComponents"
                >Num Components</label
              >
              <input
                type="number"
                class="form-control"
                name="numComponents"
                id="task-proceed-checkout-numComponents"
                readonly
                data-cy="numComponents"
                :class="{
                  valid: !$v.taskContext.clientOrderProcess.clientOrder.numComponents.$invalid,
                  invalid: $v.taskContext.clientOrderProcess.clientOrder.numComponents.$invalid,
                }"
                v-model.number="$v.taskContext.clientOrderProcess.clientOrder.numComponents.$model"
              />
            </div>
            <div class="form-group">
              <label
                class="form-control-label"
                v-text="$t('cabonpontocomApp.taskProceedCheckout.deliveryAdd')"
                for="task-proceed-checkout-deliveryAdd"
                >Delivery Add</label
              >
              <input
                type="text"
                class="form-control"
                name="deliveryAdd"
                id="task-proceed-checkout-deliveryAdd"
                data-cy="deliveryAdd"
                :class="{
                  valid: !$v.taskContext.clientOrderProcess.clientOrder.deliveryAdd.$invalid,
                  invalid: $v.taskContext.clientOrderProcess.clientOrder.deliveryAdd.$invalid,
                }"
                v-model="$v.taskContext.clientOrderProcess.clientOrder.deliveryAdd.$model"
              />
            </div>
            <div class="form-group">
              <label
                class="form-control-label"
                v-text="$t('cabonpontocomApp.taskProceedCheckout.proceedToCheckout')"
                for="task-proceed-checkout-proceedToCheckout"
                >Proceed To Checkout</label
              >
              <input
                type="checkbox"
                class="form-check"
                name="proceedToCheckout"
                id="task-proceed-checkout-proceedToCheckout"
                data-cy="proceedToCheckout"
                :class="{
                  valid: !$v.taskContext.clientOrderProcess.clientOrder.proceedToCheckout.$invalid,
                  invalid: $v.taskContext.clientOrderProcess.clientOrder.proceedToCheckout.$invalid,
                }"
                v-model="$v.taskContext.clientOrderProcess.clientOrder.proceedToCheckout.$model"
                required
              />
              <div
                v-if="
                  $v.taskContext.clientOrderProcess.clientOrder.proceedToCheckout.$anyDirty &&
                  $v.taskContext.clientOrderProcess.clientOrder.proceedToCheckout.$invalid
                "
              >
                <small
                  class="form-text text-danger"
                  v-if="!$v.taskContext.clientOrderProcess.clientOrder.proceedToCheckout.required"
                  v-text="$t('entity.validation.required')"
                >
                  This field is required.
                </small>
              </div>
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

<script lang="ts" src="./task-proceed-checkout-execute.component.ts"></script>
