import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LaunchMenuComponent } from './launch-menu/launch-menu.component';
import { TransactionsComponent } from './transactions/transactions.component';
import { InterestRulesComponent } from './interest-rules/interest-rules.component';
import { PrintStatementComponent } from './print-statement/print-statement.component';

const routes: Routes = [
  { path: '', component: LaunchMenuComponent }, // Default route
  { path: 'transactions', component: TransactionsComponent }, // Route for Transactions
  { path: 'interest-rules', component: InterestRulesComponent }, 
  { path: 'print-statement', component: PrintStatementComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)], // Ensure RouterModule is correctly configured
  exports: [RouterModule],
})
export class AppRoutingModule {}
