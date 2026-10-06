import {Component, OnInit} from '@angular/core';
import {BookRequest} from '../../../../services/models/book-request';
import {FormsModule} from '@angular/forms';
import {Api} from '../../../../services/api';
import {findAllBooksByOwner, FindAllBooksByOwner$Params} from '../../../../services/fn/book/find-all-books-by-owner';
import {SaveBook$Params, saveBook} from '../../../../services/fn/book/save-book';
import {
  uploadBookCoverPicture,
  UploadBookCoverPicture$Params
} from '../../../../services/fn/book/upload-book-cover-picture';
import {ActivatedRoute, Router} from '@angular/router';
import {findBookById, FindBookById$Params} from '../../../../services/fn/book/find-book-by-id';

@Component({
  selector: 'app-manage-book',
  imports: [
    FormsModule
  ],
  templateUrl: './manage-book.html',
  styleUrl: './manage-book.scss',
})
export class ManageBook implements OnInit{
  bookRequest: BookRequest = {
    authorName: '',
    isbn: '',
    synopsys: '',
    title: ''
  };
  errorMsg: Array<string> = [];
  selectedBookCover: any;
  selectedPicture: string | undefined;

  constructor(
    private api: Api,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
  }

  async ngOnInit() {


    const bookId = this.activatedRoute.snapshot.params['bookId'];
    if(bookId) {
      const params: FindBookById$Params = {
        bookId: bookId
      }

      try {
        const book = await this.api.invoke(findBookById, params);
        this.bookRequest = {
          id: book.id,
          title: book.title as string,
          authorName: book.authorName as string,
          isbn: book.isbn as string,
          synopsys: book.synopsys as string,
          shareable: book.shareable
        }
      } catch (err: any) {
        console.log(err.error.error);
      }
    }
  }

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

  async saveBook() {
    const paramsBook: SaveBook$Params = {
      body: this.bookRequest
    };


    try{
      const resBook = await this.api.invoke(saveBook, paramsBook);
      const bookId = await resBook;

      const paramsCover: UploadBookCoverPicture$Params = {
        bookId: bookId,
        body: {
          file: this.selectedBookCover
        }
      };

      await this.api.invoke(uploadBookCoverPicture, paramsCover);

      this.router.navigate(['/books/my-books']);
    }catch (err: any) {
      this.errorMsg = err.error.ValidationError;
    }
  }
}
