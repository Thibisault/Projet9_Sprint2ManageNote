package com.example.Sprint2ManageNote.service;

import com.example.Sprint2ManageNote.entity.Note;
import com.example.Sprint2ManageNote.repository.NoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
@Transactional
public class NoteService {

    @Autowired
    NoteRepository noteRepository;

    public List<Note> getAllNoteFromOnePatient(Integer idPatient){
        List<Note> noteList = noteRepository.findAllByIdPatientForNote(idPatient);
        return noteList;
    }
    public Note takeNote(Integer idPatient, String newNote, Note note) {
        note.setDateNote(new Date());
        note.setTakeNote(newNote);
        note.setIdPatientForNote(idPatient);
        this.saveNewNote(note);
        return note;
    }

    public Note findNoteById(Integer idNote){
        return noteRepository.findByIdNote(idNote).orElse(null);
    }

    public Note findNoteByIdPatientForNote(Integer idPatientForNote){
        return noteRepository.findByIdNote(idPatientForNote).orElse(null);
    }

    public void saveNewNote(Note note){
        noteRepository.save(note);
    }

    public void deleteNote(Note note) {
        noteRepository.delete(note);
    }


}
