import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LaunchMenuComponent } from './launch-menu/launch-menu.component';
import { TransactionsComponent } from './transactions/transactions.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { InterestRulesComponent } from './interest-rules/interest-rules.component';
import { PrintStatementComponent } from './print-statement/print-statement.component';

@NgModule({
  declarations: [
    AppComponent,
    LaunchMenuComponent,
    TransactionsComponent,
    InterestRulesComponent,
    PrintStatementComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
