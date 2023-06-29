package Vehicle.Data;

import java.io.Serializable;

public class ShieldGenerator implements Serializable {

	int currentValue;
	int maxValue;
	int rechargeRate;
	
	public ShieldGenerator(int currentValue, int maxValue, int rechargeRate) {
        this.currentValue = currentValue;
        this.maxValue = maxValue;
        this.rechargeRate = rechargeRate;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getRechargeRate() {
        return rechargeRate;
    }

    public void setRechargeRate(int rechargeRate) {
        this.rechargeRate = rechargeRate;
    }
}
	

