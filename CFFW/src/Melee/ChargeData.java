package Melee;

import java.io.Serializable;

public class ChargeData implements Serializable {
	public double velocity;
	public boolean flankCharge;
	public boolean rearCharge;
	public boolean incoming;

	public ChargeData(ChargeData data) {
		this.velocity = data.velocity;
		this.flankCharge = data.flankCharge;
		this.rearCharge = data.rearCharge;
		this.incoming = data.incoming;
	}
	
	public ChargeData(double velocity, boolean flankCharge, boolean rearCharge, boolean incoming) {
		this.velocity = velocity;
		this.flankCharge = flankCharge;
		this.rearCharge = rearCharge;
	}
}
