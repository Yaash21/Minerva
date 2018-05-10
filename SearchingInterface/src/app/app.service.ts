import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs/Observable";
import { Imessage } from "./Imessage";
import { throws } from "assert";
import { Http, Response } from "@angular/http";
import { Headers, RequestOptions } from "@angular/http";

@Injectable()
export class AppService {
  constructor(private http: Http) {}

  postLoginCredentials(credentials: Imessage): Observable<any> {
    let headers = new Headers({ "Content-Type": "application/json" });
    let options = new RequestOptions({ headers: headers });

    return this.http.post(
      "http://minerva.stackroute.in/apigateway/login",
      credentials,
      options
    );
  }
}
