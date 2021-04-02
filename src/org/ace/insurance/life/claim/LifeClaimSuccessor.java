package org.ace.insurance.life.claim;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.common.interfaces.IEntity;
import org.ace.insurance.system.common.relationship.RelationShip;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */

@Entity
@DiscriminatorValue(value = LifeClaimBeneficiaryRole.SUCCESSOR)
public class LifeClaimSuccessor extends LifeClaimBeneficiary implements Serializable, IEntity {

	private static final long serialVersionUID = 8970824422791183434L;

	@Column(name = "NAME")
	private String name;

	@Column(name = "NRCNO")
	private String nrcNo;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RELATIONSHIPID", referencedColumnName = "ID")
	private RelationShip relationShip;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLAIMSUCCESSOR_CLAIMID", referencedColumnName = "ID")
	private LifeClaim lifeClaim;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "claimSuccessor", orphanRemoval = true)
	private List<LifeClaimInsuredPersonBeneficiary> claimInsuredPersonBeneficiaryList;

	@Embedded
	private UserRecorder recorder;

	public LifeClaimSuccessor() {

	}

	public LifeClaimSuccessor(String name, String nrcNo, RelationShip relationShip) {
		super();
		this.name = name;
		this.nrcNo = nrcNo;
		this.relationShip = relationShip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNrcNo() {
		return nrcNo;
	}

	public void setNrcNo(String nrcNo) {
		this.nrcNo = nrcNo;
	}

	public RelationShip getRelationShip() {
		return relationShip;
	}

	public void setRelationShip(RelationShip relationShip) {
		this.relationShip = relationShip;
	}

	public List<LifeClaimInsuredPersonBeneficiary> getClaimInsuredPersonBeneficiaryList() {
		return new ArrayList();
	}

	public void setClaimInsuredPersonBeneficiaryList(List<LifeClaimInsuredPersonBeneficiary> claimInsuredPersonBeneficiaryList) {
		this.claimInsuredPersonBeneficiaryList = claimInsuredPersonBeneficiaryList;
	}

	public LifeClaim getLifeClaim() {
		return lifeClaim;
	}

	public void setLifeClaim(LifeClaim lifeClaim) {
		this.lifeClaim = lifeClaim;
	}

	public void addClaimInsuredPersonBeneficiary(LifeClaimInsuredPersonBeneficiary claimInsuredPersonBeneficiary) {
		if (claimInsuredPersonBeneficiaryList == null) {
			claimInsuredPersonBeneficiaryList = new ArrayList<LifeClaimInsuredPersonBeneficiary>();
		}

		boolean newEntity = true;

		claimInsuredPersonBeneficiary.setClaimSuccessor(this);
		if (!claimInsuredPersonBeneficiary.isNew()) {
			for (LifeClaimInsuredPersonBeneficiary cipb : claimInsuredPersonBeneficiaryList) {
				if (!cipb.isNew()) {
					if (cipb.getId().equals(claimInsuredPersonBeneficiary.getId())) {
						// this.totalPercentage = this.totalPercentage -
						// cipb.getPolicyInsuredPersonBeneficiaries().getPercentage();
						// cipb.update(claimInsuredPersonBeneficiary);
						// this.totalPercentage = this.totalPercentage +
						// claimInsuredPersonBeneficiary.getPolicyInsuredPersonBeneficiaries().getPercentage();
						newEntity = false;
					}

				}
			}
		}
		if (newEntity) {
			// this.totalPercentage = this.totalPercentage +
			// claimInsuredPersonBeneficiary.getPolicyInsuredPersonBeneficiaries().getPercentage();
			claimInsuredPersonBeneficiaryList.add(claimInsuredPersonBeneficiary);
		}
	}

	public void removeClaimInsuredPersonBeneficiary(LifeClaimInsuredPersonBeneficiary claimInsuredPersonBeneficiary) {
		int removeIndex = -1;
		if (claimInsuredPersonBeneficiary.getId() != null) {
			for (int i = 0; i < claimInsuredPersonBeneficiaryList.size(); i++) {
				if (claimInsuredPersonBeneficiaryList.get(i).getId() != null) {
					if (claimInsuredPersonBeneficiaryList.get(i).getId().equals(claimInsuredPersonBeneficiary.getId())) {
						removeIndex = i;
					}
				}
			}
		}

		if (removeIndex > -1) {
			claimInsuredPersonBeneficiary.setClaimSuccessor(null);
			claimInsuredPersonBeneficiaryList.remove(removeIndex);
		}
	}

	public boolean hasThisPersonAsClaimInsuredPersonBeneficiary(LifeClaimInsuredPersonBeneficiary claimInsuredPersonBeneficiary) {
		boolean result = false;
		if (claimInsuredPersonBeneficiary.getId() != null) {
			for (LifeClaimBeneficiary cb : claimInsuredPersonBeneficiaryList) {
				if (cb.getId() != null) {
					if (cb.getId().equals(claimInsuredPersonBeneficiary.getId())) {
						result = true;
					}
				}
			}
		} else {
			if (claimInsuredPersonBeneficiaryList != null) {
				result = claimInsuredPersonBeneficiaryList.contains(claimInsuredPersonBeneficiary);
			}
		}

		return result;
	}

	@Override
	public String getFullName() {
		return name;
	}

	@Override
	public String getFullResidentAddress() {
		return new String("");
	}

	@Override
	public String getIdNo() {
		return this.nrcNo;
	}

	@Override
	public String getFatherName() {
		return new String("");
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((nrcNo == null) ? 0 : nrcNo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LifeClaimSuccessor other = (LifeClaimSuccessor) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nrcNo == null) {
			if (other.nrcNo != null)
				return false;
		} else if (!nrcNo.equals(other.nrcNo))
			return false;
		return true;
	}

}
