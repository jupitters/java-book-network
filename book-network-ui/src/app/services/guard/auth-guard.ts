import {CanActivateFn, Router} from '@angular/router';
import { inject } from '@angular/core';
import {Api} from '../api';
import {Token} from '../token/token';

export const authGuard: CanActivateFn = () => {
  const token = inject(Token);
  const router = inject(Router);
  if(token.isTokenNotValid()){
    router.navigate(['login']);
    return false;
  }

  return true;
};
