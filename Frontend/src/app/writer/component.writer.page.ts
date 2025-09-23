import { Component, OnInit, Injector, Input } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { Pet } from '../models/pet.model';
import { PageBaseComponent } from '../util/page-base.component';
import { PetService } from '../service/PetService';
import { PetStatus } from '../query/query.page';
import { ModalController } from '@ionic/angular';
import { Action } from '../models/pet.action';
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

  pets: Pet[] = [];
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
    if (!this.form) {
      this.form = new FormGroup({
        petId: new FormControl('', Validators.nullValidator),
        name: new FormControl('', Validators.required),
        description: new FormControl('', Validators.required), // changed from desc
        status: new FormControl('', Validators.required),
        photoUrl: new FormControl('', [Validators.required, urlValidator]),
      });
    }

    if (this.action === Action.Add) {
      this.form.reset({ description: '' });
    } else if (this.selectedPet) {
      this.patchForm(this.selectedPet);
    }
  }

  private patchForm(pet: Pet): void {
    this.form?.patchValue({
      petId: pet.petId,
      name: pet.name,
      description: pet.description,
      status: pet.status,
      photoUrl: pet.photoUrl
    });
  }

  async onSubmitPet(): Promise<void> {
    if (!this.form) return;

    if (this.form.invalid) return;

    const formValue = this.form.value;
    const payload: Pet = {
      petId: formValue.petId,
      name: formValue.name,
      status: formValue.status,
      description: formValue.description,
      photoUrl: formValue.photoUrl,
      tags: [{ id: 1 }],
      category: { id: 1 } // provide default
    };

    try {
      let result: any;
      if (this.action === Action.Add) {
        result = await this.petService.postPet(payload);
      } else if (this.action === Action.Edit && this.selectedPet) {
        result = await this.petService.putPet(payload);
      }

      if (result) {
        GlobalConstants.globalBESuccess = "Operation Successfully";
        this.modalCtrl.dismiss({ pet: payload }, Action.Submitted);
      }
    } finally {
      console.log("Submit completed");
    }
  }

  async deletePet(): Promise<void> {
    if (!this.form) return;

    if (this.form.invalid) return;
    const formValue = this.form.value;
    let petId = formValue.petId;

    let result = await this.petService.deletePet(petId)
    if (result) {
      GlobalConstants.globalBESuccess = "Operation Successfully";
      this.modalCtrl.dismiss({ pet: undefined }, Action.Submitted);
    }
  }

  cancel(): void {
    this.selectedPet = undefined;
    this.action = Action.Cancel;
    this.modalCtrl.dismiss(null, Action.Cancel);
  }

  close(): void {
    this.modalCtrl.dismiss(null, Action.Cancel);
  }
}
