import { Component, OnInit } from '@angular/core';
import { StructureService } from '../structure.service';

@Component({
  selector: 'app-l2view',
  templateUrl: './l2view.component.html',
  styleUrls: ['./l2view.component.css']
})
export class L2viewComponent implements OnInit {

  public l2Name = "L2Name";
  public l2id = "5f7f5cffafb260649723b309";
  public childs = [];
  public errorMsg;

  constructor(private _structureService : StructureService) { }

  ngOnInit(): void {
    this.getChilds();
  }

  getChilds(){
    console.log("Childs api called");
    this._structureService.getChilds(this.l2id)
      .subscribe(data =>{this.childs = data;},
                error => this.errorMsg = error);
  }

}
