<template>
  <div>
    <h2 class="jh-entity-heading" data-cy="clientOrderProcessDetailsHeading">
      <span v-text="$t('cabonpontocomApp.clientOrderProcess.home.title')">ClientOrderProcess</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('cabonpontocomApp.clientOrderProcess.home.refreshListLabel')">Refresh List</span>
        </button>

        <router-link :to="`/process-definition/ClientOrderProcess/init`" tag="button" class="btn btn-primary mr-2">
          <font-awesome-icon icon="plus"></font-awesome-icon>
          <span v-text="$t('cabonpontocomApp.clientOrderProcess.home.createLabel')"> Create a new Travel Plan Process Instance </span>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && clientOrderProcessList && clientOrderProcessList.length === 0">
      <span v-text="$t('cabonpontocomApp.clientOrderProcess.home.notFound')">No clientOrderProcess found</span>
    </div>
    <div class="table-responsive" v-if="clientOrderProcessList && clientOrderProcessList.length > 0">
      <table class="table table-striped" aria-describedby="clientOrderProcessList">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span>Process Instance</span></th>
            <th scope="row"><span>Client Order</span></th>
            <th scope="row"><span>Status</span></th>
            <th scope="row"><span>Start Date</span></th>
            <th scope="row"><span>End Date</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="clientOrderProcess in clientOrderProcessList" :key="clientOrderProcess.id" data-cy="entityTable">
            <td>{{ clientOrderProcess.id }}</td>
            <td>
              <div v-if="clientOrderProcess.processInstance">
                <router-link :to="`/process-definition/ClientOrderProcess/instance/${clientOrderProcess.processInstance.id}/view`">
                  {{ clientOrderProcess.processInstance.businessKey }}
                </router-link>
              </div>
            </td>
            <td>
              <div v-if="clientOrderProcess.clientOrder">
                <router-link :to="{ name: 'ClientOrderView', params: { clientOrderId: clientOrderProcess.clientOrder.id } }">{{
                  clientOrderProcess.clientOrder.id
                }}</router-link>
              </div>
            </td>
            <td>
              <akip-show-process-instance-status :status="clientOrderProcess.processInstance.status"></akip-show-process-instance-status>
            </td>
            <td>
              {{
                clientOrderProcess.processInstance.startDate ? $d(Date.parse(clientOrderProcess.processInstance.startDate), 'short') : ''
              }}
            </td>
            <td>
              {{ clientOrderProcess.processInstance.endDate ? $d(Date.parse(clientOrderProcess.processInstance.endDate), 'short') : '' }}
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="`/process-definition/ClientOrderProcess/instance/${clientOrderProcess.processInstance.id}/view`"
                  tag="button"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                </router-link>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
      <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
    </button>
  </div>
</template>

<script lang="ts" src="./client-order-process-list.component.ts"></script>
