package com.example.Sprint2ManageNote.repository;

import com.example.Sprint2ManageNote.entity.Note;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer>, JpaSpecificationExecutor<Note> {

    public List<Note> findAll(Specification<Note> patientSpecification);
    public List<Note> findAllByIdPatientForNote(Integer idPatient);


    public Optional<Note> findByIdNote(Integer idNote);

    public Optional<Note> findByIdPatientForNote(Integer idPatientForNote);

}
