package org.ace.insurance.life.claim;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.disabilitypart.DisabilityPart;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.LIFECLAIM_DISABILITYPART_LINK)
@TableGenerator(name = "LIFECLAIM_DISABILITYPART_LINK_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LIFECLAIM_DISABILITYPART_LINK_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class DisabilityLifeClaimPartLink {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LIFECLAIM_DISABILITYPART_LINK_GEN")
	private String id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DISABILITYPARTID", referencedColumnName = "ID")
	private DisabilityPart disabilityPart;

	private double percentage;
	@Transient
	private double previourPercentage;

	private double termDisabilityAmount;

	private double disabilityAmount;

	private String causeofdisability;
	private String causeofPropose;

	@Embedded
	private UserRecorder recorder;

	public DisabilityLifeClaimPartLink() {
	}

	public DisabilityLifeClaimPartLink(DisabilityPart disabilityPart, double previourPercentage) {
		super();
		this.disabilityPart = disabilityPart;
		this.previourPercentage = previourPercentage;
		this.percentage = previourPercentage;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DisabilityPart getDisabilityPart() {
		return disabilityPart;
	}

	public void setDisabilityPart(DisabilityPart disabilityPart) {
		this.disabilityPart = disabilityPart;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public double getPreviourPercentage() {
		return previourPercentage;
	}

	public void setPreviourPercentage(double previourPercentage) {
		this.previourPercentage = previourPercentage;
	}

	public double getTermDisabilityAmount() {
		return termDisabilityAmount;
	}

	public void setTermDisabilityAmount(double termDisabilityAmount) {
		this.termDisabilityAmount = termDisabilityAmount;
	}

	public double getDisabilityAmount() {
		return disabilityAmount;
	}

	public void setDisabilityAmount(double disabilityAmount) {
		this.disabilityAmount = disabilityAmount;
	}

	public String getCauseofdisability() {
		return causeofdisability;
	}

	public void setCauseofdisability(String causeofdisability) {
		this.causeofdisability = causeofdisability;
	}

	public String getCauseofPropose() {
		return causeofPropose;
	}

	public void setCauseofPropose(String causeofPropose) {
		this.causeofPropose = causeofPropose;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

}
