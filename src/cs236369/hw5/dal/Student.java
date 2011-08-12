package cs236369.hw5.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Student extends Base {

  static String tableName = "students";

  @Override
  void setFieldTypes() {
    fieldsTypes.put("username", "string");
  }

  public String getTableName() {
    return tableName;
  }
  public Student(ResultSet rs) {
    super(rs);
  }

  public Student() {
    super();
  }

  public Student(int id) {
    super(id);
  }

  public static Student[] GetByIds(int[] ids) throws SQLException {
    ResultSet rs = Utils.getTableRowsByIds(tableName, ids);
    ArrayList<Student> students = new ArrayList<Student>();
    do {
      students.add(new Student(rs));
    } while (!rs.isLast());
    Student[] arrayStudents = new Student[students.size()];
    return arrayStudents;
  }

  public Course[] getCourses() throws SQLException {
    ResultSet rs = Utils.executeQuery("SELECT * FROM courses_students WHERE student_id = " + getId());
    ArrayList<Integer> ids = new ArrayList<Integer>();
    while(rs.next()) {
      ids.add(rs.getInt("student_id"));
    }
    int[] arrayIds = new int[ids.size()];
    for (int i=0; i < arrayIds.length; i++){
      arrayIds[i] = ids.get(i).intValue();
    }
    Course[] courses =  Course.GetByIds(arrayIds);
    System.out.println(courses.length);
    return courses;
  }
}
