export class Country {
    private readonly id: number;
    private readonly name: string;

    constructor(id: number, name: string) {
        this.id = id;
        this.name = name;
    }

    public getId(): number { return this.id; }
    public getName(): string { return this.name;}
}
