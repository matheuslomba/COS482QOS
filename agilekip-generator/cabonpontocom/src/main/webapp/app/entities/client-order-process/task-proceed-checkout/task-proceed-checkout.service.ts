import axios from 'axios';
import { TaskProceedCheckoutContext } from './task-proceed-checkout.model';

const baseApiUrl = 'api/client-order-process/task-proceed-checkout';

export default class TaskProceedCheckoutService {
  public loadContext(taskId: number): Promise<TaskProceedCheckoutContext> {
    return new Promise<TaskProceedCheckoutContext>((resolve, reject) => {
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

  public claim(taskId: number): Promise<TaskProceedCheckoutContext> {
    return new Promise<TaskProceedCheckoutContext>((resolve, reject) => {
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

  public complete(taskProceedCheckoutContext: TaskProceedCheckoutContext): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/complete`, taskProceedCheckoutContext)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
