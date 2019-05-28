import { Component, OnInit } from '@angular/core';
import { Movie } from './movie.model';
import { HttpService } from '../services/http.service.';

@Component({
  selector: 'app-movie',
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.css']
})
export class MovieComponent implements OnInit {

  movies = new Array<Movie>();

  constructor(public httpService: HttpService) { }

  ngOnInit() {

    this.httpService.getAllMovies().subscribe(response => {this.movies = response; });
  }

}
