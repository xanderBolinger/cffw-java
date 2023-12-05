package Explosion.Shrap;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.opencsv.exceptions.CsvException;
import com.opencsv.*;

import UtilityClasses.ExcelUtility;

public class ShootingData {
    public int range;
    public int prone;
    public int kneel;
    public int stand;
    public int coverProne;
    public int coverKneel;
    public int coverStand;

    @Override
    public String toString() {
        return "ShootingData{" +
                "range=" + range +
                ", prone=" + prone +
                ", kneel=" + kneel +
                ", stand=" + stand +
                ", coverProne=" + coverProne +
                ", coverKneel=" + coverKneel +
                ", coverStand=" + coverStand +
                '}';
    }

    public static void main(String[] args) throws CsvException {
        String filePath = ExcelUtility.path+"\\PC Hit Calc Xlsxs\\shrap_cover.csv";

        try {
            List<ShootingData> shootingDataList = readCSV(filePath);
            for (ShootingData data : shootingDataList) {
                System.out.println(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<ShootingData> readCSV(String filePath) throws IOException, CsvException {
        Path path = Paths.get(filePath);
        try (CSVReader csvReader = new CSVReader(Files.newBufferedReader(path))) {
            List<String[]> allData = csvReader.readAll();
            // Assuming the header is present in the CSV file
            allData.remove(0);

            List<ShootingData> shootingDataList = new ArrayList<>();
            for (String[] row : allData) {
                ShootingData shootingData = new ShootingData();
                shootingData.range = Integer.parseInt(row[0]);
                shootingData.prone = Integer.parseInt(row[1]);
                shootingData.kneel = Integer.parseInt(row[2]);
                shootingData.stand = Integer.parseInt(row[3]);
                shootingData.coverProne = Integer.parseInt(row[4]);
                shootingData.coverKneel = Integer.parseInt(row[5]);
                shootingData.coverStand = Integer.parseInt(row[6]);
                shootingDataList.add(shootingData);
            }
            return shootingDataList;
        }
    }
}

