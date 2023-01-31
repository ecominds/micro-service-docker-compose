/**
 * @author	: Rajiv Kumar
 * @project	: user-service
 * @since	: 0.0.1
 * @date	: 31-Jan-2023
 */

package com.github.ecominds.api.rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.ecominds.api.rest.entity.UserInfo;

@Repository
public interface IUserInfoDAO  extends JpaRepository<UserInfo,Long> {
    
}