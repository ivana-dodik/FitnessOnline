import { Component, HostListener, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {
  isSmallScreen: boolean = false;
  isMenuOpen: boolean = false;
  isLoggedIn: boolean = false;

  constructor(private authenticationService: AuthenticationService, private router: Router) {}

  ngOnInit(): void {
    this.authenticationService.isLoggedIn.subscribe(value => this.isLoggedIn = value);
  }

  ngOnDestroy() {
    this.isMenuOpen = false;
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.checkScreenSize();
  }

  checkScreenSize() {
    this.isSmallScreen = window.innerWidth < 768;
    if (!this.isSmallScreen) {
      this.isMenuOpen = false;
    }
  }

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
    console.log('toggle' + this.isMenuOpen);
  }

  logOut() {
    this.authenticationService.isLoggedIn.next(false);
    localStorage.removeItem("user");
    this.isMenuOpen = false;
    this.router.navigate(['/login']);
  }
}
