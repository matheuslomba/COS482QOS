/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import CpuUpdateComponent from '@/entities/cpu/cpu-update.vue';
import CpuClass from '@/entities/cpu/cpu-update.component';
import CpuService from '@/entities/cpu/cpu.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Cpu Management Update Component', () => {
    let wrapper: Wrapper<CpuClass>;
    let comp: CpuClass;
    let cpuServiceStub: SinonStubbedInstance<CpuService>;

    beforeEach(() => {
      cpuServiceStub = sinon.createStubInstance<CpuService>(CpuService);

      wrapper = shallowMount<CpuClass>(CpuUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          cpuService: () => cpuServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.cpu = entity;
        cpuServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cpuServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.cpu = entity;
        cpuServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cpuServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCpu = { id: 123 };
        cpuServiceStub.find.resolves(foundCpu);
        cpuServiceStub.retrieve.resolves([foundCpu]);

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
