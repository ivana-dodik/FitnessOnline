import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ActivityLogDto, AddActivityLogDto } from '../interfaces/activityLog';

@Injectable({
  providedIn: 'root'
})
export class ActivityLogsService {
  private apiUrl = '/api/activity-logs';

  constructor(private http: HttpClient) { }

  addNewActivityLog(addActivityLogDto: AddActivityLogDto): Observable<ActivityLogDto> {
    return this.http.post<ActivityLogDto>('http://localhost:9000/api/v1/activity-logs', addActivityLogDto);
  }

  getActivityLogs(): Observable<ActivityLogDto[]> {
    return this.http.get<ActivityLogDto[]>('http://localhost:9000/api/v1/activity-logs');
  }

  getWeightOverTime(before?: string, after?: string): Observable<any> {
    let params = new HttpParams();
    if (before) params = params.set('before', before);
    if (after) params = params.set('after', after);

    console.log("SERVIS: before: " + before + "   after: " + after);
    console.log("SERVIS: params.before: " + params.get('before') + "   params.after: " + params.get('after'));

    return this.http.get<any>('http://localhost:9000/api/v1/activity-logs/user/weight', {params});
    
  }
}
