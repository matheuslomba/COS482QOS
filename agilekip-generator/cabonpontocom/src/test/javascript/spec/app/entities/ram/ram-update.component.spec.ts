/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import RamUpdateComponent from '@/entities/ram/ram-update.vue';
import RamClass from '@/entities/ram/ram-update.component';
import RamService from '@/entities/ram/ram.service';

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
  describe('Ram Management Update Component', () => {
    let wrapper: Wrapper<RamClass>;
    let comp: RamClass;
    let ramServiceStub: SinonStubbedInstance<RamService>;

    beforeEach(() => {
      ramServiceStub = sinon.createStubInstance<RamService>(RamService);

      wrapper = shallowMount<RamClass>(RamUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          ramService: () => ramServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.ram = entity;
        ramServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(ramServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.ram = entity;
        ramServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(ramServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRam = { id: 123 };
        ramServiceStub.find.resolves(foundRam);
        ramServiceStub.retrieve.resolves([foundRam]);

        // WHEN
        comp.beforeRouteEnter({ params: { ramId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.ram).toBe(foundRam);
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
