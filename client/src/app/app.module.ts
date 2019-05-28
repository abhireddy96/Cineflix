import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {StoreModule} from '@ngrx/store'
import { EffectsModule } from '@ngrx/effects';
import { FlexLayoutModule } from '@angular/flex-layout';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MovieComponent } from './movie/movie.component';
import { DemoMaterialModule } from './material.module';
import { FormsModule } from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { AuthGuardService } from './services/auth.guard.service';
import { LayoutModule } from './layout/layout.module';
import { AuthModule } from './auth/auth.module';
import {NgxPaginationModule} from 'ngx-pagination';
import { MAT_DIALOG_DEFAULT_OPTIONS } from '@angular/material';
import { MovieInfoComponent } from './movie/movie-info/movie-info.component';
import { StarInfoComponent } from './movie/star-info/star-info.component';
import { SignupComponent } from './auth/signup/signup.component';
import { UserComponent } from './user/user.component';
import { HttpService } from './services/http.service.';
import { AuthEffects } from './auth/state/auth.effects';
import { reducers } from './state/app-state';
import { SnackBarComponent } from './shared/snack-bar/snack-bar.component';
import { MovieListComponent } from './movie/movie-list/movie-list.component';
import { SocialComponent } from './shared/social/social.component';

@NgModule({
  declarations: [
    AppComponent,
    MovieComponent,
    MovieInfoComponent,
    StarInfoComponent,
    SignupComponent,
    UserComponent,
    SnackBarComponent,
    MovieListComponent
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    DemoMaterialModule,
    FormsModule,
    BrowserAnimationsModule,
    HttpClientModule,
    LayoutModule,
    AuthModule,
    FlexLayoutModule,
    NgxPaginationModule,
    StoreModule.forRoot(reducers,{}),
    EffectsModule.forRoot([AuthEffects])
  ],
  exports : [MovieComponent],
  providers: [
    {provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: {hasBackdrop: false}},
    HttpService,
    AuthGuardService
  ],
  bootstrap: [AppComponent],
  entryComponents: [SnackBarComponent]
})
export class AppModule { }
