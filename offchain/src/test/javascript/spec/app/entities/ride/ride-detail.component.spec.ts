/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { WegoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RideDetailComponent } from '../../../../../../main/webapp/app/entities/ride/ride-detail.component';
import { RideService } from '../../../../../../main/webapp/app/entities/ride/ride.service';
import { Ride } from '../../../../../../main/webapp/app/entities/ride/ride.model';

describe('Component Tests', () => {

    describe('Ride Management Detail Component', () => {
        let comp: RideDetailComponent;
        let fixture: ComponentFixture<RideDetailComponent>;
        let service: RideService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WegoTestModule],
                declarations: [RideDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RideService,
                    JhiEventManager
                ]
            }).overrideTemplate(RideDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RideDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RideService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Ride(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.ride).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
