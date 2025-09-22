import { Component, OnInit, Injector, Input } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Pet } from '../models/pet.model';
import { PageBaseComponent } from '../util/page-base.component';
import { PetService } from '../service/PetService';
import { PetStatus } from '../reader/reader.page';
import { ModalController } from '@ionic/angular';
import { Action } from '../models/pet.action';

@Component({
  selector: 'app-writer',
  templateUrl: 'writer.page.html',
  styleUrls: ['writer.page.css']
})
export class WriterPage extends PageBaseComponent implements OnInit {

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
    // Initialize form if not passed from parent
    if (!this.form) {
      this.form = new FormGroup({
        petId: new FormControl('', Validators.nullValidator),
        name: new FormControl('', Validators.required),
        categoryId: new FormControl(1, Validators.required),
        tagId: new FormControl(1, Validators.required),
        status: new FormControl('', Validators.required),
        photoUrl: new FormControl('', Validators.required),
      });
    }

    if (this.action === Action.Add) {
      this.form.reset({ categoryId: 1, tagId: 1 });
    } else if (this.selectedPet) {
      this.patchForm(this.selectedPet);
    }
  }

  private patchForm(pet: Pet): void {
    this.form?.patchValue({
      petId: pet.petId,
      name: pet.name,
      categoryId: pet.category?.id ?? 1,
      tagId: pet.tags?.[0]?.id ?? 1,
      status: pet.status,
      photoUrl: pet.photoUrl
    });
  }

  async onSubmitPet(): Promise<void> {
    if (!this.form) return;

    const formValue = this.form.value;
    const payload: Pet = {
      petId: formValue.petId,
      name: formValue.name,
      status: formValue.status,
      category: { id: formValue.categoryId },
      tags: [{ id: formValue.tagId }],
      photoUrl: formValue.photoUrl
    };

    try {
      if (this.action === Action.Add) {
        await this.petService.postPet(payload);
      } else if (this.action === Action.Edit && this.selectedPet) {
        await this.petService.putPet(payload);
      }

      this.modalCtrl.dismiss({ pet: payload }, Action.Submitted);
    } finally {
      this.action = Action.View;
    }
  }

  onDeletePet(): void {
    if (this.selectedPet) {
      this.modalCtrl.dismiss({ pet: this.selectedPet }, 'delete');
      this.selectedPet = undefined;
      this.action = Action.View;
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
