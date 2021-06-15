import {Component, OnInit} from '@angular/core';
import {Pagination} from '../../../dtos/pagination';
import {Promotion} from '../../../dtos/promotion';
import {PromotionService} from '../../../services/promotion.service';
import {OperatorAuthService} from '../../../services/auth/operator-auth.service';
import {Globals} from '../../../global/globals';

@Component({
  selector: 'app-operator-promotions',
  templateUrl: './operator-promotion.component.html',
  styleUrls: ['./operator-promotion.component.scss']
})
export class OperatorPromotionComponent implements OnInit {

  error = false;
  errorMessage = '';
  promotions: Promotion[];
  page = 0;
  pageSize = 15;
  collectionSize = 0;
  form = false;

  constructor(private promotionService: PromotionService,
              private authService: OperatorAuthService,
              private globals: Globals) {
  }

  ngOnInit(): void {
    this.loadPromotionsForPage();
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  /**
   * goes to next page if not on the last page
   */
  nextPage() {
    if ((this.page + 1) * this.pageSize < this.collectionSize) {
      this.page += 1;
      this.loadPromotionsForPage();
    }
  }

  /**
   * goes to previous page if not on the first page
   */
  previousPage() {
    if (this.page > 0) {
      this.page -= 1;
      this.loadPromotionsForPage();
    }
  }

  parseDates(date: string): string {

    return (date.slice(0, 16)).replace('T', ' ');
  }

  toggleForm() {
    this.form = !this.form;
  }

  isAdmin() {
    console.log(this.authService.getUserRole());
    return this.authService.getUserRole() === this.globals.roles.admin;
  }

  /**
   * calls on Service class to fetch all customer accounts from backend
   */
  private loadPromotionsForPage() {
    this.promotionService.getAllPromotionsForPage(this.page, this.pageSize).subscribe(
      (paginationDto: Pagination<Promotion>) => {
        console.log(paginationDto);
        this.promotions = paginationDto.items;
        this.collectionSize = paginationDto.totalItemCount;
      },
      error => {
        this.error = true;
        this.errorMessage = error.error;
      }
    );
  }


}
