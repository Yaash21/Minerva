import { Input } from "@angular/core";

export class TermBankModel {
  public term: any;
  public intent: any;
  public weight: any;

  public get termName(): any {
    return this.term;
  }

  public set termName(term: any) {
    this.term = term;
  }
  public get intentName(): any {
    return this.intent;
  }

  public set intentName(intent: any) {
    this.intent = intent;
  }

  public get weightName(): any {
    return this.weight;
  }
  public set weightName(weight: any) {
    this.weight = weight;
  }
}
