package FatigueSystem;

import java.io.Serializable;

import Trooper.Trooper;
import UtilityClasses.TrooperUtility;

public class FatigueSystem implements Serializable
{
    public transient Trooper character; 
    public double recoverableFP;
    public int analeticValue;
    public boolean strenuousWork; 
    
    public ActionElapsedTime actionElapsedTime;
    public PhysicalRecoveryTime physicalRecoveryTime; 
    public FatiguePoints fatiguePoints; 
    public float lightActionElapsedTime; 
    // variables for debugging 
    public int strenuousWorkCalls;

    public class PhysicalRecoveryTime implements Serializable
    {
    	private float physicalRecoveryTime; 
    	
    	public PhysicalRecoveryTime() {
    		physicalRecoveryTime = 0; 
    	}
    	
        public float get()
        {
            return physicalRecoveryTime;
        }
        public void set(float value)
        {
            physicalRecoveryTime = value;
            OnPhysicalRecoveryElapsedTimeChange();
        }
    }

    public void OnPhysicalRecoveryElapsedTimeChange()
    {
    	//System.out.println("On PRETC");
    	
        //double AVMod = TrooperUtility.getAVMod(analeticValue);
        
        /*if (physicalRecoveryTime.get() > 30 * AVMod)
        {
            physicalRecoveryTime.set(0); 
            if (recoverableFP - analeticValue / 10 < 0)
            {
                fatiguePoints.decrement(recoverableFP);
                recoverableFP = 0; 
            }
            else
            {
            	// Is this legal? 
                fatiguePoints.set(fatiguePoints.get() - analeticValue / 10);
                recoverableFP -= analeticValue / 10;
            }
        }*/
        
       /* float count = physicalRecoveryTime.get();
        System.out.println("Seconds: "+count);
        System.out.println("FP: "+fatiguePoints.get());
        for (int i = 0; i < count; i++) {
            double recovery = ((double) analeticValue / 10.0) / 12.0;
            //System.out.println("Recovery: "+recovery);
            //System.out.println("Pre Recovery FP: "+fatiguePoints.get());
            
            System.out.println("Diff: "+(fatiguePoints.get() - recovery));
            
            if (fatiguePoints.get() - recovery < 0) {
            	fatiguePoints.set(0);
            }
            else {
            	fatiguePoints.set(fatiguePoints.get() - recovery);
            }
            //System.out.println("Post Recovery FP: "+fatiguePoints.get());

            if (physicalRecoveryTime.get() - 1 < 0) { physicalRecoveryTime.set(0); } else { physicalRecoveryTime.set(physicalRecoveryTime.get() - 1); }
        }*/
        
    }

    public class ActionElapsedTime implements Serializable
    {
    	private float actionElapsedTime;
    	
    	
    	public ActionElapsedTime() {
    		actionElapsedTime = 0; 
    	}
    	
        public float get()
        {
            return actionElapsedTime;
        }
        public void set(float value)
        {
            actionElapsedTime = value;
            OnActionElapsedTimeChange();
        }
    }

    public void OnActionElapsedTimeChange()
    {
        //Debug.Log("Pass On Action Elapsed Time, ET: "+ActionElapsedTime+", TN: "+ (float)analeticValue / (float)80);
        if (actionElapsedTime.get() >= (float)analeticValue / (float)80)
        {
            actionElapsedTime.set(0); 
            if (strenuousWork) 
                StrenuousWork();
            else
                LightWork();
        }
    }

    public class FatiguePoints implements Serializable
    {
    	
    	private double fatiguePoints; 
    	
    	public FatiguePoints() {
    		fatiguePoints = 0; 
    	}
    	
        public double get()
        {
           // return (int) Math.round(fatiguePoints);
        	return fatiguePoints;
        }
        
        /*public double getDecimal() {
        	return fatiguePoints; 
        }*/
        
        public void set(double value)
        {
        	if(character.entirelyMechanical) {
        		fatiguePoints = 0; 
        		return;
        	}
        	
            OnFatiguePointsChange(true);
            fatiguePoints = value;
            
            if(fatiguePoints < 0)
            	fatiguePoints = 0; 
            
            OnFatiguePointsChange(false);
        }
        
        public void decrement(double value)
        {
        	if(character.entirelyMechanical) {
        		fatiguePoints = 0; 
        		return; 
        	}
        	
        	OnFatiguePointsChange(true);
            fatiguePoints -= value;
            
            if(fatiguePoints < 0)
            	fatiguePoints = 0; 
            
            OnFatiguePointsChange(false);
        }
        
    }
    
    
    

