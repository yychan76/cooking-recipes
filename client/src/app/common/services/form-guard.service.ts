import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanDeactivate,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable } from 'rxjs';
import { FormGuard } from '../models';

@Injectable({
  providedIn: 'root',
})
export class FormGuardService implements CanDeactivate<FormGuard> {
  canDeactivate(
    component: FormGuard,
    currentRoute: ActivatedRouteSnapshot,
    currentState: RouterStateSnapshot,
    nextState?: RouterStateSnapshot
  ):
    | boolean
    | UrlTree
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree> {
    if (component.needsSave()) {
      return Promise.resolve(
        confirm('You have unsaved changes. \nAre you sure you want to leave?')
      );
    }
    return true;
  }
}
