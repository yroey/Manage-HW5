package cs236369.hw5;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import cs236369.hw5.dal.Course;
import cs236369.hw5.dal.Student;


/**
 * @author Dennis
 *
 */
public class XmlGenerator
{
	public static int days_per_week = 7;
	public static int hours_per_day = 10;
	
	public XmlGenerator(){
		Course[][] timetable = new Course[days_per_week][hours_per_day];
		for (int day = 0; day < days_per_week; ++day) {
			for (int hour = 0; hour < hours_per_day; ++ hour) {
				timetable[day][hour] = new Course();
			}
		}
	}
	public static void generateTimetableXml(int studID, String filePath){
		Student s = new Student(studID);
		try
		{
			Course[][] timetable = s.getTimeTable(7, 10);
			timeTableToXml(filePath, timetable);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private static void timeTableToXml(String filePath, Course[][] timetable)
	{
		String contents = new String();
		contents += "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n";
		contents += "<timeTable>\n";
		for (int day = 0; day < days_per_week; ++day){
			contents += "<day>\n";
			for (int hour = 0; hour < hours_per_day; ++hour){
				contents += "<hour" + hour + ">" + 
					(timetable[day][hour].getName().equals("") ? "none" : timetable[day][hour].getName()) + "</hour" + hour + ">\n";
			}
			contents += "</day>\n";
		}
		contents += "</timeTable>\n";
		File xmlFile = new File(filePath);
		xmlFile.delete();
		try
		{
			xmlFile.createNewFile();
			FileWriter fw = new FileWriter(xmlFile);
			fw.write(contents);
			fw.flush();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void generate(int studId, String path, String resultPath){
		
		generateTimetableXml(studId, path);
		// Instantiate a DocumentBuilderFactory. 
		DocumentBuilderFactory dfactory = javax.xml.parsers.DocumentBuilderFactory.newInstance(); 
		// Use the DocumentBuilderFactory to provide access to a DocumentBuilder. 
		DocumentBuilder dBuilder;
		try
		{
			dBuilder = dfactory.newDocumentBuilder();
		// Use the DocumentBuilder to parse the XML input. 
			Document inDoc = dBuilder.parse(path);
			// Generate a Transformer. 			
			Transformer transformer = TransformerFactory.newInstance().newTransformer(new javax.xml.transform.stream.StreamSource("C:\\temp\\test.xsl")); 
			// Create an empy DOMResult object for the output. 
			DOMResult domResult = new javax.xml.transform.dom.DOMResult(); 
			// Perform the transformation. 
			File resultFile = new File(resultPath);
			resultFile.delete();
			resultFile.createNewFile();
			transformer.transform(new DOMSource(inDoc),
                    new StreamResult(new FileOutputStream(resultPath)));
			// Now you can get the output Node from the DOMResult. 
			//Node node = domResult.getNode();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
/*	public static void main(String[] args){
		XmlGenerator x = new XmlGenerator();
		x.generateTimetableXml(1);
		x.generate(null, null);
		
	}*/
}
