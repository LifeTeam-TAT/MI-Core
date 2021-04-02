/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.web.manage.menu;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.menu.MainMenu;
import org.ace.insurance.menu.MenuItem;
import org.ace.insurance.menu.SubMenu;
import org.ace.insurance.menu.service.interfaces.IMenuService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "AddNewMenuActionBean")
public class AddNewMenuActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private MainMenu menu;
	private SubMenu subMenu;
	private MenuItem menuItem;
	private boolean isNew;
	private boolean isMenu;
	private boolean isSubMenu;
	private boolean isMenuItem;

	@ManagedProperty(value = "#{MenuService}")
	private IMenuService menuService;

	public void setMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}

	private void initialization() {
		menu = (MainMenu) getParam("menu");
		subMenu = (SubMenu) getParam("subMenu");
		menuItem = (MenuItem) getParam("menuItem");
	}

	@PreDestroy
	public void destory() {
		removeParam("menu");
		removeParam("subMenu");
		removeParam("menuItem");
	}

	@PostConstruct
	public void init() {
		initialization();
		isMenu = menu != null;
		isSubMenu = subMenu != null;
		isMenuItem = menuItem != null;
		if (isMenu)
			isNew = menu.getId() != null ? false : true;
		else if (isSubMenu)
			isNew = subMenu.getId() != null ? false : true;
		else
			isNew = menuItem.getId() != null ? false : true;
	}

	public String addNewMenu() {
		String result = null;
		try {
			String messageId = null;
			String message = null;
			if (isNew) {
				if (isMenu) {
					menuService.addNewMainMenu(menu);
					message = menu.getName();
				} else if (isSubMenu) {
					menuService.addNewSubMenu(subMenu);
					message = subMenu.getName();
				} else {
					menuService.addNewMenuItem(menuItem);
					message = menuItem.getName();
				}

				messageId = MessageId.INSERT_SUCCESS;
			} else {
				if (isMenu) {
					menuService.updateMainMenu(menu);
					message = menu.getName();
				} else if (isSubMenu) {
					menuService.updateSubMenu(subMenu);
					message = subMenu.getName();
				} else {
					menuService.updateMenuItem(menuItem);
					message = menuItem.getName();
				}

				messageId = MessageId.UPDATE_SUCCESS;
			}

			addInfoMessage(null, messageId, message);
			result = "manageMenu";
		} catch (SystemException ex) {
			handelSysException(ex);
		}

		return result;
	}

	public MainMenu getMenu() {
		return menu;
	}

	public SubMenu getSubMenu() {
		return subMenu;
	}

	public MenuItem getMenuItem() {
		return menuItem;
	}

	public boolean getIsMenu() {
		return isMenu;
	}

	public boolean getIsSubMenu() {
		return isSubMenu;
	}

	public boolean getIsMenuItem() {
		return isMenuItem;
	}

	public boolean getIsNew() {
		return isNew;
	}

}
