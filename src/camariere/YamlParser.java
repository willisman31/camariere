package camariere;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class YamlParser {

	private File yamlFile;
	
	public YamlParser(File f) {
		this.yamlFile = f;
	}
	
	public YamlParser(String str) {
		this.yamlFile = new File(str);
	}
	
	private String read() throws YamlParsingError{
		try {
			Scanner yamlParser = new Scanner(this.yamlFile, "r");
			String temp = "";
			while (yamlParser.hasNextLine()) {
				temp += yamlParser.nextLine();
			}
			return temp;
		} catch (FileNotFoundException f){
			return null;
		}
	}
}