    public void OnFatiguePointsChange(boolean reset)
    {
    	if(character.entirelyMechanical)
    		return; 
    	
        int change = TrooperUtility.getFatiguePointPenaltyChange((int) fatiguePoints.get());

        if (reset)
        {
           
        	int actionPoints = character.P1 + character.P2 + (change/2); 
            if(actionPoints % 2 == 0) {
            	character.P1 = actionPoints / 2; 
            	character.P2 = actionPoints / 2; 
            } else {
            	character.P1 = actionPoints / 2; 
            	character.P2 = actionPoints / 2 + 1; 
            }
        }
        else
        {

        	int actionPoints = character.P1 + character.P2 - (change/2); 
            if(actionPoints % 2 == 0) {
            	character.P1 = actionPoints / 2; 
            	character.P2 = actionPoints / 2; 
            } else {
            	character.P1 = actionPoints / 2; 
            	character.P2 = actionPoints / 2 + 1; 
            }
            
        }
    }

    public FatigueSystem(Trooper character)
    {
    	actionElapsedTime = new ActionElapsedTime();
        physicalRecoveryTime = new PhysicalRecoveryTime(); 
        fatiguePoints = new FatiguePoints(); 
        this.character = character;
        fatiguePoints.set(0);
        CalcAV();
        
    }


    public void CalcAV()
    {
    	 analeticValue = (int) (((character.getSkill("Endurance") / 3) * (character.wil / 3)  / 2) * (character.getBaseSpeed() / 2));
    }

  
    // Applies FP for a unit of light physical work 
    public void LightWork()
    {
    	if(analeticValue < 40) {
    		StrenuousWork();
    	}
    		
    	double AVMod = TrooperUtility.getAVMod(analeticValue);
    	
        /*double AVMod = 0;

        if (analeticValue < 40)
            AVMod = 2;
        else if (analeticValue < 89)
            AVMod = 1;
        else if (analeticValue < 139)
            AVMod = 0.75;
        else if (analeticValue > 140)
            AVMod = 0.5;
        else if (analeticValue > 180)
            AVMod = 0.25;*/

       
        fatiguePoints.set(fatiguePoints.get() + 0.333 * AVMod);
        
        character.setCombatStats(character);
        //recoverableFP += 0.333 * AVMod;
    }

    // Applies FP for a unit of heavy physical work 
    public void StrenuousWork()
    {
        //Debug.Log("Pass Strenuous Work");
    	double AVMod = TrooperUtility.getAVMod(analeticValue);

        fatiguePoints.set(fatiguePoints.get() + (1.0 * AVMod));
        //recoverableFP += 1.0 * AVMod;

        strenuousWorkCalls++; 
        
        character.setCombatStats(character);
        
    }

    public void AddRecoveryTime(float time)
    {
    	if(fatiguePoints.get() == 0) {
    		physicalRecoveryTime.set(0);
    		return; 
    	}
    	
    	physicalRecoveryTime.set(physicalRecoveryTime.get() + time);
    	float count = physicalRecoveryTime.get();
        //System.out.println("Seconds: "+count);
        //System.out.println("FP: "+fatiguePoints.get());
        
        
        
        for (int i = 0; i < count; i++) {
            double recovery = ((double) analeticValue / 10.0) / 12.0;
            //System.out.println("Recovery("+i+"): "+recovery);
            //System.out.println("Pre Recovery FP: "+fatiguePoints.get());
            
            //System.out.println("Diff: "+(fatiguePoints.get() - recovery));
            
            if (fatiguePoints.get() - recovery < 0) {
            	fatiguePoints.set(0);
            }
            else {
            	fatiguePoints.set(fatiguePoints.get() - recovery);
            }
            //System.out.println("Post Recovery FP: "+fatiguePoints.get());

            //if (physicalRecoveryTime.get() - 1 < 0) { physicalRecoveryTime.set(0); } else { physicalRecoveryTime.set(physicalRecoveryTime.get() - 1); }
        }
        physicalRecoveryTime.set(0);
        
        character.setCombatStats(character);
        
    }

    public void AddStrenuousActivityTime(float time)
    {
        strenuousWork = true; 
        actionElapsedTime.set(actionElapsedTime.get() + time);
    }
    
    public void AddLightActivityTime(float time)
    {
        strenuousWork = false;
        actionElapsedTime.set(actionElapsedTime.get() + time);
    }

    public String ToString()
    {
        String rslt = "";

        /*public int fatiguePoints;
        public int recoverablePhysicalFatiguePoints; 
        public int analeticValue;
        public bool strenuousWork; 
        public int actionElapsedTime;
        public int physicalRecoveryElapsedTime; 
        public int fatigueDifficultyDice;*/

        rslt += "Fatigue Points: " + fatiguePoints + "\n";
        rslt += "Recoverable Physical Fatigue Points: " + recoverableFP + "\n";
        rslt += "Analetic Value: " + analeticValue + "\n";
        rslt += "Strenuous Work: " + strenuousWork + "\n";
        rslt += "Action Elapsed Time: " + actionElapsedTime + "\n";
        rslt += "Physical Recovery Elapsed Time: " + physicalRecoveryTime + "\n";

        return rslt; 
    }

    


}
