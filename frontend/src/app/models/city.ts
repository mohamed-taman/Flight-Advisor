import {Comment} from './comment';

export class City {
    id: number;
    name: string;
    country?: string;
    countryId?: number;
    description: string;
    comments?: Comment[];

    constructor(name: string, description: string, countryId?: number, country?: string) {
        this.id = 0;
        this.name = name;
        if (country?.trim().length != 0)
            this.country = country;
        if (countryId != 0)
            this.countryId = countryId;
        this.description = description;
    }

    public static of(id: number, name: string, description: string,
                     country?: string, comments?: Comment[]): City {
        let city = new City(name, description, 0, country);
        city.id = id;
        if (comments) {
            city.comments = comments
        }
        return city;
    }

    public toString = (): string => {
        return `City {Id:${this.id}, Name:${this.name}, Country: ${this.country},
        With ${this.comments ? this.comments.length : 0} comment(s)}`;
    }
}
