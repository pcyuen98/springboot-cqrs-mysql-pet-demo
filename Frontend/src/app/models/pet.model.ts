// models/pet.model.ts
export interface Pet {
  petId?: number; // backend may generate this
  name: string;
  status: string;
  category: {
    id: number;
  };
  tags: Array<{
    id: number;
  }>;
  photoUrl: string[]; // backend expects array
}
