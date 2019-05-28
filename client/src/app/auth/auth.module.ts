import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {StoreModule} from '@ngrx/store'
import { LoginComponent } from './login/login.component';
import { FormsModule } from '@angular/forms';
import { DemoMaterialModule } from '../material.module';
import {AuthReducer} from './state/auth.reducers'

@NgModule({
  declarations: [LoginComponent],
  imports: [
    CommonModule, 
    FormsModule,
    DemoMaterialModule,
    StoreModule.forFeature("auth",AuthReducer)
  ]
})
export class AuthModule { }
