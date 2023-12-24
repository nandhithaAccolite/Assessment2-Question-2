
package com.example.demo;

import java.io.IOException;

import java.util.List;

import static com.example.demo.ReadExcel.readExcel;


public class Application {
	public static void main(String[] args) throws IOException, IllegalArgumentException {
		List<DataModel> interviewData=readExcel();
		DatabaseManager.createTable();
		DatabaseManager.insertData(interviewData);
		DatabaseManager.teamWithTheMaxInterviews();
		DatabaseManager.teamWithTheMinInterviews();
		DatabaseManager.theTop3Skills();
		DatabaseManager.theTop3Panels();
		DatabaseManager.theSkillsInPeakTime();

		// Step 5: Generate PDF and embed charts
		PdfGenerator.generatePdf(interviewData, "C:\\Users\\linga.nandhitha\\Downloads\\trial2\\demo\\src\\main\\java\\output\\Output1.pdf");
	}
}






