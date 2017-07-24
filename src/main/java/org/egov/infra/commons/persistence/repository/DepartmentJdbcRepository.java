package org.egov.infra.commons.persistence.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.egov.common.domain.model.Pagination;
import org.egov.common.persistence.repository.JdbcRepository;
import org.egov.infra.commons.model.Department;
import org.egov.infra.commons.model.DepartmentSearch;
import org.egov.infra.commons.persistence.entity.DepartmentEntity;
import org.egov.infra.commons.persistence.entity.DepartmentSearchEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DepartmentJdbcRepository extends JdbcRepository {
	private static final Logger LOG = LoggerFactory.getLogger(DepartmentJdbcRepository.class);

	static {
		LOG.debug("init department");
		init(DepartmentEntity.class);
		LOG.debug("end init department");
	}

	public DepartmentJdbcRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public DepartmentEntity create(DepartmentEntity entity) {

		entity.setId(UUID.randomUUID().toString().replace("-", ""));
		super.create(entity);
		return entity;
	}

	public DepartmentEntity update(DepartmentEntity entity) {
		super.update(entity);
		return entity;

	}

	public Pagination<Department> search(DepartmentSearch domain) {
		DepartmentSearchEntity departmentSearchEntity = new DepartmentSearchEntity();
		departmentSearchEntity.toEntity(domain);

		String searchQuery = "select :selectfields from :tablename :condition  :orderby   ";

		Map<String, Object> paramValues = new HashMap<>();
		 StringBuffer params = new StringBuffer();

		if (departmentSearchEntity.getSortBy() != null && !departmentSearchEntity.getSortBy().isEmpty()) {
			validateSortByOrder(departmentSearchEntity.getSortBy());
			validateEntityFieldName(departmentSearchEntity.getSortBy(), DepartmentEntity.class);
		}

		String orderBy = "order by id";
		if (departmentSearchEntity.getSortBy() != null && !departmentSearchEntity.getSortBy().isEmpty()) {
			orderBy = "order by " + departmentSearchEntity.getSortBy();
		}

		searchQuery = searchQuery.replace(":tablename", DepartmentEntity.TABLE_NAME);

		searchQuery = searchQuery.replace(":selectfields", " * ");

		// implement jdbc specfic search
		if (departmentSearchEntity.getId() != null) {
			if (params.length() > 0)
				params.append(" and ");
			params.append("id =:id");
			paramValues.put("id", departmentSearchEntity.getId());
		}
		if (departmentSearchEntity.getName() != null) {
			if (params.length() > 0)
				params.append(" and ");
			params.append("name =:name");
			paramValues.put("name", departmentSearchEntity.getName());
		}
		if (departmentSearchEntity.getCode() != null) {
			if (params.length() > 0)
				params.append(" and ");
			params.append("code =:code");
			paramValues.put("code", departmentSearchEntity.getCode());
		}
		if (departmentSearchEntity.getActive() != null) {
			if (params.length() > 0)
				params.append(" and ");
			params.append("active =:active");
			paramValues.put("active", departmentSearchEntity.getActive());
		}

		Pagination<Department> page = new Pagination<>();
		if (departmentSearchEntity.getOffset() != null) {
			page.setOffset(departmentSearchEntity.getOffset());
		}
		if (departmentSearchEntity.getPageSize() != null) {
			page.setPageSize(departmentSearchEntity.getPageSize());
		}

		/*
		 * if (params.length() > 0) {
		 *
		 * searchQuery = searchQuery.replace(":condition", " where " +
		 * params.toString());
		 *
		 * } else {
		 */
		searchQuery = searchQuery.replace(":condition", "");

		searchQuery = searchQuery.replace(":orderby", orderBy);

		page = (Pagination<Department>) getPagination(searchQuery, page, paramValues);
		searchQuery = searchQuery + " :pagination";

		searchQuery = searchQuery.replace(":pagination",
				"limit " + page.getPageSize() + " offset " + page.getOffset() * page.getPageSize());

		BeanPropertyRowMapper row = new BeanPropertyRowMapper(DepartmentEntity.class);

		List<DepartmentEntity> departmentEntities = namedParameterJdbcTemplate.query(searchQuery.toString(),
				paramValues, row);

		page.setTotalResults(departmentEntities.size());

		List<Department> departments = new ArrayList<>();
		for (DepartmentEntity departmentEntity : departmentEntities) {

			departments.add(departmentEntity.toDomain());
		}
		page.setPagedData(departments);

		return page;
	}

	public DepartmentEntity findById(DepartmentEntity entity) {
		List<String> list = allIdentitiferFields.get(entity.getClass().getSimpleName());

		Map<String, Object> paramValues = new HashMap<>();

		for (String s : list) {
			paramValues.put(s, getValue(getField(entity, s), entity));
		}

		List<DepartmentEntity> departments = namedParameterJdbcTemplate.query(
				getByIdQuery.get(entity.getClass().getSimpleName()).toString(), paramValues,
				new BeanPropertyRowMapper(DepartmentEntity.class));
		if (departments.isEmpty()) {
			return null;
		} else {
			return departments.get(0);
		}

	}

}