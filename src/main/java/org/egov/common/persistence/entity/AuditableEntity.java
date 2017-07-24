package org.egov.common.persistence.entity;

import java.util.Date;

import org.egov.common.domain.model.Auditable;
import org.egov.common.domain.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditableEntity {
	protected String tenantId;
	protected String createdBy;
	protected String lastModifiedBy;
	protected Date createdDate;
	protected Date lastModifiedDate;

	protected void toDomain(Auditable domain) {
		if (createdBy != null) {
			domain.setCreatedBy(User.builder().id(this.getCreatedBy()).build());
		}
		if (lastModifiedBy != null) {
			domain.setLastModifiedBy(User.builder().id(this.getLastModifiedBy()).build());
		}
		domain.setCreatedDate(this.getCreatedDate());
		domain.setLastModifiedDate(this.getLastModifiedDate());
		domain.setTenantId(this.getTenantId());
	}

	protected void toEntity(Auditable domain) {
		this.setCreatedBy(domain.getCreatedBy() != null ? domain.getCreatedBy().getId() : null);
		this.setLastModifiedBy(domain.getLastModifiedBy() != null ? domain.getLastModifiedBy().getId() : null);
		this.setCreatedDate(domain.getCreatedDate());
		this.setLastModifiedDate(domain.getLastModifiedDate());
		this.setTenantId(domain.getTenantId());
	}

}
