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

@Component({
  selector: 'app-reader',
  templateUrl: 'reader.page.html',
  styleUrls: ['reader.page.css']
})
export class ReaderPage extends PageBaseComponent implements OnInit {
  pets: any;
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
      type: new FormControl('', Validators.required),
      status: new FormControl('', Validators.required),
    });
  }

async loadPets() {
  this.pets = await this.petService.getPets();
  console.log("this.pets-->" + this.pets);
}
  onAddPet(): void {
    this.form.reset();
    this.action = Action.Add;
  }

  onEditPet(pet: Pet): void {
    this.selectedPet = pet;
    this.form.patchValue(pet);
    this.action = Action.Edit;
  }

  onDeletePet(pet: Pet): void {
    this.selectedPet = pet;
    this.action = Action.Delete;
  }

  onSubmitPet() {
    if (this.action === Action.Add) {
      this.pets.push(this.form.value); // TODO: POST to backend
    } else if (this.action === Action.Edit) {
      this.pets[this.getSelectedPetIndex()] = this.form.value;
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
    return this.pets.findIndex((p: Pet | undefined) => p === this.selectedPet);
  }
}
