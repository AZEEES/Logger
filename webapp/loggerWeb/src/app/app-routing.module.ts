import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { StructureComponent } from 'src/app/structure/structure.component';
import { UserComponent } from './user/user.component';

// const routes: Routes = [];
const routes: Routes = [
  { path : 'structure', component: StructureComponent},
  { path : 'user', component: UserComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
