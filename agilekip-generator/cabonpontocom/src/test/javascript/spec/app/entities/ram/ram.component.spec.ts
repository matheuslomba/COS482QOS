/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import RamComponent from '@/entities/ram/ram.vue';
import RamClass from '@/entities/ram/ram.component';
import RamService from '@/entities/ram/ram.service';

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
  describe('Ram Management Component', () => {
    let wrapper: Wrapper<RamClass>;
    let comp: RamClass;
    let ramServiceStub: SinonStubbedInstance<RamService>;

    beforeEach(() => {
      ramServiceStub = sinon.createStubInstance<RamService>(RamService);
      ramServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<RamClass>(RamComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          ramService: () => ramServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      ramServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllRams();
      await comp.$nextTick();

      // THEN
      expect(ramServiceStub.retrieve.called).toBeTruthy();
      expect(comp.rams[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      ramServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeRam();
      await comp.$nextTick();

      // THEN
      expect(ramServiceStub.delete.called).toBeTruthy();
      expect(ramServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
