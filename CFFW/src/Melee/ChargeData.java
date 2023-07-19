package Melee;

import java.io.Serializable;

public class ChargeData implements Serializable {
	public double velocity;
	public boolean flankCharge;
	public boolean rearCharge;

	public ChargeData(double velocity, boolean flankCharge, boolean rearCharge) {
		this.velocity = velocity;
		this.flankCharge = flankCharge;
		this.rearCharge = rearCharge;
	}
}
