import { Component, OnInit, Injector } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ModalController } from '@ionic/angular';
import { Pet } from '../models/pet.model';
import { PageBaseComponent } from '../util/page-base.component';
import { PetService } from '../service/PetService';
import { WriterPage } from '../writer/writer.page';
import { Action } from '../models/pet.action';

export enum PetStatus {
  AVAILABLE = 'AVAILABLE',
  PENDING = 'PENDING',
  SOLD = 'SOLD'
}

@Component({
  selector: 'app-reader',
  templateUrl: 'reader.page.html',
  styleUrls: ['reader.page.css']
})
export class ReaderPage extends PageBaseComponent implements OnInit {
  pets: Pet[] = [];
  selectedPet?: Pet;
  action: Action = Action.View;
  actions = Action;
  form: FormGroup;

  petStatuses = ["ANY", ...Object.values(PetStatus)];

  constructor(
    injector: Injector,
    private petService: PetService,
    private modalCtrl: ModalController
  ) {
    super(injector);
    
  }

  ngOnInit(): void {
    this.loadPets();
  }

  getTagIds(pet: Pet): string {
    return pet.tags?.map(t => t.id).join(', ') || 'No tags';
  }

  async loadPets(): Promise<void> {
    this.pets = await this.petService.getPets();
  }

  private async openPetModal(
    action: Action,
    pet?: Pet
  ): Promise<void> {

    const modal = await this.modalCtrl.create({
      component: WriterPage,
      componentProps: { action, form: this.form, selectedPet: pet || null }
    });

    await modal.present();

    const result = await modal.onDidDismiss();

    if (result.role === Action.Cancel) {
      console.log('Modal cancelled');
      return;
    }
    else {
      await this.loadPets();
    }

  }

  async onAddPet(): Promise<void> {
    await this.openPetModal(Action.Add);
  }

  async onEditPet(pet: Pet): Promise<void> {
    await this.openPetModal(Action.Edit, pet);
  }

  async onDeletePet(pet: Pet): Promise<void> {
    await this.openPetModal(Action.Delete, pet);
  }

  async search(id: number, status: string , data: string): Promise<void> {
    const numId = Number(id);
    if (!isNaN(numId) && numId > 0) {
      const pet = await this.petService.getPetById(numId);
      this.pets = pet ? [pet] : [];
      
    } else {
      if (status === "ANY") { status = ""}
      this.pets = await this.petService.search(status, data)
    }

    //await this.loadPets();

  }

  async searchPetsByStatus(status: string): Promise<void> {
    this.pets = await this.petService.getPetsByStatus(status);
  }
}
