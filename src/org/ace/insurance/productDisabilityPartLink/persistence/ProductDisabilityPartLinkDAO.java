package org.ace.insurance.productDisabilityPartLink.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.productDisabilityPartLink.ProductDisabilityPart;
import org.ace.insurance.productDisabilityPartLink.ProductDisabilityRate;
import org.ace.insurance.productDisabilityPartLink.persistence.interfaces.IProductDisabilityPartLinkDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("ProductDisabilityPartLinkDAO")
public class ProductDisabilityPartLinkDAO extends BasicDAO implements IProductDisabilityPartLinkDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProductDisabilityPart> findAll() {
		List<ProductDisabilityPart> result;
		StringBuilder builder = new StringBuilder();
		try {
			builder.append("Select p from ProductDisabilityPart p");
			Query q = em.createQuery(builder.toString());
			result = q.getResultList();
		} catch (PersistenceException e) {
			throw translate("Faield to Find All Product Disability Part Link", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewProductDisabilityPart(ProductDisabilityPart productDisabilityPartLink) {
		try {
			em.persist(productDisabilityPartLink);
			em.flush();
		} catch (PersistenceException e) {
			throw translate("Faield to add a new Product_DisabilityPart", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateProductDisabilityPart(ProductDisabilityPart productDisabilityPartLink) {
		try {
			em.merge(productDisabilityPartLink);
			em.flush();
		} catch (PersistenceException e) {
			throw translate("Faield to add a new Product_DisabilityPart", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteProductDisabilityPart(ProductDisabilityPart productDisabilityPartLink) {
		try {
			productDisabilityPartLink = em.merge(productDisabilityPartLink);
			em.remove(productDisabilityPartLink);
			em.flush();
		} catch (PersistenceException e) {
			throw translate("Faield to add a new Product_DisabilityPart", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ProductDisabilityPart findById(String id) {
		ProductDisabilityPart disabilityPart = null;
		try {
			Query q = em.createQuery("Select p from ProductDisabilityPart p where p.id=:id");
			q.setParameter("id", id);
			disabilityPart = (ProductDisabilityPart) q.getSingleResult();
		} catch (PersistenceException e) {
			throw translate("Faield to find Product_DisabilityPart By Id", e);
		}
		return disabilityPart;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProductDisabilityRate> findAllDisabilityRateByProduct(String produtId) {
		List<ProductDisabilityRate> result = null;
		try {
			Query q = em.createQuery("SELECT r FROM ProductDisabilityPart p JOIN p.disabilityRateList r WHERE p.product.id = :productId");
			q.setParameter("productId", produtId);
			result = q.getResultList();
		} catch (PersistenceException e) {
			throw translate("Faield to find All Disability Rate By Product", e);
		}
		return result;
	}

}
