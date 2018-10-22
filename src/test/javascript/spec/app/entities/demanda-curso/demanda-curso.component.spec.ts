/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ProcessoSeletivoTestModule } from '../../../test.module';
import { DemandaCursoComponent } from 'app/entities/demanda-curso/demanda-curso.component';
import { DemandaCursoService } from 'app/entities/demanda-curso/demanda-curso.service';
import { DemandaCurso } from 'app/shared/model/demanda-curso.model';

describe('Component Tests', () => {
    describe('DemandaCurso Management Component', () => {
        let comp: DemandaCursoComponent;
        let fixture: ComponentFixture<DemandaCursoComponent>;
        let service: DemandaCursoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProcessoSeletivoTestModule],
                declarations: [DemandaCursoComponent],
                providers: []
            })
                .overrideTemplate(DemandaCursoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DemandaCursoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DemandaCursoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new DemandaCurso(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.demandaCursos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
