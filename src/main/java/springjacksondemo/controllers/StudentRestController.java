package springjacksondemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import springjacksondemo.error.StudentNotFoundException;
import springjacksondemo.model.Student;
import springjacksondemo.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    @Autowired
    private StudentService service;

    @GetMapping("/students")
    public List<Student> getStudents(){

        return service.getStudents();
    }
    @GetMapping("/students/{id}")
    public Student getStudentById(@PathVariable int id){

        if(service.getStudent(id)==null)
            throw new StudentNotFoundException("Student id ("+id+") not found");

        return service.getStudent(id);
    }

    @DeleteMapping("/students/{id}")
    public String deleteStudentById(@PathVariable int id){

       service.deleteStudent(id);

       return "Student with id "+id+" is deleted";
    }

    @PostMapping("/students")
    public Student saveStudent(@RequestBody Student student){

        int studentId = 0;
        student.setId(studentId);
        service.saveStudent(student);

        return student;
    }

    @PutMapping("/students")
    public Student updateStudent(@RequestBody Student student){

        service.saveStudent(student);

        return student;
    }

}
