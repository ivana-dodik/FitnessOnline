import { AttributeIdValue, AttributeNameValue } from './attribute';

export interface Program {
  id: number;
  title: string;
  description: string;
  price: number;
  difficultyLevel : string;
  durationMinutes : number;
  location: string;
  instructorId: number;
  deleted: boolean;
  available: boolean;
  contactPhone: string;
  categoryId: number;
  dateCreated : Date;
  attributes: AttributeNameValue[] | null;
}

export interface AddProgramDto {
  title: string;
  description: string;
  price: number;
  difficultyLevel : string;
  durationMinutes : number;
  location: string;
  contactPhone: string;
  categoryId: number;
  dateCreated : Date;
  attributes: AttributeIdValue[];
}
