/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { WegoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RideRequestDetailComponent } from '../../../../../../main/webapp/app/entities/ride-request/ride-request-detail.component';
import { RideRequestService } from '../../../../../../main/webapp/app/entities/ride-request/ride-request.service';
import { RideRequest } from '../../../../../../main/webapp/app/entities/ride-request/ride-request.model';

describe('Component Tests', () => {

    describe('RideRequest Management Detail Component', () => {
        let comp: RideRequestDetailComponent;
        let fixture: ComponentFixture<RideRequestDetailComponent>;
        let service: RideRequestService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WegoTestModule],
                declarations: [RideRequestDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RideRequestService,
                    JhiEventManager
                ]
            }).overrideTemplate(RideRequestDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RideRequestDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RideRequestService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RideRequest(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.rideRequest).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
