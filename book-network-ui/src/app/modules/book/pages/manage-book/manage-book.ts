import { Component } from '@angular/core';
import {BookRequest} from '../../../../services/models/book-request';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-manage-book',
  imports: [
    FormsModule
  ],
  templateUrl: './manage-book.html',
  styleUrl: './manage-book.scss',
})
export class ManageBook {
  bookRequest: BookRequest = {
    authorName: '',
    isbn: '',
    synopsys: '',
    title: ''
  };
  errorMsg: Array<string> = [];
  selectedBookCover: any;
  selectedPicture: string | undefined;

  onFileSelected(event: any) {
    this.selectedBookCover = event.target.files[0];
    if(this.selectedBookCover) {
      const reader = new FileReader();
      reader.onload = () => {
        this.selectedPicture = reader.result as string;
      };
      reader.readAsDataURL(this.selectedBookCover);
    }
  }
}
