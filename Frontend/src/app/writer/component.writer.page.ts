import { Component, OnInit, Injector, Input } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ModalController } from '@ionic/angular';

import { PageBaseComponent } from '../util/page-base.component';
import { PetService } from '../service/PetService';
import { Pet } from '../models/pet.model';
import { Action } from '../models/pet.action';
import { PetStatus } from '../query/query.page';
import { GlobalConstants } from 'src/environments/GlobalConstants';
import { urlValidator } from '../util/url-validator-highlight.directive';

@Component({
  selector: 'app-writer',
  templateUrl: 'component.writer.page.html',
  styleUrls: ['component.writer.page.css']
})
export class ComponentWriterPage extends PageBaseComponent implements OnInit {
  @Input() action: Action = Action.View;
  @Input() form?: FormGroup;
  @Input() selectedPet?: Pet;

  actions = Action;
  petStatuses = Object.values(PetStatus);

  constructor(
    injector: Injector,
    private petService: PetService,
    private modalCtrl: ModalController
  ) {
    super(injector);
  }

  ngOnInit(): void {
    this.initForm();
    if (this.action === Action.Add) {
      this.form?.reset({ description: '' });
    } else if (this.selectedPet) {
      this.patchForm(this.selectedPet);
    }
  }

  /** Initialize form if not provided from parent */
  private initForm(): void {
    if (this.form) return;

    this.form = new FormGroup({
      petId: new FormControl('', Validators.nullValidator),
      name: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
      status: new FormControl('', Validators.required),
      photoUrl: new FormControl('', [Validators.required, urlValidator]),
    });
  }

  /** Patch form with existing pet values */
  private patchForm(pet: Pet): void {
    this.form?.patchValue({
      petId: pet.petId,
      name: pet.name,
      description: pet.description,
      status: pet.status,
      photoUrl: pet.photoUrl
    });
  }

  /** Build payload object from form */
  private buildPayload(): Pet {
    const value = this.form?.value;
    return {
      petId: value.petId,
      name: value.name,
      description: value.description,
      status: value.status,
      photoUrl: value.photoUrl,
      tags: [{ id: 1 }],
      category: { id: 1 }
    };
  }

  /** Handle create or update */
  async onSubmitPet(): Promise<void> {
    if (!this.form || this.form.invalid) return;

    const payload = this.buildPayload();

    try {
      let result;
      switch (this.action) {
        case Action.Add:
          result = await this.petService.postPet(payload);
          break;
        case Action.Edit:
          result = await this.petService.putPet(payload);
          break;
      }

      if (result) {
        GlobalConstants.globalBESuccess = "Operation Successfully";
        this.modalCtrl.dismiss({ pet: payload }, Action.Submitted);
      }
    } finally {
      console.log("Submit completed");
    }
  }

  /** Handle delete */
  async deletePet(): Promise<void> {
    if (!this.form || this.form.invalid) return;

    const petId = this.form.value.petId;
    const result = await this.petService.deletePet(petId);

    if (result) {
      GlobalConstants.globalBESuccess = "Operation Successfully";
      this.modalCtrl.dismiss({ pet: undefined }, Action.Submitted);
    }
  }

  /** Cancel modal without changes */
  cancel(): void {
    this.selectedPet = undefined;
    this.modalCtrl.dismiss(null, Action.Cancel);
  }

  /** Close modal (same as cancel, kept for template readability) */
  close(): void {
    this.cancel();
  }

  isFormMode(): boolean {
    return this.action === this.actions.Add || this.action === this.actions.Edit;
  }

  isDeleteMode(): boolean {
    return this.action === this.actions.Delete;
  }

  getTitle(): string {
  switch (this.action) {
    case this.actions.Add: return 'Add New Pet';
    case this.actions.Edit: return 'Edit Pet';
    case this.actions.Delete: return 'Delete Pet';
    default: return 'Pet';
  }
}

}
