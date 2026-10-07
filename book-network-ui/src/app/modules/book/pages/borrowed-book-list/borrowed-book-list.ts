import {Component, OnInit} from '@angular/core';
import {BorrowedBookResponse} from '../../../../services/models/borrowed-book-response';
import {PageResponseBorrowedBookResponse} from '../../../../services/models/page-response-borrowed-book-response';
import {Api} from '../../../../services/api';
import {FindAllBorrowedBooks$Params, findAllBorrowedBooks} from '../../../../services/fn/book/find-all-borrowed-books';
import {FeedbackRequest} from '../../../../services/models/feedback-request';
import {Rating} from '../../components/rating/rating';
import {FormsModule} from '@angular/forms';
import {ReturnBorrowedBook$Params, returnBorrowedBook} from '../../../../services/fn/book/return-borrowed-book';
import {saveFeedback, SaveFeedback$Params} from '../../../../services/fn/feedbacks/save-feedback';

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
  selectedBook: BorrowedBookResponse | undefined = undefined;

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
    this.selectedBook = book;
    this.feedbackRequest.bookId = book.id as number;
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

  async returnBook(withFeedback: boolean) {
    const params: ReturnBorrowedBook$Params = {
      bookId: this.selectedBook?.id as number
    }

    try {
      const res = await this.api.invoke(returnBorrowedBook, params);

      if(withFeedback) {
        this.giveFeedback();
      }
      this.selectedBook = undefined;
      this.findAllBorrowedBooks();
    } catch(err: any) {
      console.log(err.error.error);
    }
  }

  private async giveFeedback() {
    const params: SaveFeedback$Params = {
      body: this.feedbackRequest
    }

    try {
      this.api.invoke(saveFeedback, params);
    } catch (err: any) {
      console.log(err.error.error)
    }
  }
}
