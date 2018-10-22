/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProcessoSeletivoTestModule } from '../../../test.module';
import { UnidadeDetailComponent } from 'app/entities/unidade/unidade-detail.component';
import { Unidade } from 'app/shared/model/unidade.model';

describe('Component Tests', () => {
    describe('Unidade Management Detail Component', () => {
        let comp: UnidadeDetailComponent;
        let fixture: ComponentFixture<UnidadeDetailComponent>;
        const route = ({ data: of({ unidade: new Unidade(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProcessoSeletivoTestModule],
                declarations: [UnidadeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(UnidadeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UnidadeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.unidade).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
