import {Action} from '@ngrx/store'
import { User } from 'src/app/user/user.model';


export enum AuthActionTypes {
    LOGIN = '[Auth] Login',
    LOGIN_SUCCESS = '[Auth] Login Success',
    LOGIN_FAILURE = '[Auth] Login Failure',
    SIGNUP = '[Auth] Signup',
    SIGNUP_SUCCESS = '[Auth] Signup Success',
    SIGNUP_FAILURE = '[Auth] Signup Failure',
    LOGOUT = '[Auth] Logout',
    LOAD_USER = '[User] Load'
  }

  export class Login implements Action {
    readonly type = AuthActionTypes.LOGIN;
    constructor(public payload: any) {}
  }
  
  export class LoginSuccess implements Action {
    readonly type = AuthActionTypes.LOGIN_SUCCESS;
    constructor(public payload: any) {}
  }
  
  export class LoginFailure implements Action {
    readonly type = AuthActionTypes.LOGIN_FAILURE;
    constructor(public payload: any) {}
  }

  export class Signup implements Action {
    readonly type = AuthActionTypes.SIGNUP;
    constructor(public payload: any) {}
  }
  
  export class SignupSuccess implements Action {
    readonly type = AuthActionTypes.SIGNUP_SUCCESS;
    constructor(public payload: any) {}
  }
  
  export class SignupFailure implements Action {
    readonly type = AuthActionTypes.SIGNUP_FAILURE;
    constructor(public payload: any) {}
  }
  
  export class Logout implements Action {
    readonly type = AuthActionTypes.LOGOUT;
  }

  export class LoadUser implements Action {
    readonly type = AuthActionTypes.LOAD_USER;
    constructor(public payload: User) {}
  }
  
  export type All =
    | Login
    | LoginSuccess
    | LoginFailure
    | Signup
    | SignupSuccess
    | SignupFailure
    | Logout
    | LoadUser;