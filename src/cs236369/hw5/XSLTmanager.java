package cs236369.hw5;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import cs236369.hw5.dal.Xslt;


/**
 * @author Dennis
 *
 */
public class XSLTmanager
{
	public static int days_per_week = 7;
	public static int hours_per_day = 10;

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
			contents += "<!DOCTYPE timeTable [" + 
						"<!ELEMENT timeTable (day+)> " +
						"<!ELEMENT day (hour+)> " + 
						"<!ELEMENT hour (#PCDATA)> " +  
						"<!ATTLIST day value CDATA #REQUIRED> " + 
						"<!ATTLIST hour value CDATA #REQUIRED> ]>\n";
			contents += "<timeTable>\n";
			for (int day = 0; day < days_per_week; ++day){
				contents += "<day value='" + day + "'>\n";
				for (int hour = 0; hour < hours_per_day; ++hour){
					contents += "<hour value='" + hour + "'>" + 
					(timetable[day][hour].getName().equals("") ? "none" : timetable[day][hour].getName()) + "</hour>\n";
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
	public static void generate(int studId, InputStream xslContent, ByteArrayOutputStream baos){

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
			newTransformer(new javax.xml.transform.stream.StreamSource(xslContent)); 
			// Create an empty DOMResult object for the output.
			// Perform the transformation. 
			transformer.transform(new DOMSource(inDoc),
					new StreamResult(baos));
			// Now you can get the output Node from the DOMResult. 
			//Node node = domResult.getNode();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public static void applyStyleSheet(int studId, int xsltId, ByteArrayOutputStream baos){
		try
		{
			Connection conn = Utils.getConnection();
			String query = "SELECT * FROM xslt WHERE id=?;";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, xsltId);
			Logger.log(ps.toString());
			ResultSet rs = ps.executeQuery();		
			if (!rs.next()){
				Utils.closeConnection(rs, ps, conn);
				//ERROR
			}
			InputStream xslContent = rs.getBinaryStream("content");
			Utils.closeConnection(rs, ps, conn);
			generate(studId, xslContent, baos);	
			
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void upload(String name, String content,int ulid){
		ByteArrayInputStream bais = new ByteArrayInputStream(content.getBytes());
		Connection conn = Utils.getConnection();
		String str = "INSERT INTO xslt (name, content,uid) VALUES (? ,?, ?);";
		PreparedStatement ps;
		try
		{
			ps = conn.prepareStatement(str);
			ps.setString(1, name);
			ps.setBinaryStream(2, bais, bais.available());
			ps.setInt(3, ulid);
			ps.executeUpdate();
			Utils.closeConnection(null, ps, conn);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
