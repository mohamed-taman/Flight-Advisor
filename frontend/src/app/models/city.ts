import {Comment} from './comment';

export class City {
    id?: number;
    name: string;
    country?: string;
    countryId?: number;
    description: string;
    comments?: Comment[];

    constructor(name: string, description: string, countryId?: number, country?: string) {
        this.name = name;
        if (country?.trim().length != 0)
            this.country = country;
        if (countryId != 0)
            this.countryId = countryId;
        this.description = description;
    }

    public static of(id: number, name: string, country: string,
              description: string, comments?: Comment[]): City {
        let city = new City(name,description,0, country);
        city.id = id;
        if (comments) {
            city.comments = comments
        }
        return city;
    }
}
