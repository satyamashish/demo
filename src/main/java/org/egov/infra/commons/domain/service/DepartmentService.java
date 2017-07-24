package org.egov.infra.commons.domain.service;

import java.util.List;

import org.egov.common.domain.exception.CustomBindException;
import org.egov.common.domain.model.Pagination;
import org.egov.infra.commons.domain.repository.DepartmentRepository;
import org.egov.infra.commons.model.Department;
import org.egov.infra.commons.model.DepartmentSearch;
import org.egov.infra.commons.web.requests.DepartmentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.SmartValidator;

@Service
@Transactional(readOnly = true)
public class DepartmentService {

	public static final String ACTION_CREATE = "create";
	public static final String ACTION_UPDATE = "update";
	public static final String ACTION_VIEW = "view";
	public static final String ACTION_EDIT = "edit";
	public static final String ACTION_SEARCH = "search";

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private SmartValidator validator;


	private BindingResult validate(List<Department> departments, String method, BindingResult errors) {

		try {
			switch (method) {
			case ACTION_VIEW:
				// validator.validate(departmentContractRequest.getDepartment(), errors);
				break;
			case ACTION_CREATE:
				Assert.notNull(departments, "Departments to create must not be null");
				for (Department department : departments) {
					validator.validate(department, errors);
				}
				break;
			case ACTION_UPDATE:
				Assert.notNull(departments, "Departments to update must not be null");
				for (Department department : departments) {
					validator.validate(department, errors);
				}
				break;
			default:

			}
		} catch (IllegalArgumentException e) {
			errors.addError(new ObjectError("Missing data", e.getMessage()));
		}
		return errors;

	}

	public List<Department> fetchRelated(List<Department> departments) {
		for (Department department : departments) {
			// fetch related items

		}

		return departments;
	}

	@Transactional
	public List<Department> add(List<Department> departments, BindingResult errors) {
		departments = fetchRelated(departments);
		validate(departments, ACTION_CREATE, errors);
		if (errors.hasErrors()) {
			throw new CustomBindException(errors);
		}
		return departments;

	}

	@Transactional
	public List<Department> update(List<Department> departments, BindingResult errors) {
		departments = fetchRelated(departments);
		validate(departments, ACTION_UPDATE, errors);
		if (errors.hasErrors()) {
			throw new CustomBindException(errors);
		}
		return departments;

	}

	public void addToQue(DepartmentRequest request) {
		departmentRepository.add(request);
	}

	public Pagination<Department> search(DepartmentSearch departmentSearch) {
		return departmentRepository.search(departmentSearch);
	}

	@Transactional
	public Department save(Department department) {
		return departmentRepository.save(department);
	}

	@Transactional
	public Department update(Department department) {
		return departmentRepository.update(department);
	}

}