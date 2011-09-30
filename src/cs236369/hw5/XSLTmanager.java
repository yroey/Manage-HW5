package cs236369.hw5;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import cs236369.hw5.dal.Course;
import cs236369.hw5.dal.Student;
import cs236369.hw5.dal.Utils;


/**
 * @author Dennis
 *
 */
public class XSLTmanager
{
	public static int days_per_week = 7;
	public static int hours_per_day = 10;

	public static void insertDefaults(){
		//TODO check if exists
		File file = new File("C:\\Users\\Dennis\\git\\manageHW5\\xslt\\default_1.xsl");

		try
		{
			FileInputStream fis = new FileInputStream(file);
			Connection conn = Utils.getConnection();
			String str = "INSERT INTO xslt (name, content) VALUES (? ,?);";
			PreparedStatement ps = conn.prepareStatement(str);
			ps.setString(1, "Default1");
			ps.setBinaryStream(2, fis, (int) file.length());
			ps.executeUpdate();

			file = new File("C:\\Users\\Dennis\\git\\manageHW5\\xslt\\default_2.xsl");
			fis = new FileInputStream(file);
			conn = Utils.getConnection();
			str = "INSERT INTO xslt (name, content) VALUES (? ,?);";
			ps = conn.prepareStatement(str);
			ps.setString(1, "Default2");
			ps.setBinaryStream(2, fis, (int) file.length());
			ps.executeUpdate();

		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private static ByteArrayInputStream timeTableToXml(int studID){
		Course[][] timetable = new Course[days_per_week][hours_per_day];
		for (int day = 0; day < days_per_week; ++day) {
			for (int hour = 0; hour < hours_per_day; ++ hour) {
				timetable[day][hour] = new Course();
			}
		}
		Student s = new Student(studID);
		try
		{
			timetable = s.getTimeTable(7, 10);
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
			ByteArrayInputStream is = new ByteArrayInputStream(contents.getBytes());
			return is;
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;


	}
	public static void generate(int studId, InputStream xslContent, String resultPath){

		ByteArrayInputStream xmlContent = timeTableToXml(studId);
		// Instantiate a DocumentBuilderFactory. 
		DocumentBuilderFactory dfactory = javax.xml.parsers.DocumentBuilderFactory.newInstance(); 
		// Use the DocumentBuilderFactory to provide access to a DocumentBuilder. 
		DocumentBuilder dBuilder;
		try
		{
			dBuilder = dfactory.newDocumentBuilder();
			// Use the DocumentBuilder to parse the XML input.
			Document inDoc = dBuilder.parse(xmlContent);
			// Generate a Transformer. 			
			Transformer transformer = TransformerFactory.newInstance().
			newTransformer(new javax.xml.transform.stream.StreamSource(xslContent/*C:\\temp\\test.xsl"*/)); 
			// Create an empty DOMResult object for the output. 
			//DOMResult domResult = new javax.xml.transform.dom.DOMResult(); 
			// Perform the transformation. 
			File resultFile = new File("C:\\temp\\bla.html");
			resultFile.delete();
			resultFile.createNewFile();
			transformer.transform(new DOMSource(inDoc),
					new StreamResult(new FileOutputStream("C:\\temp\\bla.html"/*resultPath*/)));
			// Now you can get the output Node from the DOMResult. 
			//Node node = domResult.getNode();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public static void applyStyleSheet(int studId, int id){
		try
		{
			Connection conn = Utils.getConnection();
			String query = "SELECT * FROM xslt WHERE id=?;";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			Logger.log(ps.toString());
			ResultSet rs = ps.executeQuery();		
			if (!rs.next()){
				//ERROR
			}
			InputStream xslContent = rs.getBinaryStream("content");
			generate(studId, xslContent, null);	
			Utils.closeConnection(rs, ps, conn);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int upload(String name, String content){
		ByteArrayInputStream bais = new ByteArrayInputStream(content.getBytes());
		Connection conn = Utils.getConnection();
		String str = "INSERT INTO xslt (name, content) VALUES (? ,?);";
		PreparedStatement ps;
		try
		{
			ps = conn.prepareStatement(str);
			ps.setString(1, name);
			ps.setBinaryStream(2, bais, bais.available());
			ps.executeUpdate();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//TODO return id;
		return 0;
	}
}
