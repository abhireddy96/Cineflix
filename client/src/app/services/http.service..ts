import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Movie } from '../movie/movie.model';
import { Star } from '../movie/star.model';
import { User } from '../user/user.model';
import { environment } from 'src/environments/environment.prod';

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  apiURL: string;

  constructor(private httpClient: HttpClient) {
    this.apiURL = environment.api_url;
   }

  public logout(jwtToken: string): Observable<Object> {
    return this.httpClient.post<Object>(this.apiURL + '/user/logout', {}, {
      headers: new HttpHeaders().set('Authorization', 'Bearer ' + jwtToken)
    });
  }

  public login(payload: object): Observable<Object> {

    return this.httpClient.post<Object>(this.apiURL + '/user/login', payload);
  }

  public signup(payload: object): Observable<Object> {

    return this.httpClient.post<Object>(this.apiURL + '/user/register', payload);
  }


  public getAllMovies(): Observable<Movie[]> {

    return this.httpClient.get<Movie[]>(this.apiURL + '/movie/search?type=all&size=20');
  }

  public getMovieById(id: string): Observable<Movie[]> {

    return this.httpClient.get<Movie[]>(this.apiURL + '/movie/search?type=id&value=' + id);
  }

  public getMoviesOfCrewByName(name: string): Observable<Movie[]> {

    return this.httpClient.get<Movie[]>(this.apiURL + '/movie/search?type=crew&value=' + name);
  }

  public getMoviesByGenre(name: string): Observable<Movie[]> {

    return this.httpClient.get<Movie[]>(this.apiURL + '/movie/search?type=genres&value=' + name);
  }

  public getStarByName(name: string): Observable<Star[]> {

    return this.httpClient.get<Star[]>(this.apiURL + '/star/search?type=name&value=' + name);
  }

  public getUserByEmail(id: string): Observable<User> {

    return this.httpClient.get<User>(this.apiURL + '/user/search?type=id&value=' + id);
  }

  public subscribeUserToMovie(userID: string, movieID: string) {

    return this.httpClient.post<Object>(this.apiURL + '/user/subscribe?type=movies&user=' + userID + '&program=' + movieID, {});
  }

  public unSubscribeUserFromMovie(userID: string, movieID: string) {

    return this.httpClient.delete<Object>(this.apiURL + '/user/subscribe?type=movies&user=' + userID + '&program=' + movieID, {});
  }

  public searchText(text: string): Observable<Movie[]> {

    console.log("Search : ", text);
    if (text.length)
      return this.httpClient.get<Movie[]>(this.apiURL + '/movie/search?type=query&value=' + text);
      throw Observable.throw(null);
  }

}
