import { Component, OnInit } from '@angular/core';
import { HttpService } from 'src/app/services/http.service.';
import { Movie } from 'src/app/movie/movie.model';
import { Subject, Observable } from 'rxjs';
import { debounceTime, distinctUntilChanged, map, switchMap } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent {

  seacrhText = new Subject<string>();
  movies: Movie[];

  constructor(private httpService: HttpService, private router: Router) {
    this.search(this.seacrhText).subscribe(
      (res) => {
        this.movies = res;
        console.log("Result : ", res);
      },
      (error) => {
        this.movies = null;
      }
    );
    router.events.subscribe(res => {
      this.movies = null;
    });
  }

  search(terms: Observable<string>): Observable<Movie[]> {
    return terms.pipe(
      debounceTime(400),
      distinctUntilChanged(),
      switchMap(term => this.httpService.searchText(term))
      );
  }

  onKeyEnter(text: string){
   if(text!=='')
     this.seacrhText.next(text);
    else
      this.clearSearch();
  }

  clearSearch(){
    this.movies=null;
  }

}
