package org.ace.java.web;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.ace.insurance.common.utils.CurrencyUtils;
import org.ace.insurance.system.common.currency.Currency;
import org.ace.java.web.common.BaseBean;

@SessionScoped
@ManagedBean(name = "ApplicationSetting")
public class ApplicationSetting extends BaseBean {
	private static final String DATE_TIME_FORMAT = "dd-MM-yyyy hh:mm a";
	private static final String DATE_FORMAT = "dd-MM-yyyy";
	private static final String DATE_FORMAT_ONE = "MM-dd-yyyy";
	private static final String CURRENCY_FORMAT = "##,###.00";
	private static final String CURRENCY_USD_FORMAT = "Ã‚Â¤ #,##0.00";
	private static final String CURRENCY_FORMAT2 = "##,##0.0000";
	private static final String CURRENCY_FORMAT3 = "##,##0.00";
	private static final String PREMIUM_RATE_FORMAT = "##0.000";

	private static final String NUMBER_FORMAT = "##,###.00";
	private static final String PERCENT_FORMAT = "0.#####";
	private static final String DATE_DAY_MONTH_FORMAT = "dd-MMMM";
	private static final String MOBILE_MASK = "09-999-999-999?99";
	private static final int MOBILE_MASK_LENGTH = 13;
	private static final String PHONE_MASK = "999-999-99?9";
	private static final int PHONE_MASK_LENGTH = 9;
	private static final String COMP_NAME = "GRAND GUARDIAN INSURANCE";
	private static final String ACEPLUS_NAME = "THURIYA ACE Technology";
	private static final String COMP_MYAN_NAME = "Ã¡â‚¬â€šÃ¡â‚¬â€ºÃ¡â‚¬â€žÃ¡â‚¬Â¹Ã¡â‚¬Â¸Ã¡â‚¬â€šÃ¡â‚¬Â«Ã¡â‚¬Â¸Ã¡â‚¬â€™Ã¡â‚¬Â®Ã¡â‚¬Â¸Ã¡â‚¬Å¡Ã¡â‚¬â€�Ã¡â‚¬Â¹Ã¡â‚¬Â¸ Ã¡â‚¬Â¡Ã¡â‚¬Â¬Ã¡â‚¬â„¢Ã¡â‚¬ï¿½Ã¡â‚¬Â¶";
	private static final String ACEPLUS_MYAN_NAME = "";
	private static final String COMPANY_ICON = "report-template/TAT-Icon.jpg";
	private static final String ACEPLUS_ICON = "report-template/TAT-logo.jpg";
	private static final String ACEPLUS_LABEL = "THURIYA ACE Technology. ";
	private static final String CLIENT_LABEL = "Insurance Life Co., Ltd. ";
	private static final String ACEPLUS_LOGO = "report-template/TAT-logo.jpg";
	private static final String GGIP_LOGO = "report-template/TAT-logo.jpg";
	private static final String GGIP_NEW_LOGO = "report-template/TAT-logo.jpg";
	private static final String ACEPLUS_ADDLOGO = "report-template/aceplus_address.jpg";
	private static final String GGIP_ADDLOGO = "report-template/ggip_address.jpg";
	private static final String RENEWAL_ISSUE_LOGO = "report-template/renewal-issue-logo.jpg";
	private static final String RENEWAL_ISSUE_COMPANY_LOGO = "report-template/TAT-Icon.jpg";
	private static final String GGI_LOGO = "report-template/TAT-logo.jpg";
	private static final String GGI_ADDRESS = "report-template/aceplus_address.jpg";
	private static final String FNIPUBLIC_LOGO = "report-template/TAT-logo.png";
	private static final String FNI_LOGO = "/images/TAT-logo.jpg";
	private static final String FNIADDRESS = "No.29/A, Than Thu Mar Road,Thuwana-Thingangun,Township,Yangon,Myanmar,";
	private static final String FNIPHONE = "Phone :95-1-570521,570998 Fax :95-1-570298";
	private static final String FNI_RECEIPT_4FLY = "document-template/images/receipt4Fly.png";
	/**
	 * #WARRNING if edit this image byte code, also edit in
	 * LANGUAGE_en.properties and also edit in TERM_CONDITION_myn.properties
	 */
	private static final String FNI_POLICY_4FLY = "document-template/images/policy4Fly.png";

	private String cssFilePath;
	private static String theme = getPrimeTheme();

	// question Detail
	private static final String QUE_DETAIL_STYLE_ClASS = "queDetailIcon";
	private static final String MILOGO = "report-template/TAT-logo.jpg";

