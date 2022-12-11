package com.example.Sprint2ManageNote.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class Patient {

    private Integer idPatient;

    private String firstName;

    private String lastName;

    private Integer age;

    private String address;

    private Sexe sexe;

    private Integer phoneNumber;


}
