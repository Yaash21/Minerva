import { Component, OnInit, Input } from "@angular/core";
import { MatIconModule } from "@angular/material";
import { Router } from "@angular/router";

@Component({
  selector: "app-navbar",
  templateUrl: "./navbar.component.html",
  styleUrls: ["./navbar.component.css"],
  inputs: ["role"]
})
export class NavbarComponent implements OnInit {
@Input() testchild;
  loggedIn: string;
  role: string;
  constructor(private router: Router) {}
  value: boolean = false;

  ngOnInit() {
    this.loggedIn = "true";
    if(this.role === "Minerva") {
      this.loginStatus = "login";
    } else if(this.role === "Login") {
      this.loginStatus = "Home";
    } else {
      this.loginStatus = "logout";
    }
  }
  loginStatus = "login";

  perform() {
    if(this.loginStatus === "login") {
      this.router.navigate(['login']);
    } else if(this.loginStatus === "Home") {
      this.router.navigate(['home']);
      console.log("home is being called");
    } else {
      this.router.navigate(['login']);
    }
  }
}
