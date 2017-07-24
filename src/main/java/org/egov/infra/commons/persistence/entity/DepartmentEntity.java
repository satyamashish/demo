package org.egov.infra.commons.persistence.entity;
import org.egov.common.domain.model.Auditable;
import org.egov.common.persistence.entity.AuditableEntity;
import org.egov.infra.commons.model.Department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Builder
public class DepartmentEntity extends AuditableEntity
{
public static final String TABLE_NAME ="egf_department";
private String id;
private String name;
private String code;
private Boolean active;
public Department toDomain(){ 
Department department = new Department (); 
super.toDomain( department);department.setId(this.id);
department.setName(this.name);
department.setCode(this.code);
department.setActive(this.active);
return department ;}

public DepartmentEntity toEntity( Department department ){
super.toEntity(( Auditable)department);
this.id=department.getId();
this.name=department.getName();
this.code=department.getCode();
this.active=department.getActive();
return this;} 

}
