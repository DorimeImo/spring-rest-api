package springjacksondemo.dao;

import springjacksondemo.model.Student;

import java.util.List;

public interface StudentDAO {

	public List<Student> getStudents();

	public void saveStudent(Student theStudent);

	public Student getStudent(int theId);

	public void deleteStudent(int theId);
	
}
