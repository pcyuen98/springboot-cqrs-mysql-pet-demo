import { Component, OnInit, Injector } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Pet } from '../models/pet.model';
import { PageBaseComponent } from '../util/page-base.component';
import { PetService } from '../service/PetService';

enum Action {
  View = 'VIEW',
  Add = 'ADD',
  Edit = 'EDIT',
  Delete = 'DELETE'
}

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

  constructor(injector: Injector, private petService: PetService) {
    super(injector);
  }

  ngOnInit(): void {
    this.loadPets();
    this.form = new FormGroup({
      name: new FormControl('', Validators.required),
      categoryId: new FormControl(1, Validators.required),
      tagId: new FormControl(1, Validators.required),
      status: new FormControl('', Validators.required),
      photoUrl: new FormControl('', Validators.required),
    });
  }

  // 👇 expose enum values to template
  petStatuses = Object.values(PetStatus);

  /**
   * Helper function to render tags as a string in the template.
   */
  getTagIds(pet: Pet): string {
    return pet.tags?.map(t => t.id).join(', ') || 'No tags';
  }

  async loadPets() {
    this.pets = await this.petService.getPets();
  }

  onAddPet(): void {
    this.form.reset({ categoryId: 1, tagId: 1 });
    this.action = Action.Add;
  }

  async onEditPet(pet: Pet): Promise<void> {
    this.selectedPet = pet;
    this.form.patchValue({
      name: pet.name,
      categoryId: pet.category?.id ?? 1,
      tagId: pet.tags?.[0]?.id ?? 1,
      status: pet.status,
      photoUrl: pet.photoUrl
    });
    this.action = Action.Edit;

  }

  onDeletePet(pet: Pet): void {
    this.selectedPet = pet;
    this.action = Action.Delete;
  }

  async onSubmitPet() {
    const formValue = this.form.value;

    const payload: Pet = {
      name: formValue.name,
      status: formValue.status,
      category: { id: formValue.categoryId },
      tags: [{ id: formValue.tagId }],
      photoUrl: formValue.photoUrl
    };

    if (this.action === Action.Add) {
      await this.petService.postPet(payload);
      await this.loadPets(); // always reload from backend to stay consistent
    } else if (this.action === Action.Edit && this.selectedPet) {
      const index = this.getSelectedPetIndex();
      this.pets[index] = { ...this.selectedPet, ...payload };
      await this.petService.putPet(payload);
      await this.loadPets(); // always reload from backend to stay consistent
      // Optionally call PUT endpoint if backend supports update
    }
    this.action = Action.View;
  }

  deletePet(): void {
    this.pets.splice(this.getSelectedPetIndex(), 1);
    this.action = Action.View;
  }

  cancel(): void {
    this.selectedPet = undefined;
    this.action = Action.View;
  }

  private getSelectedPetIndex(): number {
    return this.pets.findIndex((p) => p === this.selectedPet);
  }
}
