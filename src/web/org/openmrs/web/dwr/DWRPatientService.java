package org.openmrs.web.dwr;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.api.PatientService;
import org.openmrs.context.Context;
import org.openmrs.web.Constants;

import uk.ltd.getahead.dwr.ExecutionContext;

public class DWRPatientService {

	protected final Log log = LogFactory.getLog(getClass());
	
	public Collection findPatients(String identifier, String givenName, String familyName) {
		
		Collection patientList = new Vector();

		Context context = (Context) ExecutionContext.get().getSession()
				.getAttribute(Constants.OPENMRS_CONTEXT_HTTPSESSION_ATTR);
		try {
			if (!context.isAuthenticated())
				context.authenticate("USER-1", "test");
			PatientService ps = context.getPatientService();
			List<Patient> patients;
			
			log.debug("ident: |" + identifier + "|");
			
			if (identifier != null && !identifier.equals(""))
				patients = ps.getPatientsByIdentifier(identifier);
			else
				patients = ps.getPatientsByName(givenName, familyName);
			
			patientList = new Vector<PatientListItem>(patients.size());
			int i = 0;
			for (Patient p : patients) {
				patientList.add(new PatientListItem(p));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return patientList;
	}

}
