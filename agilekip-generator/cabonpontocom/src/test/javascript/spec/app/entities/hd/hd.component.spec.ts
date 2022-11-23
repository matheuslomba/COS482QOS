/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import HdComponent from '@/entities/hd/hd.vue';
import HdClass from '@/entities/hd/hd.component';
import HdService from '@/entities/hd/hd.service';

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
  describe('Hd Management Component', () => {
    let wrapper: Wrapper<HdClass>;
    let comp: HdClass;
    let hdServiceStub: SinonStubbedInstance<HdService>;

    beforeEach(() => {
      hdServiceStub = sinon.createStubInstance<HdService>(HdService);
      hdServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<HdClass>(HdComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          hdService: () => hdServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      hdServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllHds();
      await comp.$nextTick();

      // THEN
      expect(hdServiceStub.retrieve.called).toBeTruthy();
      expect(comp.hds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      hdServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeHd();
      await comp.$nextTick();

      // THEN
      expect(hdServiceStub.delete.called).toBeTruthy();
      expect(hdServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
