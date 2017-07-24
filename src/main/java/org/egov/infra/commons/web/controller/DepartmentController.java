package org.egov.infra.commons.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.egov.common.domain.exception.CustomBindException;
import org.egov.common.domain.model.Pagination;
import org.egov.common.web.contract.PaginationContract;
import org.egov.common.web.contract.RequestInfo;
import org.egov.common.web.contract.ResponseInfo;
import org.egov.infra.commons.domain.service.DepartmentService;
import org.egov.infra.commons.model.Department;
import org.egov.infra.commons.model.DepartmentSearch;
import org.egov.infra.commons.web.contract.DepartmentContract;
import org.egov.infra.commons.web.contract.DepartmentSearchContract;
import org.egov.infra.commons.web.requests.DepartmentRequest;
import org.egov.infra.commons.web.requests.DepartmentResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@PostMapping("/_create")
	@ResponseStatus(HttpStatus.CREATED)
	public DepartmentResponse create(@RequestBody DepartmentRequest departmentRequest, BindingResult errors) {
		if (errors.hasErrors()) {
			throw new CustomBindException(errors);
		}

		ModelMapper model = new ModelMapper();
		DepartmentResponse departmentResponse = new DepartmentResponse();
		departmentResponse.setResponseInfo(getResponseInfo(departmentRequest.getRequestInfo()));
		List<Department> departments = new ArrayList<>();
		Department department;
		List<DepartmentContract> departmentContracts = new ArrayList<>();
		DepartmentContract contract;

		departmentRequest.getRequestInfo().setAction("create");

		for (DepartmentContract departmentContract : departmentRequest.getDepartments()) {
			department = new Department();
			model.map(departmentContract, department);
			department.setCreatedDate(new Date());
			department.setCreatedBy(departmentRequest.getRequestInfo().getUserInfo());
			department.setLastModifiedBy(departmentRequest.getRequestInfo().getUserInfo());
			departments.add(department);
		}

		departments = departmentService.add(departments, errors);

		for (Department f : departments) {
			contract = new DepartmentContract();
			contract.setCreatedDate(new Date());
			model.map(f, contract);
			departmentContracts.add(contract);
		}

		departmentRequest.setDepartments(departmentContracts);
		departmentService.addToQue(departmentRequest);
		departmentResponse.setDepartments(departmentContracts);

		return departmentResponse;
	}

	@PostMapping("/_update")
	@ResponseStatus(HttpStatus.CREATED)
	public DepartmentResponse update(@RequestBody DepartmentRequest departmentRequest, BindingResult errors) {

		if (errors.hasErrors()) {
			throw new CustomBindException(errors);
		}
		departmentRequest.getRequestInfo().setAction("update");
		ModelMapper model = new ModelMapper();
		DepartmentResponse departmentResponse = new DepartmentResponse();
		List<Department> departments = new ArrayList<>();
		departmentResponse.setResponseInfo(getResponseInfo(departmentRequest.getRequestInfo()));
		Department department;
		DepartmentContract contract;
		List<DepartmentContract> departmentContracts = new ArrayList<>();

		for (DepartmentContract departmentContract : departmentRequest.getDepartments()) {
			department = new Department();
			model.map(departmentContract, department);
			department.setLastModifiedBy(departmentRequest.getRequestInfo().getUserInfo());
			department.setLastModifiedDate(new Date());
			departments.add(department);
		}

		departments = departmentService.update(departments, errors);

		for (Department departmentObj : departments) {
			contract = new DepartmentContract();
			model.map(departmentObj, contract);
			departmentObj.setLastModifiedDate(new Date());
			departmentContracts.add(contract);
		}

		departmentRequest.setDepartments(departmentContracts);
		departmentService.addToQue(departmentRequest);
		departmentResponse.setDepartments(departmentContracts);

		return departmentResponse;
	}

	@PostMapping("/_search")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public DepartmentResponse search(@ModelAttribute DepartmentSearchContract departmentSearchContract, RequestInfo requestInfo,
			BindingResult errors) {

		ModelMapper mapper = new ModelMapper();
		DepartmentSearch domain = new DepartmentSearch();
		mapper.map(departmentSearchContract, domain);
		DepartmentContract contract;
		ModelMapper model = new ModelMapper();
		List<DepartmentContract> departmentContracts = new ArrayList<>();
		Pagination<Department> departments = departmentService.search(domain);

		for (Department department : departments.getPagedData()) {
			contract = new DepartmentContract();
			model.map(department, contract);
			departmentContracts.add(contract);
		}

		DepartmentResponse response = new DepartmentResponse();
		response.setDepartments(departmentContracts);
		response.setPage(new PaginationContract(departments));
		response.setResponseInfo(getResponseInfo(requestInfo));

		return response;

	}

	private ResponseInfo getResponseInfo(RequestInfo requestInfo) {
		return ResponseInfo.builder().apiId(requestInfo.getApiId()).ver(requestInfo.getVer()).ts(new Date())
				.resMsgId(requestInfo.getMsgId()).resMsgId("placeholder").status("placeholder").build();
	}

}