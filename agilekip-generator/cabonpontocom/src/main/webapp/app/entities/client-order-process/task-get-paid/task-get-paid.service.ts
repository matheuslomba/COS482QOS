import axios from 'axios';
import { TaskGetPaidContext } from './task-get-paid.model';

const baseApiUrl = 'api/client-order-process/task-get-paid';

export default class TaskGetPaidService {
  public loadContext(taskId: number): Promise<TaskGetPaidContext> {
    return new Promise<TaskGetPaidContext>((resolve, reject) => {
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

  public claim(taskId: number): Promise<TaskGetPaidContext> {
    return new Promise<TaskGetPaidContext>((resolve, reject) => {
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

  public complete(taskGetPaidContext: TaskGetPaidContext): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/complete`, taskGetPaidContext)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
