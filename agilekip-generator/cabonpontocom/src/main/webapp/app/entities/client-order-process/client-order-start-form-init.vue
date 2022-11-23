<template>
  <div class="row justify-content-center">
    <div class="col-10">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="cabonpontocomApp.clientOrderStartForm.home.createOrEditLabel"
          data-cy="ClientOrderStartFormCreateUpdateHeading"
          v-text="$t('cabonpontocomApp.clientOrderStartForm.home.createOrEditLabel')"
        >
          Create or edit a ClientOrderStartForm
        </h2>
        <div v-if="clientOrderProcess.processInstance">
          <akip-show-process-definition :processDefinition="clientOrderProcess.processInstance.processDefinition">
            <template v-slot:body>
              <hr />
              <div v-if="clientOrderProcess.clientOrder">
                <div class="form-group">
                  <label
                    class="form-control-label"
                    v-text="$t('cabonpontocomApp.clientOrderStartForm.orderID')"
                    for="client-order-start-form-orderID"
                    >Order ID</label
                  >
                  <input
                    type="text"
                    class="form-control"
                    name="orderID"
                    id="client-order-start-form-orderID"
                    data-cy="orderID"
                    :class="{
                      valid: !$v.clientOrderProcess.clientOrder.orderID.$invalid,
                      invalid: $v.clientOrderProcess.clientOrder.orderID.$invalid,
                    }"
                    v-model="$v.clientOrderProcess.clientOrder.orderID.$model"
                  />
                </div>
                <div class="form-group">
                  <label
                    class="form-control-label"
                    v-text="$t('cabonpontocomApp.clientOrderStartForm.orderDate')"
                    for="client-order-start-form-orderDate"
                    >Order Date</label
                  >
                  <b-input-group class="mb-3">
                    <b-input-group-prepend>
                      <b-form-datepicker
                        aria-controls="client-order-start-form-orderDate"
                        v-model="$v.clientOrderProcess.clientOrder.orderDate.$model"
                        name="orderDate"
                        class="form-control"
                        :locale="currentLanguage"
                        button-only
                        today-button
                        reset-button
                        close-button
                      >
                      </b-form-datepicker>
                    </b-input-group-prepend>
                    <b-form-input
                      id="client-order-start-form-orderDate"
                      data-cy="orderDate"
                      type="text"
                      class="form-control"
                      name="orderDate"
                      :class="{
                        valid: !$v.clientOrderProcess.clientOrder.orderDate.$invalid,
                        invalid: $v.clientOrderProcess.clientOrder.orderDate.$invalid,
                      }"
                      v-model="$v.clientOrderProcess.clientOrder.orderDate.$model"
                    />
                  </b-input-group>
                </div>
                <div class="form-group">
                  <label
                    class="form-control-label"
                    v-text="$t('cabonpontocomApp.clientOrderStartForm.clientName')"
                    for="client-order-start-form-clientName"
                    >Client Name</label
                  >
                  <input
                    type="text"
                    class="form-control"
                    name="clientName"
                    id="client-order-start-form-clientName"
                    data-cy="clientName"
                    :class="{
                      valid: !$v.clientOrderProcess.clientOrder.clientName.$invalid,
                      invalid: $v.clientOrderProcess.clientOrder.clientName.$invalid,
                    }"
                    v-model="$v.clientOrderProcess.clientOrder.clientName.$model"
                  />
                </div>
                <div class="form-group">
                  <label
                    class="form-control-label"
                    v-text="$t('cabonpontocomApp.clientOrderStartForm.clientEmail')"
                    for="client-order-start-form-clientEmail"
                    >Client Email</label
                  >
                  <input
                    type="text"
                    class="form-control"
                    name="clientEmail"
                    id="client-order-start-form-clientEmail"
                    data-cy="clientEmail"
                    :class="{
                      valid: !$v.clientOrderProcess.clientOrder.clientEmail.$invalid,
                      invalid: $v.clientOrderProcess.clientOrder.clientEmail.$invalid,
                    }"
                    v-model="$v.clientOrderProcess.clientOrder.clientEmail.$model"
                  />
                </div>
              </div>
            </template>
          </akip-show-process-definition>
          <br />
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.clientOrderProcess.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="play"></font-awesome-icon>&nbsp;<span>Start</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./client-order-start-form-init.component.ts"></script>
