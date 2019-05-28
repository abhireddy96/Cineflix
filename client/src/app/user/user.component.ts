import { Component, OnInit } from '@angular/core';
import { User } from './user.model';
import { Movie } from '../movie/movie.model';
import { ActivatedRoute } from '@angular/router';
import { HttpService } from '../services/http.service.';
import { Store } from '@ngrx/store';
import { AppState, authState } from '../state/app-state';
import { Observable } from 'rxjs';


@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  user: User;
  movies: Array<Movie>;
  getState: Observable<any>;

  constructor(private route: ActivatedRoute, private store: Store<AppState>, private httpService: HttpService) { 
    this.getState = this.store.select(authState);
  }
  ngOnInit() {
    this.getState.subscribe((state) => {
      this.user = state.user;
      this.httpService.getMovieById(this.user.preferences.movies.join()).subscribe(res=>{this.movies=res;});
    });
}

}
