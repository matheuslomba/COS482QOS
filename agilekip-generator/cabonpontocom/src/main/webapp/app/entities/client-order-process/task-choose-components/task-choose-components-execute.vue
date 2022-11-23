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
            <div class="form-group">
              <label
                class="form-control-label"
                v-text="$t('cabonpontocomApp.taskChooseComponents.motherBoard')"
                for="task-choose-components-motherBoard"
                >Mother Board</label
              >
              <select
                class="form-control"
                id="task-choose-components-motherBoard"
                data-cy="motherBoard"
                name="motherBoard"
                v-model="taskContext.clientOrderProcess.clientOrder.motherBoard"
              >
                <option v-bind:value="null"></option>
                <option
                  v-bind:value="
                    taskContext.clientOrderProcess.clientOrder.motherBoard &&
                    motherBoardOption.id === taskContext.clientOrderProcess.clientOrder.motherBoard.id
                      ? taskContext.clientOrderProcess.clientOrder.motherBoard
                      : motherBoardOption
                  "
                  v-for="motherBoardOption in motherBoards"
                  :key="motherBoardOption.id"
                >
                  {{ motherBoardOption.motherBoardName }}
                </option>
              </select>
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('cabonpontocomApp.taskChooseComponents.cpu')" for="task-choose-components-cpu"
                >Cpu</label
              >
              <select
                class="form-control"
                id="task-choose-components-cpu"
                data-cy="cpu"
                name="cpu"
                v-model="taskContext.clientOrderProcess.clientOrder.cpu"
              >
                <option v-bind:value="null"></option>
                <option
                  v-bind:value="
                    taskContext.clientOrderProcess.clientOrder.cpu && cpuOption.id === taskContext.clientOrderProcess.clientOrder.cpu.id
                      ? taskContext.clientOrderProcess.clientOrder.cpu
                      : cpuOption
                  "
                  v-for="cpuOption in cpus"
                  :key="cpuOption.id"
                >
                  {{ cpuOption.cpuName }}
                </option>
              </select>
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('cabonpontocomApp.taskChooseComponents.gpu')" for="task-choose-components-gpu"
                >Gpu</label
              >
              <select
                class="form-control"
                id="task-choose-components-gpu"
                data-cy="gpu"
                name="gpu"
                v-model="taskContext.clientOrderProcess.clientOrder.gpu"
              >
                <option v-bind:value="null"></option>
                <option
                  v-bind:value="
                    taskContext.clientOrderProcess.clientOrder.gpu && gpuOption.id === taskContext.clientOrderProcess.clientOrder.gpu.id
                      ? taskContext.clientOrderProcess.clientOrder.gpu
                      : gpuOption
                  "
                  v-for="gpuOption in gpus"
                  :key="gpuOption.id"
                >
                  {{ gpuOption.gpuName }}
                </option>
              </select>
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('cabonpontocomApp.taskChooseComponents.ram')" for="task-choose-components-ram"
                >Ram</label
              >
              <select
                class="form-control"
                id="task-choose-components-ram"
                data-cy="ram"
                name="ram"
                v-model="taskContext.clientOrderProcess.clientOrder.ram"
              >
                <option v-bind:value="null"></option>
                <option
                  v-bind:value="
                    taskContext.clientOrderProcess.clientOrder.ram && ramOption.id === taskContext.clientOrderProcess.clientOrder.ram.id
                      ? taskContext.clientOrderProcess.clientOrder.ram
                      : ramOption
                  "
                  v-for="ramOption in rams"
                  :key="ramOption.id"
                >
                  {{ ramOption.ramName }}
                </option>
              </select>
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('cabonpontocomApp.taskChooseComponents.hd')" for="task-choose-components-hd"
                >Hd</label
              >
              <select
                class="form-control"
                id="task-choose-components-hd"
                data-cy="hd"
                name="hd"
                v-model="taskContext.clientOrderProcess.clientOrder.hd"
              >
                <option v-bind:value="null"></option>
                <option
                  v-bind:value="
                    taskContext.clientOrderProcess.clientOrder.hd && hdOption.id === taskContext.clientOrderProcess.clientOrder.hd.id
                      ? taskContext.clientOrderProcess.clientOrder.hd
                      : hdOption
                  "
                  v-for="hdOption in hds"
                  :key="hdOption.id"
                >
                  {{ hdOption.hdName }}
                </option>
              </select>
            </div>
            <div class="form-group">
              <label
                class="form-control-label"
                v-text="$t('cabonpontocomApp.taskChooseComponents.powerSource')"
                for="task-choose-components-powerSource"
                >Power Source</label
              >
              <select
                class="form-control"
                id="task-choose-components-powerSource"
                data-cy="powerSource"
                name="powerSource"
                v-model="taskContext.clientOrderProcess.clientOrder.powerSource"
              >
                <option v-bind:value="null"></option>
                <option
                  v-bind:value="
                    taskContext.clientOrderProcess.clientOrder.powerSource &&
                    powerSourceOption.id === taskContext.clientOrderProcess.clientOrder.powerSource.id
                      ? taskContext.clientOrderProcess.clientOrder.powerSource
                      : powerSourceOption
                  "
                  v-for="powerSourceOption in powerSources"
                  :key="powerSourceOption.id"
                >
                  {{ powerSourceOption.powerSourceName }}
                </option>
              </select>
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
