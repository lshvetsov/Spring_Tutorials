import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class LoggingService {

  logEvent(message: any) {
    console.log(message);
  }

}
