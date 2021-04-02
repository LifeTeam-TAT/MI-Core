package org.ace.insurance.web;

import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.ace.insurance.menu.MainMenu;
import org.ace.insurance.menu.MainMenuValue;
import org.ace.insurance.menu.MenuItem;
import org.ace.insurance.menu.MenuItemValue;
import org.ace.insurance.menu.SubMenu;
import org.ace.insurance.menu.SubMenuValue;
import org.ace.insurance.process.interfaces.IUserProcessService;
import org.ace.insurance.user.User;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

@SessionScoped
@ManagedBean(name = "MenuActionBean")
public class MenuActionBean extends BaseBean {

	@ManagedProperty(value = "#{UserProcessService}")
	private IUserProcessService userProcessService;

	public void setUserProcessService(IUserProcessService userProcessService) {
		this.userProcessService = userProcessService;
	}

	private User user;
	private MenuModel model;

	@PreDestroy
	public void destroy() {
		// FacesContext.getCurrentInstance().getExternalContext().setSessionMaxInactiveInterval(0);
	}

	@PostConstruct
	public void init() {
		user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Constants.LOGIN_USER);
		if (user != null && user.getRole() != null) {
			createMenu();
		}
	}

	public void createMenu() {
		model = new DefaultMenuModel();
		DefaultSubMenu mainMenu;
		DefaultSubMenu subMenu;
		DefaultMenuItem menuItem;

		MainMenu roleMainMenu;
		SubMenu roleSubMenu;
		MenuItem roleMenuItem;
		Collections.sort(user.getRole().getMainMenuValueList());
		for (MainMenuValue mainMenuValue : user.getRole().getMainMenuValueList()) {
			roleMainMenu = mainMenuValue.getMainMenu();
			if (mainMenuValue.getSubMenuValueList().size() < 1 && haveAction(roleMainMenu.getAction())
			// && haveUrl(roleMainMenu.getUrl())
			) {
				menuItem = new DefaultMenuItem(roleMainMenu.getName());
				menuItem.setCommand(roleMainMenu.getAction());
				model.addElement(menuItem);
			} else if (mainMenuValue.getSubMenuValueList().size() > 0) {
				mainMenu = new DefaultSubMenu(roleMainMenu.getName());
				Collections.sort(mainMenuValue.getSubMenuValueList());
				for (SubMenuValue subMenuValue : mainMenuValue.getSubMenuValueList()) {
					roleSubMenu = subMenuValue.getSubMenu();
					if (subMenuValue.getMenuItemValueList().size() < 1 && haveAction(roleSubMenu.getAction())
					// && haveUrl(roleSubMenu.getUrl())
					) {
						menuItem = new DefaultMenuItem(roleSubMenu.getName());
						menuItem.setCommand(roleSubMenu.getAction());
						mainMenu.addElement(menuItem);
					} else if (subMenuValue.getMenuItemValueList().size() > 0) {
						subMenu = new DefaultSubMenu(roleSubMenu.getName());
						Collections.sort(subMenuValue.getMenuItemValueList());
						for (MenuItemValue menuItemValue : subMenuValue.getMenuItemValueList()) {
							roleMenuItem = menuItemValue.getMenuItem();
							menuItem = new DefaultMenuItem(roleMenuItem.getName());
							menuItem.setCommand(roleMenuItem.getAction());
							subMenu.addElement(menuItem);
						}
						mainMenu.addElement(subMenu);
					}
				}
				model.addElement(mainMenu);
			}
		}
	}

	private boolean haveAction(String action) {
		if (action != null && !action.trim().isEmpty()) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unused")
	private boolean haveUrl(String url) {
		if (url != null && url.contains(".xhtml")) {
			return true;
		}
		return false;
	}

	public MenuModel getModel() {
		return model;
	}

	public User getUser() {
		return user;
	}

}
