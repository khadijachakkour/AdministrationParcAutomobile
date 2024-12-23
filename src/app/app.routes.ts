import { Routes } from '@angular/router';
import { LoginComponent } from './Pages/login/login.component';
import { LayoutComponent } from './Pages/layout/layout.component';
import { DashboardComponent } from './Pages/dashboard/dashboard.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { RegistrationComponent } from './registration/registration.component';
import { AuthenticationComponent } from './authentication/authentication.component';
import { ParcDashboardComponent } from './Pages/parc-dashboard/parc-dashboard.component';
import { TechnicianDashboardComponent } from './Pages/technician-dashboard/technician-dashboard.component';
import { UserRegistrationComponent } from './user-registration/user-registration.component';

export const  routes: Routes = [
    {
        path: '' , redirectTo:'home', pathMatch :'full'
    },
    {
        path: '' , redirectTo:'login', pathMatch :'full'
    },
    {
        path: '' , redirectTo:'userlist', pathMatch :'full'
    },
    {
        path: '' , redirectTo:'register', pathMatch :'full'
    },
    {
        path: '' , redirectTo:'auth', pathMatch :'full'
    },
    {
        path: '' , redirectTo:'parc-dashboard', pathMatch :'full'
    },
    {
        path: '' , redirectTo:'technician-dashboard', pathMatch :'full'
    },
    {
        path: '' , redirectTo:'Registrer', pathMatch :'full'
    },
    {
        path : 'Registrer',
        component: UserRegistrationComponent
    },
    {
        path : 'home',
        component: LayoutComponent
    },
    {
        path : 'technician-dashboard',
        component: TechnicianDashboardComponent
    },
    {
        path : 'parc-dashboard',
        component: ParcDashboardComponent
    },
    {
        path : 'auth',
        component: AuthenticationComponent
    },
    {
        path : 'register',
        component: RegistrationComponent
    },
    {
        path : 'userlist',
        component: UserListComponent
    },
    {
        path : 'login',
        component: LoginComponent
    },
    {
        path : '',
        component:DashboardComponent,
        children: [
            {
                path: 'dashboard',
                component:DashboardComponent
            }
        ]
    }
];
