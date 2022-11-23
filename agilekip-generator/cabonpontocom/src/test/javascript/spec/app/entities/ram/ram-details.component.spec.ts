/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import RamDetailComponent from '@/entities/ram/ram-details.vue';
import RamClass from '@/entities/ram/ram-details.component';
import RamService from '@/entities/ram/ram.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Ram Management Detail Component', () => {
    let wrapper: Wrapper<RamClass>;
    let comp: RamClass;
    let ramServiceStub: SinonStubbedInstance<RamService>;

    beforeEach(() => {
      ramServiceStub = sinon.createStubInstance<RamService>(RamService);

      wrapper = shallowMount<RamClass>(RamDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { ramService: () => ramServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRam = { id: 123 };
        ramServiceStub.find.resolves(foundRam);

        // WHEN
        comp.retrieveRam(123);
        await comp.$nextTick();

        // THEN
        expect(comp.ram).toBe(foundRam);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRam = { id: 123 };
        ramServiceStub.find.resolves(foundRam);

        // WHEN
        comp.beforeRouteEnter({ params: { ramId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.ram).toBe(foundRam);
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
