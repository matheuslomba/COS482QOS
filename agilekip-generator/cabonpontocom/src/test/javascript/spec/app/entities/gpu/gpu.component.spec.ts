/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import GpuComponent from '@/entities/gpu/gpu.vue';
import GpuClass from '@/entities/gpu/gpu.component';
import GpuService from '@/entities/gpu/gpu.service';

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
  describe('Gpu Management Component', () => {
    let wrapper: Wrapper<GpuClass>;
    let comp: GpuClass;
    let gpuServiceStub: SinonStubbedInstance<GpuService>;

    beforeEach(() => {
      gpuServiceStub = sinon.createStubInstance<GpuService>(GpuService);
      gpuServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<GpuClass>(GpuComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          gpuService: () => gpuServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      gpuServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllGpus();
      await comp.$nextTick();

      // THEN
      expect(gpuServiceStub.retrieve.called).toBeTruthy();
      expect(comp.gpus[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      gpuServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeGpu();
      await comp.$nextTick();

      // THEN
      expect(gpuServiceStub.delete.called).toBeTruthy();
      expect(gpuServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
