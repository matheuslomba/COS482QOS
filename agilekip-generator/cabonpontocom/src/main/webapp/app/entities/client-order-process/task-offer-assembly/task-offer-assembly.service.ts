import axios from 'axios';
import { TaskOfferAssemblyContext } from './task-offer-assembly.model';

const baseApiUrl = 'api/client-order-process/task-offer-assembly';

export default class TaskOfferAssemblyService {
  public loadContext(taskId: number): Promise<TaskOfferAssemblyContext> {
    return new Promise<TaskOfferAssemblyContext>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${taskId}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public claim(taskId: number): Promise<TaskOfferAssemblyContext> {
    return new Promise<TaskOfferAssemblyContext>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${taskId}/claim`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public complete(taskOfferAssemblyContext: TaskOfferAssemblyContext): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/complete`, taskOfferAssemblyContext)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
