package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.product.Product;
import org.ace.insurance.product.service.interfaces.IProductService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "ProductDialogActionBean")
@ViewScoped
public class ProductDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{ProductService}")
	private IProductService productService;

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	private List<Product> productList = new ArrayList<Product>();

	@PostConstruct
	public void init() {
		if (isExistParam("INSURANCETYPE")) {
			InsuranceType insuType = (InsuranceType) getParam("INSURANCETYPE");
			productList = productService.findProductByInsuranceType(insuType);
		} else {
			productList = productService.findProductByInsuranceType(InsuranceType.STUDENT_LIFE);
		}
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void selectProduct(Product product) {
		RequestContext.getCurrentInstance().closeDialog(product);
	}
}
