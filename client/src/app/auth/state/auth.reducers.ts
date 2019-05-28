import { User } from 'src/app/user/user.model';
import * as forRoot from '../../state/app-state'
import * as AuthActions from './auth.actions';
import { State } from '@ngrx/store';

export interface AuthState {
    user: User;
    jwtToken: string;
    isAuthenticated: boolean;
    errorMessage: string | null;
}

const initialState: AuthState = {
    user: null,
    jwtToken: '',
    isAuthenticated: false,
    errorMessage: null

};

export function AuthReducer(state = initialState, action: AuthActions.All): AuthState {

    switch (action.type) {
        case AuthActions.AuthActionTypes.LOGIN_SUCCESS: {
            console.log(action);
            return {
                ...state,
                user: action.payload["user"],
                jwtToken: action.payload["auth_token"],
                isAuthenticated: true,
            };
        }
        case AuthActions.AuthActionTypes.LOGIN_FAILURE: {
            return {
                ...state,
                isAuthenticated: false,
                errorMessage: 'Incorrect email and/or password.'
            };
        }
        case AuthActions.AuthActionTypes.SIGNUP_SUCCESS: {
            return {
                ...state,
                user: action.payload["user"],
                jwtToken: action.payload["auth_token"],
                isAuthenticated: true,
            };
        }
        case AuthActions.AuthActionTypes.SIGNUP_FAILURE: {
            return {
                ...state,
                isAuthenticated: false,
                errorMessage: 'Incorrect email and/or password.'
            };
        }
        case AuthActions.AuthActionTypes.LOGOUT: {
            return initialState;
        }
        case AuthActions.AuthActionTypes.LOAD_USER: {
            return {
                ...state,
                user: action.payload
            };
        }
        default: {
            return state;
        }
    }
}