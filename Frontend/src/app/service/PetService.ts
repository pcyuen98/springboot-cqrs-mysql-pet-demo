// services/pet.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { Pet } from '../models/pet.model';
import { CommonHTTPService } from './CommonHTTPService';
import { GlobalConstants } from 'src/environments/GlobalConstants';

@Injectable({
    providedIn: 'root'
})
export class PetService {
    private apiUrl = 'http://localhost:8082/pet/';
    private writeApiUrl = 'http://localhost:8081/pet';

    constructor(
        private httpCommonService: CommonHTTPService,
    ) { }

    async getPets(): Promise<Pet[]> {
        const response = await this.httpCommonService.getResource(this.apiUrl + 'all');

        // response is the raw array with `id` + `data` string
        const pets: Pet[] = response.map((item: any) => {
            return JSON.parse(item.data) as Pet;
        });

        return pets;
    }

    async postPet(pet: Pet): Promise<Pet> {
        // Call the write API with POST and the Pet payload
        const response = await this.httpCommonService.postResource(this.writeApiUrl, pet);
        // Assuming the write API returns the created pet directly
        return response as Pet;
    }

    async putPet(pet: Pet): Promise<Pet> {
        // Call the write API with PUT and the Pet payload
        const response = await this.httpCommonService.putResource(this.writeApiUrl, pet);
        // Assuming the write API returns the created pet directly
        return response as Pet;
    }

    async getPetById(id: number): Promise<Pet | null> {
        const response = await this.httpCommonService.getResource(`${this.apiUrl}${id}`);
        return response ? JSON.parse(response.data) as Pet : null;
    }

    async getPetsByStatus(status: string): Promise<Pet[]> {
        const response = await this.httpCommonService.getResource(`${this.apiUrl}findByStatus/${status}`);
        return response.map((item: any) => JSON.parse(item.data) as Pet);
    }
}
