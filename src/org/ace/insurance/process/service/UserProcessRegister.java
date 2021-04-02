package org.ace.insurance.process.service;

import org.ace.insurance.process.interfaces.IUserProcessService;
import org.ace.insurance.user.User;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service(value = "UserProcessService")
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class UserProcessRegister implements IUserProcessService {
	private User user;
	
	public void registerUser(User user) {
		this.user = user;
	}
	
	public User getLoginUser() {
		return user;
	}
}
