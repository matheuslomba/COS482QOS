/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import HdDetailComponent from '@/entities/hd/hd-details.vue';
import HdClass from '@/entities/hd/hd-details.component';
import HdService from '@/entities/hd/hd.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Hd Management Detail Component', () => {
    let wrapper: Wrapper<HdClass>;
    let comp: HdClass;
    let hdServiceStub: SinonStubbedInstance<HdService>;

    beforeEach(() => {
      hdServiceStub = sinon.createStubInstance<HdService>(HdService);

      wrapper = shallowMount<HdClass>(HdDetailComponent, { store, i18n, localVue, router, provide: { hdService: () => hdServiceStub } });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundHd = { id: 123 };
        hdServiceStub.find.resolves(foundHd);

        // WHEN
        comp.retrieveHd(123);
        await comp.$nextTick();

        // THEN
        expect(comp.hd).toBe(foundHd);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundHd = { id: 123 };
        hdServiceStub.find.resolves(foundHd);

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
