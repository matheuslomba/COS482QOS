/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import HdUpdateComponent from '@/entities/hd/hd-update.vue';
import HdClass from '@/entities/hd/hd-update.component';
import HdService from '@/entities/hd/hd.service';

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
  describe('Hd Management Update Component', () => {
    let wrapper: Wrapper<HdClass>;
    let comp: HdClass;
    let hdServiceStub: SinonStubbedInstance<HdService>;

    beforeEach(() => {
      hdServiceStub = sinon.createStubInstance<HdService>(HdService);

      wrapper = shallowMount<HdClass>(HdUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          hdService: () => hdServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.hd = entity;
        hdServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(hdServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.hd = entity;
        hdServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(hdServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundHd = { id: 123 };
        hdServiceStub.find.resolves(foundHd);
        hdServiceStub.retrieve.resolves([foundHd]);

        // WHEN
        comp.beforeRouteEnter({ params: { hdId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.hd).toBe(foundHd);
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
