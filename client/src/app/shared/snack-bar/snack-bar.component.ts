import { Component, OnInit, Inject } from '@angular/core';
import { MatSnackBar, MAT_SNACK_BAR_DATA, MatSnackBarRef } from '@angular/material';

@Component({
  selector: 'app-snack-bar',
  templateUrl: './snack-bar.component.html',
  styleUrls: ['./snack-bar.component.css']
})
export class SnackBarComponent {

  action: String;
  message: string;
  constructor(private snackBarRef: MatSnackBarRef<SnackBarComponent>,
               @Inject(MAT_SNACK_BAR_DATA) public data: any) {
                 this.action=data.action;
                 this.message=data.message;
                }

  doAction(){
    this.snackBarRef.dismissWithAction();
  }
}
