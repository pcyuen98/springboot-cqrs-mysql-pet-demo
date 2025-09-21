// services/pet.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { Pet } from '../models/pet.model';
import { CommonHTTPService } from './CommonHTTPService';

@Injectable({
    providedIn: 'root'
})
export class PetService {
    private apiUrl = 'http://localhost:8082/api/pets/read/v1';

    constructor(
        private httpCommonService: CommonHTTPService,
    ) { }

 async getPets(): Promise<Pet[]> {
  const response = await this.httpCommonService.getResource(this.apiUrl);

  // response is the raw array with `id` + `data` string
  const pets: Pet[] = response.map((item: any) => {
    return JSON.parse(item.data) as Pet;
  });

  return pets;
}

}
