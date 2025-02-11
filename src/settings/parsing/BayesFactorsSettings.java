package settings.parsing;

import java.util.LinkedList;

import structure.data.Location;

public class BayesFactorsSettings implements DiscreteSpreadDataSettings {

	//---REQUIRED---//
	
	// path to locations file
	public String locationsFilename = null;
	
	// path to log file
	public String logFilename = null;
	
	//---OPTIONAL---//
	
	// path to json output file 
	public String outputFilename = "output.json";

	// burnin in %
	public Double burninPercent = 10.0;

	public Double meanPoissonPrior = Math.log(2);
	
	public Double offsetPoissonPrior = null;
	
	public boolean hasHeader = false;
	
//	public boolean generateJson = true;

	public String geojsonFilename = null;
	
	//---GUI---//
	
	public Double[][] indicators = null;
	public LinkedList<Location> locationsList = null;

	@Override
	public String getLocationsFilename() {
		return locationsFilename;
	}
	
	@Override
	public void setLocationsFilename(String locationsFilename) {
		this.locationsFilename = locationsFilename;
	}
	
	@Override
	public boolean hasHeader() {
		return hasHeader;
	}

	@Override
	public LinkedList<Location> getLocationsList() {
		return locationsList;
	}

	@Override
	public void setLocationsList(LinkedList<Location> locationsList) {
		this.locationsList = locationsList;
	}

}//END: class
