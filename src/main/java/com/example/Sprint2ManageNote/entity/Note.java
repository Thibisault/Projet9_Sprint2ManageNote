package com.example.Sprint2ManageNote.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "note")
public class Note {

    @Id
    @Column(name = "idNote", nullable = false)
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idNote;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateNote")
    private Date dateNote;

    @Column(name = "takeNote")
    private String takeNote;

    @Column(name = "idPatientForNote")
    private Integer idPatientForNote;

}
