import * as auth from '../auth/state/auth.reducers'
import { createFeatureSelector, createSelector } from '@ngrx/store';

export interface AppState{
    isAuthenticated: boolean;
    authState: auth.AuthState;
}

export const reducers = {
    auth: auth.AuthReducer
  };

  export const authState = createFeatureSelector<AppState>('auth');