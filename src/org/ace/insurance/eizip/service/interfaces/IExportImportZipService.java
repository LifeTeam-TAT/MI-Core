package org.ace.insurance.eizip.service.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

public interface IExportImportZipService {

	public String getGenerateZip(Date startDate, Date endDate, String zipFileName, String tempFilePath) throws IOException;

	public void insertByTableName(String extractedFilePath, String extractedFolderName) throws FileNotFoundException;

}
