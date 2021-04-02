package org.ace.insurance.eizip.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;

import org.ace.insurance.common.GraphAdapterBuilder;
import org.ace.insurance.common.Utils;
import org.ace.insurance.eizip.ZipFileName;
import org.ace.insurance.eizip.persistence.interfaces.IExportImportZipDAO;
import org.ace.insurance.eizip.service.interfaces.IExportImportZipService;
import org.ace.insurance.payment.TLF;
import org.ace.insurance.product.Product;
import org.ace.insurance.product.ProductGroup;
import org.ace.insurance.role.Role;
import org.ace.insurance.system.common.addon.AddOn;
import org.ace.insurance.system.common.bank.Bank;
import org.ace.insurance.system.common.bankBranch.BankBranch;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.buildingOccupation.BuildingOccupation;
import org.ace.insurance.system.common.city.City;
import org.ace.insurance.system.common.classofinsurance.ClassOfInsurance;
import org.ace.insurance.system.common.coinsurancecompany.CoinsuranceCompany;
import org.ace.insurance.system.common.company.Company;
import org.ace.insurance.system.common.country.Country;
import org.ace.insurance.system.common.currency.Currency;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.express.Express;
import org.ace.insurance.system.common.industry.Industry;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.insurance.system.common.occupation.Occupation;
import org.ace.insurance.system.common.occurrence.Occurrence;
import org.ace.insurance.system.common.organization.Organization;
import org.ace.insurance.system.common.paymenttype.PaymentType;
import org.ace.insurance.system.common.province.Province;
import org.ace.insurance.system.common.qualification.Qualification;
import org.ace.insurance.system.common.relationship.RelationShip;
import org.ace.insurance.system.common.religion.Religion;
import org.ace.insurance.system.common.township.Township;
import org.ace.insurance.system.common.typesOfSport.TypesOfSport;
import org.ace.insurance.system.common.workshop.WorkShop;
import org.ace.insurance.system.configuration.SettingVariable;
import org.ace.insurance.user.User;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Service(value = "ExportImportZipService")
public class ExportImportZipService extends BaseService implements IExportImportZipService {
	@Resource(name = "ExportImportZipDAO")
	private IExportImportZipDAO exportImportZipDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public String getGenerateZip(Date startDate, Date endDate, String folderName, String tempFilePath) throws IOException {
		List<String> tableNameList = Utils.getTableNameList(folderName);
		if (ZipFileName.TLF.equals(folderName) && startDate != null && endDate != null) {
			folderName = folderName + "(" + Utils.getDateFormatString(startDate) + " - " + Utils.getDateFormatString(endDate) + ")";
		} else {
			startDate = null;
			endDate = null;
		}
		String zipFilePath = tempFilePath + folderName + ".zip";
		List<Object> objectList;
		FileWriter fw = null;
		String fileName = null;
		String json = null;
		Gson gson;
		try {
			for (String tableName : tableNameList) {
				fileName = tableName + ".json";
				objectList = exportImportZipDAO.findObjectsByDate(startDate, endDate, tableName);
				gson = getGson(tableName);
				json = gson.toJson(objectList);
				FileUtils.forceMkdir(new File(tempFilePath));
				fw = new FileWriter(tempFilePath + fileName);
				fw.write(json);
				fw.close();
			}
			FileOutputStream fos = new FileOutputStream(zipFilePath);
			ZipOutputStream zos = new ZipOutputStream(fos);
			for (String jsonFileName : tableNameList) {
				addToZipFile(tempFilePath + jsonFileName + ".json", zos);
			}
			zos.close();
			fos.close();

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find tlfByDAte", e);
		}
		return zipFilePath;
	}

	public static void addToZipFile(String filePath, ZipOutputStream zos) throws FileNotFoundException, IOException {
		File file = new File(filePath);
		String fileName = file.getName().replaceAll("\\s", "_");
		FileInputStream fis = new FileInputStream(file);
		ZipEntry zipEntry = new ZipEntry(fileName);
		zos.putNextEntry(zipEntry);

		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zos.write(bytes, 0, length);
		}

		zos.closeEntry();
		fis.close();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void insertByTableName(String extractedFilePath, String extractedFolderName) throws FileNotFoundException {
		Gson gson = null;
		BufferedReader br = null;
		List<Object> objectList = null;
		List<String> strList = Utils.getTableNameList(extractedFolderName);
		try {
			if (!ZipFileName.TLF.equals(extractedFolderName)) {
				exportImportZipDAO.deleteTablesByZipFileName(extractedFolderName);
			}
			for (String tableName : strList) {
				br = new BufferedReader(new FileReader(extractedFilePath + extractedFolderName + "/" + tableName + ".json"));
				gson = getGson(tableName);
				objectList = gson.fromJson(br, getObjectTypeToken(tableName).getType());
				for (Object obj : objectList) {
					exportImportZipDAO.insertObjectByTableName(obj, tableName);
				}
			}

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to insertByTableName", e);
		}
	}

	private boolean checkCircularReferenc(String tableName) {
		boolean circularReference = false;
		if (ZipFileName.COINSURANCECOMPANY.equals(tableName) || ZipFileName.USER.equals(tableName) || ZipFileName.BUILDINGCLASS.equals(tableName)) {
			circularReference = true;
		}
		return circularReference;
	}

