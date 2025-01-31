import { ChangeDetectorRef, Component } from '@angular/core';
import { Router } from '@angular/router';
import { TransactionsService } from '../transactions.service';

@Component({
  selector: 'app-transactions',
  templateUrl: './transactions.component.html',
  styleUrls: ['./transactions.component.css'],
})
export class TransactionsComponent {
  input: string = ''; // Input for the transaction details
  successMessage: string = '';
  errorMessage: string = '';
  account: any = null; // To store the account details
  transactions: any[] = []; // To store the transactions
  transactionSubmitted: boolean = false;
  userMessage:string ='';

  constructor(
    private transactionsService: TransactionsService,
    private router: Router,
    // private cdr: ChangeDetectorRef
  ) {}

  // Handle transaction submission
  submitTransaction(): void {
    this.successMessage = '';
    this.errorMessage = '';


    // Check for blank input
    if (!this.input.trim()) {
      this.router.navigate(['/']); // Navigate back to the main menu
      return;
    }

    // Parse the input
    const parts = this.input.split(' '); // Split by spaces
    if (parts.length !== 4) {
      this.errorMessage = 'Invalid format. Please use: <Date> <Account> <Type> <Amount>';
      return;
    }

    const [date, accountId, type, amount] = parts;

    // Validate the parsed data
    if (!/^\d{8}$/.test(date)) {
      this.errorMessage = 'Invalid date format. Use YYYYMMDD.';
      return;
    }
    if (!/^[D|W]$/i.test(type)) {
      this.errorMessage = 'Invalid type. Use D for deposit or W for withdrawal.';
      return;
    }
    if (isNaN(+amount) || +amount <= 0) {
      this.errorMessage = 'Invalid amount. Must be a positive number.';
      return;
    }

    // Send the transaction to the backend
    const transaction = {
      date,
      accountId,
      type: type.toUpperCase(),
      amount: parseFloat(amount),
    };

    this.transactionsService.addTransaction(transaction).subscribe({
      next: (response: any) => { // Explicitly set type for response
        this.successMessage = 'Transaction submitted successfully!';
        this.input = ''; // Clear the input
        
        this.loadAccountDetails(accountId);
       
          this.transactionSubmitted = true;
          // this.cdr.detectChanges();
          console.log('Transaction submitted:', this.transactionSubmitted);

      },
      error: (error: any) => { // Explicitly set type for error
        console.error('Error submitting transaction:', error);
        this.errorMessage = 'Failed to submit transaction. Please try again.';
      },
    });
  }

  // Fetch account details including transactions
  loadAccountDetails(accountId: string): void {
    this.transactionsService.getAccount(accountId).subscribe({
      next: (response: any) => {
        this.account = response; // Store account details
        this.transactions = response.transactions || []; // Store transactions
      },
      error: (error: any) => {
        console.error('Error fetching account details:', error);
        this.errorMessage = 'Failed to load account details.';
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
