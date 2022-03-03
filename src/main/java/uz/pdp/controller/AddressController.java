package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.Address;
import uz.pdp.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    AddressRepository addressRepository;

    //todo READ ALL

    @GetMapping
    public List<Address> getAddresses() {
        List<Address> addressList = addressRepository.findAll();
        return addressList;
    }

    //todo READ ONE

    @GetMapping("/{id}")
    public Address getAddress(@PathVariable Integer id) {
        Optional<Address> addressOptional = addressRepository.findById(id);
        if (!addressOptional.isPresent())
            return null;
        Address address = addressOptional.get();
        return address;
    }

    //todo CREATE
    @PostMapping
    public String addAddress(@RequestBody Address address) {
        Address createdAddress = new Address();
        createdAddress.setCity(address.getCity());
        createdAddress.setDistrict(address.getDistrict());
        createdAddress.setStreet(address.getStreet());
        addressRepository.save(createdAddress);
        return "Address savede";
    }

    //todo UPDATE

    @PutMapping("/{id}")
    public String editAddress(@PathVariable Integer id, @RequestBody Address address) {
        Optional<Address> addressOptional = addressRepository.findById(id);
        if (!addressOptional.isPresent())
            return "Not found such id";
        Address editedAddress = addressOptional.get();
        editedAddress.setCity(address.getCity());
        editedAddress.setDistrict(address.getDistrict());
        editedAddress.setStreet(address.getStreet());

        addressRepository.save(editedAddress);
        return "Address edited!";


    }

    @DeleteMapping("/{id}")
    public String deleteAddress(@PathVariable Integer id) {
        Optional<Address> addressOptional = addressRepository.findById(id);
        if (!addressOptional.isPresent())
            return "Not found given address";
        addressRepository.deleteById(id);
        return "Address deleted";
    }

}
