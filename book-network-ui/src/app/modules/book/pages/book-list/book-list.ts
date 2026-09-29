import {Component, OnInit} from '@angular/core';
import {Api} from '../../../../services/api';
import {Router} from '@angular/router';
import {FindAllBooks$Params, findAllBooks} from '../../../../services/fn/book/find-all-books';
import {PageResponseBookResponse} from '../../../../services/models/page-response-book-response';
import {BookCard} from '../../components/book-card/book-card';

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

    }
  }
}
