import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TransactionsService {
  private apiUrl = 'http://localhost:8080/api/accounts/transaction'; // Updated backend API endpoint

  constructor(private http: HttpClient) {}

  // Method to send a new transaction to the backend
  addTransaction(transaction: {
    date: string;
    accountId: string;
    type: string;
    amount: number;
  }): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' });

    // Convert the transaction object into URL-encoded parameters
    const body = new URLSearchParams();
    body.set('date', transaction.date);
    body.set('accountId', transaction.accountId);
    body.set('type', transaction.type);
    body.set('amount', transaction.amount.toString());

    return this.http.post<any>(this.apiUrl, body.toString(), { headers });
  }

  getAccountStatement(accountId: string, yearMonth: string): Observable<any> {
    const url = `http://localhost:8080/api/accounts/${accountId}/statement?yearMonth=${yearMonth}`;
    return this.http.get<any>(url);
  }

  getAccount(accountId: string): Observable<any> {
    const url = `http://localhost:8080/api/accounts/${accountId}`;
    return this.http.get<any>(url);
  }
}
