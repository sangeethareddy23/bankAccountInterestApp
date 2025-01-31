import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class InterestRulesService {
  private apiUrl = 'http://localhost:8080/api/interest-rules'; // Updated base URL for interest rules

  constructor(private http: HttpClient) {}

  // Add a new interest rule
  addInterestRule(rule: { date: string; ruleId: string; rate: number }): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' });

    const body = new URLSearchParams();
    body.set('date', rule.date);
    body.set('ruleId', rule.ruleId);
    body.set('rate', rule.rate.toString());

    return this.http.post<any>(`${this.apiUrl}/define`, body.toString(), { headers });
  }

  // Get all interest rules
  getInterestRules(): Observable<any> {
    return this.http.get<any>(this.apiUrl);
  }
}
