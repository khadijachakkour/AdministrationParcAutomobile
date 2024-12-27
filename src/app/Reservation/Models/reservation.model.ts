export class Reservation {
    id?: string | number;
    userId: number;
    vehicleId: number;
    reservationDate: Date;
    status: string;
    email: string;
    marque: string;
    modele: string;
    typeVehicule: string;
    couleur: string;
  
    constructor(
      userId: number = 0,
      vehicleId: number = 0,
      reservationDate: Date = new Date(),
      status: string = '',
      email: string = '',
      marque: string = '',
      modele: string = '',
      typeVehicule: string = '',
      couleur: string = '',
      id?: string | number
    ) {
      this.id = id;
      this.userId = userId;
      this.vehicleId = vehicleId;
      this.reservationDate = reservationDate;
      this.status = status;
      this.email = email;
      this.marque = marque;
      this.modele = modele;
      this.typeVehicule = typeVehicule;
      this.couleur = couleur;
    }
  }
  