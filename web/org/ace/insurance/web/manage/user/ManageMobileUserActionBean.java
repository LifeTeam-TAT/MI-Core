/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.web.manage.user;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.user.MobileUser;
import org.ace.insurance.user.service.interfaces.IMobileUserService;
import org.ace.java.component.SystemException;
import org.ace.java.component.service.PasswordCodeHandler;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "ManageMobileUserActionBean")
public class ManageMobileUserActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{MobileUserService}")
	private IMobileUserService mobileUserService;

	public void setMobileUserService(IMobileUserService mobileUserService) {
		this.mobileUserService = mobileUserService;
	}

	private MobileUser mobileUser;
	private List<MobileUser> mobileUserList;
	private boolean createNew;
	private String criteria;
	private String password;

	public enum UserType {
		AGENT("Agent"), STAFF("GGIP's Staff"), OTHER("Other");

		private String label;

		private UserType(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}
	}

	@PostConstruct
	public void init() {
		createNewMobileUser();
		loadMobileUser();
	}

	public void createNewMobileUser() {
		createNew = true;
		criteria = "";
		password = "";
		mobileUser = new MobileUser();
	}

	public void loadMobileUser() {
		mobileUserList = mobileUserService.findAllMobileUser();
	}

	public void prepareUpdateMobileUser(MobileUser mUser) {
		createNew = false;
		PasswordCodeHandler c = new PasswordCodeHandler();
		this.mobileUser = mUser;
		this.password = c.decode(mobileUser.getPassword());
	}

	public void addNewMobileUser() {
		try {
			mobileUser.setPassword("ggip!@#123");
			mobileUserService.addNewMobileUser(mobileUser);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, mobileUser.getName());
			init();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateMobileUser() {
		try {
			mobileUser.setPassword(password);
			mobileUserService.updateMobileUser(mobileUser);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, mobileUser.getName());
			init();
			loadMobileUser();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public String deleteMobileUser() {
		try {
			mobileUserService.deleteMobileUser(mobileUser);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, mobileUser.getName());
			init();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return null;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public void searchMobileUser() {
		mobileUserList = mobileUserService.findByCriteria(criteria);
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public MobileUser getMobileUser() {
		return mobileUser;
	}

	public void setMobileUser(MobileUser mobileUser) {
		this.mobileUser = mobileUser;
	}

	public List<MobileUser> getMobileUserList() {
		return mobileUserList;
	}

	public UserType[] getUserTypes() {
		return UserType.values();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
