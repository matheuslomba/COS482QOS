/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import CpuDetailComponent from '@/entities/cpu/cpu-details.vue';
import CpuClass from '@/entities/cpu/cpu-details.component';
import CpuService from '@/entities/cpu/cpu.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Cpu Management Detail Component', () => {
    let wrapper: Wrapper<CpuClass>;
    let comp: CpuClass;
    let cpuServiceStub: SinonStubbedInstance<CpuService>;

    beforeEach(() => {
      cpuServiceStub = sinon.createStubInstance<CpuService>(CpuService);

      wrapper = shallowMount<CpuClass>(CpuDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { cpuService: () => cpuServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCpu = { id: 123 };
        cpuServiceStub.find.resolves(foundCpu);

        // WHEN
        comp.retrieveCpu(123);
        await comp.$nextTick();

        // THEN
        expect(comp.cpu).toBe(foundCpu);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCpu = { id: 123 };
        cpuServiceStub.find.resolves(foundCpu);

        // WHEN
        comp.beforeRouteEnter({ params: { cpuId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.cpu).toBe(foundCpu);
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
