import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Actions, Effect, ofType } from '@ngrx/effects';
import { tap, map, switchMap, catchError } from 'rxjs/operators';
import { HttpService } from 'src/app/services/http.service.';
import { AuthActionTypes, Login, LoginSuccess, LoginFailure, SignupSuccess, Signup, SignupFailure, } from './auth.actions';
import { Observable } from 'rxjs';
import { User } from 'src/app/user/user.model';
import { rootRenderNodes } from '@angular/core/src/view';
import { routerNgProbeToken } from '@angular/router/src/router_module';
import { MatSnackBar } from '@angular/material';
import { SnackBarComponent } from 'src/app/shared/snack-bar/snack-bar.component';

@Injectable()
export class AuthEffects {

  constructor(
    private actions: Actions,
    private httpService: HttpService,
    private router: Router,
    private snackBar: MatSnackBar
  ) { }

  @Effect({ dispatch: false })
  LoginSuccess: Observable<any> = this.actions.pipe(
    ofType(AuthActionTypes.LOGIN_SUCCESS),
    tap((result) => {
      console.log(result);
      sessionStorage.setItem('token', result.payload["auth_token"]);
      sessionStorage.setItem('user', JSON.stringify(result.payload["user"]));
      
    })
  );

  @Effect({ dispatch: false })
  LoginFailure: Observable<any> = this.actions.pipe(
    ofType(AuthActionTypes.LOGIN_FAILURE));


  @Effect({ dispatch: false })
  SignupSuccess: Observable<any> = this.actions.pipe(
    ofType(AuthActionTypes.SIGNUP_SUCCESS),
    tap((result) => {
      console.log(result);
      sessionStorage.setItem('token', JSON.stringify(result.payload["auth_token"]));
      sessionStorage.setItem('userID', result.payload["user"]["email"]);
      this.router.navigateByUrl("/");
      this.snackBar.openFromComponent(SnackBarComponent,
        {
          data: { message: "Registration Successfull!!!" },
          duration: 3000,
          verticalPosition: 'top'
        });
    })
  );

  @Effect({ dispatch: false })
  SignupFailure: Observable<any> = this.actions.pipe(
    ofType(AuthActionTypes.SIGNUP_FAILURE));

  @Effect()
  SignUp: Observable<any> = this.actions
    .pipe(ofType(AuthActionTypes.SIGNUP),
      map((action: Signup) => action.payload),
      switchMap(payload => {
        return this.httpService.signup(payload).pipe(
          map((response) => {
            console.log(response);
            return new SignupSuccess(response);
          }), catchError((error) => {
            console.log(error);
            this.snackBar.openFromComponent(SnackBarComponent,
              {
                data: { message: "Registration Failed!!!" },
                duration: 3000,
                verticalPosition: 'top'
              });
            return Observable.create(new SignupFailure({ error: error }));
          })
        );
      })
    );

  @Effect()
  Login: Observable<any> = this.actions
    .pipe(ofType(AuthActionTypes.LOGIN),
      map((action: Login) => action.payload),
      switchMap(payload => {
        return this.httpService.login(payload)
          .pipe(
            map((response) => {
              console.log(response); return new LoginSuccess(response);
            }),
            catchError((error) => {
              console.log(error); 
              this.snackBar.openFromComponent(SnackBarComponent,
                {
                  data: { message: "Login Failed!!!" },
                  duration: 3000,
                  verticalPosition: 'top'
                });
              return Observable.create(new LoginFailure({ error: error }));
            })
          );

      })
    );

  @Effect({ dispatch: false })
  Logout: Observable<any> = this.actions.pipe(
    ofType(AuthActionTypes.LOGOUT),
    tap((user) => {
      console.log("Token: ", sessionStorage.getItem('token'))
      this.httpService.logout(sessionStorage.getItem('token')).subscribe(res => {
        sessionStorage.clear();
      },
        error => {
          sessionStorage.clear();
        },
        ()=>{
          this.router.navigateByUrl('/');
          this.snackBar.openFromComponent(SnackBarComponent,
            {
              data: { message: "Logged OUT!!!" },
              duration: 3000,
              verticalPosition: 'top'
            });
        });
    })
  );
}

