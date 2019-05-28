
export class User {

	// public id: string;
	// public name: string;
	// public email: string;
	// public preferences: UserPreference;
	// public isAdmin: boolean;

    constructor(public id: string, public name: string, public email: string, public preferences: UserPreference, public isAdmin: boolean) {
    }

}

export class UserPreference {

	// public movies: Array<string>;
	// public stars: Array<string>;
	// public genres: Array<string>;

    constructor(public movies: Array<string>,public stars: Array<string>,genres: Array<string>) {
    }

}



