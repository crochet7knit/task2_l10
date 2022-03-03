package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.Faculty;
import uz.pdp.entity.University;
import uz.pdp.payload.FacultyDto;
import uz.pdp.repository.FacultyRepository;
import uz.pdp.repository.UniversityRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/faculty")
public class FacultyController {

    @Autowired
    FacultyRepository facultyRepository;
    @Autowired
    UniversityRepository universityRepository;

    //todo vazirlik un
    @GetMapping
    public List<Faculty> getFaculties() {
        List<Faculty> all = facultyRepository.findAll();
        return all;
    }

    //todo universitet xodimi un

    @GetMapping("/byUniversityId/{universityId}")
    public List<Faculty> getFacultiesByUniversityId(@PathVariable Integer universityId) {
        List<Faculty> allByUniversityId = facultyRepository.findAllByUniversityId(universityId);
        return allByUniversityId;
    }

    //todo create

    @PostMapping
    public String addFaculty(@RequestBody FacultyDto facultyDto) {
        boolean exists = facultyRepository.existsByNameAndUniversityId(facultyDto.getName(), facultyDto.getUniversityId());
        if (exists)
            return "This university such faculty exist!";
        Faculty faculty = new Faculty();
        faculty.setName(facultyDto.getName());
        Optional<University> universityOptional = universityRepository.findById(facultyDto.getUniversityId());
        if (!universityOptional.isPresent())
            return "University not found";
        faculty.setUniversity(universityOptional.get());
        facultyRepository.save(faculty);
        return "faculty saved";
    }

                            //todo update
    @PutMapping("/{id}")
    public String editedFaculty(@PathVariable Integer id, @RequestBody FacultyDto facultyDto) {
        Optional<Faculty> facultyOptional = facultyRepository.findById(id);
        if (facultyOptional.isPresent()) {
//            return "Not found this facultyId";
            Faculty faculty = facultyOptional.get();
            faculty.setName(facultyDto.getName());

            Optional<University> universityOptional = universityRepository.findById(facultyDto.getUniversityId());
            if (!universityOptional.isPresent()) {
                return "Noy found this universityId";
            }
            faculty.setUniversity(universityOptional.get());

            facultyRepository.save(faculty);
            return "Faculty edited!";
        }
            return "Faculty not found!";
    }
    //todo DELETE

    @DeleteMapping("/{id}")
    public String deleteFaculty(@PathVariable Integer id) {
        Optional<Faculty> facultyOptional = facultyRepository.findById(id);
        if (!facultyOptional.isPresent())
            return "Faculty not found!";

        facultyRepository.deleteById(id);
        return "Faculty deleted!";
    }
}
