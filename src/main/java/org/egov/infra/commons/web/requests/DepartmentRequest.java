package org.egov.infra.commons.web.requests;

import java.util.ArrayList;
import java.util.List;

import org.egov.common.web.contract.RequestInfo;
import org.egov.infra.commons.web.contract.DepartmentContract;

import lombok.Data;

public @Data class DepartmentRequest {
	private RequestInfo requestInfo = new RequestInfo();
	private List<DepartmentContract> departments = new ArrayList<DepartmentContract>();
}