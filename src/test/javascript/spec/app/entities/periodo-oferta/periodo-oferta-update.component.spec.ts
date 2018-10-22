/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ProcessoSeletivoTestModule } from '../../../test.module';
import { PeriodoOfertaUpdateComponent } from 'app/entities/periodo-oferta/periodo-oferta-update.component';
import { PeriodoOfertaService } from 'app/entities/periodo-oferta/periodo-oferta.service';
import { PeriodoOferta } from 'app/shared/model/periodo-oferta.model';

describe('Component Tests', () => {
    describe('PeriodoOferta Management Update Component', () => {
        let comp: PeriodoOfertaUpdateComponent;
        let fixture: ComponentFixture<PeriodoOfertaUpdateComponent>;
        let service: PeriodoOfertaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProcessoSeletivoTestModule],
                declarations: [PeriodoOfertaUpdateComponent]
            })
                .overrideTemplate(PeriodoOfertaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PeriodoOfertaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PeriodoOfertaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PeriodoOferta(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.periodoOferta = entity;
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
                    const entity = new PeriodoOferta();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.periodoOferta = entity;
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
