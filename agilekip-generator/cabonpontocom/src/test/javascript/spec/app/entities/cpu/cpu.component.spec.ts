/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CpuComponent from '@/entities/cpu/cpu.vue';
import CpuClass from '@/entities/cpu/cpu.component';
import CpuService from '@/entities/cpu/cpu.service';

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
  describe('Cpu Management Component', () => {
    let wrapper: Wrapper<CpuClass>;
    let comp: CpuClass;
    let cpuServiceStub: SinonStubbedInstance<CpuService>;

    beforeEach(() => {
      cpuServiceStub = sinon.createStubInstance<CpuService>(CpuService);
      cpuServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CpuClass>(CpuComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          cpuService: () => cpuServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      cpuServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllCpus();
      await comp.$nextTick();

      // THEN
      expect(cpuServiceStub.retrieve.called).toBeTruthy();
      expect(comp.cpus[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      cpuServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeCpu();
      await comp.$nextTick();

      // THEN
      expect(cpuServiceStub.delete.called).toBeTruthy();
      expect(cpuServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
