import axios from 'axios';
import { TaskChooseComponentsContext } from './task-choose-components.model';

const baseApiUrl = 'api/client-order-process/task-choose-components';

export default class TaskChooseComponentsService {
  public loadContext(taskId: number): Promise<TaskChooseComponentsContext> {
    return new Promise<TaskChooseComponentsContext>((resolve, reject) => {
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

  public claim(taskId: number): Promise<TaskChooseComponentsContext> {
    return new Promise<TaskChooseComponentsContext>((resolve, reject) => {
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

  public complete(taskChooseComponentsContext: TaskChooseComponentsContext): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/complete`, taskChooseComponentsContext)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
