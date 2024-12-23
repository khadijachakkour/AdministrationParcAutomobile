export class Vehicle {
    id?: number;
    marque: string;
    modele: string;
    typeVehicule: string;
    couleur: string;
    dateDerniereMaintenance: Date;  
    dateAchat: Date;                
    statut: 'DISPONIBLE' | 'EN_MAINTENANCE' | 'RESERVE';
  
    constructor(
      marque: string,
      modele: string,
      typeVehicule: string,
      couleur: string,
      dateDerniereMaintenance: Date,
      dateAchat: Date,
      statut: 'DISPONIBLE' | 'EN_MAINTENANCE' | 'RESERVE',
      id?: number
    ) {
      this.marque = marque;
      this.modele = modele;
      this.typeVehicule = typeVehicule;
      this.couleur = couleur;
      this.dateDerniereMaintenance = dateDerniereMaintenance;
      this.dateAchat = dateAchat;
      this.statut = statut;
      if (id) this.id = id;
    }
  }