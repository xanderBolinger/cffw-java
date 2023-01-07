package Ship;


public class ElectronicCell {
	public int ZD;
	public int ecm;
	public int eccm;
	public ElectronicType electronicType;
	
	public enum ElectronicType {
		SENSORS,COMMUNICATIONS,NAVCOMPUTER
	}
}
