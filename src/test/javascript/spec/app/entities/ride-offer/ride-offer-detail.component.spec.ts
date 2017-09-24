/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JhipsterTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RideOfferDetailComponent } from '../../../../../../main/webapp/app/entities/ride-offer/ride-offer-detail.component';
import { RideOfferService } from '../../../../../../main/webapp/app/entities/ride-offer/ride-offer.service';
import { RideOffer } from '../../../../../../main/webapp/app/entities/ride-offer/ride-offer.model';

describe('Component Tests', () => {

    describe('RideOffer Management Detail Component', () => {
        let comp: RideOfferDetailComponent;
        let fixture: ComponentFixture<RideOfferDetailComponent>;
        let service: RideOfferService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTestModule],
                declarations: [RideOfferDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RideOfferService,
                    JhiEventManager
                ]
            }).overrideTemplate(RideOfferDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RideOfferDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RideOfferService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RideOffer(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.rideOffer).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
