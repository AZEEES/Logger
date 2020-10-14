import { Injectable } from '@angular/core';
import { GlobalConstants } from './global-constants';
import { HttpClientModule, HttpErrorResponse, HttpParams, HttpClient } from '@angular/common/http'
import {Observable, throwError} from 'rxjs';
import { catchError } from 'rxjs/operators';
import { IStructure } from './structure';

@Injectable({
  providedIn: 'root'
})
export class StructureService {
  private server_ip = GlobalConstants.ip_server;
  private _url_structure : string = "http://" + this.server_ip + "/api/structure";
  private _url_structure_childs : string = "http://" + this.server_ip + "/api/structure/getChilds";

  constructor(private http : HttpClient  ) { }

  getChilds(parent_id) : Observable <IStructure[]>{
    let params = new HttpParams().set("parent",parent_id);
    return this.http.get<any>(this._url_structure_childs, {params : params})
            .pipe(catchError(this.handleError));
  }

  handleError(error : HttpErrorResponse){
    return throwError(error || "Server Error"); 
  }

}
