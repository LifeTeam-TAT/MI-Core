/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.claimproduct;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.CLAIMPRODUCTRATE)
@TableGenerator(name = "CLAIMPRODUCTRATE_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "CLAIMPRODUCTRATE_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "ClaimProductRate.findAll", query = "SELECT p FROM ClaimProductRate p "),
		@NamedQuery(name = "ClaimProductRate.findById", query = "SELECT p FROM ClaimProductRate p WHERE p.id = :id"),
		@NamedQuery(name = "ClaimProductRate.findByClaimProductId", query = "SELECT p FROM ClaimProductRate p WHERE p.claimProduct.id = :productId"),
		@NamedQuery(name = "ClaimProductRate.deleteById", query = "DELETE FROM ClaimProductRate p WHERE p.id = :id") })
@EntityListeners(IDInterceptor.class)
public class ClaimProductRate implements Serializable, Comparable<ClaimProductRate> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "CLAIMPRODUCTRATE_GEN")
	private String id;

	private double rate;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "CLAIMPRODUCTRATEID", referencedColumnName = "ID")
	private List<ClaimProductRateKeyFactor> claimProductRateKeyFactors;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLAIMPRODUCTID", referencedColumnName = "ID")
	private ClaimProduct claimProduct;

	@Embedded
	private UserRecorder recorder;

	@Version
	private int version;

	public ClaimProductRate() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public List<ClaimProductRateKeyFactor> getClaimProductRateKeyFactors() {
		return claimProductRateKeyFactors;
	}

	public void setClaimProductRateKeyFactors(List<ClaimProductRateKeyFactor> claimProductRateKeyFactors) {
		this.claimProductRateKeyFactors = claimProductRateKeyFactors;
	}

	public void addClaimProductRateKeyFactor(ClaimProductRateKeyFactor cprkf) {
		if (this.claimProductRateKeyFactors == null)
			this.claimProductRateKeyFactors = new ArrayList<ClaimProductRateKeyFactor>();
		this.claimProductRateKeyFactors.add(cprkf);
	}

	public ClaimProduct getClaimProduct() {
		return claimProduct;
	}

	public void setClaimProduct(ClaimProduct claimProduct) {
		this.claimProduct = claimProduct;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((claimProduct == null) ? 0 : claimProduct.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		long temp;
		temp = Double.doubleToLongBits(rate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + version;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClaimProductRate other = (ClaimProductRate) obj;
		if (claimProduct == null) {
			if (other.claimProduct != null)
				return false;
		} else if (!claimProduct.equals(other.claimProduct))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(rate) != Double.doubleToLongBits(other.rate))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	@Override
	public int compareTo(ClaimProductRate o) {
		if (this.getRate() > o.getRate()) {
			return 1;
		} else {
			return -1;
		}
	}

	public ClaimProductRateKeyFactor getClaimRateKFbyKF(KeyFactor keyFactor) {
		for (ClaimProductRateKeyFactor cprkf : this.claimProductRateKeyFactors) {
			if (cprkf.getKeyFactor().equals(keyFactor))
				return cprkf;
		}
		return null;
	}

}