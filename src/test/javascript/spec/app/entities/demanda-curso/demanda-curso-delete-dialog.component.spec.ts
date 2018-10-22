/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ProcessoSeletivoTestModule } from '../../../test.module';
import { DemandaCursoDeleteDialogComponent } from 'app/entities/demanda-curso/demanda-curso-delete-dialog.component';
import { DemandaCursoService } from 'app/entities/demanda-curso/demanda-curso.service';

describe('Component Tests', () => {
    describe('DemandaCurso Management Delete Component', () => {
        let comp: DemandaCursoDeleteDialogComponent;
        let fixture: ComponentFixture<DemandaCursoDeleteDialogComponent>;
        let service: DemandaCursoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProcessoSeletivoTestModule],
                declarations: [DemandaCursoDeleteDialogComponent]
            })
                .overrideTemplate(DemandaCursoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DemandaCursoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DemandaCursoService);
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
