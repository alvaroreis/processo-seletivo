/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ProcessoSeletivoTestModule } from '../../../test.module';
import { PeriodoOfertaDeleteDialogComponent } from 'app/entities/periodo-oferta/periodo-oferta-delete-dialog.component';
import { PeriodoOfertaService } from 'app/entities/periodo-oferta/periodo-oferta.service';

describe('Component Tests', () => {
    describe('PeriodoOferta Management Delete Component', () => {
        let comp: PeriodoOfertaDeleteDialogComponent;
        let fixture: ComponentFixture<PeriodoOfertaDeleteDialogComponent>;
        let service: PeriodoOfertaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProcessoSeletivoTestModule],
                declarations: [PeriodoOfertaDeleteDialogComponent]
            })
                .overrideTemplate(PeriodoOfertaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PeriodoOfertaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PeriodoOfertaService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
