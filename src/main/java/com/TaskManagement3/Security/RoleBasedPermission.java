package com.TaskManagement3.Security;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.TaskManagement3.Enum.Permission;
import com.TaskManagement3.Enum.Role;

public class RoleBasedPermission {

	public static Map<Role, Set<Permission>> getRolePermission() {
		
		Map<Role, Set<Permission>> map = new HashMap<>();

		// ADMIN Permissions
		map.put(Role.ADMIN, new HashSet<>(Arrays.asList(
				Permission.ISSUE_VIEW, 
				Permission.ISSUE_CREATE, 
				Permission.ISSU_EEDIT, 
				Permission.ISSUE_DELETE, 
				Permission.COMMENT_ADD, 
				Permission.COMMENT_DELETE, 
				Permission.USER_MANAGE
		)));

		// MANAGER Permissions
		map.put(Role.MANAGER, new HashSet<>(Arrays.asList(
				Permission.ISSUE_VIEW, 
				Permission.ISSUE_CREATE, 
				Permission.ISSU_EEDIT, 
				Permission.COMMENT_ADD
		)));

		// DEVELOPER Permissions
		map.put(Role.DEVELOPER, new HashSet<>(Arrays.asList(
				Permission.ISSUE_VIEW, 
				Permission.ISSU_EEDIT, 
				Permission.COMMENT_ADD
		)));

		// TESTER Permissions
		map.put(Role.TESTER, new HashSet<>(Arrays.asList(
				Permission.ISSUE_VIEW, 
				Permission.COMMENT_ADD
		)));

		return map;
	}
}