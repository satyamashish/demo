package org.egov.infra.commons.domain.repository;

import java.util.HashMap;
import java.util.Map;

import org.egov.common.domain.model.Pagination;
import org.egov.infra.commons.model.Department;
import org.egov.infra.commons.model.DepartmentSearch;
import org.egov.infra.commons.persistence.entity.DepartmentEntity;
import org.egov.infra.commons.persistence.repository.DepartmentJdbcRepository;
import org.egov.infra.commons.web.requests.DepartmentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentRepository {

	@Autowired
	private DepartmentJdbcRepository departmentJdbcRepository;
	/*@Autowired
	private MastersQueueRepository departmentQueueRepository;*/

	public Department findById(Department department) {
		DepartmentEntity entity = departmentJdbcRepository.findById(new DepartmentEntity().toEntity(department));
		return entity.toDomain();

	}

	@Transactional
	public Department save(Department department) {
		DepartmentEntity entity = departmentJdbcRepository.create(new DepartmentEntity().toEntity(department));
		return entity.toDomain();
	}

	@Transactional
	public Department update(Department department) {
		DepartmentEntity entity = departmentJdbcRepository.update(new DepartmentEntity().toEntity(department));
		return entity.toDomain();
	}

	public void add(DepartmentRequest request) {
		Map<String, Object> message = new HashMap<>();

		if (request.getRequestInfo().getAction().equalsIgnoreCase("create")) {
			message.put("department_create", request);
		} else {
			message.put("department_update", request);
		}
		//departmentQueueRepository.add(message);
	}

	public Pagination<Department> search(DepartmentSearch domain) {

		return departmentJdbcRepository.search(domain);

	}

}