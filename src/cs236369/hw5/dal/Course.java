package cs236369.hw5.dal;


public class Course extends Base {

  public Course(int id) {
    super(id);
  }

  public Course() {
    super();
  }

  @Override
  void setTableName() {
    tableName = "course";
  }

  @Override
  void setFieldTypes() {
    fieldsTypes.put("name", "string");
    fieldsTypes.put("capacity", "int");
  }

}
