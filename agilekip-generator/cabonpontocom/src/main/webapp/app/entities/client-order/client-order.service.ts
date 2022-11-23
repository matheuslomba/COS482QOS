import axios from 'axios';

import { IClientOrder } from '@/shared/model/client-order.model';

const baseApiUrl = 'api/client-orders';

export default class ClientOrderService {
  public find(id: number): Promise<IClientOrder> {
    return new Promise<IClientOrder>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieve(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
