import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpService } from 'src/app/services/http.service.';
import { Star } from '../star.model';
import { Movie } from '../movie.model';

@Component({
  selector: 'app-star-info',
  templateUrl: './star-info.component.html',
  styleUrls: ['./star-info.component.css']
})
export class StarInfoComponent implements OnInit {
  star: Star;
  movies: Array<Movie>;
  constructor(private route: ActivatedRoute, private httpService: HttpService) { }
  ngOnInit() {
    this.httpService.getStarByName(this.route.snapshot.paramMap.get('name'))
      .subscribe(response => { this.star = response[0]; });
    this.httpService.getMoviesOfCrewByName(this.route.snapshot.paramMap.get('name'))
      .subscribe(response => { this.movies = response; });
  }
}
