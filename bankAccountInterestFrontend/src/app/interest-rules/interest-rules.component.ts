import { Component, OnInit } from '@angular/core';
import { InterestRulesService } from '../services/interest-rules.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-interest-rules',
  templateUrl: './interest-rules.component.html',
  styleUrls: ['./interest-rules.component.css'],
})
export class InterestRulesComponent implements OnInit {
  input: string = ''; // User input
  successMessage: string = '';
  errorMessage: string = '';
  rules: any[] = []; // To store the list of rules
  transactionSubmitted: boolean = false;
  userMessage : string ='';

  constructor(private interestRulesService: InterestRulesService ,private router: Router
  ) {}

  ngOnInit(): void {
    this.loadInterestRules();
  }

  // Load interest rules from the backend
  loadInterestRules(): void {
    this.interestRulesService.getInterestRules().subscribe({
      next: (response) => {
        // Convert the response map into an array of rules
        this.rules = Object.entries(response).flatMap(([date, rules]) => rules);
      },
      error: (error) => {
        console.error('Error fetching interest rules:', error);
        this.errorMessage = 'Failed to load interest rules.';
      },
    });
  }

  // Submit the interest rule in <Date> <RuleId> <Rate> format
  submitInterestRule(): void {
    this.successMessage = '';
    this.errorMessage = '';

    const parts = this.input.split(' '); // Split the input by spaces
    if (parts.length !== 3) {
      this.errorMessage = 'Invalid format. Please use: <Date> <RuleId> <Rate in %>';
      return;
    }

    const [date, ruleId, rate] = parts;

    // Validate Date
    if (!/^\d{8}$/.test(date)) {
      this.errorMessage = 'Invalid date format. Use YYYYMMDD.';
      return;
    }

    // Validate RuleId
    if (!ruleId.trim()) {
      this.errorMessage = 'RuleId cannot be empty.';
      return;
    }

    // Validate Rate
    const numericRate = parseFloat(rate);
    if (isNaN(numericRate) || numericRate <= 0 || numericRate >= 100) {
      this.errorMessage = 'Interest rate should be greater than 0 and less than 100.';
      return;
    }

    // Prepare rule data
    const ruleData = {
      date,
      ruleId,
      rate: numericRate,
    };

    // Call backend to define the interest rule
    this.interestRulesService.addInterestRule(ruleData).subscribe({
      next: (response) => {
        this.successMessage = 'Interest rule added successfully!';
        this.loadInterestRules(); // Reload the rules after adding
        this.input = ''; // Clear the input field
        this.transactionSubmitted = true;
      },
      error: (error) => {
        console.error('Error adding interest rule:', error);
        this.errorMessage = 'Failed to add interest rule.';
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
