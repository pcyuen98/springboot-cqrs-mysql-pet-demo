// services/pet.service.ts
import { Injectable } from '@angular/core';
import { Pet } from '../models/pet.model';
import { CommonHTTPService } from './CommonHTTPService';
import { GlobalConstants } from 'src/environments/GlobalConstants';

@Injectable({
    providedIn: 'root'
})
export class PetService {

    constructor(
        private httpCommonService: CommonHTTPService,
    ) { }

    async getPets(): Promise<Pet[]> {
        const response = await this.httpCommonService.getResource(GlobalConstants.query_url + 'all');

        // response is the raw array with `id` + `data` string
        const pets: Pet[] = response.map((item: any) => {
            return JSON.parse(item.data) as Pet;
        });

        return pets;
    }

    async postPet(pet: Pet): Promise<Pet> {
        // Call the write API with POST and the Pet payload
        const response = await this.httpCommonService.postResource(GlobalConstants.command_url, pet);
        // Assuming the write API returns the created pet directly
        return response as Pet;
    }

    async deletePet(petId: number): Promise<String> {
        // Call the write API with PUT and the Pet payload
        const response = await this.httpCommonService.deleteResource(GlobalConstants.command_url + '/' + petId);
        // Assuming the write API returns the created pet directly
        return "success"
    }

    async putPet(pet: Pet): Promise<Pet> {
        // Call the write API with PUT and the Pet payload
        const response = await this.httpCommonService.putResource(GlobalConstants.command_url, pet);
        // Assuming the write API returns the created pet directly
        return response as Pet;
    }

    async getPetById(id: number): Promise<Pet | null> {
        const response = await this.httpCommonService.getResource(`${GlobalConstants.query_url}${id}`);
        return response ? JSON.parse(response.data) as Pet : null;
    }

    async getPetsByStatus(status: string): Promise<Pet[]> {
        const response = await this.httpCommonService.getResource(`${GlobalConstants.query_url}findByStatus/${status}`);
        return response.map((item: any) => JSON.parse(item.data) as Pet);
    }

    async search(status: String, data: String): Promise<Pet[]> {

        const response = await this.httpCommonService.getResource(`${GlobalConstants.query_url}search?status=${status}&data=${data}`);
        return response.map((item: any) => JSON.parse(item.data) as Pet);
    }
}
