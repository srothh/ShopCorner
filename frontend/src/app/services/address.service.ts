import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Globals} from '../global/globals';
import {AuthRequest} from "../dtos/auth-request";
import {tap} from "rxjs/operators";
import {Address} from "../dtos/address";

@Injectable({
  providedIn: 'root'
})
export class AddressService {
  private addressBaseUri: string = this.globals.backendUri + '/address';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  addAddress(address: Address): Observable<Address> {
    console.log('Create new Address', address);
    return this.httpClient.post<Address>(this.addressBaseUri, address);
  }

}
