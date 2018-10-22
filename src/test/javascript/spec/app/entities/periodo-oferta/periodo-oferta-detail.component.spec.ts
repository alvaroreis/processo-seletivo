/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProcessoSeletivoTestModule } from '../../../test.module';
import { PeriodoOfertaDetailComponent } from 'app/entities/periodo-oferta/periodo-oferta-detail.component';
import { PeriodoOferta } from 'app/shared/model/periodo-oferta.model';

describe('Component Tests', () => {
    describe('PeriodoOferta Management Detail Component', () => {
        let comp: PeriodoOfertaDetailComponent;
        let fixture: ComponentFixture<PeriodoOfertaDetailComponent>;
        const route = ({ data: of({ periodoOferta: new PeriodoOferta(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProcessoSeletivoTestModule],
                declarations: [PeriodoOfertaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PeriodoOfertaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PeriodoOfertaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.periodoOferta).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
