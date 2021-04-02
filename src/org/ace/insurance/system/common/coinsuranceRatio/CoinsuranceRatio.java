package org.ace.insurance.system.common.coinsuranceRatio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.product.ProductGroup;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.COINSURANCE_RATIO)
@TableGenerator(name = "COINSURANCE_RATIO_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "COINSURANCE_RATIO_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class CoinsuranceRatio implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "COINSURANCE_RATIO_GEN")
	private String id;
	private int setNo;
	private double commission;

	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTGROUPID", referencedColumnName = "ID")
	private ProductGroup productGroup;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "COINSURANCERATIOID", referencedColumnName = "ID")
	private List<CoinsuranceDetailRatio> coinsuranceDetailRatioList;

	@Embedded
	private UserRecorder recorder;

	@Version
	private int version;

	public CoinsuranceRatio() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSetNo() {
		return setNo;
	}

	public void setSetNo(int setNo) {
		this.setNo = setNo;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public ProductGroup getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(ProductGroup productGroup) {
		this.productGroup = productGroup;
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

	public List<CoinsuranceDetailRatio> getCoinsuranceDetailRatioList() {
		if (coinsuranceDetailRatioList == null) {
			coinsuranceDetailRatioList = new ArrayList<CoinsuranceDetailRatio>();
		}
		return coinsuranceDetailRatioList;
	}

	public List<CoinsuranceDetailRatio> getSortedCoinsuranceDetialRatioList() {
		Collections.sort(getCoinsuranceDetailRatioList(), new Comparator<CoinsuranceDetailRatio>() {
			@Override
			public int compare(CoinsuranceDetailRatio detailRatio1, CoinsuranceDetailRatio detailRatio2) {
				Double percent1 = detailRatio1.getPercentage();
				Double percent2 = detailRatio2.getPercentage();
				return percent1.compareTo(percent2);
			}
		});
		return coinsuranceDetailRatioList;
	}

	public void setCoinsuranceDetailRatioList(List<CoinsuranceDetailRatio> coinsuranceDetailRatioList) {
		this.coinsuranceDetailRatioList = coinsuranceDetailRatioList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coinsuranceDetailRatioList == null) ? 0 : coinsuranceDetailRatioList.hashCode());
		long temp;
		temp = Double.doubleToLongBits(commission);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((productGroup == null) ? 0 : productGroup.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + setNo;
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
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
		CoinsuranceRatio other = (CoinsuranceRatio) obj;
		if (coinsuranceDetailRatioList == null) {
			if (other.coinsuranceDetailRatioList != null)
				return false;
		} else if (!coinsuranceDetailRatioList.equals(other.coinsuranceDetailRatioList))
			return false;
		if (Double.doubleToLongBits(commission) != Double.doubleToLongBits(other.commission))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (productGroup == null) {
			if (other.productGroup != null)
				return false;
		} else if (!productGroup.equals(other.productGroup))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (setNo != other.setNo)
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
