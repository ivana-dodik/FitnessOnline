import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { AddProgramDto, Program } from "../interfaces/item";
import { Observable, catchError } from "rxjs";
import { ImageUrlDto } from "../interfaces/image";
import { hrtime } from "process";

@Injectable({
    providedIn: 'root',
  })

export class ProgramService {
    getProductById(id: number) {
      throw new Error('Method not implemented.');
    }

    constructor(private http: HttpClient){}

    items: Program[] = [];

    public getPrograms(): Observable<Program[]> {
        return this.http.get<Program[]>('http://localhost:9000/api/v1/programs');
    }

    public getActivePrograms(): Observable<Program[]> {
        return this.http.get<Program[]>('http://localhost:9000/api/v1/programs/available');
      }

    public getProgramById(id: number): Observable<Program> {
        return this.http.get<Program>(
          'http://localhost:9000/api/v1/programs/' + id
        );
    }

    public addNewProgram(addProgramDto: AddProgramDto): Observable<Program> {
        return this.http.post<Program>('http://localhost:9000/api/v1/programs', addProgramDto);
    }

    public participateInProgram(participationDto: any): Observable<any>{
        return this.http.post('http://localhost:9000/api/v1/participation', participationDto);
    }

    public getAllFinished(): Observable<Program[]> {
        return this.http.get<Program[]>('http://localhost:9000/api/v1/programs/finished');
    }

    public getAllParticipated(): Observable<Program[]> {
        return this.http.get<Program[]>('http://localhost:9000/api/v1/programs/participated');
    }

    public getAllCurrentlyInstructing(): Observable<Program[]> {
        return this.http.get<Program[]>('http://localhost:9000/api/v1/programs/instructing');
    }

    public deleteItemById(id: number): Observable<null> {
        return this.http.delete<null>('http://localhost:9000/api/v1/programs/' + id);
    }

    public getImagesForProgram(id: number): Observable<string[]> {
        return this.http.get<string[]>(
          'http://localhost:9000/api/v1/programs/' + id + '/images'
        );
    }

    public getThumbnailForProgram(id: number): Observable<ImageUrlDto> {
        return this.http.get<ImageUrlDto>(
          'http://localhost:9000/api/v1/resources/programs/' + id + '/thumbnail'
        );
    }

    public uploadImagesForProgram(id: number, files: File[]) {
        let headers = new HttpHeaders();
        let formData = new FormData();
        for (let i = 0; i < files.length; i++){
            const blob = new Blob([files[i]], {type: files[i].type});
            formData.append('files', blob, files[i].name);
        }
        headers = headers.set('Content-Type', 'multipart/form-data');

        return this.http.post<void>('http://localhost:9000/api/v1/resources/programs/' + id, formData, {headers: headers});
    }

    public endProgram(editProgram: Program): Observable<any> {
        return this.http.patch<any>('http://localhost:9000/api/v1/programs', editProgram)
          .pipe(
            catchError(error => {
              throw 'An error occurred while closing the program.';
            })
          );
      }

}