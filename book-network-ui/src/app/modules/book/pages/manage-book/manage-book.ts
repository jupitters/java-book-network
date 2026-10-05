import { Component } from '@angular/core';

@Component({
  selector: 'app-manage-book',
  imports: [],
  templateUrl: './manage-book.html',
  styleUrl: './manage-book.scss',
})
export class ManageBook {
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
