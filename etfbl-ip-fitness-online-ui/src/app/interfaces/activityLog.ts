export interface ActivityLogDto {
   username: string;
   dateTime: string;
   exerciseType: string;
   durationMinutes: number;
   intensity: string;
   results: string;
}

export interface AddActivityLogDto {
   exerciseType: string;
   durationMinutes: number;
   intensity: string;
   results: number;
}

export interface ActivityLog{
   id: number;
   exerciseType: string;
   durationMinutes: number;
   intensity: string;
   results: number;
   logDate: Date;
   userId: number;
}