package springjacksondemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springjacksondemo.dao.StudentDAO;
import springjacksondemo.model.Student;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentDAO studentDAO;
	
	@Override
	@Transactional
	public List<Student> getStudents() {

		return studentDAO.getStudents();
	}

	@Override
	@Transactional
	public void saveStudent(Student theStudent) {

		studentDAO.saveStudent(theStudent);
	}

	@Override
	@Transactional
	public Student getStudent(int theId) {
		
		return studentDAO.getStudent(theId);
	}

	@Override
	@Transactional
	public void deleteStudent(int theId) {
		
		studentDAO.deleteStudent(theId);
	}
}





