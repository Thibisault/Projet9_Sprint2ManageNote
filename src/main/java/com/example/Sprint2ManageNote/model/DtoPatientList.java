package com.example.Sprint2ManageNote.model;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DtoPatientList {

    private List<Patient> patientList= new ArrayList<>();

}
