import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPeriodoOferta } from 'app/shared/model/periodo-oferta.model';
import { PeriodoOfertaService } from './periodo-oferta.service';

@Component({
    selector: 'jhi-periodo-oferta-delete-dialog',
    templateUrl: './periodo-oferta-delete-dialog.component.html'
})
export class PeriodoOfertaDeleteDialogComponent {
    periodoOferta: IPeriodoOferta;

    constructor(
        private periodoOfertaService: PeriodoOfertaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.periodoOfertaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'periodoOfertaListModification',
                content: 'Deleted an periodoOferta'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-periodo-oferta-delete-popup',
    template: ''
})
export class PeriodoOfertaDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ periodoOferta }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PeriodoOfertaDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.periodoOferta = periodoOferta;
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
