package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.Address;
import uz.pdp.entity.University;
import uz.pdp.payload.UniversityDto;
import uz.pdp.repository.AddressRepository;
import uz.pdp.repository.UniversityRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class UniversityController {

    @Autowired
    UniversityRepository universityRepository;

    @Autowired
    AddressRepository addressRepository;

    //todo READ

    @RequestMapping(value = "/university", method = RequestMethod.GET)
    public List<University> getUniversities() {
        List<University> universityList = universityRepository.findAll();
        return universityList;
    }

    //todo CREATE

    @RequestMapping(value = "/university", method = RequestMethod.POST)
    public String addUniversity(@RequestBody UniversityDto universityDto) {
        Address address = new Address();
        address.setCity(universityDto.getCity());
        address.setDistrict(universityDto.getDistrict());
        address.setStreet(universityDto.getStreet());
        Address savedAddress = addressRepository.save(address);

        University university = new University();
        university.setName(universityDto.getName());
        university.setAddress(savedAddress);
        universityRepository.save(university);

        return "University saved";
    }

    //todo Update

    @RequestMapping(value = "/university/{id}", method = RequestMethod.PUT)
    public String editUniversity(@PathVariable Integer id, @RequestBody UniversityDto universityDto) {
        Optional<University> universityOptional = universityRepository.findById(id);
        if (universityOptional.isPresent()) {

            University editUniversity = universityOptional.get();
            editUniversity.setName(universityDto.getName());
            Address address = editUniversity.getAddress();
            address.setCity(universityDto.getCity());
            address.setDistrict(universityDto.getDistrict());
            address.setStreet(universityDto.getStreet());
            addressRepository.save(address);

            universityRepository.save(editUniversity);
            return "University edited";
        }
        return "University not found";
    }

    //todo Delete

    @RequestMapping(value = "/university/{id}", method = RequestMethod.DELETE)
    public String deleteUniversity(@PathVariable Integer id) {
        universityRepository.deleteById(id);
        return "University deleted  ";
    }


}
