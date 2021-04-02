package org.ace.insurance.process.interfaces;

import org.ace.insurance.user.User;

public interface IUserProcessService {
	public void registerUser(User user);
	public User getLoginUser();
}
