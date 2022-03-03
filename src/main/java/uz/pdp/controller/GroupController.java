package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.Faculty;
import uz.pdp.entity.Group;
import uz.pdp.payload.GroupDto;
import uz.pdp.repository.FacultyRepository;
import uz.pdp.repository.GroupRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/group")
public class GroupController {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    FacultyRepository facultyRepository;

    //todo READ

    //todo vazirlik un
    @GetMapping
    public List<Group> getGroups() {
        List<Group> all = groupRepository.findAll();
        return all;
    }


    //todo universitet xodimi un
    @GetMapping("/byUniversityId/{universityId}")
    public List<Group> getGroupsByUniversityId(@PathVariable Integer universityId) {
        List<Group> allByUniversityId = groupRepository.findAllByFaculty_UniversityId(universityId);
        List<Group> groupsByUniversityId = groupRepository.getGroupsByUniversityId(universityId);
        List<Group> groupsByUniversityIdNative = groupRepository.getGroupsByUniversityIdNative(universityId);
        return allByUniversityId;
    }

    //todo create

    @PostMapping
    public String addGroups(@RequestBody GroupDto groupDto) {
        Group group = new Group();
        group.setName(groupDto.getName());
        Optional<Faculty> facultyOptional = facultyRepository.findById(groupDto.getFacultyId());
        if (!facultyOptional.isPresent())
            return "Such faculty not found!";
        group.setFaculty(facultyOptional.get());
        groupRepository.save(group);
        return "Group added";
    }

    //todo UPDATE

    @PutMapping("/{id}")
    public String editGroup(@PathVariable Integer id, @RequestBody GroupDto groupDto) {
        Optional<Group> groupOptional = groupRepository.findById(id);
        if (!groupOptional.isPresent())
            return "Not found given group";
        Group editingGroup = groupOptional.get();
        editingGroup.setName(groupDto.getName());

        Optional<Faculty> facultyOptional = facultyRepository.findById(groupDto.getFacultyId());
        if (!facultyOptional.isPresent())
            return "Not found given facultyId";
        editingGroup.setFaculty(facultyOptional.get());

        groupRepository.save(editingGroup);
        return "Group edited";
    }

    //todo DELETE
    @DeleteMapping("/{id}")
    public String deleteGroup(@PathVariable Integer id) {
        Optional<Group> groupOptional = groupRepository.findById(id);
        if (!groupOptional.isPresent())
            return "Not found given group!";

        groupRepository.deleteById(id);
        return "Deleted group";
    }


}












