import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog, MatDialogConfig, MatDialogRef, MatSnackBar } from '@angular/material';
import { HttpService } from 'src/app/services/http.service.';
import { Router, ActivatedRoute } from '@angular/router';
import { NgForm } from '@angular/forms';
import { Store} from '@ngrx/store';
import { AppState } from 'src/app/state/app-state';
import { Signup } from '../state/auth.actions';
import { take, map, delay } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { SnackBarComponent } from 'src/app/shared/snack-bar/snack-bar.component';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  getState: Observable<any>;

  constructor(
    public changeDetectorRefs: ChangeDetectorRef,
    public router: Router,
    private route: ActivatedRoute,
    private store: Store<AppState>,
    private snackBar: MatSnackBar) {}

    return :string ='';
    

  ngOnInit() {
    this.route.queryParams
    .subscribe(params => {this.return = params['return'];});
  }

  signup(form: NgForm) {
    this.store.dispatch(new Signup(form.value));
  }

  signupSuccess() {
    this.snackBar.openFromComponent(SnackBarComponent,
      {
     data:{message:"Registration Successfull!!!"},
     duration: 3000,
     verticalPosition: 'top'
    } );
}
    
  signupFailure() {
    this.snackBar.openFromComponent(SnackBarComponent,
      {
     data:{message:"Registration Failed!!!"},
     duration: 3000,
     verticalPosition: 'top'
    } );
}
}
