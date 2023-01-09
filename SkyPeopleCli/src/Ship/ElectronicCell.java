package Ship;


public class ElectronicCell {
	public int ZD;
	public int ecm;
	public int eccm;
	public ElectronicType electronicType;
	public boolean destroyed;
	
	public enum ElectronicType {
		SENSORS,COMMUNICATIONS,NAVCOMPUTER
	}
	
	public ElectronicCell(int ZD, int ecm, int eccm, ElectronicType electronicType) {
		this.ZD = ZD;
		this.ecm = ecm;
		this.eccm = eccm;
		this.electronicType = electronicType;
		destroyed = false;
	}
	
}
