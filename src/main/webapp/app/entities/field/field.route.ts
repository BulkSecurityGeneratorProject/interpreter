import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { FieldComponent } from './field.component';
import { FieldDetailComponent } from './field-detail.component';
import { FieldPopupComponent } from './field-dialog.component';
import { FieldDeletePopupComponent } from './field-delete-dialog.component';

export const fieldRoute: Routes = [
    {
        path: 'field',
        component: FieldComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Fields'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'field/:id',
        component: FieldDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Fields'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fieldPopupRoute: Routes = [
    {
        path: 'field-new',
        component: FieldPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Fields'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'field/:id/edit',
        component: FieldPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Fields'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'field/:id/delete',
        component: FieldDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Fields'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
