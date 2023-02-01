/**
 * @author	: Rajiv Kumar
 * @project	: user-service
 * @since	: 0.0.1
 * @date	: 31-Jan-2023
 */

package com.github.ecominds.api.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.ecominds.api.rest.entity.UserInfo;
import com.github.ecominds.api.rest.exception.AppException;
import com.github.ecominds.api.rest.model.UserDtlsVO;
import com.github.ecominds.api.rest.service.impl.IUserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserApiController {

    @Autowired
    private IUserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserInfo create(@RequestBody UserInfo entity) {
        log.info("Save user details : " + entity);
        entity.setRecordId(null);
        return service.save(entity);
    }
    
    @GetMapping
    public List<UserDtlsVO> getAll() {
    	log.info("Request received to find all entities");
        return service.findAll();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDtlsVO> getUserWithDepartment(@PathVariable Long userId) {
		log.info("Request received to find entity. USER ID : " + userId);
		return service.findById(userId)
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new AppException(String.format("No such entity found with USER_ID='%s'", userId)));
	}
    
    @PutMapping("{userId}")
	public ResponseEntity<UserDtlsVO> update(@PathVariable Long userId,
			@RequestBody UserInfo entity) {
		log.info("Request received to update entity. USER ID : " + userId);
		return service.findById(userId)
				.map(vo -> {
					UserInfo savedEntity = vo.getUserInfo();
					savedEntity.setFirstName(entity.getFirstName());
					savedEntity.setLastName(entity.getLastName());
					savedEntity.setEmail(entity.getEmail());
					savedEntity.setDeptRecordId(entity.getDeptRecordId());
					
					UserInfo updatedEntity = service.save(savedEntity);
					vo.setUserInfo(updatedEntity);
					return ResponseEntity.ok(vo); 
				})
				.orElseThrow(() -> new AppException(String.format("No such entity found with USER_ID='%s'", userId)));
	}
}