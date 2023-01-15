package SaveRunner;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.Gson;

import Game.Game;
import Ship.Ship;

public class JsonSaveRunner {

	public static String path = System.getProperty("user.dir");
	
	public static String saveGame(Game game) {
		//System.out.println("Save game: "+path);
		return new Gson().toJson(game);
	}

	public static Game loadGame(String json) {
		Game ship = new Gson().fromJson(json, Game.class);
		return ship;
	}
	
	public static void saveFile(String companyName, String json) throws IOException {
		
		//System.out.println("Save file");
		
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

	public static String loadFile() throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(path));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON FILES", "json", "json");
		fileChooser.setFileFilter(filter);
		int result = fileChooser.showOpenDialog(new JPanel());
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			Path path = Path.of(selectedFile.getAbsolutePath());
			return Files.readString(path);
			// System.out.println("Selected file: " + selectedFile.getAbsolutePath());
			// Path path = Path.of(selectedFile.getAbsolutePath());
			// System.out.println("Content: "+Files.readString(path));
		}

		throw new IOException("Load File not found");
	}

}
