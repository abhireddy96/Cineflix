import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DemoMaterialModule } from 'src/app/material.module';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { AppRoutingModule } from '../app-routing.module';
import { HomeComponent } from './home/home.component';
import { SearchComponent } from '../shared/search/search.component';
import { SocialComponent } from '../shared/social/social.component';
import { MovieComponent } from '../movie/movie.component';

@NgModule({
  declarations: [
    FooterComponent,
    HeaderComponent,
    HomeComponent,
    SearchComponent,
    SocialComponent
  ],
  imports: [
   CommonModule,
   DemoMaterialModule,
   AppRoutingModule
  ],
  exports: [
    FooterComponent,
    HeaderComponent
  ]
})
export class LayoutModule { }
