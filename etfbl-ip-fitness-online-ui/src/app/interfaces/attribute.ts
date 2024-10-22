
export interface AttributeNameValue {
  name: string;
  value: string;
}

export interface AttributeIdValue {
  attributeId: number;
  value: string | null;
}


export interface AttributeEntity {
  id: number;
  name: string;
  categoryId: number;
  deleted: boolean;
}
