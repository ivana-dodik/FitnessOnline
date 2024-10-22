import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";
import { LoginDto } from "../interfaces/login";
import { UserEntity } from "../interfaces/user";

@Injectable({
    providedIn: 'root', // Ovaj servis je dostupan na nivou celog aplikacije (root injector)
})

export class AuthenticationService {
    constructor(private http: HttpClient) {}

    public isLoggedIn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

    public login(loginDto: LoginDto): Observable<LoginDto> {

        console.log("AUTH SERVICE: isLoggedIn: " + this.isLoggedIn);

        console.log("AUTH SERVICE: loginDTO: " + loginDto.username + "  " + loginDto.password);

        return this.http.post<LoginDto>(
            'http://localhost:9000/api/v1/auth/login', 
            loginDto 
        );
    }

    getLoggedInUser() {
        let loggedInUser: LoginDto | null = null;
        //let loggedInUser: LoginDto = JSON.parse(localStorage.getItem('user') || '{}');
        if (typeof localStorage !== 'undefined') {
            
            const userString: string | null = localStorage.getItem('user');
            if (userString) {
                loggedInUser = JSON.parse(userString);
            }
        }
        return this.http.post<UserEntity>(
            'http://localhost:9000/api/v1/auth/user-details',
            loggedInUser
        )
    }
}
