export interface MaintenanceCout {
  id: number;
  id_vehicule: number;
  cout: number;
  date: Date;
  vehicule: Vehicule; // Inclut les informations du véhicule
}

export interface Vehicule {
  id: number;
  marque: string;
  modele: string;
  typeVehicule: string;
  couleur: string;
  dateDerniereMaintenance: Date;
  dateAchat: Date; 
  statut: 'DISPONIBLE' | 'EN_MAINTENANCE' | 'RESERVE';
}
