import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-launch-menu',
  templateUrl: './launch-menu.component.html',
  styleUrls: ['./launch-menu.component.css'],
})
export class LaunchMenuComponent {

  userMessage: string = '';  

  constructor(private router: Router) {}

  handleUserAction(action: string): void {
    console.log('User action:', action);
    switch (action.toUpperCase()) {
      case 'T':
        console.log('Navigating to Transactions Page');
        this.router.navigate(['/transactions']); 
        break;
      case 'I':
        this.router.navigate(['/interest-rules']); // Example for another page
        break;
      case 'P':
        this.router.navigate(['/print-statement']); // Example for another page
        break;
      case 'Q':
        this.userMessage = 'Thank you for banking with AwesomeGIC Bank!'; // Set the message
        break;
      default:
        this.userMessage = 'Invalid option. Please choose T, I, P, or Q.';
    }
  }
}
