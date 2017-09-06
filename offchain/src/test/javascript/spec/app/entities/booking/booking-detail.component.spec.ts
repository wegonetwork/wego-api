/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { WegoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BookingDetailComponent } from '../../../../../../main/webapp/app/entities/booking/booking-detail.component';
import { BookingService } from '../../../../../../main/webapp/app/entities/booking/booking.service';
import { Booking } from '../../../../../../main/webapp/app/entities/booking/booking.model';

describe('Component Tests', () => {

    describe('Booking Management Detail Component', () => {
        let comp: BookingDetailComponent;
        let fixture: ComponentFixture<BookingDetailComponent>;
        let service: BookingService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WegoTestModule],
                declarations: [BookingDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BookingService,
                    JhiEventManager
                ]
            }).overrideTemplate(BookingDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BookingDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BookingService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Booking(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.booking).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
