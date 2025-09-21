// services/pet.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { Pet } from '../models/pet.model';

@Injectable({
  providedIn: 'root'
})
export class PetService {
  private apiUrl = 'http://localhost:8082/api/pets/read/v1';

  constructor(private http: HttpClient) {}

  getPets(): Observable<Pet[]> {
    return this.http.get<any[]>(this.apiUrl).pipe(
      map(response =>
        response.map(row => JSON.parse(row.data) as Pet)
      )
    );
  }
}
