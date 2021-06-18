import {Component, OnInit} from '@angular/core';
import {OrderService} from '../../../services/order.service';
import {CancellationPeriod} from '../../../dtos/cancellationPeriod';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-operator-order-settings',
  templateUrl: './operator-order-settings.component.html',
  styleUrls: ['./operator-order-settings.component.scss']
})
export class OperatorOrderSettingsComponent implements OnInit {

  error = false;
  errorMessage = '';
  cancellationPeriodForm: FormGroup;

  constructor(private orderService: OrderService, private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.cancellationPeriodForm = this.formBuilder.group({
      days: [0, [Validators.required, Validators.min(0)]]
    });
  }

  vanishError() {
    this.error = false;
  }

  setCancellationPeriod() {
    const period = new CancellationPeriod(this.cancellationPeriodForm.controls.days.value);
    this.orderService.setCancellationPeriod(period).subscribe(() => {

    }, (error) => {
      this.error = true;
      this.errorMessage = error.message;
    });
  }

}
