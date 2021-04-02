package org.ace.insurance.web.manage.user;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.ace.insurance.user.User;
import org.ace.insurance.user.service.interfaces.IUserService;

@ManagedBean(name = "ProfileActionBean")
public class ProfileActionBean {
	@ManagedProperty(value = "#{UserService}")
	private IUserService userService;

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	private User user;

}
