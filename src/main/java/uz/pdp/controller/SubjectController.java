package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.Subject;
import uz.pdp.repository.SubjectRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/subject")
public class SubjectController {

    @Autowired
    SubjectRepository subjectRepository;

    //todo Create
    @RequestMapping(method = RequestMethod.POST)
    public String addSubject(@RequestBody Subject subject) {
        boolean existsByName = subjectRepository.existsByName(subject.getName());
        if (existsByName)
            return "This subject already exist";
        subjectRepository.save(subject);
        return "Subject added";
    }

    //todo READ
//    @RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public List<Subject> getSubjects() {
        List<Subject> subjectList = subjectRepository.findAll();
        return subjectList;
    }

    //todo Update

    @PutMapping("/{id}")
    public String editSubject(@PathVariable Integer id, @RequestBody Subject subject){
        Optional<Subject> subjectOptional = subjectRepository.findById(id);
        if (subjectOptional.isPresent()){
            Subject editingSubject = subjectOptional.get();
            editingSubject.setName(subject.getName());
            subjectRepository.save(editingSubject);
            return "This subject edited";
        }
        return "Subject not found";
    }

    //todo Delete

    @DeleteMapping("/{id}")
    public String deleteSubject(@PathVariable Integer id){
        subjectRepository.deleteById(id);
        return "Subject deleted";

    }

}
