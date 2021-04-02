package org.ace.insurance.productDisabilityPartLink;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.product.Product;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.PRODUCT_DISABILITYPART)
@TableGenerator(name = "PRODUCTDISABILITYPART_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "PRODUCTDISABILITYPART_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class ProductDisabilityPart implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "PRODUCTDISABILITYPART_GEN")
	private String id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTID", referencedColumnName = "ID")
	private Product product;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "PRODUCTDISABILITYID", referencedColumnName = "ID")
	private List<ProductDisabilityRate> disabilityRateList;

	@Embedded
	private UserRecorder recorder;

	@Version
	private int version;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<ProductDisabilityRate> getDisabilityRateList() {
		return disabilityRateList;
	}

	public void setDisabilityRateList(List<ProductDisabilityRate> disabilityRateList) {
		this.disabilityRateList = disabilityRateList;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}
