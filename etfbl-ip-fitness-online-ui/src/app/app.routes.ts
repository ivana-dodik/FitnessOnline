import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { NgModule } from '@angular/core';
import { DailySuggestionsComponent } from './components/daily-suggestions/daily-suggestions.component';
import { ItemsComponent } from './components/items/items.component';
import { NewsComponent } from './components/news/news.component';
import { ConfirmRegistrationComponent } from './components/confirm-registration/confirm-registration.component';
import { ProgramDetailsComponent } from './components/program-details/program-details.component';
import { AuthenticationGuard } from './guards/authentication.guard';
import { AddProgramComponent } from './components/add-program/add-program.component';
import { ContactFormComponent } from './components/contact-form/contact-form.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { UserAccountEditComponent } from './components/user-account-edit/user-account-edit.component';
import { UserMessageService } from './services/userMessage.service';
import { UserMessagesComponent } from './components/user-messages/user-messages.component';
import { CorrespondenceComponent } from './components/correspondence/correspondence.component';
import { ActivityLogsComponent } from './components/activity-logs/activity-logs.component';
import { AddActivityLogComponent } from './components/add-activity-log/add-activity-log.component';
import { WeightGraphComponent } from './components/weight-graph/weight-graph.component';
import { CategoryModalComponent } from './components/category-modal/category-modal.component';

export const routes: Routes = [ 
    {
        path: '',
        redirectTo: '/news',
        pathMatch: 'full'
    },
    {
      path: 'news',
      component: NewsComponent,
    },
    {
      path: 'daily-suggestions',
      component: DailySuggestionsComponent,
    },
    {
        path: 'login',
        component: LoginComponent,
    },
    {
        path: 'registration',
        component: RegistrationComponent, 
    },
    {
      path: 'registration/validate',
      component: ConfirmRegistrationComponent, 
    },
    {
      path: 'login/validate',
      component: ConfirmRegistrationComponent,
    },
    {
      path: 'programs',
      component: ItemsComponent,
    },
    {
      path: 'programs/add',
      pathMatch: 'full',
      canActivate: [AuthenticationGuard],
      component: AddProgramComponent
    },
    {
      path: 'programs/:id',
      component: ProgramDetailsComponent,
    },
    {
      path: 'profile',
      canActivate: [AuthenticationGuard],
      component: UserProfileComponent,
    },
    {
      path: 'profile/subscribe',
      canActivate: [AuthenticationGuard],
      component: CategoryModalComponent
    },
    {
      path: 'profile/edit',
      canActivate: [AuthenticationGuard],
      component: UserAccountEditComponent,
    },
    {
      path: 'profile/activity-logs',
      canActivate: [AuthenticationGuard],
      component: ActivityLogsComponent,
    },
    {
      path: 'profile/activity-logs/add',
      pathMatch: 'full',
      canActivate: [AuthenticationGuard],
      component: AddActivityLogComponent,
    },
    {
      path: 'profile/activity-logs/graph',
      pathMatch: 'full',
      canActivate: [AuthenticationGuard],
      component: WeightGraphComponent,
    },
    {
      path: 'contact',
      canActivate: [AuthenticationGuard],
      component: ContactFormComponent,
    }, 
    {
      path: 'inbox',
      canActivate: [AuthenticationGuard],
      component: UserMessagesComponent,
    },
    { path: 'messages/:senderId', 
      component: CorrespondenceComponent 
    }
    
];
