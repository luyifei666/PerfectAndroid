package maitong.com.perfect.bean;

public class Course {
	//课程名称
	private String course;
	private String courseId;
	private String courseTableId;
	private String index;
	private String pinyin;
	private String school;
	private String schoolId;
	private String show;
	private String teacher;
	private String x;
	private String y;
	public Course() {
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getCourseTableId() {
		return courseTableId;
	}
	public void setCourseTableId(String courseTableId) {
		this.courseTableId = courseTableId;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getShow() {
		return show;
	}
	public void setShow(String show) {
		this.show = show;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	@Override
	public String toString() {
		return "Course [course=" + course + ", courseId=" + courseId
				+ ", courseTableId=" + courseTableId + ", index=" + index
				+ ", pinyin=" + pinyin + ", school=" + school + ", schoolId="
				+ schoolId + ", show=" + show + ", teacher=" + teacher + ", x="
				+ x + ", y=" + y + "]";
	}



}
