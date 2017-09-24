/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { WegoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RatingDetailComponent } from '../../../../../../main/webapp/app/entities/rating/rating-detail.component';
import { RatingService } from '../../../../../../main/webapp/app/entities/rating/rating.service';
import { Rating } from '../../../../../../main/webapp/app/entities/rating/rating.model';

describe('Component Tests', () => {

    describe('Rating Management Detail Component', () => {
        let comp: RatingDetailComponent;
        let fixture: ComponentFixture<RatingDetailComponent>;
        let service: RatingService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WegoTestModule],
                declarations: [RatingDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RatingService,
                    JhiEventManager
                ]
            }).overrideTemplate(RatingDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RatingDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RatingService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Rating(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.rating).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
