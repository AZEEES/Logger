import { Component, OnInit, Input } from '@angular/core';
import { StructureService } from '../structure.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-structure',
  templateUrl: './structure.component.html',
  styleUrls: ['./structure.component.css']
})

export class StructureComponent implements OnInit {

  @Input() parent_id : string;

  public panelOpenState = false;
  public structures = [];
  public childs = [];
  public editEnabled = false;
  public levels = ["M","L2","L1","L0"];

  public errorMsg;
  public successMsg;

  constructor(private _structureService : StructureService, private _snackBar : MatSnackBar) { }

  ngOnInit(): void {
    // this.getStructures();
    console.log("Parent_id : " + this.parent_id);
    this.getChilds();
  }

  editEnable(child){
    // this.editEnabled = true;
    child.edit_enabled = true;
  }

  update(child){
    this._structureService.updateStructure(child._id, JSON.stringify(child))
      .subscribe(data => { this.openSnackBar("Updating ", data); child.edit_enabled=false; },
                error => this.errorMsg = error);
  }

  add(child){
    delete child._id;
    child.name="New Entry";
    child.level_leaf="M";
    child.parent=child.id;
    this._structureService.addStructure(JSON.stringify(child))
      .subscribe(data => { this.openSnackBar("Adding ", data); child.edit_enabled=false; },
                error => this.errorMsg = error);
  }

  delete(child){
    this._structureService.deleteStructure(child._id)
      .subscribe(data => { this.openSnackBar("Deleting ", data); child.is_hidden=true; },
                error => this.errorMsg = error);
  }

  openPanel(id){
    console.log(id  + " opened");
  }
  
  getStructures(){
    console.log("Structures api callled");
    this._structureService.getStructure()
      .subscribe(data => this.structures = data,
                error => this.errorMsg = error);
  }

  getChilds(){
    console.log("Childs api callled");
    this._structureService.getChilds(this.parent_id)
      .subscribe(data => this.childs = data,
                error => this.errorMsg = error);
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 10000,
    });
  }




}
