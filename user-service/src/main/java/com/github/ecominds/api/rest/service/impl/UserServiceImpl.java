/**
 * @author	: Rajiv Kumar
 * @project	: user-service
 * @since	: 0.0.1
 * @date	: 31-Jan-2023
 */

package com.github.ecominds.api.rest.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.github.ecominds.api.rest.dao.IUserInfoDAO;
import com.github.ecominds.api.rest.entity.UserInfo;
import com.github.ecominds.api.rest.exception.AppException;
import com.github.ecominds.api.rest.model.DeptInfoVO;
import com.github.ecominds.api.rest.model.UserDtlsVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements IUserService{

    @Autowired
    private IUserInfoDAO dao;

    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${ecominds.endpoint.dept-service.base-path}")
    private String deptServiceBaseUrl;

    public UserInfo save(UserInfo entity) {
        log.info("Proceeding with entity validation prior to save");
        validate(entity);
        return dao.save(entity);
    }

	public Optional<UserDtlsVO> findById(Long recordId) {
		log.info("Executing USER_SERVICE::findById");
		return dao.findById(recordId).map(userInfo -> {
			DeptInfoVO deptInfo = getDeptInfo(userInfo.getDeptRecordId());
			return new UserDtlsVO(userInfo, deptInfo);
		});
    }

	@Override
	public List<UserDtlsVO> findAll() {
		log.info("Executing USER_SERVICE::findAll");
		
		Map<Long, DeptInfoVO> deptCol = getDeptInfoCol();
		
		return dao.findAll().stream().map(userInfo -> {
			return new UserDtlsVO(userInfo, deptCol.get(userInfo.getDeptRecordId()));
		}).collect(Collectors.toList());
	}

	/**
	 * @param savedEntity
	 */
	private void validate(UserInfo entity) {
		DeptInfoVO vo = getDeptInfo(entity.getDeptRecordId());
		if(vo == null) {
			throw new AppException("Invalid department attribute: " + entity.getDeptRecordId());
		}
	}
	
	private DeptInfoVO getDeptInfo(Long deptId) {
		try {
			log.info("Accessing: " + deptServiceBaseUrl);
			return restTemplate.getForObject(deptServiceBaseUrl + deptId, DeptInfoVO.class);
		}catch(RestClientException ex) {
			handleRestException(ex);
		}
		return null;
	}

	private Map<Long, DeptInfoVO> getDeptInfoCol() {
		try {
			DeptInfoVO[] tmpArr = restTemplate.getForObject(deptServiceBaseUrl, DeptInfoVO[].class);
			return Arrays.asList(tmpArr).stream().collect(
					Collectors.toMap(DeptInfoVO::getRecordId, entity -> entity));
		}catch(RestClientException ex) {
			handleRestException(ex);
		}
		return Collections.emptyMap();
	}
	
	/**
	 * @param ex
	 */
	private void handleRestException(RestClientException ex) {
		if(ex instanceof ResourceAccessException) {
			log.error("The department is not accessible. Kindly try after some time");
		}else if(ex instanceof HttpClientErrorException) {
			log.error("HttpClientErrorException: " + ex.getMessage());
		}else {
			log.error("Exception occured in executing REST call to dept service", ex);
		}
	}
}