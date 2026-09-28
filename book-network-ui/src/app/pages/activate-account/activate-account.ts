import {ChangeDetectorRef, Component} from '@angular/core';
import {Router} from '@angular/router';
import {Api} from '../../services/api';
import {CodeInputModule} from 'angular-code-input';
import {authenticate, Authenticate$Params} from '../../services/fn/authentication/authenticate';
import {Register$Params} from '../../services/fn/authentication/register';
import {confirm, Confirm$Params} from '../../services/fn/authentication/confirm';

@Component({
  selector: 'app-activate-account',
  imports: [
    CodeInputModule
  ],
  templateUrl: './activate-account.html',
  styleUrl: './activate-account.scss',
})
export class ActivateAccount {
  message = '';
  isOkay = true;
  submitted = false;

  constructor(
    private router: Router,
    private api: Api,
    private cdr: ChangeDetectorRef
  ) {
  }

  onCodeCompleted(token: string) {
    this.confirmAccount(token);
  }

  redirectToLogin() {
    this.router.navigate(['login']);
  }

  private async confirmAccount(token: string) {
    const params: Confirm$Params = {
      token
    }

    try{
      const res = await this.api.invoke(confirm, params);
      this.message = 'Your account has been successfully activated!\n Now you can proceed to login.';
      this.submitted = true;
      this.isOkay = true;
    } catch (err) {
      this.message = 'Token has been expired or invalid!';
      this.submitted = true;
      this.isOkay = false;
    }
    this.cdr.detectChanges();
  }
}
