package com.example.Sprint2ManageNote.service;

import com.example.Sprint2ManageNote.controller.NoteController;
import com.example.Sprint2ManageNote.entity.Note;
import com.example.Sprint2ManageNote.model.Patient;
import com.example.Sprint2ManageNote.model.Sexe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.*;

@Configuration
@SpringBootTest
public class serviceTest {

    @Autowired
    NoteService noteService;

    @Autowired
    NoteController noteController;

    @Value("${server.patient}")
    private String serverPatient;

    @Test
    public void contextLoads() {
    }

    public void createNote(Note note){
        note.setDateNote(new Date());
        note.setIdPatientForNote(99);
        note.setTakeNote("fumeur vertige");
        noteService.saveNewNote(note);
    }

    @Test
    public void takeNote(){
        Note note = new Note();
        this.createNote(note);
        final Note noteResult = noteService.findNoteById(note.getIdNote());
        assertNotNull("Le patitent n'a pas été trouvé grâce à son Id", noteResult);
        noteService.deleteNote(note);
    }

    @Test
    public void getAllNoteFromOnePatient(){
        Note note = new Note();
        Note note_2 = new Note();

        List<Note> noteList = new ArrayList<>();
        noteList.add(note);
        noteList.add(note_2);
        this.createNote(note);
        this.createNote(note_2);

        Patient patient = new Patient();
        patient.setIdPatient(99);
        patient.setAddress("33WithMock");
        patient.setAge(15);
        patient.setFirstName("moi");
        patient.setLastName("toi");
        patient.setPhoneNumber(060606);
        patient.setSexe(Sexe.FEMALE);

        final List<Note> noteResult = noteService.getAllNoteFromOnePatient(patient.getIdPatient());
        for (Note noteTest : noteResult){
            if (noteTest.getIdPatientForNote() != patient.getIdPatient()){
                noteResult.remove(note);
            }
        }

        assertEquals("La méthode n'a pas réussi à récuperer toute les notes", 2, noteResult.size());
        noteService.deleteNote(note);
        noteService.deleteNote(note_2);

    }

    @Test
    public void findNoteById(){
        Note note = new Note();
        this.createNote(note);
        final Note noteResult = noteService.findNoteById(note.getIdNote());
        assertNotNull("La note n'a pas été trouvé grâce à son Id", noteResult);
        noteService.deleteNote(note);
    }

    @Test
    public void saveNewNote(){
        Note note = new Note();
        this.createNote(note);
        final Note noteResult = noteService.findNoteById(note.getIdNote());
        assertNotNull("La patient n'a pas été crée ou n'a pas été trouvé dans la base de donéés", noteResult);
        noteService.deleteNote(note);
    }

    @Test
    public void deleteNote(){
        Note note = new Note();
        this.createNote(note);
        final Note noteResult = noteService.findNoteById(note.getIdNote());
        assertNotNull("La note n'a pas été supprimé", noteResult);
        noteService.deleteNote(note);
    }
}
