package org.ace.insurance.menu.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.menu.MainMenu;
import org.ace.insurance.menu.MainMenuValue;
import org.ace.insurance.menu.MenuItem;
import org.ace.insurance.menu.MenuItemValue;
import org.ace.insurance.menu.SubMenu;
import org.ace.insurance.menu.SubMenuValue;
import org.ace.insurance.menu.persistence.interfaces.IMenuDAO;
import org.ace.insurance.menu.service.interfaces.IMenuService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "MenuService")
public class MenuService implements IMenuService {

	@Resource(name = "MenuDAO")
	private IMenuDAO menuDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewMainMenu(MainMenu mainMenu) throws SystemException {
		try {
			menuDAO.insert(mainMenu);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new MainMenu", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateMainMenu(MainMenu mainMenu) throws SystemException {
		try {
			menuDAO.update(mainMenu);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a MainMenu", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteMainMenu(MainMenu mainMenu) throws SystemException {
		try {
			List<MainMenuValue> obList = menuDAO.findMainMenuValueByMainMenuId(mainMenu.getId());
			for (MainMenuValue value : obList) {
				menuDAO.delete(value);
			}
			menuDAO.delete(mainMenu);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a MainMenu", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewSubMenu(SubMenu subMenu) throws SystemException {
		try {
			menuDAO.insert(subMenu);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new SubMenu", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateSubMenu(SubMenu subMenu) throws SystemException {
		try {
			menuDAO.update(subMenu);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a SubMenu", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteSubMenu(SubMenu subMenu) throws SystemException {
		try {
			List<SubMenuValue> obList = menuDAO.findSubMenuValueBySubMenuId(subMenu.getId());
			for (SubMenuValue value : obList) {
				menuDAO.delete(value);
			}
			menuDAO.delete(subMenu);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a SubMenu", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewMenuItem(MenuItem menuItem) throws SystemException {
		try {
			menuDAO.insert(menuItem);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new MenuItem", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateMenuItem(MenuItem menuItem) throws SystemException {
		try {
			menuDAO.update(menuItem);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a MenuItem", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteMenuItem(MenuItem menuItem) throws SystemException {
		try {
			List<MenuItemValue> obList = menuDAO.findMenuItemValueByMenuItemId(menuItem.getId());
			for (MenuItemValue value : obList) {
				menuDAO.delete(value);
			}
			menuDAO.delete(menuItem);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a MenuItem", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<MainMenu> findAllMainMenu() throws SystemException {
		List<MainMenu> result = null;
		try {
			result = menuDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of MainMenu)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public MainMenu findMainMenuById(String id) throws SystemException {
		MainMenu result = null;
		try {
			result = menuDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a MainMenu (ID : " + id + ")", e);
		}
		return result;
	}

}
