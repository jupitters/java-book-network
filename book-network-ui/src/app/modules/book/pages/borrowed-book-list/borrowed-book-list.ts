import {Component, OnInit} from '@angular/core';
import {BorrowedBookResponse} from '../../../../services/models/borrowed-book-response';
import {PageResponseBorrowedBookResponse} from '../../../../services/models/page-response-borrowed-book-response';
import {Api} from '../../../../services/api';
import {FindAllBorrowedBooks$Params, findAllBorrowedBooks} from '../../../../services/fn/book/find-all-borrowed-books';
import {FeedbackRequest} from '../../../../services/models/feedback-request';
import {Rating} from '../../components/rating/rating';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-borrowed-book-list',
  imports: [
    Rating,
    FormsModule
  ],
  templateUrl: './borrowed-book-list.html',
  styleUrl: './borrowed-book-list.scss',
})
export class BorrowedBookList implements OnInit{
  borrowedBooks: PageResponseBorrowedBookResponse = {};
  feedbackRequest: FeedbackRequest = {bookId:0, comment: ''};
  page = 0;
  size = 5;
  selectedBook: BorrowedBookResponse = {};

  constructor(
    private api: Api
  ) {
  }

  ngOnInit() {
    this.findAllBorrowedBooks();
  }

  goToFirstPage() {
    this.page = 0;
    this.findAllBorrowedBooks();
  }

  goToPreviousPage() {
    this.page--;
    this.findAllBorrowedBooks();
  }

  goToPage(page: number){
    this.page = page;
    this.findAllBorrowedBooks();
  }

  goToNextPage() {
    this.page++;
    this.findAllBorrowedBooks();
  }

  goToLastPage() {
    this.page = this.borrowedBooks.totalPages as number - 1;
    this.findAllBorrowedBooks();
  }

  get isLastPage() {
    return this.page == this.borrowedBooks.totalPages as number - 1;
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
