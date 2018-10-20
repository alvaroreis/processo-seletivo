/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ProcessoSeletivoTestModule } from '../../../test.module';
import { UnidadeDeleteDialogComponent } from 'app/entities/unidade/unidade-delete-dialog.component';
import { UnidadeService } from 'app/entities/unidade/unidade.service';

describe('Component Tests', () => {
    describe('Unidade Management Delete Component', () => {
        let comp: UnidadeDeleteDialogComponent;
        let fixture: ComponentFixture<UnidadeDeleteDialogComponent>;
        let service: UnidadeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProcessoSeletivoTestModule],
                declarations: [UnidadeDeleteDialogComponent]
            })
                .overrideTemplate(UnidadeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UnidadeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UnidadeService);
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
