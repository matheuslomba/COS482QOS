<template>
  <div>
    <h2 id="page-heading" data-cy="ClientOrderHeading">
      <span v-text="$t('cabonpontocomApp.clientOrder.home.title')" id="client-order-heading">Client Orders</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('cabonpontocomApp.clientOrder.home.refreshListLabel')">Refresh List</span>
        </button>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && clientOrders && clientOrders.length === 0">
      <span v-text="$t('cabonpontocomApp.clientOrder.home.notFound')">No clientOrders found</span>
    </div>
    <div class="table-responsive" v-if="clientOrders && clientOrders.length > 0">
      <table class="table table-striped" aria-describedby="clientOrders">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('cabonpontocomApp.clientOrder.orderID')">Order ID</span></th>
            <th scope="row"><span v-text="$t('cabonpontocomApp.clientOrder.orderDate')">Order Date</span></th>
            <th scope="row"><span v-text="$t('cabonpontocomApp.clientOrder.clientName')">Client Name</span></th>
            <th scope="row"><span v-text="$t('cabonpontocomApp.clientOrder.clientEmail')">Client Email</span></th>
            <th scope="row"><span v-text="$t('cabonpontocomApp.clientOrder.orderPrice')">Order Price</span></th>
            <th scope="row"><span v-text="$t('cabonpontocomApp.clientOrder.numComponents')">Num Components</span></th>
            <th scope="row"><span v-text="$t('cabonpontocomApp.clientOrder.payment')">Payment</span></th>
            <th scope="row"><span v-text="$t('cabonpontocomApp.clientOrder.proceedToCheckout')">Proceed To Checkout</span></th>
            <th scope="row"><span v-text="$t('cabonpontocomApp.clientOrder.assemblyPC')">Assembly PC</span></th>
            <th scope="row"><span v-text="$t('cabonpontocomApp.clientOrder.deliveryAdd')">Delivery Add</span></th>
            <th scope="row"><span v-text="$t('cabonpontocomApp.clientOrder.isCompatible')">Is Compatible</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="clientOrder in clientOrders" :key="clientOrder.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ClientOrderView', params: { clientOrderId: clientOrder.id } }">{{ clientOrder.id }}</router-link>
            </td>
            <td>{{ clientOrder.orderID }}</td>
            <td>{{ clientOrder.orderDate }}</td>
            <td>{{ clientOrder.clientName }}</td>
            <td>{{ clientOrder.clientEmail }}</td>
            <td>{{ clientOrder.orderPrice }}</td>
            <td>{{ clientOrder.numComponents }}</td>
            <td>{{ clientOrder.payment }}</td>
            <td>{{ clientOrder.proceedToCheckout }}</td>
            <td>{{ clientOrder.assemblyPC }}</td>
            <td>{{ clientOrder.deliveryAdd }}</td>
            <td>{{ clientOrder.isCompatible }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ClientOrderView', params: { clientOrderId: clientOrder.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="cabonpontocomApp.clientOrder.delete.question" data-cy="clientOrderDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-clientOrder-heading" v-text="$t('cabonpontocomApp.clientOrder.delete.question', { id: removeId })">
          Are you sure you want to delete this Client Order?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-clientOrder"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeClientOrder()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./client-order.component.ts"></script>
