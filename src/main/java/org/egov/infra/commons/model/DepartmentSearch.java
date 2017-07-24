package org.egov.infra.commons.model ;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class DepartmentSearch extends Department{ private String ids; 
private String  sortBy; 
private Integer pageSize; 
private Integer offset; 
} 