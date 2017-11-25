/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { WegoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TermsDetailComponent } from '../../../../../../main/webapp/app/entities/terms/terms-detail.component';
import { TermsService } from '../../../../../../main/webapp/app/entities/terms/terms.service';
import { Terms } from '../../../../../../main/webapp/app/entities/terms/terms.model';

describe('Component Tests', () => {

    describe('Terms Management Detail Component', () => {
        let comp: TermsDetailComponent;
        let fixture: ComponentFixture<TermsDetailComponent>;
        let service: TermsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WegoTestModule],
                declarations: [TermsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TermsService,
                    JhiEventManager
                ]
            }).overrideTemplate(TermsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TermsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TermsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Terms(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.terms).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
