import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Give } from 'src/app/give';

@Component({
  selector: 'app-donation',
  templateUrl: './donation.component.html',
  styleUrls: ['./donation.component.css']
})
export class DonationComponent implements OnInit {
  array:object;
  name:string;
  website:string;
  phone:string;
  address:string;
  email:string;

  constructor() { }

  newDonation = new Give(0,"","","","");
  @Output() addDonation = new EventEmitter<Give>()

  submitDonation(){
    this.addDonation.emit(this.newDonation);
  }

  ngOnInit(): void {
  }

}
