import { Component } from '@angular/core';
import { TransactionsService } from '../transactions.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-print-statement',
  templateUrl: './print-statement.component.html',
  styleUrls: ['./print-statement.component.css'],
})
export class PrintStatementComponent {
  input: string = '';  // User input in the form "<Account> <YearMonth>"
  statement: any = null;
  errorMessage: string = '';
  transactionSubmitted: boolean = false;
  userMessage:string ='';

  constructor(private transactionsService: TransactionsService , private router: Router) {}

  // Fetch statement based on user input
  fetchStatement(): void {
    this.errorMessage = '';
    this.statement = null;

    // Split the user input into accountId and yearMonth
    const inputParts = this.input.split(' ');

    if (inputParts.length !== 2) {
      this.errorMessage = 'Invalid input format. Please enter in <Account> <YearMonth> format.';
      return;
    }

    const [accountId, yearMonth] = inputParts;

    // Call backend service to fetch account statement
    this.transactionsService.getAccountStatement(accountId, yearMonth).subscribe({
      next: (response) => {
        this.statement = response;
        this.transactionSubmitted = true;
      },
      error: (error) => {
        console.error('Error fetching statement:', error);
        this.errorMessage = 'Account Not Found';
      },
    });
  }
  handleUserAction(action: string): void {
    switch (action.toUpperCase()) {
      case 'T':
        this.router.navigate(['/transactions']);
        break;
      case 'I':
        this.router.navigate(['/interest-rules']);
        break;
      case 'P':
        this.router.navigate(['/print-statement']);
        break;
      case 'Q':
        this.router.navigate(['/']);
        this.userMessage = 'Thank you for banking with AwesomeGIC Bank!'; 
        break;
      default:
        alert('Invalid option. Please choose T, I, P, or Q.');
    }
  }

  navigateToHome(): void {
    this.router.navigate(['/']); 
  }
}
