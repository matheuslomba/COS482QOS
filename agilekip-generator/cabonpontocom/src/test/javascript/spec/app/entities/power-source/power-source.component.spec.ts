/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import PowerSourceComponent from '@/entities/power-source/power-source.vue';
import PowerSourceClass from '@/entities/power-source/power-source.component';
import PowerSourceService from '@/entities/power-source/power-source.service';

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
  describe('PowerSource Management Component', () => {
    let wrapper: Wrapper<PowerSourceClass>;
    let comp: PowerSourceClass;
    let powerSourceServiceStub: SinonStubbedInstance<PowerSourceService>;

    beforeEach(() => {
      powerSourceServiceStub = sinon.createStubInstance<PowerSourceService>(PowerSourceService);
      powerSourceServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PowerSourceClass>(PowerSourceComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          powerSourceService: () => powerSourceServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      powerSourceServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPowerSources();
      await comp.$nextTick();

      // THEN
      expect(powerSourceServiceStub.retrieve.called).toBeTruthy();
      expect(comp.powerSources[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      powerSourceServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removePowerSource();
      await comp.$nextTick();

      // THEN
      expect(powerSourceServiceStub.delete.called).toBeTruthy();
      expect(powerSourceServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
