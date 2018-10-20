import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDemandaCurso } from 'app/shared/model/demanda-curso.model';
import { DemandaCursoService } from './demanda-curso.service';

@Component({
    selector: 'jhi-demanda-curso-delete-dialog',
    templateUrl: './demanda-curso-delete-dialog.component.html'
})
export class DemandaCursoDeleteDialogComponent {
    demandaCurso: IDemandaCurso;

    constructor(
        private demandaCursoService: DemandaCursoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.demandaCursoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'demandaCursoListModification',
                content: 'Deleted an demandaCurso'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-demanda-curso-delete-popup',
    template: ''
})
export class DemandaCursoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ demandaCurso }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DemandaCursoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.demandaCurso = demandaCurso;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