	// search
	private static final String EXCEL_ICON = "/images/excel.png";
	private static final String EXCEL_STYLE_ClASS = "excelIcon";

	// icon image url and css style class
	// search
	private static String SEARCH_ICON;

	private static String QUE_DETAIL_ICON;
	// policy
	private static String POLICY_ICON;
	// select
	private static String SELECT_ICON;
	// edit
	private static String EDIT_ICON;
	// delete
	private static String DELETE_ICON;
	// detail
	private static String DETAIL_ICON;
	// attach
	private static String ATTACH_ICON;
	// config
	private static String CONFIG_ICON;
	// printer
	private static String PRINT_ICON;
	// add
	private static String ADD_ICON;
	// filter
	private static String FILTER_ICON;
	// renew
	private static String RENEW_ICON;
	// report logo
	private static String REPORT_LOGO;
	// comp address
	private static String COMP_ADDRESS;

	// report logo
	private static String NEW_COMP_LOGO;
	// comp address
	private static String NEW_COMP_ADDRESS;
	// calculate
	private static String CALCULATE_ICON;

	// StyleClass
	private static final String SEARCH_STYLE_ClASS = "searchIcon";
	private static final String SELECT_STYLE_CLASS = "selectIcon";
	private static final String EDIT_STYLE_CLASS = "editIcon";
	private static final String DELETE_STYLE_CLASS = "deleteIcon";
	private static final String DETAIL_STYLE_CLASS = "detailIcon";
	private static final String ATTACH_STYLE_CLASS = "attachIcon";
	private static final String CONFIG_STYLE_CLASS = "configIcon";
	private static final String PRINT_STYLE_CLASS = "printIcon";
	private static final String ADD_STYLE_CLASS = "addIcon";
	private static final String FILTER_STYLE_CLASS = "filterIcon";
	private static final String RENEW_STYLE_CLASS = "renewIcon";
	private static final String CALCULATE_STYLE_CLASS = "calculateIcon";

	@PostConstruct
	public void createThemeIcon() {
		NEW_COMP_LOGO = "/report-template/TAT-logo.jpg";
		NEW_COMP_ADDRESS = "/report-template/aceplus_address.jpg";
		if (theme.equalsIgnoreCase("hot-sneaks")) {
			SEARCH_ICON = "/images/ps_search.png";
			SELECT_ICON = "/images/ps_select.png";
			EDIT_ICON = "/images/ps_edit.png";
			DELETE_ICON = "/images/ps_delete.png";
			DETAIL_ICON = "/images/ps_details.png";
			ATTACH_ICON = "/images/ps_attach.png";
			CONFIG_ICON = "/images/ps_config.png";
			PRINT_ICON = "/images/ps_printer.png";
			ADD_ICON = "/images/ps_add.png";
			FILTER_ICON = "/images/ps_filter.png";
			RENEW_ICON = "/images/ps_renewal.png";
			QUE_DETAIL_ICON = "/images/question.png";
			cssFilePath = "theme-home.css";
			REPORT_LOGO = "/report-template/TAT-logo.jpg";
			POLICY_ICON = "/images/policy.png";
			CALCULATE_ICON = "/images/ps_calculate-icon.png";
		} else {
			SEARCH_ICON = "/images/search.png";
			SELECT_ICON = "/images/select.png";
			EDIT_ICON = "/images/edit.png";
			DELETE_ICON = "/images/delete.png";
			DETAIL_ICON = "/images/details.png";
			ATTACH_ICON = "/images/attachment.png";
			CONFIG_ICON = "/images/config.png";
			PRINT_ICON = "/images/print.png";
			ADD_ICON = "/images/add.png";
			FILTER_ICON = "/images/filter.png";
			RENEW_ICON = "/images/renewal.png";
			cssFilePath = "default.css";
			QUE_DETAIL_ICON = "/images/question.jpg";
			REPORT_LOGO = "/report-template/TAT-logo.jpg";
			COMP_ADDRESS = "/report-template/aceplus_address.jpg";
			POLICY_ICON = "/images/policy.png";
			CALCULATE_ICON = "/images/ps_calculate-icon.png";
		}
	}

	public static String getMilogo() {
		return MILOGO;
	}

	public String getDateDayMonthFormat() {
		return DATE_DAY_MONTH_FORMAT;
	}

	public String getTimeZone() {
		return TimeZone.getDefault().getID();
	}

	public String getDateTimeFormat() {
		return DATE_TIME_FORMAT;
	}

