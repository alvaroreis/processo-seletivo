/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProcessoSeletivoTestModule } from '../../../test.module';
import { DemandaCursoDetailComponent } from 'app/entities/demanda-curso/demanda-curso-detail.component';
import { DemandaCurso } from 'app/shared/model/demanda-curso.model';

describe('Component Tests', () => {
    describe('DemandaCurso Management Detail Component', () => {
        let comp: DemandaCursoDetailComponent;
        let fixture: ComponentFixture<DemandaCursoDetailComponent>;
        const route = ({ data: of({ demandaCurso: new DemandaCurso(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProcessoSeletivoTestModule],
                declarations: [DemandaCursoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DemandaCursoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DemandaCursoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.demandaCurso).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
