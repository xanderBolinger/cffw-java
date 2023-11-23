package JsonSaveRunner;

import java.util.ArrayList;

import Company.Company;

public class CompanyJson {

	public ArrayList<UnitJson> unitsJson;
	
	public CompanyJson(Company company) {
		
		unitsJson = new ArrayList<UnitJson>();
		
		for(var unit : company.getUnits())
			unitsJson.add(new UnitJson(unit));
		
	}
	
}
