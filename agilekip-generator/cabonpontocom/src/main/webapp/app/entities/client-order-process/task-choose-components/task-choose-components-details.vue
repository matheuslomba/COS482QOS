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
              <label class="form-control-label" v-text="$t('cabonpontocomApp.taskChooseComponents.orderID')">orderID</label>
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
              <label class="form-control-label" v-text="$t('cabonpontocomApp.taskChooseComponents.orderDate')">orderDate</label>
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
              <label class="form-control-label" v-text="$t('cabonpontocomApp.taskChooseComponents.clientName')">clientName</label>
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
              <label class="form-control-label" v-text="$t('cabonpontocomApp.taskChooseComponents.numComponents')">numComponents</label>
              <input
                readonly
                type="text"
                class="form-control"
                name="numComponents"
                id="client-order-numComponents"
                data-cy="numComponents"
                v-model="taskContext.clientOrderProcess.clientOrder.numComponents"
              />
            </div>
            <div class="form-group">
              <label
                class="form-control-label"
                v-text="$t('cabonpontocomApp.taskChooseComponents.motherBoard')"
                for="task-choose-components-motherBoard"
                >Mother Board</label
              >
              <input
                v-if="taskContext.clientOrderProcess.clientOrder.motherBoard"
                readonly
                type="text"
                class="form-control"
                name="motherBoard"
                id="client-order-motherBoard"
                data-cy="motherBoard"
                :value="taskContext.clientOrderProcess.clientOrder.motherBoard.motherBoardName"
              />
              <input
                v-else
                readonly
                type="text"
                class="form-control"
                name="motherBoard"
                id="client-order-motherBoard"
                data-cy="motherBoard"
                value=""
              />
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('cabonpontocomApp.taskChooseComponents.cpu')" for="task-choose-components-cpu"
                >Cpu</label
              >
              <input
                v-if="taskContext.clientOrderProcess.clientOrder.cpu"
                readonly
                type="text"
                class="form-control"
                name="cpu"
                id="client-order-cpu"
                data-cy="cpu"
                :value="taskContext.clientOrderProcess.clientOrder.cpu.cpuName"
              />
              <input v-else readonly type="text" class="form-control" name="cpu" id="client-order-cpu" data-cy="cpu" value="" />
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('cabonpontocomApp.taskChooseComponents.gpu')" for="task-choose-components-gpu"
                >Gpu</label
              >
              <input
                v-if="taskContext.clientOrderProcess.clientOrder.gpu"
                readonly
                type="text"
                class="form-control"
                name="gpu"
                id="client-order-gpu"
                data-cy="gpu"
                :value="taskContext.clientOrderProcess.clientOrder.gpu.gpuName"
              />
              <input v-else readonly type="text" class="form-control" name="gpu" id="client-order-gpu" data-cy="gpu" value="" />
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('cabonpontocomApp.taskChooseComponents.ram')" for="task-choose-components-ram"
                >Ram</label
              >
              <input
                v-if="taskContext.clientOrderProcess.clientOrder.ram"
                readonly
                type="text"
                class="form-control"
                name="ram"
                id="client-order-ram"
                data-cy="ram"
                :value="taskContext.clientOrderProcess.clientOrder.ram.ramName"
              />
              <input v-else readonly type="text" class="form-control" name="ram" id="client-order-ram" data-cy="ram" value="" />
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('cabonpontocomApp.taskChooseComponents.hd')" for="task-choose-components-hd"
                >Hd</label
              >
              <input
                v-if="taskContext.clientOrderProcess.clientOrder.hd"
                readonly
                type="text"
                class="form-control"
                name="hd"
                id="client-order-hd"
                data-cy="hd"
                :value="taskContext.clientOrderProcess.clientOrder.hd.hdName"
              />
              <input v-else readonly type="text" class="form-control" name="hd" id="client-order-hd" data-cy="hd" value="" />
            </div>
            <div class="form-group">
              <label
                class="form-control-label"
                v-text="$t('cabonpontocomApp.taskChooseComponents.powerSource')"
                for="task-choose-components-powerSource"
                >Power Source</label
              >
              <input
                v-if="taskContext.clientOrderProcess.clientOrder.powerSource"
                readonly
                type="text"
                class="form-control"
                name="powerSource"
                id="client-order-powerSource"
                data-cy="powerSource"
                :value="taskContext.clientOrderProcess.clientOrder.powerSource.powerSourceName"
              />
              <input
                v-else
                readonly
                type="text"
                class="form-control"
                name="powerSource"
                id="client-order-powerSource"
                data-cy="powerSource"
                value=""
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
