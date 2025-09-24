import { Directive, Input, NgModule, TemplateRef, ViewContainerRef } from '@angular/core';
import { UserService } from '../service/UserService';
import { Role } from '../models/role';

@Directive({
  selector: '[isAdmin]'
})
export class IsAdminDirective {

  constructor(
    private templateRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef,
    private userService: UserService
  ) {}

  @Input() set isAdmin(condition: boolean | null) {
    // Only render if user has 'admin' role and condition is truthy (optional)
    if (condition !== false && this.userService.hasRole(Role.Admin)) {
      this.viewContainer.createEmbeddedView(this.templateRef);
    } else {
      this.viewContainer.clear();
    }
  }
}

@NgModule({
  declarations: [IsAdminDirective],
  exports: [IsAdminDirective]
})
export class IsAdminModule {}
