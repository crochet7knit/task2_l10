package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.Address;
import uz.pdp.entity.Group;
import uz.pdp.entity.Student;
import uz.pdp.payload.StudentDto;
import uz.pdp.repository.AddressRepository;
import uz.pdp.repository.GroupRepository;
import uz.pdp.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    GroupRepository groupRepository;

    //todo 1. VAZIRLIK
    @GetMapping("/forMinistry")
    public Page<Student> getStudentForMinistry(@RequestParam(required = false, defaultValue = "1") int size,
                                               @RequestParam(required = false, defaultValue = "0") int page){

        Pageable pageable= PageRequest.of(page,size);
        Page<Student> studentPage = studentRepository.findAll(pageable);
        return studentPage;
    }

    //todo 2.UNIVERSITY
    @GetMapping("/forUniversity/{universityId}")
    public Page<Student> getStudentForUniversity(@PathVariable Integer universityId , @RequestParam int page){

        Pageable pageable= PageRequest.of(page,10);
        Page<Student> studentPage = studentRepository.findAllByGroup_Faculty_UniversityId(universityId,pageable);
        return studentPage;
    }

    //todo 3.FACULTY DEKANAT
    @GetMapping("/forDepartment/{departmentId}")
    public Page<Student> getStudentForDepartment(@PathVariable Integer departmentId, @RequestParam int page){

        Pageable pageable= PageRequest.of(page,10);
        Page<Student> studentPage = studentRepository.findAllByGroup_FacultyId(departmentId,pageable);
        return studentPage;
    }

    //todo 4.GROUP OWNER

    @GetMapping("/forGroupOwner/{groupId}")
    public Page<Student> getStudentForGroupOwner(@PathVariable Integer groupId, @RequestParam int page){

        Pageable pageable= PageRequest.of(page,10);
        Page<Student> studentPage = studentRepository.findAllByGroupId(groupId,pageable);
        return studentPage;
    }


    @GetMapping
    public List<Student> getAll(){
        return studentRepository.findAll();
    }
    @GetMapping("/{id}")
    public Student getOne(@PathVariable Integer id){
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (!optionalStudent.isPresent()) {
            return new Student();
        }
        return optionalStudent.get();
    }

    //todo create
    @PostMapping
    public String addStudent(@RequestBody StudentDto studentDto) {
        Optional<Group> groupOptional = groupRepository.findById(studentDto.getGroupId());
        if (!groupOptional.isPresent())
            return "Not found such group!";

        Student student = new Student();
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());

        Group group = groupOptional.get();
        student.setGroup(group);

        Address address = new Address();
        address.setCity(studentDto.getCity());
        address.setDistrict(studentDto.getDistrict());
        address.setStreet(studentDto.getStreet());
        Address savedAddress = addressRepository.save(address);

        student.setAddress(savedAddress);
        studentRepository.save(student);
        return "STUDENT SAVED!";
    }

    @PutMapping("/{id}")
    public String updateStudent(@PathVariable Integer id ,@RequestBody StudentDto studentDto) {
        Optional<Group> groupOptional = groupRepository.findById(studentDto.getGroupId());
        if (!groupOptional.isPresent())
            return "Not found such group!";
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (!optionalStudent.isPresent())
            return "Not found such student id!";
        Student student = optionalStudent.get();
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());

        Address address = student.getAddress();
        address.setCity(studentDto.getCity());
        address.setDistrict(studentDto.getDistrict());
        address.setStreet(studentDto.getStreet());
        Address savedAddress = addressRepository.save(address);

        student.setAddress(savedAddress);
        studentRepository.save(student);
        return "STUDENT UPDATED!";
    }

    @DeleteMapping("/{id}")
    public String addStudent(@PathVariable Integer id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (!optionalStudent.isPresent())
            return "Not found such student id!";
        studentRepository.deleteById(id);
        return "student deleted!";

    }
}
