/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import PowerSourceUpdateComponent from '@/entities/power-source/power-source-update.vue';
import PowerSourceClass from '@/entities/power-source/power-source-update.component';
import PowerSourceService from '@/entities/power-source/power-source.service';

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
  describe('PowerSource Management Update Component', () => {
    let wrapper: Wrapper<PowerSourceClass>;
    let comp: PowerSourceClass;
    let powerSourceServiceStub: SinonStubbedInstance<PowerSourceService>;

    beforeEach(() => {
      powerSourceServiceStub = sinon.createStubInstance<PowerSourceService>(PowerSourceService);

      wrapper = shallowMount<PowerSourceClass>(PowerSourceUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          powerSourceService: () => powerSourceServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.powerSource = entity;
        powerSourceServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(powerSourceServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.powerSource = entity;
        powerSourceServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(powerSourceServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPowerSource = { id: 123 };
        powerSourceServiceStub.find.resolves(foundPowerSource);
        powerSourceServiceStub.retrieve.resolves([foundPowerSource]);

        // WHEN
        comp.beforeRouteEnter({ params: { powerSourceId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.powerSource).toBe(foundPowerSource);
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
