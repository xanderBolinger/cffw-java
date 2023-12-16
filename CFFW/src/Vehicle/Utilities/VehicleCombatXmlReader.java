package Vehicle.Utilities;

import java.util.ArrayList;
import java.util.Arrays;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import Vehicle.Vehicle;
import Vehicle.Combat.VehicleTurret;

public class VehicleCombatXmlReader {

	public static ArrayList<VehicleTurret> getVehicleTurrets(Document vehicleData, Vehicle vehicle) throws Exception {
		
		ArrayList<VehicleTurret> turrets = new ArrayList<VehicleTurret>();
		
		NodeList turretList = vehicleData.getElementsByTagName("turret");
		for (int i = 0; i < turretList.getLength(); i++) {
			Element turretNode = (Element) turretList.item(i);
			String turretName = turretNode.getElementsByTagName("turret_name").item(0).getTextContent();
			int facingWidth = Integer.parseInt(turretNode.getElementsByTagName("facing_width").item(0).getTextContent());
			int rotationSpeed = Integer.parseInt(turretNode.getElementsByTagName("rotation_speed").item(0).getTextContent());
			boolean canRotate = Boolean.parseBoolean(turretNode.getElementsByTagName("can_rotate").item(0).getTextContent());
			int minFacing = Integer.parseInt(turretNode.getElementsByTagName("min_facing").item(0).getTextContent());
			int maxFacing = Integer.parseInt(turretNode.getElementsByTagName("max_facing").item(0).getTextContent());
			var turret = new VehicleTurret(turretName, facingWidth, rotationSpeed, canRotate, minFacing, maxFacing);
			
			var positionString = turretNode.getElementsByTagName("positions").item(0).getTextContent();
			var positionNames = positionString.split(",");
			
			for(var name : positionNames)
				turret.addPosition(name, vehicle);
			
			turrets.add(turret);
		}
		
		return turrets;
	}
	
}
