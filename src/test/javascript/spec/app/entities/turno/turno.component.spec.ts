/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ProcessoSeletivoTestModule } from '../../../test.module';
import { TurnoComponent } from 'app/entities/turno/turno.component';
import { TurnoService } from 'app/entities/turno/turno.service';
import { Turno } from 'app/shared/model/turno.model';

describe('Component Tests', () => {
    describe('Turno Management Component', () => {
        let comp: TurnoComponent;
        let fixture: ComponentFixture<TurnoComponent>;
        let service: TurnoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProcessoSeletivoTestModule],
                declarations: [TurnoComponent],
                providers: []
            })
                .overrideTemplate(TurnoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TurnoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TurnoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Turno(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.turnos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
