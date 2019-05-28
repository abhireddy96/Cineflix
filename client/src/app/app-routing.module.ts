import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './layout/home/home.component';
import { MovieComponent } from './movie/movie.component';
import { AuthGuardService } from './services/auth.guard.service';
import { LoginComponent } from './auth/login/login.component';
import { MovieInfoComponent } from './movie/movie-info/movie-info.component';
import { StarInfoComponent } from './movie/star-info/star-info.component';
import { SignupComponent } from './auth/signup/signup.component';
import { UserComponent } from './user/user.component';

const routes: Routes = [
  {
    path : '',
    component: HomeComponent
  },
  {
    path : 'movie',
    component: MovieComponent,
    children :[
    ]
    //,canActivate: [AuthGuardService]
  },
  {
    path : 'login',
    component: LoginComponent
  },
  {
    path : 'signup',
    component: SignupComponent
  },
  { path : 'movie/movie-info/:id',
    component: MovieInfoComponent
  } ,
  { path : 'movie/star-info/:name',
    component: StarInfoComponent
  },
  { path : 'user',
    component: UserComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