	private Gson getGson(String tableName) {
		Gson gson = null;
		if (!checkCircularReferenc(tableName)) {
			gson = new Gson();
			return gson;
		} else {
			if (ZipFileName.COINSURANCECOMPANY.equals(tableName)) {
				GsonBuilder gsonBuilder = new GsonBuilder();
				new GraphAdapterBuilder().addType(CoinsuranceCompany.class).registerOn(gsonBuilder);
				gson = gsonBuilder.create();
				return gson;
			} else if (ZipFileName.USER.equals(tableName)) {
				GsonBuilder gsonBuilder = new GsonBuilder();
				new GraphAdapterBuilder().addType(User.class).registerOn(gsonBuilder);
				gson = gsonBuilder.create();
				return gson;
			}
		}
		return gson;
	}

	public TypeToken getObjectTypeToken(String name) {
		if ("CoinsuranceCompany".equals(name)) {
			TypeToken<List<CoinsuranceCompany>> token = new TypeToken<List<CoinsuranceCompany>>() {
			};
			return token;
		} else if ("Company".equals(name)) {
			TypeToken<List<Company>> token = new TypeToken<List<Company>>() {
			};
			return token;
		} else if ("Township".equals(name)) {
			TypeToken<List<Township>> token = new TypeToken<List<Township>>() {
			};
			return token;
		} else if ("Country".equals(name)) {
			TypeToken<List<Country>> token = new TypeToken<List<Country>>() {
			};
			return token;
		} else if ("TLF".equals(name)) {
			TypeToken<List<TLF>> token = new TypeToken<List<TLF>>() {
			};
			return token;
		} else if ("Province".equals(name)) {
			TypeToken<List<Province>> token = new TypeToken<List<Province>>() {
			};
			return token;
		} else if ("Branch".equals(name)) {
			TypeToken<List<Branch>> token = new TypeToken<List<Branch>>() {
			};
			return token;
		} else if ("Organization".equals(name)) {
			TypeToken<List<Organization>> token = new TypeToken<List<Organization>>() {
			};
			return token;
		} else if ("Customer".equals(name)) {
			TypeToken<List<Customer>> token = new TypeToken<List<Customer>>() {
			};
			return token;
		} else if ("Occupation".equals(name)) {
			TypeToken<List<Occupation>> token = new TypeToken<List<Occupation>>() {
			};
			return token;
		} else if ("BuildingOccupation".equals(name)) {
			TypeToken<List<BuildingOccupation>> token = new TypeToken<List<BuildingOccupation>>() {
			};
			return token;
		} else if ("Industry".equals(name)) {
			TypeToken<List<Industry>> token = new TypeToken<List<Industry>>() {
			};
			return token;
		} else if ("Qualification".equals(name)) {
			TypeToken<List<Qualification>> token = new TypeToken<List<Qualification>>() {
			};
			return token;
		} else if ("Religion".equals(name)) {
			TypeToken<List<Religion>> token = new TypeToken<List<Religion>>() {
			};
			return token;
		} else if ("RelationShip".equals(name)) {
			TypeToken<List<RelationShip>> token = new TypeToken<List<RelationShip>>() {
			};
			return token;
		} else if ("TypesOfSport".equals(name)) {
			TypeToken<List<TypesOfSport>> token = new TypeToken<List<TypesOfSport>>() {
			};
			return token;
		} else if ("WorkShop".equals(name)) {
			TypeToken<List<WorkShop>> token = new TypeToken<List<WorkShop>>() {
			};
			return token;
		} else if ("ClassOfInsurance".equals(name)) {
			TypeToken<List<ClassOfInsurance>> token = new TypeToken<List<ClassOfInsurance>>() {
			};
			return token;
		} else if ("Product".equals(name)) {
			TypeToken<List<Product>> token = new TypeToken<List<Product>>() {
			};
			return token;
		} else if ("ProductGroup".equals(name)) {
			TypeToken<List<ProductGroup>> token = new TypeToken<List<ProductGroup>>() {
			};
			return token;
		} else if ("AddOn".equals(name)) {
			TypeToken<List<AddOn>> token = new TypeToken<List<AddOn>>() {
			};
			return token;
		} else if ("KeyFactor".equals(name)) {
			TypeToken<List<KeyFactor>> token = new TypeToken<List<KeyFactor>>() {
			};
			return token;
		} else if ("Currency".equals(name)) {
			TypeToken<List<Currency>> token = new TypeToken<List<Currency>>() {
			};
			return token;
		} else if ("PaymentType".equals(name)) {
			TypeToken<List<PaymentType>> token = new TypeToken<List<PaymentType>>() {
			};
			return token;
		} else if ("Bank".equals(name)) {
			TypeToken<List<Bank>> token = new TypeToken<List<Bank>>() {
			};
			return token;
		} else if ("BankBranch".equals(name)) {
			TypeToken<List<BankBranch>> token = new TypeToken<List<BankBranch>>() {
			};
			return token;
		} else if ("User".equals(name)) {
			TypeToken<List<User>> token = new TypeToken<List<User>>() {
			};
			return token;
		} else if ("Role".equals(name)) {
			TypeToken<List<Role>> token = new TypeToken<List<Role>>() {
			};
			return token;
		} else if ("Express".equals(name)) {
			TypeToken<List<Express>> token = new TypeToken<List<Express>>() {
			};
			return token;
		} else if ("Occurrence".equals(name)) {
			TypeToken<List<Occurrence>> token = new TypeToken<List<Occurrence>>() {
			};
			return token;
		} else if ("City".equals(name)) {
			TypeToken<List<City>> token = new TypeToken<List<City>>() {
			};
			return token;
		} else if ("SettingVariable".equals(name)) {
			TypeToken<List<SettingVariable>> token = new TypeToken<List<SettingVariable>>() {
			};
			return token;
		}
		return null;
	}

}
