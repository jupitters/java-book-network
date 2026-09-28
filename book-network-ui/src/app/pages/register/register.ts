import {ChangeDetectorRef, Component} from '@angular/core';
import {RegistrationRequest} from '../../services/models/registration-request';
import {FormsModule} from '@angular/forms';
import {Api} from '../../services/api';
import {Router} from '@angular/router';
import {authenticate, Authenticate$Params} from '../../services/fn/authentication/authenticate';
import {Register$Params} from '../../services/fn/authentication/register';

@Component({
  selector: 'app-register',
  imports: [
    FormsModule
  ],
  templateUrl: './register.html',
  styleUrl: './register.scss',
})
export class Register {
  registerRequest: RegistrationRequest = {email: '', firstname: '', lastname: '', password: ''};
  errorMsg: Array<string> = [];

  constructor (
    private router: Router,
    private api: Api,
    private cdr: ChangeDetectorRef,
  ){

  }

  async register() {
    this.errorMsg = [];
    const params: Register$Params = {
      body: this.registerRequest
    }

    try{
      const res = await this.api.invoke(authenticate, params);
      this.router.navigate(['activate-account']);
    } catch (err: any) {
      console.log(err);

      let error = err.error;

      if (error instanceof Blob) {
        error = JSON.parse(await error.text());
      }
      if (error?.validationErrors) {
        this.errorMsg = error.validationErrors;
      } else if (error?.error) {
        this.errorMsg = [error.error];
      } else {
        this.errorMsg = ['Erro ao fazer login'];
      }
      this.cdr.detectChanges();
    }

  }

  login () {
    this.router.navigate(['login']);
  }
}
