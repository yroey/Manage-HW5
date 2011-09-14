package cs236369.hw5.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Course extends Base {

  public static String tableName = "courses";

  public String getTableName() {
    return tableName;
  }

  public Course(int id) {
    super(id);
  }

  public Course() {
    super();
  }

  public Course(ResultSet rs) {
    super(rs);
  }

  public static Course[] getAll() throws SQLException{
	  ResultSet rs = Utils.executeQuery("SELECT * FROM courses");
	  ArrayList<Course> courses = new ArrayList<Course>();
	  while(rs.next()) {
		  courses.add(new Course(rs));
	  }
	  Course[] arrayCourses = new Course[courses.size()];
	  courses.toArray(arrayCourses);
	  return arrayCourses;
  }

  @Override
  void setFieldTypes() {
	fieldsTypes.put("id", "int");
	fieldsTypes.put("group", "int");
    fieldsTypes.put("name", "string");
    fieldsTypes.put("capacity", "int");
    fieldsTypes.put("credit_points", "int");
    fieldsTypes.put("course_description", "string");
    fieldsTypes.put("creator_id", "int");
  }

  public String getName() {
    return getStringField("name");
  }

  public static Course[] GetByIds(int[] ids) throws SQLException {
    ResultSet rs = Utils.getTableRowsByIds(tableName, ids);
    ArrayList<Course> courses = new ArrayList<Course>();
    while(rs.next()) {
      courses.add(new Course(rs));
    }
    Course[] arrayCourses = new Course[courses.size()];
    courses.toArray(arrayCourses);
    return arrayCourses;
  }

  public Student[] getStudents() throws SQLException {
    ResultSet rs = Utils.executeQuery("SELECT * FROM courses_students WHERE course_id = " + getId());
    ArrayList<Integer> ids = new ArrayList<Integer>();
    while(rs.next()) {
      ids.add(rs.getInt("course_id"));
    }
    int[] arrayIds = new int[ids.size()];
    for (int i=0; i < arrayIds.length; i++){
      arrayIds[i] = ids.get(i).intValue();
    }
    return Student.GetByIds(arrayIds);
  }

}
