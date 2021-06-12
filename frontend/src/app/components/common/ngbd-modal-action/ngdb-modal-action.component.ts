import {Component, Input} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-ngbd-modal-content',
  template: `
    <div class="modal-header">
      <h5 class="modal-title" id="deleteModalLabel">{{title}}</h5>
      <button type="button" class="close" aria-label="Close" (click)="activeModal.dismiss('Cross click')">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
    <div class="modal-body">
      {{body}}
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-outline-secondary" (click)="activeModal.close('Close click')">Abbrechen</button>
      <button type="button" [ngClass]="[getButtonStyle(), 'btn']" (click)="dismissThenAction()">{{actionButtonTitle}}</button>
    </div>
  `
})
export class NgdbModalActionComponent {
  @Input() title;
  @Input() body;
  @Input() action: () => void;
  @Input() actionButtonTitle;
  @Input() actionButtonStyle;

  constructor(public activeModal: NgbActiveModal) {
  }

  getButtonStyle() {
    switch (this.actionButtonStyle) {
      case 'danger':
        return 'btn-danger';
      case 'primary':
        return 'btn-primary';
      default:
        return 'btn-danger';
    }
  }

  dismissThenAction() {
    this.activeModal.close('Close click');
    this.action();
  }
}
