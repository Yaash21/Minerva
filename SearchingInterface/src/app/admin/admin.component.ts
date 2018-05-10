import { Component, Input, OnInit } from "@angular/core";
import {
  FormControl,
  Validators,
  FormGroupDirective,
  NgForm
} from "@angular/forms";
import { ErrorStateMatcher, MatSnackBar } from "@angular/material";
import { AdminService } from "./admin.service";
import {MatChipInputEvent} from '@angular/material';
import {ENTER, COMMA} from '@angular/cdk/keycodes';
import { IDomainExpert } from "./IDomainExpert";

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
  selector: "app-admin",
  templateUrl: "./admin.component.html",
  styleUrls: ["./admin.component.css"]
})
export class AdminComponent implements OnInit {
  role = "Admin";
  register: any;
  domainExperts: Array<IDomainExpert> = [];
  visible: boolean = true;
  selectable: boolean = true;
  removable: boolean = true;
  addOnBlur: boolean = true;

  @Input() emailId;
  @Input() password;

  buttondisabled: boolean = false;

  credentials: any;

  constructor(
    private _adminService: AdminService,
    private snackBar: MatSnackBar
  ) {}

  emailFormControl = new FormControl("", [
    Validators.required,
    Validators.email
  ]);
  passwordFormControl = new FormControl("", [Validators.required]);
  matcher = new CustomErrorStateMatcher();

  checking() {
    this.buttondisabled =
      this.emailFormControl.hasError("email") ||
      this.emailFormControl.hasError("required") ||
      this.passwordFormControl.hasError("required");
  }

  loginbutton() {
    this.credentials = {
      emailId: this.emailId,
      password: this.password,
      role: "Domain Expert"
    };

    this._adminService
      .postAddDomainExpertCredentials(this.credentials)
      .subscribe(data => {
        this.register = data._body;
        this.show();
      });
  }

  show() {
    let value = this.register;
    if(JSON.parse(this.register).message != null )
      value = JSON.parse(this.register).message;
    this.snackBar.open(value, "", { duration: 2000 });
  }

  add(event: MatChipInputEvent): void {
    let input = event.input;
    let value = event.value;
    if (input) {
    }
  }


  showAllButton(){
    this._adminService
      .getAllDomainExperts()
      .subscribe(data => {
        this.domainExperts = data;
      });
  }

  responseFromDelete : string = "";

  remove(domainExpert: IDomainExpert): void {
    let index = this.domainExperts.indexOf(domainExpert);

    if (index >= 0) {
      this.responseFromDelete = "";
      this.domainExperts.splice(index, 1);
      console.log(domainExpert.emailId);
      this._adminService
        .deleteDomainExpert(domainExpert.emailId)
        .subscribe(data => {
          console.log(data);
          this.snackBar.open(data._body, "", { duration: 2000 } );
        });

    }

  }

  ngOnInit() {}
}
