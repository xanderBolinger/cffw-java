package CreateGame;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import Company.Company;
import Trooper.Trooper;
import Trooper.generateSquad;
import Unit.Unit;
import UtilityClasses.ExcelUtility;

public class JsonSaveRunner {

	public static void main(String[] args) throws IOException {
		String fileContents = loadFileFromName("Alpha CT-62-8075.json");
		System.out.println(fileContents);
		Trooper trooper = loadTrooper(fileContents);
		System.out.println("Trooper name: "+trooper.name);
		//var fileNames = getFileNames();
		//System.out.println("Files: "+fileNames.toString());
		
		/*
		 * 
		 * 
		 * Company company = new Company("BLUFOR", "Company 1", 0, 0, 0, 0, 0, 0, new
		 * ArrayList<Trooper>(), new ArrayList<Unit>());
		 * 
		 * generateSquad squad = new generateSquad("Clone Trooper Phase 1",
		 * "Riflesquad");
		 * 
		 * // Instantiates unit based off of side and type ArrayList<Trooper>
		 * individuals = squad.getSquad();
		 * 
		 * Unit unit = new Unit("Call Sign", 0, 0, individuals, 100, 0, 100, 0, 0, 20,
		 * 0, "No Contact");
		 * 
		 * company.addUnit(unit);
		 */
		// saveCompany(company);

		/*
		 * String json = saveCompany(company); System.out.println("JSON: "+ json);
		 * 
		 * System.out.println("Company Name: "+loadCompany(json).getName());
		 */
		/*
		 * try { saveFile("company", ""); //loadFile(); } catch (IOException e) {
		 * e.printStackTrace(); }
		 */
	}

	public static Company loadCompany(String json) throws JsonMappingException, JsonProcessingException {
		// return new Gson().fromJson(json, Company.class);
		return new Gson().fromJson(json, Company.class);
	}

	public static String saveCompany(Company company) throws JsonProcessingException {

		return new Gson().toJson(company);
	}

	public static String saveTrooper(TrooperJson trooper) {
		return new Gson().toJson(trooper);
	}

	public static Trooper loadTrooper(String json) {
		TrooperJson trooperJson = new Gson().fromJson(json, TrooperJson.class);
		System.out.println("Trooper Json Name: "+trooperJson.name);
		return trooperJson.getTrooper();
	}
	
	public static void saveFile(String companyName, String json) throws IOException {
		// parent component of the dialog
		JFrame parentFrame = new JFrame();

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON FILES", "json", "json");
		fileChooser.setFileFilter(filter);
		fileChooser.setSelectedFile(new File(companyName + ".json"));
		int userSelection = fileChooser.showSaveDialog(parentFrame);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			FileWriter fw = new FileWriter(fileToSave);
			fw.write(json);
			System.out.println("Save as file: " + fileToSave.getAbsolutePath());
			fw.close();
		}
	}

	public static String loadFileFromName(String fileName) throws IOException {
		String path = ExcelUtility.path+"//Character JSONS//"+fileName;
		return new String(Files.readAllBytes(Paths.get(new File(path).getAbsolutePath())));
	}
	
	public static ArrayList<String> getFileNames() {
        ArrayList<String> fileNames = new ArrayList<>();

        File folder = new File(ExcelUtility.path+"//Character JSONS//");
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Invalid folder path");
            return fileNames;
        }

        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    fileNames.add(fileName);
                }
            }
        }

        return fileNames;
    }

	
	public static String loadFile() throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(ExcelUtility.path));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON FILES", "json", "json");
		fileChooser.setFileFilter(filter);
		int result = fileChooser.showOpenDialog(new JPanel());
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			//return readUsingFiles(selecteFile.);
			return new String(Files.readAllBytes(Paths.get(selectedFile.getAbsolutePath())));
			//Path path = Path.of(selectedFile.getAbsolutePath());
			//return Files.readString(path);
			// System.out.println("Selected file: " + selectedFile.getAbsolutePath());
			// Path path = Path.of(selectedFile.getAbsolutePath());
			// System.out.println("Content: "+Files.readString(path));
		}

		throw new IOException("Load File not found");
	}

}
