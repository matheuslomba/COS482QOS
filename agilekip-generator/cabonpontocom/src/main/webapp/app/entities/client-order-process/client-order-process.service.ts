import axios from 'axios';

import { IClientOrderProcess } from '@/shared/model/client-order-process.model';

const baseApiUrl = 'api/client-order-processes';

export default class ClientOrderProcessService {
  public find(id: number): Promise<IClientOrderProcess> {
    return new Promise<IClientOrderProcess>((resolve, reject) => {
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

  public create(entity: IClientOrderProcess): Promise<IClientOrderProcess> {
    return new Promise<IClientOrderProcess>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
