package Melee;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MeleeArmorLocationData {
	public static List<String> allBodyParts;
    public static List<String> headUpper;
    public static List<String> headLower;
    public static List<String> torsoUpper;
    public static List<String> torsoLower;
    public static List<String> legsUpper;
    public static List<String> legsLower;
    public static List<String> armsUpper;
    public static List<String> armsLower;

    public MeleeArmorLocationData() {
        List<String> bodyParts = new ArrayList<>(Arrays.asList(
            "Forehead",
            "Eye",
            "Mouth",
            "Neck",
            "Base of Neck",
            "Shoulder Socket",
            "Shoulder Scapula",
            "Lung",
            "Heart",
            "Liver",
            "Stomach",
            "Stomach-Kidney",
            "Liver-Spine",
            "Liver-Kidney",
            "Intestines",
            "Spine",
            "Instetines-Pelvis",
            "Hip Socket",
            "Upper Arm",
            "Forearm",
            "Hand",
            "Thigh",
            "Shin",
            "Foot",
            "Knee",
            "Skull",
            "Face",
            "Throat",
            "Shoulder",
            "Upper Chest",
            "Lower Chest",
            "Pelvis"
        ));

        allBodyParts = bodyParts;
        headUpper = new ArrayList<>();
        headLower = new ArrayList<>();
        armsUpper = new ArrayList<>();
        armsLower = new ArrayList<>();
        legsUpper = new ArrayList<>();
        legsLower = new ArrayList<>();
        torsoUpper = new ArrayList<>();
        torsoLower = new ArrayList<>();

        for (String part : bodyParts) {
            if (part.contains("Skull") || part.contains("Forehead") || part.contains("Eye"))
                headUpper.add(part);
            if (part.contains("Mouth") || part.contains("Neck") || part.contains("Throat") || part.contains("Face"))
                headLower.add(part);
            if (part.contains("Shoulder") || part.contains("Upper Arm") || part.contains("Shoulder Socket") || part.contains("Shoulder Scapula"))
                armsUpper.add(part);
            if (part.contains("Forearm") || part.contains("Hand"))
                armsLower.add(part);
            if (part.contains("Thigh") || part.contains("Hip Socket"))
                legsUpper.add(part);
            if (part.contains("Shin") || part.contains("Foot") || part.contains("Knee"))
                legsLower.add(part);
            if (part.contains("Upper Chest") || part.contains("Heart") || part.contains("Lung") || part.contains("Spine"))
                torsoUpper.add(part);
            if (part.contains("Lower Chest") || part.contains("Pelvis") || part.contains("Stomach") || part.contains("Spine")
                || part.contains("Liver-Spine") || part.contains("Liver-Kidney") || part.contains("Liver") || part.contains("Stomach-Kidney"))
                torsoLower.add(part);
        }
    }
}

