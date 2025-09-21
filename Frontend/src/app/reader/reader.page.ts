import { Component, Injector, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { PageBaseComponent } from '../util/page-base.component';

type Car = {
  brand: string;
  model: string;
  year: number;
};

enum Action {
  View = 'VIEW',
  Add = 'ADD',
  Edit = 'EDIT',
  Delete = 'DELETE'
}

@Component({
  selector: 'app-reader',
  templateUrl: './reader.page.html',
  styleUrls: ['./reader.page.css']
})
export class ReaderPage extends PageBaseComponent implements OnInit {
  cars: Car[] = [];
  selectedCar?: Car = undefined;
  action: Action;
  actions: typeof Action = Action;
  form: FormGroup;

  constructor(injector: Injector) {
    super(injector);
  }

  ngOnInit(): void {
    this.cars = [
      { brand: 'Audi', model: 'A4', year: 2018 },
      { brand: 'BMW', model: '3 Series', year: 2015 },
      { brand: 'Mercedes-Benz', model: 'C Klasse', year: 2016 }
    ];
    this.action = Action.View;
    this.form = new FormGroup({
      brand: new FormControl('', Validators.required),
      model: new FormControl('', Validators.required),
      year: new FormControl('', [
        Validators.required,
        Validators.min(1900),
        Validators.max(new Date().getFullYear())
      ])
    });
  }

  onAddCar(): void {
    this.form.reset();
    this.action = Action.Add;
  }

  onEditCar(car: Car): void {
    this.selectedCar = car;
    this.form.patchValue(car);
    this.action = Action.Edit;
  }

  onDeleteCar(car: Car): void {
    this.selectedCar = car;
    this.action = Action.Delete;
  }

  onSubmitCar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    if (this.action === Action.Add) {
      this.addCar();
    } else if (this.action === Action.Edit) {
      this.editCar();
    }

    this.cancel();
  }

  addCar(): void {
    this.cars.push(this.form.value);
    this.action = Action.View;
  }

  editCar(): void {
    if (this.selectedCar) {
      this.cars[this.getSelectedCarIndex()] = this.form.value;
    }
    this.action = Action.View;
  }

  deleteCar(): void {
    if (this.selectedCar) {
      this.cars.splice(this.getSelectedCarIndex(), 1);
    }
    this.action = Action.View;
  }

  cancel(): void {
    this.selectedCar = undefined;
    this.action = Action.View;
    this.form.reset();
  }

  private getSelectedCarIndex(): number {
    return this.cars.findIndex(car => car === this.selectedCar);
  }
}
