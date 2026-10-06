import {Component, OnInit} from '@angular/core';
import {BookCard} from "../../components/book-card/book-card";
import {PageResponseBookResponse} from '../../../../services/models/page-response-book-response';
import {Api} from '../../../../services/api';
import {Router, RouterLink} from '@angular/router';
import {BookResponse} from '../../../../services/models/book-response';
import {findAllBooksByOwner, FindAllBooksByOwner$Params} from '../../../../services/fn/book/find-all-books-by-owner';

@Component({
  selector: 'app-my-books',
  imports: [
    BookCard,
    RouterLink
  ],
  templateUrl: './my-books.html',
  styleUrl: './my-books.scss',
})
export class MyBooks implements OnInit{
  booksResponse: PageResponseBookResponse = {};
  page = 0;
  size = 5;

  constructor(
    private api: Api,
    private router: Router,
  ) {
  }

  ngOnInit(): void {
    this.findAllBooks();
  }

  private async findAllBooks() {
    const params: FindAllBooksByOwner$Params = {
      page: this.page,
      size: this.size
    };

    try{
      const res = await this.api.invoke(findAllBooksByOwner, params);
      this.booksResponse = await res;
    }catch (err: any) {
      console.log = err.error.error;
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

  archiveBook($event: BookResponse) {

  }

  editBook(book: BookResponse) {
    this.router.navigate(['books', 'manage', book.id]);
  }
}
