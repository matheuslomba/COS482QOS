/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ClientOrderComponent from '@/entities/client-order/client-order.vue';
import ClientOrderClass from '@/entities/client-order/client-order.component';
import ClientOrderService from '@/entities/client-order/client-order.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('ClientOrder Management Component', () => {
    let wrapper: Wrapper<ClientOrderClass>;
    let comp: ClientOrderClass;
    let clientOrderServiceStub: SinonStubbedInstance<ClientOrderService>;

    beforeEach(() => {
      clientOrderServiceStub = sinon.createStubInstance<ClientOrderService>(ClientOrderService);
      clientOrderServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ClientOrderClass>(ClientOrderComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          clientOrderService: () => clientOrderServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      clientOrderServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllClientOrders();
      await comp.$nextTick();

      // THEN
      expect(clientOrderServiceStub.retrieve.called).toBeTruthy();
      expect(comp.clientOrders[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
