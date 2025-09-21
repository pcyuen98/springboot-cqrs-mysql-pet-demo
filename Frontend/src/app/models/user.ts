import { Status } from "./status";

export class User {
    idUser: any;
    username: string;
    name: string;
    surname: string;
    photo: string;
    description: string;
    creationDate: Date;
    deletionDate: Date;
    isChecked: boolean;
    status: Status;
    token: any
    language: any
    expiry: any
}