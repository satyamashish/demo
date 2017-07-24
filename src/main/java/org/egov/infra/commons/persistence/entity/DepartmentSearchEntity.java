package org.egov.infra.commons.persistence.entity ;
import org.egov.infra.commons.model.Department;
import org.egov.infra.commons.model.DepartmentSearch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class DepartmentSearchEntity extends DepartmentEntity { private String ids; 
private String  sortBy; 
private Integer pageSize; 
private Integer offset; 
@Override
public Department toDomain(){ 
Department department = new Department (); 
super.toDomain( department);return department ;}
 
public DepartmentSearchEntity toEntity( DepartmentSearch departmentSearch){
super.toEntity(( Department)departmentSearch);
this.pageSize=departmentSearch.getPageSize(); this.offset=departmentSearch.getOffset(); this.sortBy=departmentSearch.getSortBy(); this.ids=departmentSearch.getIds(); return this;} 
 
} 