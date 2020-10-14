import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-l0view',
  templateUrl: './l0view.component.html',
  styleUrls: ['./l0view.component.css']
})
export class L0viewComponent implements OnInit {

  @Input() node_id : string;
  @Input() node_name : string;
  
  public node_value = "2.0";

  constructor() { }

  ngOnInit(): void {
  }

}
