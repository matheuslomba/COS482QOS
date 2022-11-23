/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ClientOrderDetailComponent from '@/entities/client-order/client-order-details.vue';
import ClientOrderClass from '@/entities/client-order/client-order-details.component';
import ClientOrderService from '@/entities/client-order/client-order.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ClientOrder Management Detail Component', () => {
    let wrapper: Wrapper<ClientOrderClass>;
    let comp: ClientOrderClass;
    let clientOrderServiceStub: SinonStubbedInstance<ClientOrderService>;

    beforeEach(() => {
      clientOrderServiceStub = sinon.createStubInstance<ClientOrderService>(ClientOrderService);

      wrapper = shallowMount<ClientOrderClass>(ClientOrderDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { clientOrderService: () => clientOrderServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundClientOrder = { id: 123 };
        clientOrderServiceStub.find.resolves(foundClientOrder);

        // WHEN
        comp.retrieveClientOrder(123);
        await comp.$nextTick();

        // THEN
        expect(comp.clientOrder).toBe(foundClientOrder);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundClientOrder = { id: 123 };
        clientOrderServiceStub.find.resolves(foundClientOrder);

        // WHEN
        comp.beforeRouteEnter({ params: { clientOrderId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.clientOrder).toBe(foundClientOrder);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
