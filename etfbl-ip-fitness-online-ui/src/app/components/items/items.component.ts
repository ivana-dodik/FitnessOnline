import { CommonModule } from '@angular/common';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { NgxPaginationModule } from 'ngx-pagination';
import { Category } from 'src/app/interfaces/category';
import { Program } from 'src/app/interfaces/item';
import { UserEntity } from 'src/app/interfaces/user';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { CategoryService } from 'src/app/services/category.service';
import { ProgramService } from 'src/app/services/program.service';
import { UserService } from 'src/app/services/user.service';
import StartToastifyInstance from 'toastify-js';
import { ProgramCardComponent } from "../program-card/program-card.component";
import { CategoryModalComponent } from '../category-modal/category-modal.component';

@Component({
    selector: 'app-items',
    standalone: true,
    templateUrl: './items.component.html',
    styleUrl: './items.component.css',
    imports: [FormsModule, CommonModule, NgxPaginationModule, ProgramCardComponent, RouterModule, CategoryModalComponent]
})
export class ItemsComponent implements OnInit {
  @ViewChild('categoryModal')
  categoryModal!: CategoryModalComponent;

  @Input()
  public items!: Program[];

  public allPrograms!: Program[];


  @Input()
  public showSearch: boolean = true;
  @Input()
  public isDeleteable: boolean = false;
  @Input()
  public canAddNew: boolean = true;

  isAtBottom: boolean = false;
  howClose: number = 0;
  searchTerm!: string;
  searchCriteria: string = 'title';
  loggedInUser!: UserEntity;
  p: number = 1;
  showCategoriesSelector: boolean = false;
  categories: Category[] = [];
  isLoggedIn: boolean = false;

  constructor(
    private programService: ProgramService,
    private authenticationService: AuthenticationService,
    private router: Router,
    private userService: UserService,
    private categoryService: CategoryService
  ) {}

  ngOnInit(): void {
    window.addEventListener('scroll', this.scroll, true);

    this.authenticationService.getLoggedInUser().subscribe({
      next: (user) => {
        if (user!=null){
          if (!user.activated) {
            this.userService.requestNewEmail().subscribe(() => {
              StartToastifyInstance({
                  text: "Check your email.",
                  offset: {
                      x: 25, 
                      y: '85vh' 
                  },
              }).showToast();
              });
            return false;
          }
          else {
            this.isLoggedIn = true;
            this.authenticationService.isLoggedIn.next(true);
            return true;
          }
        } else {
          return false;
        }
      }
    });  

    if (!this.items) {
      this.programService
        .getActivePrograms()
        .subscribe((res) => { this.items = res; this.allPrograms = res; });
    }
  }

  ngOnDestroy() {
    window.removeEventListener('scroll', this.scroll, true);
  }

  scroll = (event: any): void => {
    this.isAtBottom = false;
    this.howClose =
      (document.body.offsetHeight + window.scrollY) /
      document.body.scrollHeight;
    if (this.howClose > 0.935) {
      this.isAtBottom = true;
    }
  };

  resetSearch() {
    this.items = this.allPrograms;
  }

  searchPrograms() {
    this.resetSearch();

    if (!this.searchTerm || this.searchTerm == '' || (!this.searchCriteria && this.searchCriteria == '')) {
      return;
    }

    if (this.searchCriteria === 'title') {
      this.items = this.items.filter((p) =>
        p.title.toLowerCase().includes(this.searchTerm.toLowerCase())
      );
    } else if (this.searchCriteria === 'description') {
      this.items = this.items.filter((p) =>
        p.description.toLowerCase().includes(this.searchTerm.toLowerCase())
      );
    }

    window.scrollBy(0, 0.01);
  }

  filterByCategory(event: any) {
    this.resetSearch();

    if (!event || !event.target || !event.target.value) {
      return;
    }

    this.items = this.items.filter(p => p.categoryId == event.target.value);

    window.scrollBy(0, 0.01);
  }

  handleSelectedSearchCategory(event: any) {
    this.resetSearch();

    if (event.target.value.trim() == 'category') {
      this.categoryService.getAll().subscribe({
        next: res => { 
          this.categories = res;
          this.showCategoriesSelector = true;
        }
      });
    }

    else {
      this.showCategoriesSelector = false;
    }
  }

  removeItemCallback = (program: Program) => {
    this.items.splice(this.items.indexOf(program), 1);
  }


}

