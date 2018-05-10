// import { Component, OnInit } from '@angular/core';
import { Component, Input, OnInit } from "@angular/core";
import { ErrorStateMatcher } from "@angular/material/core";
import {
  FormControl,
  FormGroupDirective,
  NgForm,
  Validators
} from "@angular/forms";
import { Router } from "@angular/router";
import { NavigationExtras } from "@angular/router";
import { AppService } from "../app.service";

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
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent  implements OnInit {
  login: string;
  ngOnInit(){
    this.login = "start";
    localStorage.setItem(localStorage.getItem('sessionId'),'none');
    // console.log("app-component visited ");
  }
  greeting = "Login";
  response: any;
  @Input() emailId;
  @Input() password;
  @Input() role;

  Roles = [{ name: "Admin" }, { name: "Domain Expert" }];
  buttondisabled: boolean = false;

  searchresults: any;

  credentials: any;

  received: any;

  constructor(private _appService: AppService, private router: Router) {}

  everything: string;
  token: string;
  emailFormControl = new FormControl("", [
    Validators.required,
    Validators.email
  ]);
  passwordFormControl = new FormControl("", [Validators.required]);
  roleFormControl = new FormControl("", [Validators.required]);
  matcher = new CustomErrorStateMatcher();

  checking() {
    this.buttondisabled =
      this.emailFormControl.hasError("email") ||
      this.emailFormControl.hasError("required") ||
      this.passwordFormControl.hasError("required") ||
      this.roleFormControl.hasError("required");
  }

  loginbutton() {
    this.credentials = {
      emailId: this.emailId,
      password: this.password,
      role: this.role
    };
    this._appService.postLoginCredentials(this.credentials).subscribe(data => {
      this.response = JSON.parse(data._body);
      this.show();
      this.routing();
    });
  }

  getLoginStatus() {
    return this.login;
  }

  passwordText = "password";

  changeview() {
    if(this.passwordText === "password")
      this.passwordText = "text";
    else
      this.passwordText = "password";
  }

  show() {
    this.token = this.response.token;
    this.login = this.response.AuthenticationResult;
    // console.log(this.response);
  }

  // extras: NavigationExtras = {
  //   queryParams: {
  //     token : this.token
  //   }
  // };

  routing() {
    // console.log("token value is ", this.token);
    if (this.login === "success") {
      localStorage.setItem(localStorage.getItem('sessionId'),'success');
      if (this.role === "Admin") this.router.navigate(["/admin"]);
      else if (this.role === "Domain Expert")
        this.router.navigate(["/domain-expert"], {
          queryParams: { token: this.token }
        });
    }
    else
      localStorage.setItem(localStorage.getItem('sessionId'),'none');
  }
}
