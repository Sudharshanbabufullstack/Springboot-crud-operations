package com.example.studentcrud.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.studentcrud.entity.Student;
import com.example.studentcrud.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    // CREATE
    public Student addStudent(Student student) {
        return repository.save(student);
    }

    // READ all
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    // READ by ID
    public Student getStudentById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Student with ID " + id + " not found"
                ));
    }

    // UPDATE
    public Student updateStudent(Long id, Student updatedStudent) {
        return repository.findById(id)
                .map(student -> {
                    student.setName(updatedStudent.getName());
                    student.setEmail(updatedStudent.getEmail());
                    student.setAge(updatedStudent.getAge());
                    return repository.save(student);
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Student with ID " + id + " not found"
                ));
    }

    // DELETE
    public void deleteStudent(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Student with ID " + id + " not found"
            );
        }
    }
}
