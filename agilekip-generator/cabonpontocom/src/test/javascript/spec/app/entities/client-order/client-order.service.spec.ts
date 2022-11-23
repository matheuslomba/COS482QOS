/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_FORMAT } from '@/shared/date/filters';
import ClientOrderService from '@/entities/client-order/client-order.service';
import { ClientOrder } from '@/shared/model/client-order.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('ClientOrder Service', () => {
    let service: ClientOrderService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new ClientOrderService();
      currentDate = new Date();
      elemDefault = new ClientOrder(0, 'AAAAAAA', currentDate, 'AAAAAAA', 'AAAAAAA', 0, 0, 'AAAAAAA', false, false, 'AAAAAAA', false);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            orderDate: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of ClientOrder', async () => {
        const returnedFromService = Object.assign(
          {
            orderID: 'BBBBBB',
            orderDate: dayjs(currentDate).format(DATE_FORMAT),
            clientName: 'BBBBBB',
            clientEmail: 'BBBBBB',
            orderPrice: 1,
            numComponents: 1,
            payment: 'BBBBBB',
            proceedToCheckout: true,
            assemblyPC: true,
            deliveryAdd: 'BBBBBB',
            isCompatible: true,
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            orderDate: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of ClientOrder', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
