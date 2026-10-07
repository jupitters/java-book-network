import {Component, OnInit} from '@angular/core';
import {PageResponseBorrowedBookResponse} from '../../../../services/models/page-response-borrowed-book-response';
import {FeedbackRequest} from '../../../../services/models/feedback-request';
import {BorrowedBookResponse} from '../../../../services/models/borrowed-book-response';
import {Api} from '../../../../services/api';
import {findAllReturnedBooks, FindAllReturnedBooks$Params} from '../../../../services/fn/book/find-all-returned-books';
import {
  approveReturnBorrowedBook,
  ApproveReturnBorrowedBook$Params
} from '../../../../services/fn/book/approve-return-borrowed-book';

@Component({
  selector: 'app-return-books',
  imports: [],
  templateUrl: './return-books.html',
  styleUrl: './return-books.scss',
})
export class ReturnBooks implements OnInit {
  returnedBooks: PageResponseBorrowedBookResponse = {};
  page = 0;
  size = 5;
  message = '';
  level = 'success';

  constructor(
    private api: Api
  ) {
  }

  ngOnInit() {
    this.findAllReturnedBooks();
  }

  goToFirstPage() {
    this.page = 0;
    this.findAllReturnedBooks();
  }

  goToPreviousPage() {
    this.page--;
    this.findAllReturnedBooks();
  }

  goToPage(page: number){
    this.page = page;
    this.findAllReturnedBooks();
  }

  goToNextPage() {
    this.page++;
    this.findAllReturnedBooks();
  }

  goToLastPage() {
    this.page = this.returnedBooks.totalPages as number - 1;
    this.findAllReturnedBooks();
  }

  get isLastPage() {
    return this.page == this.returnedBooks.totalPages as number - 1;
  }

  private async findAllReturnedBooks() {
    const params: FindAllReturnedBooks$Params = {
      page: this.page,
      size: this.size
    }

    try {
      const res = await this.api.invoke(findAllReturnedBooks, params);
      this.returnedBooks = res;
    } catch (err: any) {
      console.log(err.error.error)
    }
  }

  async approveBookReturn(book: BorrowedBookResponse) {
    if(!book.returned) {
      this.level = 'error';
      this.message = 'The book is not yet returned';
      return;
    }
    const params: ApproveReturnBorrowedBook$Params = {
      bookId: book.id as number
    }

    try {
      await this.api.invoke(approveReturnBorrowedBook, params)
      this.level = 'success';
      this.message = 'Book return approved';
      await this.findAllReturnedBooks();
    } catch (err: any) {
      console.log(err.error.error)
    }
  }
}
