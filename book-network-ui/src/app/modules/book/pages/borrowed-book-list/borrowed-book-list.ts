import {Component, OnInit} from '@angular/core';
import {BorrowedBookResponse} from '../../../../services/models/borrowed-book-response';
import {PageResponseBorrowedBookResponse} from '../../../../services/models/page-response-borrowed-book-response';
import {Api} from '../../../../services/api';
import {FindAllBorrowedBooks$Params, findAllBorrowedBooks} from '../../../../services/fn/book/find-all-borrowed-books';

@Component({
  selector: 'app-borrowed-book-list',
  imports: [],
  templateUrl: './borrowed-book-list.html',
  styleUrl: './borrowed-book-list.scss',
})
export class BorrowedBookList implements OnInit{
  borrowedBooks: PageResponseBorrowedBookResponse = {};
  page = 0;
  size = 5;

  constructor(
    private api: Api
  ) {
  }

  ngOnInit() {
    this.findAllBorrowedBooks();
  }

  returnBorrowedBook(book: BorrowedBookResponse) {

  }

  private async findAllBorrowedBooks() {
    const params: FindAllBorrowedBooks$Params = {
      page: this.page,
      size: this.size
    }

    try {
      const res = await this.api.invoke(findAllBorrowedBooks, params);
      this.borrowedBooks = res;
    } catch (err: any) {
      console.log(err.error.error)
    }
  }
}
