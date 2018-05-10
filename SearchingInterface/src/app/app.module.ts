import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";

import { AppComponent } from "./app.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { InfiniteScrollModule } from "ngx-infinite-scroll";

import {
  MatAutocompleteModule,
  MatFormFieldControl,
  MatButtonModule,
  MatButtonToggleModule,
  MatCardModule,
  MatCheckboxModule,
  MatChipsModule,
  MatDatepickerModule,
  MatDialogModule,
  MatDividerModule,
  MatExpansionModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatNativeDateModule,
  MatPaginatorModule,
  MatProgressBarModule,
  MatProgressSpinnerModule,
  MatRadioModule,
  MatRippleModule,
  MatSelectModule,
  MatSidenavModule,
  MatSliderModule,
  MatSlideToggleModule,
  MatSnackBarModule,
  MatSortModule,
  MatStepperModule,
  MatTableModule,
  MatTabsModule,
  MatToolbarModule,
  MatTooltipModule,
  MatFormFieldModule
} from "@angular/material";

import { CardFancyComponent } from "./card-fancy/card-fancy.component";
import { CardFancyExample } from "./card-fancy/card-fancy-example";
import { ChatComponent } from "./chat/chat.component";
import { NavbarComponent } from "./navbar/navbar.component";
import { HttpClientModule } from "@angular/common/http";

import { NgModule, Input } from "@angular/core";
import { FormControl } from "@angular/forms";

import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";

import { RouterModule, Routes } from "@angular/router";

import { AppService } from "./app.service";
import { RequestOptions, HttpModule, Http } from "@angular/http";
import { CdkTableModule } from "@angular/cdk/table";
import { DomainExpertComponent } from "./domain-expert/domain-expert.component";
import { DomainExpertService } from "./domain-expert/domain-expert.service";
import { AdminComponent } from "./admin/admin.component";
import { AdminService } from "./admin/admin.service";
import { TermBankComponent } from "./term-bank/term-bank.component";
import { Observable } from "rxjs/Observable";
import { TermBankService } from "./term-bank/term-bank.service";
import { TermBankModel } from "./term-bank/term-bank.model";
import { LoginComponent } from "./login/login.component";
import { LoginService } from "./login/login.service";

const appRoutes: Routes = [
  { path: '', component: ChatComponent },
  { path: 'home', component: ChatComponent },
  { path: 'login', component: LoginComponent },
  { path: 'admin', component: AdminComponent, canActivate : [LoginService] },
  { path: 'domain-expert', component: DomainExpertComponent, canActivate : [LoginService] },
  { path: '**', redirectTo: ''}
];

@NgModule({
  declarations: [
    AppComponent,
    CardFancyComponent,
    AdminComponent,
    DomainExpertComponent,
    LoginComponent,
    TermBankComponent,
    CardFancyExample,
    ChatComponent,
    NavbarComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    MatNativeDateModule,
    ReactiveFormsModule,
    MatExpansionModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatAutocompleteModule,
    MatInputModule,
    MatIconModule,
    MatMenuModule,
    FormsModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    MatDialogModule,
    BrowserModule,
    MatCardModule,
    MatGridListModule,
    MatButtonModule,
    MatDividerModule,
    MatAutocompleteModule,
    MatInputModule,
    FormsModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    MatSelectModule,
    HttpClientModule,
    MatDialogModule,
    MatNativeDateModule,
    MatTabsModule,
    MatToolbarModule,
    MatTooltipModule,
    InfiniteScrollModule,

    BrowserModule,
    MatCardModule,
    MatGridListModule,
    MatButtonModule,
    MatDividerModule,
    MatAutocompleteModule,
    MatInputModule,
    // FormsModule,
    BrowserAnimationsModule,
    // ReactiveFormsModule,
    MatSelectModule,
    // HttpClientModule,
    MatDialogModule,
    MatNativeDateModule,
    // HttpModule,
    MatTabsModule,
    MatChipsModule,
    MatIconModule,



    BrowserModule,
    FormsModule,
    HttpClientModule,
    MatNativeDateModule,
    ReactiveFormsModule,
    MatExpansionModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatAutocompleteModule,
    MatInputModule,
    MatIconModule,
    MatMenuModule,
    FormsModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    MatDialogModule,
    BrowserModule,
    MatChipsModule,
    MatCheckboxModule,
    MatCardModule,
    MatGridListModule,
    MatButtonModule,
    MatDividerModule,
    MatAutocompleteModule,
    MatInputModule,
    FormsModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    MatSelectModule,
    HttpClientModule,
    MatDialogModule,
    MatNativeDateModule,
    MatTabsModule,
    MatToolbarModule,
    MatTooltipModule,
    HttpModule,
    RouterModule,
    RouterModule.forRoot(appRoutes),
    MatChipsModule,
    MatSnackBarModule
  ],
  providers: [
    AppService,
    DomainExpertService,
    AdminService,
    TermBankService,
    TermBankModel,
    LoginService,
    LoginComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
