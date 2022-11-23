/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import GpuDetailComponent from '@/entities/gpu/gpu-details.vue';
import GpuClass from '@/entities/gpu/gpu-details.component';
import GpuService from '@/entities/gpu/gpu.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Gpu Management Detail Component', () => {
    let wrapper: Wrapper<GpuClass>;
    let comp: GpuClass;
    let gpuServiceStub: SinonStubbedInstance<GpuService>;

    beforeEach(() => {
      gpuServiceStub = sinon.createStubInstance<GpuService>(GpuService);

      wrapper = shallowMount<GpuClass>(GpuDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { gpuService: () => gpuServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundGpu = { id: 123 };
        gpuServiceStub.find.resolves(foundGpu);

        // WHEN
        comp.retrieveGpu(123);
        await comp.$nextTick();

        // THEN
        expect(comp.gpu).toBe(foundGpu);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundGpu = { id: 123 };
        gpuServiceStub.find.resolves(foundGpu);

        // WHEN
        comp.beforeRouteEnter({ params: { gpuId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.gpu).toBe(foundGpu);
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
