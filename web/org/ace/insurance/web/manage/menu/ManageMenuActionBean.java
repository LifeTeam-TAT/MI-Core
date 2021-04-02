package org.ace.insurance.web.manage.menu;

import java.util.List;

import javax.annotation.PostConstruct;
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
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@ViewScoped
@ManagedBean(name = "ManageMenuActionBean")
public class ManageMenuActionBean extends BaseBean {

	@ManagedProperty(value = "#{MenuService}")
	private IMenuService menuService;

	public void setMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}

	private TreeNode root;

	@PostConstruct
	public void init() {
		createMenuTree();
	}

	public void createMenuTree() {
		TreeNode menuNode = null;
		TreeNode subMenuNode = null;
		List<MainMenu> mainMenuList = menuService.findAllMainMenu();
		root = new DefaultTreeNode(null, null);
		for (MainMenu menu : mainMenuList) {
			menuNode = new DefaultTreeNode(menu, root);
			for (SubMenu subMenu : menu.getSubMenuList()) {
				subMenuNode = new DefaultTreeNode(subMenu, menuNode);
				for (MenuItem menuItem : subMenu.getMenuItemList()) {
					new DefaultTreeNode(menuItem, subMenuNode);
				}
			}
		}
	}

	public void deleteMenu(Object object) {
		try {
			if (object instanceof MainMenu) {
				MainMenu mainMenu = (MainMenu) object;
				menuService.deleteMainMenu(mainMenu);
				addInfoMessage(null, MessageId.DELETE_SUCCESS, mainMenu.getName());
			} else if (object instanceof SubMenu) {
				SubMenu subMenu = (SubMenu) object;
				menuService.deleteSubMenu(subMenu);
				addInfoMessage(null, MessageId.DELETE_SUCCESS, subMenu.getName());
			} else {
				MenuItem menuItem = (MenuItem) object;
				menuService.deleteMenuItem(menuItem);
				addInfoMessage(null, MessageId.DELETE_SUCCESS, menuItem.getName());
			}
			createMenuTree();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public TreeNode getRoot() {
		return root;
	}

	public String createMainMenu() {
		putParam("menu", new MainMenu());
		return "addNewMenu";
	}

	public String createOtherMenu(Object object) {
		if (object instanceof MainMenu) {
			MainMenu mainMenu = (MainMenu) object;
			SubMenu subMenu = new SubMenu();
			subMenu.setMainMenu(mainMenu);
			putParam("subMenu", subMenu);
		} else {
			SubMenu subMenu = (SubMenu) object;
			MenuItem menuItem = new MenuItem();
			menuItem.setSubMenu(subMenu);
			putParam("menuItem", menuItem);
		}
		return "addNewMenu";
	}

	public String updateOtherMenu(Object object) {
		if (object instanceof MainMenu) {
			MainMenu mainMenu = (MainMenu) object;
			putParam("menu", mainMenu);
		} else if (object instanceof SubMenu) {
			SubMenu subMenu = (SubMenu) object;
			putParam("subMenu", subMenu);
		} else {
			MenuItem menuItem = (MenuItem) object;
			putParam("menuItem", menuItem);
		}
		return "addNewMenu";
	}

	public boolean isMenuItem(Object object) {
		return object instanceof MenuItem;
	}
}
