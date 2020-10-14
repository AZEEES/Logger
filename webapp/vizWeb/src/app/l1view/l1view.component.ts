import { Component, OnInit, Input } from '@angular/core';
import { StructureService } from '../structure.service';

@Component({
  selector: 'app-l1view',
  templateUrl: './l1view.component.html',
  styleUrls: ['./l1view.component.css']
})
export class L1viewComponent implements OnInit {

  @Input() node_id : string;
  @Input() node_name : string;
  
  public childs = [];
  public errorMsg;

  constructor(private _structureService : StructureService) { }

  ngOnInit(): void {
    this.getChilds();
  }

  getChilds(){
    console.log("Childs api called");
    this._structureService.getChilds(this.node_id)
      .subscribe(data =>{this.childs = data; console.log(this.childs)},
                error => this.errorMsg = error);
  }

}
