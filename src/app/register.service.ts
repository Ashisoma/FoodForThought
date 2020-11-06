import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private http:HttpClient) { }

  getAllDonors(){
    return this.http.get("https://bankfood.herokuapp.com/registration");
  }

  postDonor(output){
    return this.http.post("https://bankfood.herokuapp.com/registration/new", output)
  }
}
