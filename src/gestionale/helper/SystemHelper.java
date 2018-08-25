package gestionale.helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SystemHelper {
	
	public static String getPathByUser(){
		String userDir = System.getProperty("user.home")+"/Desktop";
		Calendar now = Calendar.getInstance();
		String filename = String.valueOf(now.getTimeInMillis());
		
		JFileChooser chooser = new JFileChooser(userDir);
		chooser.setDialogTitle("Select a text file to save the report of your Order");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text File (.txt)", "txt", "text");
		chooser.setFileFilter(filter);
		chooser.setSelectedFile(new File(filename));
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int option = chooser.showSaveDialog(null);
		//Se seleziono un file entro nell'IF e stampo il mio file
		if (option == JFileChooser.APPROVE_OPTION){
			String filePath = chooser.getSelectedFile().getAbsolutePath();
			
			if (!filePath.endsWith(".txt"))
				filePath = filePath + ".txt";
			return filePath;
		}else{
			return userDir + "/" + filename + ".txt";
		}
	}
	
	public static boolean writeFile(String filename, String toWrite){
		try {
			PrintWriter pWriter = new PrintWriter(new FileWriter(filename));
			pWriter.print(toWrite);
			pWriter.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Something went wrong in saving file.");
			return false;
		}
	}

}
