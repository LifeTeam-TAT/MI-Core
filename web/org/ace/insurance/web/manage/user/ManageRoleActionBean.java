package org.ace.insurance.web.manage.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.menu.MainMenu;
import org.ace.insurance.menu.MainMenuValue;
import org.ace.insurance.menu.MenuItem;
import org.ace.insurance.menu.MenuItemValue;
import org.ace.insurance.menu.SubMenu;
import org.ace.insurance.menu.SubMenuValue;
import org.ace.insurance.menu.service.interfaces.IMenuService;
import org.ace.insurance.role.ROL001;
import org.ace.insurance.role.Role;
import org.ace.insurance.role.service.interfaces.IRoleService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@ViewScoped
@ManagedBean(name = "ManageRoleActionBean")
public class ManageRoleActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{RoleService}")
	private IRoleService roleService;

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	@ManagedProperty(value = "#{MenuService}")
	private IMenuService menuService;

	public void setMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}

	private Role role;
	private TreeNode root;
	private TreeNode[] selectedNodes;
	private boolean createNew;
	private List<ROL001> roleList;
	private List<MainMenu> mainMenuList;

	@PostConstruct
	public void init() {
		mainMenuList = menuService.findAllMainMenu();
		createNewRole();
		loadRole();
	}

	private void createTreeNode() {
		TreeNode menuNode = null;
		TreeNode subMenuNode = null;
		mainMenuList = menuService.findAllMainMenu();
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

	public void loadRole() {
		roleList = roleService.findAllRole();
	}

	public void createNewRole() {
		createNew = true;
		role = new Role();
		createTreeNode();
	}

	private void rebindSelectedNode() {
		List<MainMenu> selectedMainMenuList = new ArrayList<>();
		List<SubMenu> selectedSubMenuList = new ArrayList<>();
		List<MenuItem> selectedMenuItemList = new ArrayList<>();
		for (MainMenuValue mainMenuValue : role.getMainMenuValueList()) {
			if (mainMenuValue.getSubMenuValueList() == null || mainMenuValue.getSubMenuValueList().isEmpty()) {
				selectedMainMenuList.add(mainMenuValue.getMainMenu());
			}
			for (SubMenuValue subMenuValue : mainMenuValue.getSubMenuValueList()) {
				if (subMenuValue.getMenuItemValueList() == null || subMenuValue.getMenuItemValueList().isEmpty()) {
					selectedSubMenuList.add(subMenuValue.getSubMenu());
				}
				for (MenuItemValue menuItemValue : subMenuValue.getMenuItemValueList()) {
					selectedMenuItemList.add(menuItemValue.getMenuItem());
				}
			}
		}

		MainMenu menu = null;
		SubMenu subMenu = null;
		MenuItem menuItem = null;
		for (TreeNode menuNode : root.getChildren()) {
			menu = (MainMenu) menuNode.getData();
			if (selectedMainMenuList.contains(menu)) {
				menuNode.setSelected(true);
			}
			for (TreeNode subMenuNode : menuNode.getChildren()) {
				subMenu = (SubMenu) subMenuNode.getData();
				if (selectedSubMenuList.contains(subMenu)) {
					subMenuNode.setSelected(true);
				}
				for (TreeNode menuItemNode : subMenuNode.getChildren()) {
					menuItem = (MenuItem) menuItemNode.getData();
					if (selectedMenuItemList.contains(menuItem)) {
						menuItemNode.setSelected(true);
					}
				}
			}
		}
	}

	public void prepareUpdateRole(ROL001 rol001) {
		createNew = false;
		this.role = roleService.findRoleById(rol001.getId());
		createTreeNode();
		rebindSelectedNode();
	}

	private void loadMainMenuValueList() {
		List<MainMenuValue> mainMenuValueList = new ArrayList<MainMenuValue>();
		List<SubMenuValue> subMenuValueList = null;
		List<MenuItemValue> menuItemValueList = null;
		MainMenuValue mainMenuValue = null;
		SubMenuValue subMenuValue = null;
		MenuItemValue menuItemValue = null;
		MainMenu mainMenu = null;
		SubMenu subMenuNode = null;
		MenuItem menuItemNode = null;
		for (TreeNode menuNode : root.getChildren()) {
			subMenuValueList = new ArrayList<SubMenuValue>();
			for (TreeNode subNode : menuNode.getChildren()) {
				menuItemValueList = new ArrayList<MenuItemValue>();
				for (TreeNode itemNode : subNode.getChildren()) {
					if (itemNode.isSelected()) {
						menuItemNode = (MenuItem) itemNode.getData();
						menuItemValue = new MenuItemValue();
						menuItemValue.setMenuItem(menuItemNode);
						menuItemValueList.add(menuItemValue);
					}
				}

				if (!menuItemValueList.isEmpty() || subNode.isSelected()) {
					subMenuNode = (SubMenu) subNode.getData();
					subMenuValue = new SubMenuValue();
					subMenuValue.setSubMenu(subMenuNode);
					subMenuValue.setMenuItemValueList(menuItemValueList);
					subMenuValueList.add(subMenuValue);
				}
			}

			if (!subMenuValueList.isEmpty() || menuNode.isSelected()) {
				mainMenu = (MainMenu) menuNode.getData();
				mainMenuValue = new MainMenuValue();
				mainMenuValue.setMainMenu(mainMenu);
				mainMenuValue.setSubMenuValueList(subMenuValueList);
				mainMenuValueList.add(mainMenuValue);
			}
		}

		role.setMainMenuValueList(mainMenuValueList);
	}

	public void addNewRole() {
		try {
			loadMainMenuValueList();
			roleService.addNewRole(role);
			roleList.add(new ROL001(role));
			addInfoMessage(null, MessageId.INSERT_SUCCESS, role.getName());
			createNewRole();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateRole() {
		try {
			loadMainMenuValueList();
			roleService.updateRole(role);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, role.getName());
			createNewRole();
			loadRole();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void deleteRole(ROL001 rol001) {
		try {
			role = roleService.findRoleById(rol001.getId());
			roleService.deleteRole(role);
			roleList.remove(rol001);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, role.getName());
			createNewRole();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public List<ROL001> getRoleList() {
		return roleList;
	}

	public Role getRole() {
		return role;
	}

	public TreeNode getRoot() {
		return root;
	}

	public TreeNode[] getSelectedNodes() {
		return selectedNodes;
	}

	public void setSelectedNodes(TreeNode[] selectedNodes) {
		this.selectedNodes = selectedNodes;
	}

}
