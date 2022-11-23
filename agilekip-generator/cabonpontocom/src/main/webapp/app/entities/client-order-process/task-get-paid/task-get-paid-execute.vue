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
              <label class="form-control-label" v-text="$t('cabonpontocomApp.taskGetPaid.orderID')" for="task-get-paid-orderID"
                >Order ID</label
              >
              <input
                type="text"
                class="form-control"
                name="orderID"
                id="task-get-paid-orderID"
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
              <label class="form-control-label" v-text="$t('cabonpontocomApp.taskGetPaid.orderDate')" for="task-get-paid-orderDate"
                >Order Date</label
              >
              <b-input-group class="mb-3">
                <b-form-input
                  id="task-get-paid-orderDate"
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
              <label class="form-control-label" v-text="$t('cabonpontocomApp.taskGetPaid.clientName')" for="task-get-paid-clientName"
                >Client Name</label
              >
              <input
                type="text"
                class="form-control"
                name="clientName"
                id="task-get-paid-clientName"
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
              <label class="form-control-label" v-text="$t('cabonpontocomApp.taskGetPaid.deliveryAdd')" for="task-get-paid-deliveryAdd"
                >Delivery Add</label
              >
              <input
                type="text"
                class="form-control"
                name="deliveryAdd"
                id="task-get-paid-deliveryAdd"
                readonly
                data-cy="deliveryAdd"
                :class="{
                  valid: !$v.taskContext.clientOrderProcess.clientOrder.deliveryAdd.$invalid,
                  invalid: $v.taskContext.clientOrderProcess.clientOrder.deliveryAdd.$invalid,
                }"
                v-model="$v.taskContext.clientOrderProcess.clientOrder.deliveryAdd.$model"
              />
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('cabonpontocomApp.taskGetPaid.orderPrice')" for="task-get-paid-orderPrice"
                >Order Price</label
              >
              <input
                type="number"
                class="form-control"
                name="orderPrice"
                id="task-get-paid-orderPrice"
                readonly
                data-cy="orderPrice"
                :class="{
                  valid: !$v.taskContext.clientOrderProcess.clientOrder.orderPrice.$invalid,
                  invalid: $v.taskContext.clientOrderProcess.clientOrder.orderPrice.$invalid,
                }"
                v-model.number="$v.taskContext.clientOrderProcess.clientOrder.orderPrice.$model"
              />
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('cabonpontocomApp.taskGetPaid.payment')" for="task-get-paid-payment"
                >Payment</label
              >
              <input
                type="text"
                class="form-control"
                name="payment"
                id="task-get-paid-payment"
                data-cy="payment"
                :class="{
                  valid: !$v.taskContext.clientOrderProcess.clientOrder.payment.$invalid,
                  invalid: $v.taskContext.clientOrderProcess.clientOrder.payment.$invalid,
                }"
                v-model="$v.taskContext.clientOrderProcess.clientOrder.payment.$model"
                required
              />
              <div
                v-if="
                  $v.taskContext.clientOrderProcess.clientOrder.payment.$anyDirty &&
                  $v.taskContext.clientOrderProcess.clientOrder.payment.$invalid
                "
              >
                <small
                  class="form-text text-danger"
                  v-if="!$v.taskContext.clientOrderProcess.clientOrder.payment.required"
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

<script lang="ts" src="./task-get-paid-execute.component.ts"></script>
