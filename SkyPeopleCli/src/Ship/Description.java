package Ship;

public class Description {

	public String manufacturer;
	public String model;
	public int lengthMeters;
	public int widthMeters;
	public int heightMeters;
	public String description;
	
	
	public Description(String manufacturer, String model, int lengthMeters, int widthMeters, int heightMeters, String description) {
	    this.manufacturer = manufacturer;
	    this.model = model;
	    this.lengthMeters = lengthMeters;
	    this.widthMeters = widthMeters;
	    this.heightMeters = heightMeters;
	    this.description = description;
	}

	
	public Description(Description other) {
		  this.manufacturer = other.manufacturer;
		  this.model = other.model;
		  this.lengthMeters = other.lengthMeters;
		  this.widthMeters = other.widthMeters;
		  this.heightMeters = other.heightMeters;
		  this.description = other.description;
		}



	@Override 
	public String toString() {
		String rslts = "";
		
		rslts += "Manufacturer: "+manufacturer + "\n";
		rslts += "Model: "+model+"\n";
		rslts += "Length Meters: "+lengthMeters + "\n";
		rslts += "Width Meters: "+widthMeters + "\n";
		rslts += "Height Meters: "+heightMeters + "\n";
		rslts += "Description: "+description + "\n";
		
		return rslts; 
	}
	
	
}
