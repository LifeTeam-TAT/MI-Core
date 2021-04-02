package org.ace.insurance.medical.renewal.persistence;

import org.ace.insurance.medical.renewal.persistence.interfaces.IMedicalRenewalDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.springframework.stereotype.Repository;

@Repository("MedicalRenewalDAO")
public class MedicalRenewalDAO extends BasicDAO implements IMedicalRenewalDAO {

}
