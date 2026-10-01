import {Component, OnInit} from '@angular/core';
import {Api} from '../../../../services/api';
import {Router} from '@angular/router';
import {FindAllBooks$Params, findAllBooks} from '../../../../services/fn/book/find-all-books';
import {PageResponseBookResponse} from '../../../../services/models/page-response-book-response';
import {BookCard} from '../../components/book-card/book-card';
import {BookResponse} from '../../../../services/models/book-response';
import {BorrowBook$Params, borrowBook} from '../../../../services/fn/book/borrow-book';

@Component({
  selector: 'app-book-list',
  imports: [
    BookCard
  ],
  templateUrl: './book-list.html',
  styleUrl: './book-list.scss',
})
export class BookList implements OnInit {
  booksResponse: PageResponseBookResponse = {};
  page = 0;
  size = 5;
  message = '';
  level = 'success'

  constructor(
    private api: Api,
    private router: Router,
  ) {
  }

  ngOnInit(): void {
        this.findAllBooks();
    }

  private async findAllBooks() {
    const params: FindAllBooks$Params = {
      page: this.page,
      size: this.size
    };

    try{
      const res = await this.api.invoke(findAllBooks, params);
      this.booksResponse = await res;
    }catch (err) {
      this.message = "No books found!";
    }
  }

  goToFirstPage() {
    this.page = 0;
    this.findAllBooks();
  }

  goToPreviousPage() {
    this.page--;
    this.findAllBooks();
  }

  goToPage(page: number){
    this.page = page;
    this.findAllBooks();
  }

  goToNextPage() {
    this.page++;
    this.findAllBooks();
  }

  goToLastPage() {
    this.page = this.booksResponse.totalPages as number - 1;
    this.findAllBooks();
  }

  get isLastPage() {
    return this.page == this.booksResponse.totalPages as number - 1;
  }

  async borrowBook(book: BookResponse) {
    const params: BorrowBook$Params = {
      bookId: book.id as number
    }

    try {
      await this.api.invoke(borrowBook, params);
      this.level = 'success';
      this.message = "Book successfully added to your list"
    } catch (err: any) {
      this.level = 'error';
      this.message = err.error.error;
    }
  }
}
