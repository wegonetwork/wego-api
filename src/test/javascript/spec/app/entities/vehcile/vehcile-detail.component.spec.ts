/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { WegoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { VehcileDetailComponent } from '../../../../../../main/webapp/app/entities/vehcile/vehcile-detail.component';
import { VehcileService } from '../../../../../../main/webapp/app/entities/vehcile/vehcile.service';
import { Vehcile } from '../../../../../../main/webapp/app/entities/vehcile/vehcile.model';

describe('Component Tests', () => {

    describe('Vehcile Management Detail Component', () => {
        let comp: VehcileDetailComponent;
        let fixture: ComponentFixture<VehcileDetailComponent>;
        let service: VehcileService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WegoTestModule],
                declarations: [VehcileDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    VehcileService,
                    JhiEventManager
                ]
            }).overrideTemplate(VehcileDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VehcileDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VehcileService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Vehcile(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.vehcile).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
