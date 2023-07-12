package Melee;

public class ChargeData {
	public double velocity;
	public boolean flankCharge;
	public boolean rearCharge;

	public ChargeData(double velocity, boolean flankCharge, boolean rearCharge) {
		this.velocity = velocity;
		this.flankCharge = flankCharge;
		this.rearCharge = rearCharge;
	}
}
