package uz.pdp.payload;

import lombok.Data;

@Data
public class StudentDto {

    //Student
    private String firstName;
    private String lastName;
    //Address
    private String city;
    private String district;
    private String street;
    //Group
    private Integer groupId;
    //


}
