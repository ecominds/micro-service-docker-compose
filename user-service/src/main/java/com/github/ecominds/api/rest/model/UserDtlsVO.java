/**
 * @author	: Rajiv Kumar
 * @project	: user-service
 * @since	: 0.0.1
 * @date	: 31-Jan-2023
 */

package com.github.ecominds.api.rest.model;

import com.github.ecominds.api.rest.entity.UserInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDtlsVO {
    private UserInfo userInfo;
    private DeptInfoVO deptInfo;
}