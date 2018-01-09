import {Component, OnInit, OnDestroy, Input} from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Connection } from './connection.model';
import { ConnectionService } from './connection.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-connection',
    templateUrl: './connection.component.html'
})
export class ConnectionComponent implements OnInit, OnDestroy {
connections: Connection[];
    currentAccount: any;
    eventSubscriber: Subscription;
    @Input() fieldId;

    constructor(
        private connectionService: ConnectionService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        if (!this.fieldId) {
            this.connectionService.query().subscribe(
                (res: ResponseWrapper) => {
                    this.connections = res.json;
                },
                (res: ResponseWrapper) => this.onError(res.json)
            );
        } else {
            this.connectionService.queryByField_Id(this.fieldId).subscribe(
                (res: ResponseWrapper) => {
                    this.connections = res.json;
                },
                (res: ResponseWrapper) => this.onError(res.json)
            );
        }
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInConnections();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Connection) {
        return item.id;
    }
    registerChangeInConnections() {
        this.eventSubscriber = this.eventManager.subscribe('connectionListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
