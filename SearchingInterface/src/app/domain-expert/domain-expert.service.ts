import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs/Observable";
import { throws } from "assert";
import { Http, Response } from "@angular/http";
import { Headers, RequestOptions } from "@angular/http";

@Injectable()
export class DomainExpertService {
  constructor(private http: Http) {}

  postPopulateData(populate: any, token: string): Observable<any> {
    console.log("adfasd" + token);
    let headers = new Headers({ token: token });
    let options = new RequestOptions({ headers: headers });
    return this.http.post(
      "http://minerva.stackroute.in/apigateway/search-service",
      populate,
      options
    );

  }

  getDomainConceptList(): Observable<any> {
    return this.http.get("http://minerva.stackroute.in/apigateway/neo4jConcept");
  }
}
