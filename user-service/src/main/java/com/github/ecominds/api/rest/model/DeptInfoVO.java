/**
 * @author	: Rajiv Kumar
 * @project	: user-service
 * @since	: 0.0.1
 * @date	: 31-Jan-2023
 */

package com.github.ecominds.api.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded= true)
public class DeptInfoVO {
	@EqualsAndHashCode.Include
	private Long recordId;
	
    private String code;
    private String name;
    private String remarks;
    private boolean active;
}