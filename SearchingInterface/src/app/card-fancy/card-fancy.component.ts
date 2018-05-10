import { Component, OnInit, Input } from '@angular/core';
import { ServiceInterface } from '../serviceInterface';

@Component({
  selector: 'app-card-fancy',
  templateUrl: './card-fancy.component.html',
  styleUrls: ['./card-fancy.component.css']
})

export class CardFancyComponent implements OnInit {
  ngOnInit(): void {
    this.Url = this.cardDetails.url;
   this.Description =this.cardDetails.metaUrl;
   this.Title =this.cardDetails.titleUrl;
    this.imageCount = this.cardDetails.imgCount;
    this.videoCount = this.cardDetails.videoCount;
    this.codeCount = this.cardDetails.codeCount;

  }
i=25;
  @Input() cardDetails: any;

Title:string
  Url : string;
  Description: string;
  imageCount : number;
  videoCount :number ;
  codeCount : number;
  faviconUrl : string;
  constructor() {



  }

  ngOnChanges() {
    this.Url = this.cardDetails.url;
   this.Description =this.cardDetails.metaUrl;
   this.Title =this.cardDetails.titleUrl;
    this.imageCount = this.cardDetails.imgCount;
    this.videoCount = this.cardDetails.videoCount;
    this.codeCount = this.cardDetails.codeCount;
  }

}
