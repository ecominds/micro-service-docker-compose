/**
 * @author	: Rajiv Kumar
 * @project	: boot-rest-api
 * @since	: 0.0.3
 * @date	: 07-Jan-2023
 */
package com.github.ecominds.api.rest.service.impl;

import java.util.List;
import java.util.Optional;

import com.github.ecominds.api.rest.entity.UserInfo;
import com.github.ecominds.api.rest.model.UserDtlsVO;

public interface IUserService {
	/**
	 * @param user
	 * @return
	 */
	UserInfo save(UserInfo entity);
	
	/**
	 * @return
	 */
	List<UserDtlsVO> findAll();
	
	/**
	 * @param userId
	 * @return
	 */
	Optional<UserDtlsVO> findById(Long recordId);
}