	public String getDateFormat() {
		return DATE_FORMAT;
	}

	public String getDateFormatStartWithMonth() {
		return DATE_FORMAT_ONE;
	}

	public String getCurrencyFormat() {
		return CURRENCY_FORMAT;
	}

	public String getPremiumRateFormat() {
		return PREMIUM_RATE_FORMAT;
	}

	public String getCurrencyFormat2() {
		return CURRENCY_FORMAT2;
	}

	public String getCurrencyFormat3() {
		return CURRENCY_FORMAT3;
	}

	public String getNumberFormat() {
		return NUMBER_FORMAT;
	}

	public String getPercentFormat() {
		return PERCENT_FORMAT;
	}

	public String getMobileMask() {
		return MOBILE_MASK;
	}

	public int getMobileMaskLength() {
		return MOBILE_MASK_LENGTH;
	}

	public String getPhoneMask() {
		return PHONE_MASK;
	}

	public int getPhoneMaskLength() {
		return PHONE_MASK_LENGTH;
	}

	private int availHeight;
	private int availWidth;

	public int getAvailHeight() {
		return availHeight;
	}

	public void setAvailHeight(int availHeight) {
		this.availHeight = availHeight;
	}

	public int getAvailWidth() {
		return availWidth;
	}

	public void setAvailWidth(int availWidth) {
		this.availWidth = availWidth;
	}

	public float getContentDIVHeight() {
		return (availHeight / 100) * 55;
	}

	public float getLoginDIVTopMargin() {
		return (getContentDIVHeight() / 100) * 12;
	}

	public void process() {
	}

	public static String getMonthInString(int month) {
		String monthString = "";
		switch (month) {
			case 0: {
				monthString = "January";
				break;
			}
			case 1: {
				monthString = "Febuary";
				break;
			}
			case 2: {
				monthString = "March";
				break;
			}
			case 3: {
				monthString = "April";
				break;
			}
			case 4: {
				monthString = "May";
				break;
			}
			case 5: {
				monthString = "June";
				break;
			}
			case 6: {
				monthString = "July";
				break;
			}
			case 7: {
				monthString = "August";
				break;
			}
			case 8: {
				monthString = "September";
				break;
			}
			case 9: {
				monthString = "October";
				break;
			}
			case 10: {
				monthString = "November";
				break;
			}
			case 11: {
				monthString = "December";
				break;
			}

		}
		return monthString;
	}

	// mutator for icon image url and css style class
	public String getSearchIcon() {
		return SEARCH_ICON;
	}

	public String getSearchStyleClass() {
		return SEARCH_STYLE_ClASS;
	}

	public String getSelectIcon() {
		return SELECT_ICON;
	}

	public String getQueDetailIcon() {
		return QUE_DETAIL_ICON;
	}

	public String getQueDetailStyleClass() {
		return QUE_DETAIL_STYLE_ClASS;
	}

	public String getSelectStyleClass() {
		return SELECT_STYLE_CLASS;
	}

	public String getEditIcon() {
		return EDIT_ICON;
	}

	public String getPolicyIcon() {
		return POLICY_ICON;
	}

	public String getEditStyleClass() {
		return EDIT_STYLE_CLASS;
	}

	public String getDeleteIcon() {
		return DELETE_ICON;
	}

	public String getDeleteStyleClass() {
		return DELETE_STYLE_CLASS;
	}

	public String getAttachIcon() {
		return ATTACH_ICON;
	}

	public String getAttachStyleClass() {
		return ATTACH_STYLE_CLASS;
	}

	public String getDetailIcon() {
		return DETAIL_ICON;
	}

	public String getDetailStyleClass() {
		return DETAIL_STYLE_CLASS;
	}

	public String getConfigIcon() {
		return CONFIG_ICON;
	}

	public String getConfigStyleClass() {
		return CONFIG_STYLE_CLASS;
	}

	public String getPrintIcon() {
		return PRINT_ICON;
	}

	public String getPrintStyleClass() {
		return PRINT_STYLE_CLASS;
	}

	public String getAddIcon() {
		return ADD_ICON;
	}

	public String getAddStyleClass() {
		return ADD_STYLE_CLASS;
	}

	public String getCalculateStyleClass() {
		return CALCULATE_STYLE_CLASS;
	}

	public String getFilterIcon() {
		return FILTER_ICON;
	}

	public String getFilterStyleClass() {
		return FILTER_STYLE_CLASS;
	}

	public String getRenewIcon() {
		return RENEW_ICON;
	}

