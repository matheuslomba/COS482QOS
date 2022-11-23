/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import MotherBoardUpdateComponent from '@/entities/mother-board/mother-board-update.vue';
import MotherBoardClass from '@/entities/mother-board/mother-board-update.component';
import MotherBoardService from '@/entities/mother-board/mother-board.service';

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
  describe('MotherBoard Management Update Component', () => {
    let wrapper: Wrapper<MotherBoardClass>;
    let comp: MotherBoardClass;
    let motherBoardServiceStub: SinonStubbedInstance<MotherBoardService>;

    beforeEach(() => {
      motherBoardServiceStub = sinon.createStubInstance<MotherBoardService>(MotherBoardService);

      wrapper = shallowMount<MotherBoardClass>(MotherBoardUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          motherBoardService: () => motherBoardServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.motherBoard = entity;
        motherBoardServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(motherBoardServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.motherBoard = entity;
        motherBoardServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(motherBoardServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMotherBoard = { id: 123 };
        motherBoardServiceStub.find.resolves(foundMotherBoard);
        motherBoardServiceStub.retrieve.resolves([foundMotherBoard]);

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
