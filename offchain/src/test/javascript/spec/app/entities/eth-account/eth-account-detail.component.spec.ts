/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { WegoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EthAccountDetailComponent } from '../../../../../../main/webapp/app/entities/eth-account/eth-account-detail.component';
import { EthAccountService } from '../../../../../../main/webapp/app/entities/eth-account/eth-account.service';
import { EthAccount } from '../../../../../../main/webapp/app/entities/eth-account/eth-account.model';

describe('Component Tests', () => {

    describe('EthAccount Management Detail Component', () => {
        let comp: EthAccountDetailComponent;
        let fixture: ComponentFixture<EthAccountDetailComponent>;
        let service: EthAccountService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WegoTestModule],
                declarations: [EthAccountDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EthAccountService,
                    JhiEventManager
                ]
            }).overrideTemplate(EthAccountDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EthAccountDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EthAccountService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new EthAccount(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.ethAccount).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
