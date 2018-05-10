import { Component, OnInit, Input, OnChanges } from "@angular/core";
import { ErrorStateMatcher } from "@angular/material";
import { startWith } from "rxjs/operators/startWith";
import { map } from "rxjs/operators/map";
import {
  FormControl,
  FormGroupDirective,
  NgForm,
  Validators
} from "@angular/forms";
import { DomainExpertService } from "./domain-expert.service";
import { Router } from "@angular/router";
import { ActivatedRoute } from "@angular/router";
import { Observable } from "rxjs/Observable";
import { MatChipInputEvent } from "@angular/material";
import { ENTER, COMMA } from "@angular/cdk/keycodes";

/** Error when invalid control is dirty, touched, or submitted. */
export class CustomErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(
    control: FormControl | null,
    form: FormGroupDirective | NgForm | null
  ): boolean {
    const isSubmitted = form && form.submitted;
    return !!(
      control &&
      control.invalid &&
      (control.dirty || control.touched || isSubmitted)
    );
  }
}

@Component({
  selector: "app-domain-expert",
  templateUrl: "./domain-expert.component.html",
  styleUrls: ["./domain-expert.component.css"],
  inputs: ["response", "data"]
})
export class DomainExpertComponent implements OnInit, OnChanges {
  conceptAutoComplete: Array<string> = [];
  buttondisabled: boolean = false;
  populatebuttondisabled: boolean = true;
  populateAllButtonDisabled: boolean = true;
  domainInput: boolean = false;
  data: any;
  @Input() domain: string;
  @Input() domain1: string;
  @Input() concepts: string;
  concepts1: Array<string> = [];
  concepts2: Array<string> = [];

  visible: boolean = true;
  selectable: boolean = true;
  removable: boolean = true;
  addOnBlur: boolean = true;

  // Enter, comma
  separatorKeysCodes = [ENTER, COMMA];

  response: any;
  token: string;
  requestbody: any;
  result: any;
  role = "Domain Expert";
  domainList: any;
  conceptList: any;
  keys: Array<string> = [];

  constructor(
    private _domainExpertService: DomainExpertService,
    private _router: Router,
    private route: ActivatedRoute
  ) {}

  newConceptList: Observable<string[]>;
  newDomainList: Observable<string[]>;
  newDomainList1: Observable<string[]>;

  ngOnInit() {
    this.response = this.route.queryParams.subscribe(params => {
      this.response = params;
      this.token = this.response.token;
    });
    this._domainExpertService.getDomainConceptList().subscribe(data => {
      this.result = data._body;
      this.conceptList = JSON.parse(this.result).conceptList;
      for (var key in this.conceptList) {
        this.keys.push(key);
      }
      this.newDomainList = this.domainFormControl.valueChanges.pipe(
        startWith(""),
        map(val => this.filter(val, this.keys))
      );
      this.newDomainList1 = this.domainFormControl1.valueChanges.pipe(
        startWith(""),
        map(val => this.filter(val, this.keys))
      );
    });
  }
  filter(val: string, option: any): string[] {
    return option.filter(
      option => option.toLowerCase().indexOf(val.toLowerCase()) === 0
    );
  }
  domainFormControl = new FormControl("", [Validators.required]);
  domainFormControl1 = new FormControl("", [Validators.required]);
  conceptFormControl = new FormControl("", [Validators.required]);
  matcher = new CustomErrorStateMatcher();
  checkResponse() {}

  checking() {
    this.buttondisabled =
      this.domainFormControl.hasError("required") ||
      this.conceptFormControl.hasError("required");
  }

  checking1() {
    this.populateAllButtonDisabled = this.domainFormControl1.hasError(
      "required"
    );
  }

  fetchConcepts() {
    for (let key of this.keys) {
      if (key == this.domain) {
        this.conceptAutoComplete = [];
        this.conceptAutoComplete = this.conceptList[key];
      }
    }
    this.newConceptList = this.conceptFormControl.valueChanges.pipe(
      startWith(""),
      map(val => this.filter(val, this.conceptAutoComplete))
    );
    this.checking();
  }
  fetchAllConcepts() {
    for (let key of this.keys) {
      if (key == this.domain1) {
        this.concepts2 = this.conceptList[key];
      }
    }
    this.checking1();
    if (this.domain1 == "Java" || this.domain1 == "Investment")
      this.populateAllButtonDisabled = false;
    else this.populateAllButtonDisabled = true;
  }

  ngOnChanges() {
    this.fetchConcepts();
  }

  populatebutton() {
    console.log(this.response);
    this.activated = false;
    this.requestbody = {
      domain: this.domain,
      concepts: this.concepts1
    };
    this._domainExpertService
      .postPopulateData(this.requestbody, this.token)
      .subscribe(data => {
        this.result = data;
      });
  }

  populateAllButton() {
    console.log(this.response);
    this.requestbody = {
      domain: this.domain1,
      concepts: this.concepts2
    };
    this._domainExpertService
      .postPopulateData(this.requestbody, this.token)
      .subscribe(data => {
        this.result = data;
      });
  }

  flag: boolean = true;
  activated = false;
  addConcepts(concept: string) {
    this.flag = true;
    this.populatebuttondisabled = false;
    this.domainInput = true;
    for (var str of this.concepts1) {
      if (str == concept) this.flag = false;
    }
    if (this.flag == true) {
      this.concepts1.push(concept);
      this.concepts = "";
    }
    this.concepts = "";
  }

  add(event: MatChipInputEvent): void {
    let input = event.input;
    let value = event.value;
    if (input) {
      input.value = "";
    }
  }

  remove(concept: any): void {
    let index = this.concepts1.indexOf(concept);

    if (index >= 0) {
      this.concepts1.splice(index, 1);
    }

    if (this.concepts1.length == 0) {
      this.domainInput = false;
      this.populatebuttondisabled = true;
    }
  }
}
