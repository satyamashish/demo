package org.egov.infra.commons.web.repository ;
import org.egov.common.web.contract.CommonResponse;
import org.egov.infra.commons.web.contract.DepartmentContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper; 
@Service 
public class DepartmentContractRepository { 
private RestTemplate restTemplate; 
private String hostUrl; 
public static final String SEARCH_URL = " /egov-commons/departments/search?";
@Autowired
	private ObjectMapper objectMapper;
public DepartmentContractRepository(@Value("${egov.commonshost.url}") String hostUrl,RestTemplate restTemplate) {
this.restTemplate = restTemplate;
this.hostUrl = hostUrl;
}
public DepartmentContract findById(DepartmentContract departmentContract) {

		String url = String.format("%s%s", hostUrl, SEARCH_URL);
		StringBuffer content = new StringBuffer();
		if (departmentContract.getId() != null) {
			content.append("id=" + departmentContract.getId());
		}

		if (departmentContract.getTenantId() != null) {
			content.append("&tenantId=" + departmentContract.getTenantId());
		}
		url = url + content.toString();
		CommonResponse<DepartmentContract> result = objectMapper.convertValue(
				restTemplate.postForObject(url, null, CommonResponse.class),
				new TypeReference<CommonResponse<DepartmentContract>>() {
				});

		if (result.getData() != null && result.getData().size() == 1)
			return result.getData().get(0);
		else
			return null;

	}
} 