package org.ace.insurance.web.common.document.life;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ace.insurance.common.Utils;
import org.ace.insurance.common.utils.DateUtils;
import org.ace.insurance.life.endorsement.InsuredPersonEndorseStatus;
import org.ace.insurance.life.endorsement.LifeEndorseInfo;
import org.ace.insurance.life.endorsement.LifeEndorseInsuredPerson;
import org.ace.insurance.life.factory.PolicyInsuredPersonFactory;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.PolicyInsuredPersonDTO;
import org.ace.insurance.life.policyHistory.LifePolicyHistory;
import org.ace.insurance.life.policyHistory.PolicyInsuredPersonHistory;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.insurance.web.common.document.JasperFactory;
import org.ace.insurance.web.common.document.JasperTemplate;
import org.ace.insurance.web.common.myanmarLanguae.MyanmarLanguae;
import org.ace.java.component.persistence.BasicDAO;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class LifeEndorsementDocumentBuilder extends BasicDAO {

	public static List<JasperPrint> EndorseChangesLetters(LifePolicy lifePolicy, LifePolicyHistory lifePolicyHistory, LifeEndorseInfo lifeEndorseInfo) {
		List<JasperPrint> jpList = new ArrayList<JasperPrint>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> totalIncreInsuPerson_Map = new HashMap<String, Object>();
		Map<String, Object> endorseInsuPerson_Map = new HashMap<String, Object>();
		Map<String, Object> underwritingInsuPerson_Map = new HashMap<String, Object>();
		JasperPrint jprint = null;
		JasperPrint jprint1 = null;
		List<String> newInsuPersonCodeNoList = new ArrayList<>();
		List<String> replaceInsuPersonCodeNoList = new ArrayList<>();
		List<String> nrcNoChangePersonCodeList = new ArrayList<String>();
		// List<String> deleteInsuPersonCodeNoList = new ArrayList<>();
		List<String> termChangeInsuPersonCodeNoList = new ArrayList<>();

		boolean isShortTermLife = KeyFactorChecker.isShortTermEndowment(lifePolicy.getPolicyInsuredPersonList().get(0).getProduct().getId());
		boolean isGroupLife = KeyFactorChecker.isGroupLife(lifePolicy.getPolicyInsuredPersonList().get(0).getProduct());
		String titleName = isShortTermLife ? MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_1")
				: isGroupLife ? MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_2") : "";
		String issueDate = MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_3") + DateUtils.getYearWithMyanmarLanguage(lifePolicy.getIssueDate())
				+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_4") + DateUtils.getMonthWithMyanmarLanguage(lifePolicy.getIssueDate())
				+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_5") + DateUtils.getDayWithMyanmarLanguage(lifePolicy.getIssueDate())
				+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_6");
		String endorseInfo = "";

		for (LifeEndorseInsuredPerson result : lifeEndorseInfo.getLifeEndorseInsuredPersonInfoList()) {
			if (result.getInsuredPersonEndorseStatus().equals(InsuredPersonEndorseStatus.NEW))
				newInsuPersonCodeNoList.add(result.getInsuredPersonCodeNo());
			else if (result.getInsuredPersonEndorseStatus().equals(InsuredPersonEndorseStatus.REPLACE)) {
				replaceInsuPersonCodeNoList.add(result.getInsuredPersonCodeNo());
				// deleteInsuPersonCodeNoList.add(result.getInsuredPersonCodeNo());
			} else if (result.getInsuredPersonEndorseStatus().equals(InsuredPersonEndorseStatus.NRCNO_CHANGE)) {
				nrcNoChangePersonCodeList.add(result.getInsuredPersonCodeNo());
			} else if (result.getInsuredPersonEndorseStatus().equals(InsuredPersonEndorseStatus.TERM_CHANGE)) {
				termChangeInsuPersonCodeNoList.add(result.getInsuredPersonCodeNo());
			}
		}

		// New Insured Person List
		if (newInsuPersonCodeNoList.size() > 0 & replaceInsuPersonCodeNoList.isEmpty()) {
			paramMap = new HashMap<String, Object>();
			List<PolicyInsuredPersonDTO> increasedDTOList = new ArrayList<PolicyInsuredPersonDTO>();
			List<PolicyInsuredPersonDTO> totalInsuDTOList = new ArrayList<PolicyInsuredPersonDTO>();
			List<PolicyInsuredPersonDTO> oldInsuDTOList = new ArrayList<PolicyInsuredPersonDTO>();

			// get increased List By InsuredPersonCodeNo & Change to DTO
			for (PolicyInsuredPerson person : lifePolicy.getInsuredPersonInfo()) {
				totalInsuDTOList.add(PolicyInsuredPersonFactory.createPolicyInsuredPersonDTO(person));
				if (newInsuPersonCodeNoList.contains(person.getInsPersonCodeNo()))
					increasedDTOList.add(PolicyInsuredPersonFactory.createPolicyInsuredPersonDTO(person));
			}
			for (PolicyInsuredPersonHistory person : lifePolicyHistory.getInsuredPersonInfo()) {
				oldInsuDTOList.add(PolicyInsuredPersonFactory.createPolicyInsuredPersonDTO(person));
			}

			int totalInsuSize = totalInsuDTOList.size();
			int newInsuSize = newInsuPersonCodeNoList.size();
			int oldInsuSize = totalInsuSize - newInsuSize;
			String description = "			" + MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_7") + lifePolicy.getPolicyNo() + " "
					+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_8") + oldInsuSize
					+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_9") + newInsuSize
					+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_10") + totalInsuSize
					+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_11") + Utils.formattedDate(lifePolicy.getActivedPolicyStartDate()) + " "
					+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_12") + Utils.formattedDate(lifePolicy.getActivedPolicyEndDate()) + " "
					+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_13");
			;
			paramMap.put("titleName", titleName);
			paramMap.put("lifeEndorseInfo", MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_14"));
			paramMap.put("issueDate", issueDate);
			paramMap.put("inFormDate", Utils.formattedDate(lifePolicy.getLifeProposal().getSubmittedDate()));
			paramMap.put("endorseNo", lifePolicy.getEndorsementNo());
			paramMap.put("policyNo", lifePolicy.getPolicyNo());
			paramMap.put("customerName", lifePolicy.getCustomerName());
			paramMap.put("description", description);
			jprint1 = JasperFactory.generateJasperPrint(paramMap, JasperTemplate.ENDORSE_ISSUE_LETTER, new JREmptyDataSource());
			jpList.add(jprint1);

			endorseInfo = "Policy- " + lifePolicy.getPolicyNo() + " " + MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_15");
			endorseInsuPerson_Map.put("titleName", titleName);
			endorseInsuPerson_Map.put("endorseInfoTitle", endorseInfo);
			endorseInsuPerson_Map.put("listDataSource", new JRBeanCollectionDataSource(getSortedInsuNameList(increasedDTOList)));
			jprint = JasperFactory.generateJasperPrint(endorseInsuPerson_Map, JasperTemplate.ALL_INSURANCE_PERSONS_LETTER, new JREmptyDataSource());
			jpList.add(jprint);

			endorseInfo = "Policy- " + lifePolicy.getPolicyNo() + " " + MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_16");
			totalIncreInsuPerson_Map.put("titleName", titleName);
			totalIncreInsuPerson_Map.put("endorseInfoTitle", endorseInfo);
			totalIncreInsuPerson_Map.put("listDataSource", new JRBeanCollectionDataSource(getSortedInsuNameList(totalInsuDTOList)));
			jprint = JasperFactory.generateJasperPrint(totalIncreInsuPerson_Map, JasperTemplate.ALL_INSURANCE_PERSONS_LETTER, new JREmptyDataSource());
			jpList.add(jprint);

			endorseInfo = "Policy- " + lifePolicy.getPolicyNo() + " " + MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_17");
			underwritingInsuPerson_Map.put("titleName", titleName);
			underwritingInsuPerson_Map.put("endorseInfoTitle", endorseInfo);
			underwritingInsuPerson_Map.put("listDataSource", new JRBeanCollectionDataSource(getSortedInsuNameList(oldInsuDTOList)));
			jprint = JasperFactory.generateJasperPrint(underwritingInsuPerson_Map, JasperTemplate.ALL_INSURANCE_PERSONS_LETTER, new JREmptyDataSource());
			jpList.add(jprint);
		}

		// Replace Insured Person List
		if (replaceInsuPersonCodeNoList.size() > 0 & newInsuPersonCodeNoList.isEmpty()) {
			paramMap = new HashMap<String, Object>();
			List<PolicyInsuredPersonDTO> replaceDTOList = new ArrayList<PolicyInsuredPersonDTO>();
			List<PolicyInsuredPersonDTO> deletePerDTOList = new ArrayList<PolicyInsuredPersonDTO>();
			List<PolicyInsuredPersonDTO> totalInsuDTOList = new ArrayList<PolicyInsuredPersonDTO>();
			List<PolicyInsuredPersonDTO> oldInsuDTOList = new ArrayList<PolicyInsuredPersonDTO>();
			for (PolicyInsuredPerson person : lifePolicy.getInsuredPersonInfo()) {
				totalInsuDTOList.add(PolicyInsuredPersonFactory.createPolicyInsuredPersonDTO(person));
				if (replaceInsuPersonCodeNoList.contains(person.getInsPersonCodeNo()))
					replaceDTOList.add(PolicyInsuredPersonFactory.createPolicyInsuredPersonDTO(person));
			}
			for (PolicyInsuredPersonHistory personHistory : lifePolicyHistory.getPolicyInsuredPersonList()) {
				oldInsuDTOList.add(PolicyInsuredPersonFactory.createPolicyInsuredPersonDTO(personHistory));
				// if
				// (deleteInsuPersonCodeNoList.contains(personHistory.getInPersonCodeNo()))
				// {
				// deletePerDTOList.add(PolicyInsuredPersonFactory.createPolicyInsuredPersonDTO(personHistory));
				// }
			}
			int totalInsuSize = totalInsuDTOList.size();
			int replaceInsuSize = replaceDTOList.size();
			String description = "			" + MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_18") + lifePolicy.getPolicyNo() + " "
					+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_19") + totalInsuSize
					+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_20") + replaceInsuSize
					+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_21") + replaceInsuSize
					+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_22") + Utils.formattedDate(lifePolicy.getEndorsementConfirmDate())
					+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_23") + Utils.formattedDate(lifePolicy.getActivedPolicyStartDate()) + " "
					+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_24") + Utils.formattedDate(lifePolicy.getActivedPolicyEndDate()) + " "
					+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_25");
			paramMap.put("titleName", titleName);
			paramMap.put("lifeEndorseInfo", MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_26"));
			paramMap.put("issueDate", issueDate);
			paramMap.put("inFormDate", Utils.formattedDate(lifePolicy.getLifeProposal().getSubmittedDate()));
			paramMap.put("endorseNo", lifePolicy.getEndorsementNo());
			paramMap.put("policyNo", lifePolicy.getPolicyNo());
			paramMap.put("customerName", lifePolicy.getCustomerName());
			paramMap.put("description", description);
			jprint1 = JasperFactory.generateJasperPrint(paramMap, JasperTemplate.ENDORSE_ISSUE_LETTER, new JREmptyDataSource());
			jpList.add(jprint1);

			// Map<String, Object> deleteInsuPerson_Map = new HashMap<>();
			// deleteInsuPerson_Map.put("policyNo", lifePolicy.getPolicyNo());
			// deleteInsuPerson_Map.put("listDataSource", new
			// JRBeanCollectionDataSource(deletePerDTOList));
			// jprint2 = JasperFactory.generateJasperPrint(deleteInsuPerson_Map,
			// JasperTemplate.GP_LIFE_ENDORSE_PERSON_DELETE, new
			// JREmptyDataSource());
			// jpList.add(jprint2);

			endorseInfo = "Policy- " + lifePolicy.getPolicyNo() + " " + MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_37");
			endorseInsuPerson_Map.put("titleName", titleName);
			endorseInsuPerson_Map.put("endorseInfoTitle", endorseInfo);
			endorseInsuPerson_Map.put("listDataSource", new JRBeanCollectionDataSource(getSortedInsuNameList(replaceDTOList)));
			jprint = JasperFactory.generateJasperPrint(endorseInsuPerson_Map, JasperTemplate.ALL_INSURANCE_PERSONS_LETTER, new JREmptyDataSource());
			jpList.add(jprint);

			endorseInfo = "Policy- " + lifePolicy.getPolicyNo() + " " + MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_16");
			totalIncreInsuPerson_Map.put("titleName", titleName);
			totalIncreInsuPerson_Map.put("endorseInfoTitle", endorseInfo);
			totalIncreInsuPerson_Map.put("listDataSource", new JRBeanCollectionDataSource(getSortedInsuNameList(totalInsuDTOList)));
			jprint = JasperFactory.generateJasperPrint(totalIncreInsuPerson_Map, JasperTemplate.ALL_INSURANCE_PERSONS_LETTER, new JREmptyDataSource());
			jpList.add(jprint);

			endorseInfo = "Policy- " + lifePolicy.getPolicyNo() + " " + MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_17");
			underwritingInsuPerson_Map.put("titleName", titleName);
			underwritingInsuPerson_Map.put("endorseInfoTitle", endorseInfo);
			underwritingInsuPerson_Map.put("listDataSource", new JRBeanCollectionDataSource(getSortedInsuNameList(oldInsuDTOList)));
			jprint = JasperFactory.generateJasperPrint(underwritingInsuPerson_Map, JasperTemplate.ALL_INSURANCE_PERSONS_LETTER, new JREmptyDataSource());
			jpList.add(jprint);
		}

		// Replace and Add New Insurance Person
		if (newInsuPersonCodeNoList.size() > 0 & replaceInsuPersonCodeNoList.size() > 0) {
			paramMap = new HashMap<String, Object>();
			List<PolicyInsuredPersonDTO> replaceDTOList = new ArrayList<PolicyInsuredPersonDTO>();
			List<PolicyInsuredPersonDTO> totalInsuDTOList = new ArrayList<PolicyInsuredPersonDTO>();
			List<PolicyInsuredPersonDTO> increasedDTOList = new ArrayList<PolicyInsuredPersonDTO>();
			List<PolicyInsuredPersonDTO> oldDTOList = new ArrayList<PolicyInsuredPersonDTO>();
			for (PolicyInsuredPerson person : lifePolicy.getInsuredPersonInfo()) {
				totalInsuDTOList.add(PolicyInsuredPersonFactory.createPolicyInsuredPersonDTO(person));
				if (replaceInsuPersonCodeNoList.contains(person.getInsPersonCodeNo()))
					replaceDTOList.add(PolicyInsuredPersonFactory.createPolicyInsuredPersonDTO(person));
			}
			for (PolicyInsuredPersonHistory personHistory : lifePolicyHistory.getPolicyInsuredPersonList()) {
				oldDTOList.add(PolicyInsuredPersonFactory.createPolicyInsuredPersonDTO(personHistory));
			}

			for (PolicyInsuredPerson person : lifePolicy.getInsuredPersonInfo()) {
				if (newInsuPersonCodeNoList.contains(person.getInsPersonCodeNo()))
					increasedDTOList.add(PolicyInsuredPersonFactory.createPolicyInsuredPersonDTO(person));
			}

			int totalInsuSize = totalInsuDTOList.size();
			int replaceInsuSize = replaceDTOList.size();
			int newInsuSize = newInsuPersonCodeNoList.size();
			int oldInsuSize = totalInsuSize - newInsuSize;
			String description = "			" + MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_27") + lifePolicy.getPolicyNo() + " "
					+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_28") + oldInsuSize
					+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_29") + replaceInsuSize
					+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_30") + replaceInsuSize
					+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_31") + newInsuSize
					+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_32") + totalInsuSize
					+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_33") + Utils.formattedDate(lifePolicy.getEndorsementConfirmDate())
					+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_34") + Utils.formattedDate(lifePolicy.getActivedPolicyStartDate()) + " "
					+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_35") + Utils.formattedDate(lifePolicy.getActivedPolicyEndDate()) + " "
					+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_13");
			paramMap.put("titleName", titleName);
			paramMap.put("lifeEndorseInfo", MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_36"));
			paramMap.put("issueDate", issueDate);
			paramMap.put("inFormDate", Utils.formattedDate(lifePolicy.getLifeProposal().getSubmittedDate()));
			paramMap.put("endorseNo", lifePolicy.getEndorsementNo());
			paramMap.put("policyNo", lifePolicy.getPolicyNo());
			paramMap.put("customerName", lifePolicy.getCustomerName());
			paramMap.put("description", description);
			jprint1 = JasperFactory.generateJasperPrint(paramMap, JasperTemplate.ENDORSE_ISSUE_LETTER, new JREmptyDataSource());
			jpList.add(jprint1);

			endorseInfo = "Policy- " + lifePolicy.getPolicyNo() + " " + MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_15");
			endorseInsuPerson_Map.put("titleName", titleName);
			endorseInsuPerson_Map.put("endorseInfoTitle", endorseInfo);
			endorseInsuPerson_Map.put("listDataSource", new JRBeanCollectionDataSource(getSortedInsuNameList(increasedDTOList)));
			jprint = JasperFactory.generateJasperPrint(endorseInsuPerson_Map, JasperTemplate.ALL_INSURANCE_PERSONS_LETTER, new JREmptyDataSource());
			jpList.add(jprint);

			endorseInfo = "Policy- " + lifePolicy.getPolicyNo() + " " + MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_37");
			endorseInsuPerson_Map.put("titleName", titleName);
			endorseInsuPerson_Map.put("endorseInfoTitle", endorseInfo);
			endorseInsuPerson_Map.put("listDataSource", new JRBeanCollectionDataSource(getSortedInsuNameList(replaceDTOList)));
			jprint = JasperFactory.generateJasperPrint(endorseInsuPerson_Map, JasperTemplate.ALL_INSURANCE_PERSONS_LETTER, new JREmptyDataSource());
			jpList.add(jprint);

			endorseInfo = "Policy- " + lifePolicy.getPolicyNo() + " " + MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_16");
			totalIncreInsuPerson_Map.put("titleName", titleName);
			totalIncreInsuPerson_Map.put("endorseInfoTitle", endorseInfo);
			totalIncreInsuPerson_Map.put("listDataSource", new JRBeanCollectionDataSource(getSortedInsuNameList(totalInsuDTOList)));
			jprint = JasperFactory.generateJasperPrint(totalIncreInsuPerson_Map, JasperTemplate.ALL_INSURANCE_PERSONS_LETTER, new JREmptyDataSource());
			jpList.add(jprint);

			endorseInfo = "Policy- " + lifePolicy.getPolicyNo() + " " + MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_17");
			underwritingInsuPerson_Map.put("titleName", titleName);
			underwritingInsuPerson_Map.put("endorseInfoTitle", endorseInfo);
			underwritingInsuPerson_Map.put("listDataSource", new JRBeanCollectionDataSource(getSortedInsuNameList(oldDTOList)));
			jprint = JasperFactory.generateJasperPrint(underwritingInsuPerson_Map, JasperTemplate.ALL_INSURANCE_PERSONS_LETTER, new JREmptyDataSource());
			jpList.add(jprint);
		}

		// Change NRC NO of Insurance Person
		if (nrcNoChangePersonCodeList.size() > 0) {
			paramMap = new HashMap<String, Object>();
			List<PolicyInsuredPersonDTO> newNrcNOInsuDTOList = new ArrayList<PolicyInsuredPersonDTO>();
			List<PolicyInsuredPersonDTO> totalInsuDTOList = new ArrayList<PolicyInsuredPersonDTO>();
			List<PolicyInsuredPersonDTO> oldInsuDTOList = new ArrayList<PolicyInsuredPersonDTO>();
			for (PolicyInsuredPerson person : lifePolicy.getInsuredPersonInfo()) {
				totalInsuDTOList.add(PolicyInsuredPersonFactory.createPolicyInsuredPersonDTO(person));
				if (nrcNoChangePersonCodeList.contains(person.getInsPersonCodeNo()))
					newNrcNOInsuDTOList.add(PolicyInsuredPersonFactory.createPolicyInsuredPersonDTO(person));
			}
			for (PolicyInsuredPersonHistory personHistory : lifePolicyHistory.getPolicyInsuredPersonList()) {
				oldInsuDTOList.add(PolicyInsuredPersonFactory.createPolicyInsuredPersonDTO(personHistory));
			}
			for (PolicyInsuredPersonDTO dto : newNrcNOInsuDTOList) {
				for (PolicyInsuredPersonDTO oldDto : oldInsuDTOList) {
					if (dto.getInsPersonCodeNo().equals(oldDto.getInsPersonCodeNo())) {
						jprint1 = new JasperPrint();
						String description = "			" + MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_38") + lifePolicy.getPolicyNo() + " "
								+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_39") + dto.getFullName() + " "
								+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_40") + oldDto.getFullIdNo() + " "
								+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_41") + dto.getFullIdNo() + " "
								+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_42") + Utils.formattedDate(lifePolicy.getEndorsementConfirmDate())
								+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_43") + Utils.formattedDate(lifePolicy.getActivedPolicyStartDate()) + " "
								+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_44") + Utils.formattedDate(lifePolicy.getActivedPolicyEndDate()) + " "
								+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_13");
						paramMap.put("titleName", titleName);
						paramMap.put("lifeEndorseInfo", MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_45"));
						paramMap.put("issueDate", issueDate);
						paramMap.put("inFormDate", Utils.formattedDate(lifePolicy.getLifeProposal().getSubmittedDate()));
						paramMap.put("endorseNo", lifePolicy.getEndorsementNo());
						paramMap.put("policyNo", lifePolicy.getPolicyNo());
						paramMap.put("customerName", lifePolicy.getCustomerName());
						paramMap.put("description", description);
						jprint1 = JasperFactory.generateJasperPrint(paramMap, JasperTemplate.ENDORSE_ISSUE_LETTER, new JREmptyDataSource());
						jpList.add(jprint1);
					}
				}
			}
			endorseInfo = "Policy- " + lifePolicy.getPolicyNo() + " " + MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_46");
			totalIncreInsuPerson_Map.put("titleName", titleName);
			totalIncreInsuPerson_Map.put("endorseInfoTitle", endorseInfo);
			totalIncreInsuPerson_Map.put("listDataSource", new JRBeanCollectionDataSource(getSortedInsuNameList(newNrcNOInsuDTOList)));
			jprint = JasperFactory.generateJasperPrint(totalIncreInsuPerson_Map, JasperTemplate.ALL_INSURANCE_PERSONS_LETTER, new JREmptyDataSource());
			jpList.add(jprint);

			endorseInfo = "Policy- " + lifePolicy.getPolicyNo() + " " + MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_17");
			totalIncreInsuPerson_Map.put("titleName", titleName);
			totalIncreInsuPerson_Map.put("endorseInfoTitle", endorseInfo);
			totalIncreInsuPerson_Map.put("listDataSource", new JRBeanCollectionDataSource(getSortedInsuNameList(oldInsuDTOList)));
			jprint = JasperFactory.generateJasperPrint(totalIncreInsuPerson_Map, JasperTemplate.ALL_INSURANCE_PERSONS_LETTER, new JREmptyDataSource());
			jpList.add(jprint);

			endorseInfo = "Policy- " + lifePolicy.getPolicyNo() + " " + MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_16");
			underwritingInsuPerson_Map.put("titleName", titleName);
			underwritingInsuPerson_Map.put("endorseInfoTitle", endorseInfo);
			underwritingInsuPerson_Map.put("listDataSource", new JRBeanCollectionDataSource(getSortedInsuNameList(totalInsuDTOList)));
			jprint = JasperFactory.generateJasperPrint(underwritingInsuPerson_Map, JasperTemplate.ALL_INSURANCE_PERSONS_LETTER, new JREmptyDataSource());
			jpList.add(jprint);

		}
		// Term Change Insurance Person
		if (termChangeInsuPersonCodeNoList.size() > 0) {
			List<PolicyInsuredPersonDTO> termChangeInsuDTOList = new ArrayList<PolicyInsuredPersonDTO>();
			List<PolicyInsuredPersonDTO> totalInsuDTOList = new ArrayList<PolicyInsuredPersonDTO>();
			List<PolicyInsuredPersonDTO> oldInsuDTOList = new ArrayList<PolicyInsuredPersonDTO>();
			for (PolicyInsuredPerson person : lifePolicy.getInsuredPersonInfo()) {
				totalInsuDTOList.add(PolicyInsuredPersonFactory.createPolicyInsuredPersonDTO(person));
				if (termChangeInsuPersonCodeNoList.contains(person.getInsPersonCodeNo()))
					termChangeInsuDTOList.add(PolicyInsuredPersonFactory.createPolicyInsuredPersonDTO(person));
			}
			for (PolicyInsuredPersonHistory personHistory : lifePolicyHistory.getPolicyInsuredPersonList()) {
				oldInsuDTOList.add(PolicyInsuredPersonFactory.createPolicyInsuredPersonDTO(personHistory));
			}
			String description = "			" + MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_47") + lifePolicy.getPolicyNo() + " "
					+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_48") + lifePolicyHistory.getPeriodOfInsurance()
					+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_49")
					+ Utils.getCurrencyFormatString(lifePolicyHistory.getPolicyInsuredPersonList().get(0).getBasicTermPremium())
					+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_50") + lifePolicy.getPeriodOfInsurance()
					+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_51")
					+ Utils.getCurrencyFormatString(lifePolicy.getPolicyInsuredPersonList().get(0).getBasicTermPremium())
					+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_52") + Utils.formattedDate(lifePolicy.getEndorsementConfirmDate())
					+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_53") + Utils.formattedDate(lifePolicy.getActivedPolicyStartDate()) + " "
					+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_54") + Utils.formattedDate(lifePolicy.getActivedPolicyEndDate()) + " "
					+ MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_55");
			paramMap.put("titleName", titleName);
			paramMap.put("lifeEndorseInfo", MyanmarLanguae.getMyanmarLanguaeString("LIFEENDORSEMENT_ISSUELETTER_LABEL_56"));
			paramMap.put("issueDate", issueDate);
			paramMap.put("inFormDate", Utils.formattedDate(lifePolicy.getLifeProposal().getSubmittedDate()));
			paramMap.put("endorseNo", lifePolicy.getEndorsementNo());
			paramMap.put("policyNo", lifePolicy.getPolicyNo());
			paramMap.put("customerName", lifePolicy.getCustomerName());
			paramMap.put("description", description);
			jprint1 = JasperFactory.generateJasperPrint(paramMap, JasperTemplate.ENDORSE_ISSUE_LETTER, new JREmptyDataSource());
			jpList.add(jprint1);
		}
		return jpList;
	}

	public static List<PolicyInsuredPersonDTO> getSortedInsuNameList(List<PolicyInsuredPersonDTO> list) {
		Collections.sort(list, new Comparator<PolicyInsuredPersonDTO>() {
			public int compare(PolicyInsuredPersonDTO o1, PolicyInsuredPersonDTO o2) {
				return o1.getFullName().compareTo(o2.getFullName());
			}
		});
		return list;
	}
}
