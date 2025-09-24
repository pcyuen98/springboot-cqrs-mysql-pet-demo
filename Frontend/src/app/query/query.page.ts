import { Component, OnInit, Injector } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ModalController } from '@ionic/angular';
import { Pet } from '../models/pet.model';
import { PageBaseComponent } from '../util/page-base.component';
import { PetService } from '../service/PetService';
import { ComponentWriterPage } from '../writer/component.writer.page';
import { Action } from '../models/pet.action';

export enum PetStatus {
  AVAILABLE = 'AVAILABLE',
  PENDING = 'PENDING',
  SOLD = 'SOLD'
}

@Component({
  selector: 'app-query',
  templateUrl: 'query.page.html',
  styleUrls: ['query.page.css']
})
export class QueryPage extends PageBaseComponent implements OnInit {
  pets: Pet[] = [];
  selectedPet?: Pet;
  action: Action = Action.View;
  actions = Action;
  form!: FormGroup;

  readonly petStatuses: string[] = ['ANY', ...Object.values(PetStatus)];

  constructor(
    injector: Injector,
    private readonly petService: PetService,
    private readonly modalCtrl: ModalController
  ) {
    super(injector);
  }

  /* ----------------------------
     Lifecycle
  ----------------------------- */
  ngOnInit(): void {
    this.loadPets();
  }

  /* ----------------------------
     Helpers
  ----------------------------- */
  getTagIds(pet: Pet): string {
    return pet.tags?.map(t => t.id).join(', ') || 'No tags';
  }

  private async openPetModal(action: Action, pet?: Pet): Promise<void> {
    const modal = await this.modalCtrl.create({
      component: ComponentWriterPage,
      componentProps: { action, form: this.form, selectedPet: pet ?? null }
    });

    await modal.present();
    const { role } = await modal.onDidDismiss();

    if (role !== Action.Cancel) {
      await this.loadPets();
    }
  }

  /* ----------------------------
     Data Loading
  ----------------------------- */
  private async loadPets(): Promise<void> {
    this.pets = await this.petService.getPets();
  }

  async search(id: number, status: string, data: string): Promise<void> {
    const petId = Number(id);

    if (!isNaN(petId) && petId > 0) {
      const pet = await this.petService.getPetById(petId);
      this.pets = pet ? [pet] : [];
      return;
    }

    const normalizedStatus = status === 'ANY' ? '' : status;
    this.pets = await this.petService.search(normalizedStatus, data);
  }

  async searchPetsByStatus(status: string): Promise<void> {
    this.pets = await this.petService.getPetsByStatus(status);
  }

  /* ----------------------------
     Actions
  ----------------------------- */
  async onAddPet(): Promise<void> {
    await this.openPetModal(Action.Add);
  }

  async onEditPet(pet: Pet): Promise<void> {
    await this.openPetModal(Action.Edit, pet);
  }

  async onDeletePet(pet: Pet): Promise<void> {
    await this.openPetModal(Action.Delete, pet);
  }
}
