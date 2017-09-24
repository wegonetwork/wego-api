/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JhipsterTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PassengerDetailComponent } from '../../../../../../main/webapp/app/entities/passenger/passenger-detail.component';
import { PassengerService } from '../../../../../../main/webapp/app/entities/passenger/passenger.service';
import { Passenger } from '../../../../../../main/webapp/app/entities/passenger/passenger.model';

describe('Component Tests', () => {

    describe('Passenger Management Detail Component', () => {
        let comp: PassengerDetailComponent;
        let fixture: ComponentFixture<PassengerDetailComponent>;
        let service: PassengerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTestModule],
                declarations: [PassengerDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PassengerService,
                    JhiEventManager
                ]
            }).overrideTemplate(PassengerDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PassengerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PassengerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Passenger(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.passenger).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
