import { Injectable } from "@angular/core";
import { Observable } from "rxjs/Observable";
import { Http, Response } from "@angular/http";
import { Headers, RequestOptions } from "@angular/http";

@Injectable()
export class TermBankService {
  constructor(private http: Http) {}

  getIndicators(): Observable<any> {
    return this.http.get("http://minerva.stackroute.in/apigateway/neo4jIntent");
  }

  getWordApi(term: any): Observable<any> {
    return this.http.get(
      // "http://words.bighugelabs.com/api/1/b9fb9122bf7e43979c005372b3eb65cf/" +
      // "http://words.bighugelabs.com/api/1/22a6d2e9b7f6d782b4cbe1fa0249c2bf/"+
      "http://words.bighugelabs.com/api/1/85069bda183dffb84592438fabb8cdd2/"+
        term +
        "/json"
    );
  }
  postTermSynonym(neo4jList:any):Observable<any>{
    return this.http.post(
      "http://minerva.stackroute.in/apigateway/neo4jSynonym",neo4jList
    );
  }
}
