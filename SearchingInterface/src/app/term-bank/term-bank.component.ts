import { Component, OnInit, Input } from "@angular/core";
import {
  FormControl,
  FormGroupDirective,
  NgForm,
  Validators
} from "@angular/forms";
import { TermBankService } from "./term-bank.service";
import { ErrorStateMatcher } from "@angular/material";
import { Observable } from "rxjs/Observable";
import { startWith } from "rxjs/operators/startWith";
import { map } from "rxjs/operators/map";
import { TermBankModel } from "./term-bank.model";

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
  selector: "app-term-bank",
  templateUrl: "./term-bank.component.html",
  styleUrls: ["./term-bank.component.css"]
})
export class TermBankComponent implements OnInit {
  constructor(
    private termbankService: TermBankService,
    private termBankModel: TermBankModel
  ) {}
  indicatorControl: FormControl = new FormControl("", [Validators.required]);
  selectorControl: FormControl = new FormControl("", [Validators.required]);
  // intentControl: FormControl = new FormControl("", [Validators.required]);
  // weightControl: FormControl = new FormControl("", [Validators.required]);
  // intentControl: FormControl = new FormControl();
  // weightControl: FormControl = new FormControl();
  matcher = new CustomErrorStateMatcher();
  filteredOptions: Observable<any[]>;
  checkBoxIndex = -1;
  intentindex = -1;
  weightIndex = -1;
  buttondisabled: boolean = true;
  checkBoxValue: boolean = false;
  check: boolean = false;
  result: any;
  result1: any;
  result2:any;
  intentList: any;
  data: any;
  data1:any;
  data2:any;
  options: any[] = [];
  intent: any[] = [];
  weight: any[] = [];
  intentTemp: any;
  weightTemp: any;
  checkBoxFlag: boolean = false;
  neo4jList: any[] = [];
  checkedIndex: any[] = [];
  @Input() term: any;
  intentNgModel:any;
  weightNgModel:any;
  list = [
    { value: "synonym", viewValue: "Synonym" },
    { value: "similar", viewValue: "Similar" }
  ];

  intentSet = [
    { value: "Beginner", viewValue: "Beginner" },
    { value: "Intermediate", viewValue: "Intermediate" },
    { value: "Advance", viewValue: "Advance" },
    { value: "Illustration", viewValue: "Illustration" }
  ];

  weightSet = [
    { value: "1", viewValue: "1" },
    { value: "2", viewValue: "2" },
    { value: "3", viewValue: "3" },
    { value: "4", viewValue: "4" },
    { value: "5", viewValue: "5" },
    { value: "6", viewValue: "6" },
    { value: "7", viewValue: "7" },
    { value: "8", viewValue: "8" },
    { value: "9", viewValue: "9" },
    { value: "10", viewValue: "10" }
  ];
  termList: any;
  flag:boolean= false;
  ngOnInit() {
    this.termbankService.getIndicators().subscribe(data => {
      // console.log(data);
      this.result = data._body;
      this.intentList = JSON.parse(this.result).intentList;
      for (var i = 0; i < this.intentList.length;i++) {
        // console.log(this.intentList[i].term + "  bhisa");
        this.options.push(this.intentList[i].term);
        this.intent.push(this.intentList[i].intent);
        this.weight.push(this.intentList[i].weight);
        // console.log(this.options.length);
      }
      this.filteredOptions = this.indicatorControl.valueChanges.pipe(
        startWith(""),
        map(val => this.filter(val, this.options))
      );
    });
  }

  filter(val: any, option: any[]): any[] {
    return option.filter(
      option => option.toLowerCase().indexOf(val.toLowerCase()) === 0
    );
  }
  checking() {
    this.buttondisabled =
      this.indicatorControl.hasError("required") ||
      this.selectorControl.hasError("required");
  }
  checkBox(a: string, b: string, c: string, d: any) {
    if (this.checkedIndex.includes(d)) {
      var index = this.checkedIndex.indexOf(d);
      this.checkedIndex.splice(index, 1);
      for (var i = 0; i < this.neo4jList.length; i++) {
        if (this.neo4jList[i].term == a) {
          this.neo4jList.splice(i--, 1);
        }
      }
      console.log(this.neo4jList.length);
      console.log(this.checkedIndex.length);
      console.log(this.neo4jList);
    } else {
      this.checkedIndex.push(d);
        console.log(this.checkedIndex.length);
      this.termBankModel = new TermBankModel();
      this.termBankModel.term=a;
      this.termBankModel.intent=b;
      this.termBankModel.weight=c;
      this.neo4jList.push(this.termBankModel);
      console.log(this.neo4jList.length);
      console.log(this.checkedIndex.length);
    }
    console.log(a);
    console.log(b);
    console.log(c);
  }
populateNeo4j(){
  this.termbankService.postTermSynonym(this.neo4jList).subscribe(data2=>{
    console.log(data2)
    this.result2 =data2._body;
    console.log(this.result2);
  });
  this.flag=false;
  for(var i=0;i<this.neo4jList.length;i++){
    this.termBankModel = new TermBankModel();;
    this.termBankModel = this.neo4jList[i];
    var t =this.termBankModel.term;
    var a =this.termBankModel.intent;
    var w = this.termBankModel.weight;
    this.options.push(t);
    this.intent.push(a);
    this.weight.push(w);
    this.neo4jList.splice(i,1);
    this.checkedIndex.splice(i--,1)
  console.log(this.options.length);
  }
  this.filteredOptions = this.indicatorControl.valueChanges.pipe(
    startWith(""),
    map(val => this.filter(val, this.options))
  );


}
  fetchApi() {
    if (!this.options.includes(this.term)) {
      console.log("The term is incorrect");
      this.check = true;
    } else {

      this.check = false;
      this.flag=true;
      this.termbankService.getWordApi(this.term).subscribe(data1 => {
        this.result1 = data1._body;
        this.termList = JSON.parse(this.result1);
        console.log(this.termList);
        var count=0;
        for (var i = 0; i < this.termList.length; i++) {
          var temp = this.termList[i];
          count++;
          if (this.options.includes(temp)) {
            this.termList.splice(i--, 1);
            console.log(temp);
          }
        }
        var index = this.options.indexOf(this.term);
        this.intentTemp = this.intent[index];
        this.weightTemp = this.weight[index];
        console.log(this.intentTemp);
        console.log(this.weightTemp);
      });
    }
  }
}
