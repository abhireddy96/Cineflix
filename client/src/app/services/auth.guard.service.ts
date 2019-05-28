import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Store } from '@ngrx/store';
import { AppState, authState } from '../state/app-state';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate{

  constructor( private router: Router, private store: Store<AppState>) {}

  isLogged:boolean = false;

 
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

    this.store.select(authState).subscribe(state => {
      this.isLogged = state.isAuthenticated;
    });

    if (this.isLogged) {
      return true;
    }

    this.router.navigateByUrl(
      this.router.createUrlTree(
        ['/login'], {
          queryParams: {
            return: state.url
          }
        }
      )
    );

    return false;
  }
}
