/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PowerSourceDetailComponent from '@/entities/power-source/power-source-details.vue';
import PowerSourceClass from '@/entities/power-source/power-source-details.component';
import PowerSourceService from '@/entities/power-source/power-source.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('PowerSource Management Detail Component', () => {
    let wrapper: Wrapper<PowerSourceClass>;
    let comp: PowerSourceClass;
    let powerSourceServiceStub: SinonStubbedInstance<PowerSourceService>;

    beforeEach(() => {
      powerSourceServiceStub = sinon.createStubInstance<PowerSourceService>(PowerSourceService);

      wrapper = shallowMount<PowerSourceClass>(PowerSourceDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { powerSourceService: () => powerSourceServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPowerSource = { id: 123 };
        powerSourceServiceStub.find.resolves(foundPowerSource);

        // WHEN
        comp.retrievePowerSource(123);
        await comp.$nextTick();

        // THEN
        expect(comp.powerSource).toBe(foundPowerSource);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPowerSource = { id: 123 };
        powerSourceServiceStub.find.resolves(foundPowerSource);

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
