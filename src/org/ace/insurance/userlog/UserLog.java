package org.ace.insurance.userlog;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.ace.insurance.common.TableName;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.USERLOG)
@TableGenerator(name = "USERLOG_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "USERLOG_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class UserLog implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String DEFAULT_PASSWORD = "password";

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "USERLOG_GEN")
	private String id;
	private String userId;
	private String password;
	private Date logInDate;
	private Date logOutDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getLogInDate() {
		return logInDate;
	}

	public void setLogInDate(Date logInDate) {
		this.logInDate = logInDate;
	}

	public Date getLogOutDate() {
		return logOutDate;
	}

	public void setLogOutDate(Date logOutDate) {
		this.logOutDate = logOutDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((logInDate == null) ? 0 : logInDate.hashCode());
		result = prime * result + ((logOutDate == null) ? 0 : logOutDate.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserLog other = (UserLog) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (logInDate == null) {
			if (other.logInDate != null)
				return false;
		} else if (!logInDate.equals(other.logInDate))
			return false;
		if (logOutDate == null) {
			if (other.logOutDate != null)
				return false;
		} else if (!logOutDate.equals(other.logOutDate))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

}
