package org.ace.insurance.productContent.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.productContent.ProductContent;
import org.ace.insurance.productContent.persistence.interfaces.IProductContentDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("ProductContentDAO")
public class ProductContentDAO extends BasicDAO implements IProductContentDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProductContent> findAll() {
		List<ProductContent> result = null;
		try {
			Query q = em.createNamedQuery("ProductContent.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of ProductContent", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewProductContent(ProductContent productContent) {
		try {
			em.persist(productContent);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to add New ProductContent", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateProductContent(ProductContent productContent) {
		try {
			em.merge(productContent);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update ProductContent", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteProductContent(ProductContent productContent) {
		try {
			productContent = em.find(ProductContent.class, productContent.getId());
			em.remove(productContent);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to delete ProductContent", pe);
		}
	}

}
