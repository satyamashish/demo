package org.egov.infra.commons.web.contract ;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class DepartmentSearchContract extends DepartmentContract { private String ids; 
private String  sortBy; 
private Integer pageSize; 
private Integer offset; 
} 