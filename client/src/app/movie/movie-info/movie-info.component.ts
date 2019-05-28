import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Movie } from '../movie.model';
import { HttpService } from 'src/app/services/http.service.';
import { Store } from '@ngrx/store';
import { AppState, authState } from 'src/app/state/app-state';
import { MatSnackBar } from '@angular/material';
import { LoadUser } from 'src/app/auth/state/auth.actions';
import { User } from 'src/app/user/user.model';
import { Observable } from 'rxjs';
import { SnackBarComponent } from 'src/app/shared/snack-bar/snack-bar.component';

@Component({
  selector: 'app-movie-info',
  templateUrl: './movie-info.component.html',
  styleUrls: ['./movie-info.component.css']
})
export class MovieInfoComponent implements OnInit {

  movie: Movie = new Movie();
  movies: Array<Movie>;
  userID: string;
  isSubscribed: boolean = false;
  isAuthenticated: boolean = false;
  getState: Observable<any>;

  constructor(private route: ActivatedRoute,
    private httpService: HttpService,
    private store: Store<AppState>,
    private snackBar: MatSnackBar) {
      this.getState = this.store.select(authState);
     }

  ngOnInit() {
    this.route.params.subscribe((response: Object) => {
      this.httpService.getMovieById(response['id'])
        .subscribe(response => {
          this.movie = response[0];
          this.httpService.getMoviesByGenre(this.movie.genres.join(','))
            .subscribe(response => { this.movies = response; });
          this.getState.subscribe((state) => {
            this.isAuthenticated = state.isAuthenticated;
            this.isSubscribed = state.user.preferences.movies.includes(this.movie.id);
          });
        });
    })
  }

  subscribeMovie() {
    this.store.select(authState).subscribe(state => { console.log("State: ", state); this.userID = state['user'].id });
    this.httpService.subscribeUserToMovie(this.userID, this.movie.id).subscribe(
      (res: User) => {
        this.store.dispatch(new LoadUser(res));
        console.log(res);
        this.snackBar.openFromComponent(SnackBarComponent,
          {
         data:{message:"Subscribed!!!"},
         duration: 3000,
         verticalPosition: 'top'
        } );
      },
      (error) => {
        console.log(error); 
        this.snackBar.openFromComponent(SnackBarComponent,
          {
         data:{message:"Unable to subscribe!!!"},
         duration: 3000,
         verticalPosition: 'top'
        } );
      });
  }

  unSubscribeMovie() {
    this.store.select(authState).subscribe(
      (state) => {
        console.log("State: ", state);
        this.userID = state['user'].id;
      });
    this.httpService.unSubscribeUserFromMovie(this.userID, this.movie.id).subscribe(
      (res: User) => {
        console.log(res);
        this.store.dispatch(new LoadUser(res));
        this.snackBar.openFromComponent(SnackBarComponent,
          {
         data:{message:"Unsubscribed!!!"},
         duration: 3000,
         verticalPosition: 'top'
        } );
      },
      (error) => {
        console.log(error);
        this.snackBar.openFromComponent(SnackBarComponent,
          {
         data:{message:"Oops Something wrong!!!"},
         duration: 3000,
         verticalPosition: 'top'
        } );
      });
  }
}
