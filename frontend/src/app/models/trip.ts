import {Airport} from "@app/models/Airport";

export class Trip {
    start?: Airport;
    through?: Airport[];
    end?: Airport;
    price?: Price;
    distance?: Distance;
}

export class Price {
    total: number = 0;
    currency: string ="";

}

export class Distance {
    total: number = 0;
    in: string ="";
}


