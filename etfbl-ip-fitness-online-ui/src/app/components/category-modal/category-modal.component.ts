import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { error } from 'console';
import { concatMap } from 'rxjs/operators';
import { Category } from 'src/app/interfaces/category';
import { CategoryService } from 'src/app/services/category.service';
import { UserService } from 'src/app/services/user.service';
import StartToastifyInstance from 'toastify-js';


@Component({
  selector: 'app-category-modal',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './category-modal.component.html',
  styleUrl: './category-modal.component.css'
})
export class CategoryModalComponent implements OnInit {
  subscribedCategories: Category[] = [];

  otherCategories: Category[] = [];

  categories: Category[] = [];
  selectedCategories: Category[] = [];
  successMessage: string = '';

  constructor(private categoryService: CategoryService, private userService: UserService) { }

  ngOnInit(): void {
    this.showAllSubscriptionsAndLoadCategories();
    //this.showAllSubscriptions();
    //this.loadAllCategories();
  }

  showAllSubscriptionsAndLoadCategories() {
    this.userService.getSubscribedCategoriesByUserId().pipe(
      concatMap((res) => {
        this.subscribedCategories = res;
        return this.categoryService.getAll();
      })
    ).subscribe({
      next: (res) => {
        this.otherCategories = res.filter(category => !this.subscribedCategories.some(subscribedCategory => subscribedCategory.id === category.id));
      },
      error: () => {
        StartToastifyInstance({
          text: 'Error while fetching categories',
          offset: {
            x: 25,
            y: '85vh',
          },
        }).showToast();
      }
    });
  }

  showAllSubscriptions(){
    this.userService.getSubscribedCategoriesByUserId().subscribe({
      next: (res) => {
        //res.forEach(r => {console.log("BLA:  " + r.name + "  " + r.id)});
        this.subscribedCategories = res;
      }, error: () => {
        StartToastifyInstance({
          text: 'Error while fetching subscriptions.',
          offset: {
            x: 25,
            y: '85vh',
          },
        }).showToast();
      }
    });
  }
  
  loadAllCategories() {
    this.categoryService.getAll().subscribe({
      next: (res) => {
        this.otherCategories = res.filter(category => !this.subscribedCategories.some(subscribedCategory => subscribedCategory.id === category.id));
      },
      error: () => {
        StartToastifyInstance({
          text: 'Error while fetching categories',
          offset: {
            x: 25,
            y: '85vh',
          },
        }).showToast();
      }
    });
  }

  unsubscribe(category: Category) {
    this.userService.unsubscribeFromCategory(category.id).subscribe({
      next: () => {
        //this.showAllSubscriptions(); // Refresh subscribed categories after unsubscribing
        //this.loadAllCategories();
        this.showAllSubscriptionsAndLoadCategories();
        //this.successMessage = 'Unsubscribed successfully.';
        StartToastifyInstance({
          text: 'Unsubscribed successfully',
          offset: {
            x: 25,
            y: '85vh',
          },
        }).showToast();
      },
      error: (e) => {
        //this.successMessage = 'Error while unsubscribing.';
        console.log(e);
        StartToastifyInstance({
          text: 'Error while unsubscribing',
          offset: {
            x: 25,
            y: '85vh',
          },
        }).showToast();
      }
    });
  }

  subscribe(category: Category) {
    this.userService.subscribeToCategory(category.id).subscribe({
      next: () => {
        //this.showAllSubscriptions(); // Refresh subscribed categories after unsubscribing
        //this.loadAllCategories();
        this.showAllSubscriptionsAndLoadCategories();
        //this.successMessage = 'Subscribed successfully.';
        StartToastifyInstance({
          text: 'Subscribed successfully',
          offset: {
            x: 25,
            y: '85vh',
          },
        }).showToast();
      },
      error: () => {
        //this.successMessage = 'Error while subscribing.';
        StartToastifyInstance({
          text: 'Error while subscribing',
          offset: {
            x: 25,
            y: '85vh',
          },
        }).showToast();
      }
    });
  }
}