	public String getRenewStyleClass() {
		return RENEW_STYLE_CLASS;
	}

	public String getCalculateIcon() {
		return CALCULATE_ICON;
	}

	public String getCssFilePath() {
		return cssFilePath;
	}

	public static String getHomePageDir() {
		if (theme.equalsIgnoreCase("hot-sneaks")) {
			return "view/ps_login.xhtml";
		} else {
			return "view/login.xhtml";
		}
	}

	public static String getHeader() {
		if (theme.equalsIgnoreCase("hot-sneaks")) {
			return "/common/ps_header.xhtml";
		} else {
			return "/common/header.xhtml";
		}
	}

	public static String getCompanyIcon() {
		if (theme.equalsIgnoreCase("hot-sneaks")) {
			return ACEPLUS_ICON;
		} else {
			return COMPANY_ICON;
		}
	}

	public static String getFNIADDRESS() {
		return FNIADDRESS;
	}

	public static String getFNIPHONE() {
		return FNIPHONE;
	}

	public static String getFNI_LOGO() {
		return FNI_LOGO;
	}

	public static String getCompanyName() {
		if (theme.equalsIgnoreCase("hot-sneaks")) {
			return ACEPLUS_NAME;
		} else {
			return COMP_NAME;
		}
	}

	public static String getCompanyMyanmarName() {
		if (theme.equalsIgnoreCase("hot-sneaks")) {
			return ACEPLUS_MYAN_NAME;
		} else {
			return COMP_MYAN_NAME;
		}
	}

	public static String getReportLogo() {
		return REPORT_LOGO;
	}

	public static String getCOMP_ADDRESS() {
		return COMP_ADDRESS;
	}

	public static String getNewCompLogo() {
		return NEW_COMP_LOGO;
	}

	public static String getNewCompAddress() {
		return NEW_COMP_ADDRESS;
	}

	public static String currencyFormatString(Currency currency) {
		if (CurrencyUtils.isUSD(currency)) {
			return CURRENCY_USD_FORMAT;
		} else {
			return CURRENCY_FORMAT;
		}
	}

	public static String getCurrencyFormatString(Double value) {
		DecimalFormat formatter = new DecimalFormat("##,###.00");
		return formatter.format(value);
	}

	public static String getCurrencyFormatString1(int value) {
		DecimalFormat formatter = new DecimalFormat("##,###.00");
		return formatter.format(value);
	}

	public static String currencySymbol(Currency currency) {
		if (CurrencyUtils.isUSD(currency)) {
			return "$";
		} else {
			return "";
		}
	}

	public static String getCompanyLabel() {
		if (theme.equalsIgnoreCase("hot-sneaks")) {
			return CLIENT_LABEL;
		} else {
			return CLIENT_LABEL;
		}
	}

	public static String getCompanyLogoFilePath() {
		if (theme.equalsIgnoreCase("hot-sneaks")) {
			return ACEPLUS_LOGO;
		} else {
			return GGIP_LOGO;
		}
	}

	public static String getCompanyAddressAdFilePath() {
		if (theme.equalsIgnoreCase("hot-sneaks")) {
			return ACEPLUS_ADDLOGO;
		} else {
			return GGIP_ADDLOGO;
		}
	}

	public static String getReceipt4Fly() {
		return FNI_RECEIPT_4FLY;
	}

	public static String getPolicy4Fly() {
		return FNI_POLICY_4FLY;
	}

	public static String getCompanyLogoNew() {
		return GGIP_NEW_LOGO;
	}

	public String getExcelIcon() {
		return EXCEL_ICON;
	}

	public String getExcelStyleClass() {
		return EXCEL_STYLE_ClASS;
	}

	public static String getRenewalIssueBackGroundLogo() {
		return RENEWAL_ISSUE_LOGO;
	}

	public static String getRenewalIssueCompanyLogo() {
		return RENEWAL_ISSUE_COMPANY_LOGO;
	}

	public static String getGGILogo() {
		return GGI_LOGO;
	}

	public static String getGGIAddress() {
		return GGI_ADDRESS;
	}

	public static String getFNIPublicLogo() {
		return FNIPUBLIC_LOGO;
	}

	public void handleDialogClose(String reportStream) {
		if (!reportStream.isEmpty() || reportStream != null) {
			String[] paths = reportStream.split("/");
			String path = paths[1] + "/" + paths[2];
			try {
				org.ace.insurance.web.util.FileHandler.forceDelete(getSystemPath() + path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
