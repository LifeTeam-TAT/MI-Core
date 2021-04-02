package org.ace.insurance.web.manage.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.common.TransactionType;
import org.ace.insurance.product.PRO001;
import org.ace.insurance.product.Product;
import org.ace.insurance.product.service.interfaces.IProductService;
import org.ace.insurance.system.common.currency.Currency;
import org.ace.insurance.system.common.currency.service.interfaces.ICurrencyService;
import org.ace.insurance.user.AuthorityPermission;
import org.ace.insurance.user.User;
import org.ace.insurance.user.service.interfaces.IUserService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "AuthorityPermissionActionBean")
public class AuthorityPermissionActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{UserService}")
	private IUserService userService;

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	@ManagedProperty(value = "#{CurrencyService}")
	private ICurrencyService currencyService;

	public void setCurrencyService(ICurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	@ManagedProperty(value = "#{ProductService}")
	private IProductService productService;

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	private AuthorityPermission authorityPermission;
	private User configUser;
	private Map<String, AuthorityPermission> authorityPermissionMap;
	private boolean isCreatNew;

	@PostConstruct
	public void init() {
		initializeInjection();
		createNewAuthorityPermission();
		/*
		 * loadCurrencyList(); loadProductList();
		 */
		loadAuthorityPermission();

	}

	private void createNewAuthorityPermission() {
		isCreatNew = true;
		authorityPermission = new AuthorityPermission();

	}

	private void loadAuthorityPermission() {
		authorityPermissionMap = new HashMap<>();
		List<AuthorityPermission> permissionList = configUser.getAuthorityPermissionList();
		String key = null;
		if (permissionList != null && !permissionList.isEmpty()) {
			for (AuthorityPermission authorityPermission : permissionList) {
				key = authorityPermission.getProductName() + authorityPermission.getProductCode() + authorityPermission.getTransactionType().getLabel();
				authorityPermissionMap.put(key, authorityPermission);
			}
		}

	}

	private void initializeInjection() {
		configUser = (User) getParam("configPermissionUser");
	}

	/*************************************************
	 * for currency type
	 *********************************************************/
	public List<Currency> getCurrencyList() {
		return currencyService.findAllCurrency();
	}

	/*************************************************
	 * for ProductCode
	 *********************************************************/

	public List<PRO001> getProductList() {
		return productService.findAllProductName();
	}

	public void changeProduct(AjaxBehaviorEvent e) {
		Product prod = productService.findProductById(authorityPermission.getProductCode());
		authorityPermission.setProductName(prod.getProductContent().getName());
	}

	public void addAuthorityPermission() {
		String key = authorityPermission.getProductName() + authorityPermission.getProductCode() + authorityPermission.getTransactionType().getLabel();
		authorityPermissionMap.put(key, authorityPermission);
		createNewAuthorityPermission();
	}

	public void prepareEditAuthorityPermission(AuthorityPermission permission) {
		this.isCreatNew = false;
		this.authorityPermission = permission;
	}

	public void deleteAuthorityPermission(AuthorityPermission permission) {
		String key = permission.getProductName() + permission.getProductCode() + permission.getTransactionType().getLabel();
		authorityPermissionMap.remove(key);
		createNewAuthorityPermission();
	}

	public String applyAuthority() {
		String result = null;
		try {
			List<AuthorityPermission> authorityList = new ArrayList<>(authorityPermissionMap.values());
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, configUser.getUsercode());
			configUser.setAuthorityPermissionList(authorityList);
			userService.updateAuthorityPermission(configUser);
			result = "manageUser";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	public AuthorityPermission getAuthorityPermission() {
		return authorityPermission;
	}

	public void setAuthorityPermission(AuthorityPermission authorityPermission) {
		this.authorityPermission = authorityPermission;
	}

	public User getUser() {
		return configUser;
	}

	public boolean isCreatNew() {
		return isCreatNew;
	}

	public List<AuthorityPermission> getAuthorityPermissionList() {
		return new ArrayList<>(authorityPermissionMap.values());
	}

	public List<TransactionType> getTransactionTypes() {
		return Arrays.asList(TransactionType.UNDERWRITING, TransactionType.ENDORSEMENT, TransactionType.RENEWAL);
	}

}
