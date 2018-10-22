/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ProcessoSeletivoTestModule } from '../../../test.module';
import { PeriodoOfertaComponent } from 'app/entities/periodo-oferta/periodo-oferta.component';
import { PeriodoOfertaService } from 'app/entities/periodo-oferta/periodo-oferta.service';
import { PeriodoOferta } from 'app/shared/model/periodo-oferta.model';

describe('Component Tests', () => {
    describe('PeriodoOferta Management Component', () => {
        let comp: PeriodoOfertaComponent;
        let fixture: ComponentFixture<PeriodoOfertaComponent>;
        let service: PeriodoOfertaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProcessoSeletivoTestModule],
                declarations: [PeriodoOfertaComponent],
                providers: []
            })
                .overrideTemplate(PeriodoOfertaComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PeriodoOfertaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PeriodoOfertaService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new PeriodoOferta(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.periodoOfertas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
