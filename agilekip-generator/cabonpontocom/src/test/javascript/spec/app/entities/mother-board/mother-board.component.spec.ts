/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import MotherBoardComponent from '@/entities/mother-board/mother-board.vue';
import MotherBoardClass from '@/entities/mother-board/mother-board.component';
import MotherBoardService from '@/entities/mother-board/mother-board.service';

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
  describe('MotherBoard Management Component', () => {
    let wrapper: Wrapper<MotherBoardClass>;
    let comp: MotherBoardClass;
    let motherBoardServiceStub: SinonStubbedInstance<MotherBoardService>;

    beforeEach(() => {
      motherBoardServiceStub = sinon.createStubInstance<MotherBoardService>(MotherBoardService);
      motherBoardServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<MotherBoardClass>(MotherBoardComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          motherBoardService: () => motherBoardServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      motherBoardServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllMotherBoards();
      await comp.$nextTick();

      // THEN
      expect(motherBoardServiceStub.retrieve.called).toBeTruthy();
      expect(comp.motherBoards[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      motherBoardServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeMotherBoard();
      await comp.$nextTick();

      // THEN
      expect(motherBoardServiceStub.delete.called).toBeTruthy();
      expect(motherBoardServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
