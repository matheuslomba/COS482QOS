/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import MotherBoardDetailComponent from '@/entities/mother-board/mother-board-details.vue';
import MotherBoardClass from '@/entities/mother-board/mother-board-details.component';
import MotherBoardService from '@/entities/mother-board/mother-board.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('MotherBoard Management Detail Component', () => {
    let wrapper: Wrapper<MotherBoardClass>;
    let comp: MotherBoardClass;
    let motherBoardServiceStub: SinonStubbedInstance<MotherBoardService>;

    beforeEach(() => {
      motherBoardServiceStub = sinon.createStubInstance<MotherBoardService>(MotherBoardService);

      wrapper = shallowMount<MotherBoardClass>(MotherBoardDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { motherBoardService: () => motherBoardServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMotherBoard = { id: 123 };
        motherBoardServiceStub.find.resolves(foundMotherBoard);

        // WHEN
        comp.retrieveMotherBoard(123);
        await comp.$nextTick();

        // THEN
        expect(comp.motherBoard).toBe(foundMotherBoard);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMotherBoard = { id: 123 };
        motherBoardServiceStub.find.resolves(foundMotherBoard);

        // WHEN
        comp.beforeRouteEnter({ params: { motherBoardId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.motherBoard).toBe(foundMotherBoard);
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
