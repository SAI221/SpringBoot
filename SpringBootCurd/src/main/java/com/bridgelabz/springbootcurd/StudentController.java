package com.bridgelabz.springbootcurd;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class StudentController {
	 @Autowired
	    StudentRepository studentRepository;
	    //Create
	    @RequestMapping(value = "/student", method = RequestMethod.POST)
	    public Student createStudent(@RequestBody Student student)
	    {
	        return studentRepository.save(student);
	    }
	    
	    //Read
	    @RequestMapping(value="/student/{id}", method = RequestMethod.GET)
	    public Optional<Student> getStudentById(@PathVariable long id)
	    {
	        return studentRepository.findById(id);
	    }
	    
	    @RequestMapping(value="/students", method = RequestMethod.GET)
	    public List getAllStudents()
	    {
	        return (List)studentRepository.findAll();
	    }
	    
	    //Update
	    @RequestMapping(value = "/student", method = RequestMethod.PUT)
	    public Student updateStudent(@RequestBody Student student)
	    {
	        return studentRepository.save(student);
	    }
	    
	    //Delete
	    @RequestMapping(value = "/student/{id}", method = RequestMethod.DELETE)
	    public void deleteStudent(@PathVariable long id)
	    {
	        studentRepository.deleteById(id);
	    }
}
