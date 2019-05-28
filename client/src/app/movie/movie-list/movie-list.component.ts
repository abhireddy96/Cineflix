import { Component, OnInit, Input } from '@angular/core';
import { Movie } from '../movie.model';

@Component({
  selector: 'app-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.css']
})
export class MovieListComponent implements OnInit {

  @Input()
  movies: Movie[];
  
  config:any={
      itemsPerPage: 10,
      currentPage: 1
  }

  pageChanged(event){
    this.config.currentPage = event;
  }

  constructor() { }

  ngOnInit() {
  }
}
