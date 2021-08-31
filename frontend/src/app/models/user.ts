export class User {
    readonly id?: string;
    username?: string;
    password?: string;
    firstName?: string;
    lastName?: string;
    readonly token?: string | null;
    readonly authorities?: Array<string>;
}
