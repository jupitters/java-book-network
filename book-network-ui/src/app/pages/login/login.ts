// @ts-ignore

import {Component, inject} from '@angular/core';
import {AuthenticationRequest} from '../../services/models/authentication-request';
import {FormsModule} from '@angular/forms';
import {Router} from '@angular/router';
import {authenticate, Authenticate$Params} from '../../services/fn/authentication/authenticate';
import {Api} from '../../services/api';
import {takeUntilDestroyed} from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-login',
  imports: [
    FormsModule
  ],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {
  authRequest: AuthenticationRequest = {email: '', password: ''};
  errorMsg: Array<string> = [];

  constructor(
    private router: Router,
    private api: Api
  ) {
  }

  async login(): Promise<void> {
    this.errorMsg = [];
    const params: Authenticate$Params = {
      body: this.authRequest
    }

    try{
      const res = await this.api.invoke(authenticate, params);
      // save token
      this.router.navigate(['books']);
    } catch (err) {
      console.log(err);
    }

  }

  register() {
    this.router.navigate(['register'])
  }
}
