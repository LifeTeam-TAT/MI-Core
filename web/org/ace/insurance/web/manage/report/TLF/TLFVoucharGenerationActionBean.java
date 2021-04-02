package org.ace.insurance.web.manage.report.TLF;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.common.GenericDataModel;
import org.ace.insurance.common.interfaces.IDataModel;
import org.ace.insurance.common.utils.CurrencyUtils;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.report.TLF.TLFVoucherDTO;
import org.ace.insurance.web.common.DocumentBuilder;
import org.ace.insurance.web.util.FileHandler;
import org.ace.java.web.ApplicationSetting;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;

@ViewScoped
@ManagedBean(name = "TLFVoucharGenerationActionBean")
public class TLFVoucharGenerationActionBean<T extends IDataModel> extends BaseBean {
	private String receiptNo;
	private int copy;
	private List<TLFVoucherDTO> tlfVoucherDTOList;
	private T[] selectedTlfVoucherDTOs;
	private GenericDataModel<IDataModel> tlfVoucherDTODataModel;

	@ManagedProperty(value = "#{PaymentService}")
	private IPaymentService paymentService;

	public void setPaymentService(IPaymentService paymentService) {
		this.paymentService = paymentService;
	}

	private final String reportName = "TLFVochar";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getSystemPath() + pdfDirPath;
	private final String fileName = reportName + ".pdf";

	@PostConstruct
	public void init() {
		// do nothing
	}

	public void filter() {
		tlfVoucherDTOList = paymentService.findTLFVoucher(receiptNo);
		tlfVoucherDTODataModel = new GenericDataModel(tlfVoucherDTOList);
	}

	public void resetCriteria() {
		receiptNo = null;
		tlfVoucherDTOList = new ArrayList<TLFVoucherDTO>();
	}

	public void generateReport() throws Exception {
		String fullTemplateFilePathForKYT = "report-template/TLFVoucher/TLFVoucher.jrxml";
		String fullTemplateFilePath = "report-template/TLFVoucher/TLFVoucherUSD.jrxml";
		List<TLFVoucherDTO> selectedList = getSelectedList();
		if (!selectedList.isEmpty()) {
			if (CurrencyUtils.isKyat(selectedList.get(0).getCurrency())) {
				DocumentBuilder.generateTLFVoucher(selectedList, fullTemplateFilePathForKYT, dirPath, fileName);
			} else {
				DocumentBuilder.generateTLFVoucher(selectedList, fullTemplateFilePath, dirPath, fileName);
			}
			RequestContext.getCurrentInstance().execute("pdfPreviewDialog.show()");
			RequestContext.getCurrentInstance().update("pdfPreviewForm");
		} else {
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.UNDERWRITING_PROCESS_SUCCESS);
			RequestContext.getCurrentInstance().update("tlfVoucharForm");
		}
	}

	public String getStream() {
		String fileFullPath = pdfDirPath + fileName;
		return fileFullPath;
	}

	public List<TLFVoucherDTO> getSelectedList() {
		String companyName = ApplicationSetting.getCompanyLabel();
		List<TLFVoucherDTO> selectedTlfVoucherDTOList = new ArrayList<TLFVoucherDTO>();
		for (int i = 1; i <= copy; i++) {
			for (T TLFVoucherDTO : selectedTlfVoucherDTOs) {
				TLFVoucherDTO tVoucherDTO = (TLFVoucherDTO) TLFVoucherDTO;
				tVoucherDTO.setCompanyName(companyName);
				selectedTlfVoucherDTOList.add(tVoucherDTO);
			}
		}
		return selectedTlfVoucherDTOList;
	}

	// getter / setter

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public List<TLFVoucherDTO> getTlfVoucharDTOList() {
		return tlfVoucherDTOList;
	}

	public T[] getSelectedTlfVoucherDTOs() {
		return selectedTlfVoucherDTOs;
	}

	public void setSelectedTlfVoucherDTOs(T[] selectedTlfVoucherDTOs) {
		this.selectedTlfVoucherDTOs = selectedTlfVoucherDTOs;
	}

	public GenericDataModel<IDataModel> getTlfVoucherDTODataModel() {
		return tlfVoucherDTODataModel;
	}

	public int getCopy() {
		return copy = 1;
	}

	public void setCopy(int copy) {
		this.copy = copy;
	}

	public void handleClose(CloseEvent event) {
		try {
			FileHandler.forceDelete(dirPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
