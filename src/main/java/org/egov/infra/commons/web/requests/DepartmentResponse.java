package org.egov.infra.commons.web.requests;

import java.util.List;

import org.egov.common.web.contract.PaginationContract;
import org.egov.common.web.contract.ResponseInfo;
import org.egov.infra.commons.web.contract.DepartmentContract;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(value = Include.NON_NULL)
public @Data class DepartmentResponse {
	private ResponseInfo responseInfo;
	private List<DepartmentContract> departments;
	private PaginationContract page;
}