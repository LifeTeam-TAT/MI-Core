package org.ace.java.component.idgen.persistence.interfaces;

import org.ace.insurance.system.common.branch.Branch;
import org.ace.java.component.idgen.IDGen;
import org.ace.java.component.persistence.exception.DAOException;

public interface IDGenDAOInf {
	public IDGen getNextId(String genName) throws DAOException;

	public IDGen getNextId(String generateItem, Branch branch) throws DAOException;

	public IDGen getIDGen(String generateItem) throws DAOException;

	public IDGen updateIDGen(IDGen idGen) throws DAOException;

	public IDGen getIDGenForAutoRenewal(String genName) throws DAOException;

	public IDGen getCustomNextNo(String generateItem, String productId) throws DAOException;

	public IDGen getCustomNextNoByBranchId(String generateItem, String branchId);
	

	IDGen getNextId(String generateItem, boolean isIgnoreBranch) throws DAOException;

	IDGen getNextId(String generateItem, Branch branch, boolean isIgnoreBranch) throws DAOException;

}
