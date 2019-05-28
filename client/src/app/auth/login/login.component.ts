import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MatDialog, MatDialogConfig, MatDialogRef } from '@angular/material';
import { Router, Route, ActivatedRoute } from '@angular/router';
import { Store } from '@ngrx/store';
import {AppState, authState} from '../../state/app-state'
import { Login } from '../state/auth.actions';
import { User } from 'src/app/user/user.model';
import { take, tap } from 'rxjs/operators';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private store: Store<AppState>) { }

  return: string = '';
  user: User;

  ngOnInit() {

    this.route.queryParams
      .subscribe(params => { this.return = params['return']; });
  }

  login(form: NgForm) {
    this.store.dispatch(new Login(form.value));
  }

}
