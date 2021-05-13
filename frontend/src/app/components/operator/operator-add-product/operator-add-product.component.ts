import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';

@Component({
  selector: 'app-operator-add-product',
  templateUrl: './operator-add-product.component.html',
  styleUrls: ['./operator-add-product.component.scss']
})
export class OperatorAddProductComponent implements OnInit {

  @ViewChild('fileInput')
  fileInput: ElementRef;
  fileToUpload: File;
  fileSource: string | ArrayBuffer;
  constructor() { }

  ngOnInit(): void {
  }
  onSelectFile(event){
    this.fileToUpload = event.target.files.item(0);
    const reader = new FileReader();
    reader.readAsDataURL(this.fileToUpload);
    reader.onload = ((loadEvent) => {
      this.fileSource = loadEvent.target.result;
    });

  }
  clearImage(){
    this.fileToUpload = undefined;
    this.fileSource = undefined;
    this.fileInput.nativeElement.value = '';
  }

}
