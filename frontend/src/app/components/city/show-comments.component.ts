import {Component, OnDestroy, OnInit} from '@angular/core';
import {City} from "@app/models";
import {AlertService, CityService, SessionService} from "@app/services";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
    templateUrl: './show-comments.component.html',
    styleUrls: ['./show-comments.component.css']
})
export class ShowCommentsComponent implements OnInit, OnDestroy {
    form?: FormGroup;
    submitted: boolean = false;
    city: City = new City("", "");

    constructor(private formBuilder: FormBuilder,
                private cityService: CityService,
                private alertService: AlertService,
                private session: SessionService) {
    }

    ngOnInit(): void {
        this.city = this.session.get("city") as City;
        this.form = this.formBuilder
            .group({description: ['']});
    }

    ngOnDestroy(): void {
        this.session.remove("city");
        this.session.remove("commentId")
    }

    // convenience getter for easy access to form fields
    get f() {
        return this.form!.controls;
    }

    deleteComment(commentId: number): void {

        // reset alerts on submit
        this.alertService.clear();

        this.cityService.deleteComment(this.city.id, commentId)
            .subscribe({
                next: () => {
                    this.alertService.info("Comment deleted successfully.",
                        {autoClose: true});

                    this.refreshCity();
                },
                error: error => {
                    this.alertService.error(error, {autoClose: true});
                }
            });
    }

    onUpdate(commentId: number, comment: string): void {
        this.session.put("commentId", commentId);
        this.f.description.setValue(comment);
    }

    updateComment(): void {

        this.submitted = true;

        // reset alerts on submit
        this.alertService.clear();

        // stop here if form is invalid
        if (this.form!.invalid) {
            return;
        }

        this.cityService.updateComment(this.city.id, this.session.get("commentId"), this.f.description.value)
            .subscribe({
                next: () => {
                    this.alertService.info("Comment Updated successfully.",
                        {autoClose: true});
                    this.refreshCity();
                },
                error: error => {
                    this.alertService.error(error, {autoClose: true});
                }
            });

        this.submitted = false;
    }

    private refreshCity() {
        this.cityService
            .search({byName: this.city.name, commentsLimit: 0})
            .subscribe({
                next: (cities) => {
                    this.city = cities ? cities[0] : new City("", "");
                }
            });
    }
}
