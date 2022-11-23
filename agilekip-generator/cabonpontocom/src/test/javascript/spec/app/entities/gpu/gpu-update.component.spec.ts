/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import GpuUpdateComponent from '@/entities/gpu/gpu-update.vue';
import GpuClass from '@/entities/gpu/gpu-update.component';
import GpuService from '@/entities/gpu/gpu.service';

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
  describe('Gpu Management Update Component', () => {
    let wrapper: Wrapper<GpuClass>;
    let comp: GpuClass;
    let gpuServiceStub: SinonStubbedInstance<GpuService>;

    beforeEach(() => {
      gpuServiceStub = sinon.createStubInstance<GpuService>(GpuService);

      wrapper = shallowMount<GpuClass>(GpuUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          gpuService: () => gpuServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.gpu = entity;
        gpuServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(gpuServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.gpu = entity;
        gpuServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(gpuServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundGpu = { id: 123 };
        gpuServiceStub.find.resolves(foundGpu);
        gpuServiceStub.retrieve.resolves([foundGpu]);

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
