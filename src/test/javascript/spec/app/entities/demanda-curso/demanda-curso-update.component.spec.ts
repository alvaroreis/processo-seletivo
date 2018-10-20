/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ProcessoSeletivoTestModule } from '../../../test.module';
import { DemandaCursoUpdateComponent } from 'app/entities/demanda-curso/demanda-curso-update.component';
import { DemandaCursoService } from 'app/entities/demanda-curso/demanda-curso.service';
import { DemandaCurso } from 'app/shared/model/demanda-curso.model';

describe('Component Tests', () => {
    describe('DemandaCurso Management Update Component', () => {
        let comp: DemandaCursoUpdateComponent;
        let fixture: ComponentFixture<DemandaCursoUpdateComponent>;
        let service: DemandaCursoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProcessoSeletivoTestModule],
                declarations: [DemandaCursoUpdateComponent]
            })
                .overrideTemplate(DemandaCursoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DemandaCursoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DemandaCursoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DemandaCurso(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.demandaCurso = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DemandaCurso();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.demandaCurso = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
