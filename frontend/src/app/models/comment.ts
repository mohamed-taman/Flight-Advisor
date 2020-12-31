export class Comment {
    readonly id: number;
    comment: string;
    readonly by?: string;
    readonly createdAt?: Date;
    readonly updatedAt?: Date;

    constructor(id: number, comment: string){
        this.id = id;
        this.comment = comment;
    }
}
