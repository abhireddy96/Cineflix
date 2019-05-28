import { Url } from 'url';

export class Movie {

  public id: string;
  public title: string;
  public year: number ;
	public type: string;
	public runtime: number;
	public poster: Url;
	public  cast: Array<string>;
	public plot: string;
	public directors: Array<string>;
	public writers: Array<string>;
	public countries: Array<string>;
	public genres: Array<string>;
	public imdb: Imdb;

    constructor() {
    }
}

export class Imdb {

  public votes: number;
  public rating: number;

    constructor() {
    }
}

