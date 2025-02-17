package com.rm.leaseinsight.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.rm.leaseinsight.entities.Adm;
import com.rm.leaseinsight.entities.Staff;
import com.rm.leaseinsight.entities.User;

@Service
public class AuthenticatedUserService {
	public boolean hasId(String id) {

		if (isAllowed(getAuthenticatedUser().getPrincipal())) {
			return true;
		}

		if (getAuthenticatedUser().getPrincipal() instanceof User user) {
			return compareId(user, id);
		}

		return false;
	}

	private Authentication getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication;
	}

	private boolean compareId(User user, String id) {
		return user.getId().equals(id);
	}

	private boolean isAllowed(Object user) {
		return user instanceof Adm || user instanceof Staff;
	}
}
