package com.example.Sprint2ManageNote.controller;

import com.example.Sprint2ManageNote.exceptions.ElementIntrouvable;
import com.example.Sprint2ManageNote.entity.Note;
import com.example.Sprint2ManageNote.model.Patient;
import com.example.Sprint2ManageNote.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Configuration
@RestController
public class NoteController {

    @Autowired
    NoteService noteService;

    @Value("${server.patient}")
    private String serverPatient;

    /**
     * Obtenir toute les notes d'un patients
     * @return
     */
    @GetMapping("/note/notePatient/{idPatient}")
    public ResponseEntity<List<Note>> noteList(@PathVariable("idPatient") @RequestBody Integer idPatient) {
        RestTemplate restTemplate = new RestTemplate();
        //Patient patient = restTemplate.getForObject("http://sprint1patient:8081/patient/getOnePatient/" + idPatient, Patient.class);
        Patient patient = restTemplate.getForObject(serverPatient + "/patient/getOnePatient/" + idPatient, Patient.class);
        System.out.println("Patient trouvé : "+ patient);
        List<Note> noteList = noteService.getAllNoteFromOnePatient(patient.getIdPatient());
        if(noteList==null){
            throw new ElementIntrouvable("Le patient avec l'id '"+patient.getIdPatient() + "' n'a pas été trouvé" , HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(noteList, HttpStatus.OK);
    }

    /**
     * Créer une nouvelle note
     * @param note
     * @return
     */
    @RequestMapping(value = "/note/validate/{idPatient}", method = { RequestMethod.GET, RequestMethod.PUT })
    public ResponseEntity<Note> validateAddNote(@PathVariable("idPatient") Integer idPatient, @RequestBody Note note) {
        RestTemplate restTemplate = new RestTemplate();
        Patient patient = restTemplate.getForObject(serverPatient +  "/patient/getOnePatient/" + idPatient, Patient.class);
        note = noteService.takeNote(patient.getIdPatient(), note.getTakeNote(), note);
        if (Objects.isNull(note)) {
            throw new ElementIntrouvable("Le patient avec l'id '"+ patient.getIdPatient() + "' n'a pas été trouvé" , HttpStatus.NOT_FOUND);
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(note.getIdNote())
                .toUri();
        return ResponseEntity.created(location).build();
    }
    /**
     * Mettre à jour une note
     * @param noteId
     * @param note
     * @return
     */
    @PutMapping("/note/updateNote/{noteId}")
    public ResponseEntity<Patient> updateNote(@PathVariable("noteId") Integer noteId, @RequestBody Note note) {
        String newNote = note.getTakeNote();
        note = noteService.findNoteById(noteId);
        noteService.takeNote(note.getIdPatientForNote(), newNote, note);
        if (Objects.isNull(note)) {
            throw new ElementIntrouvable("La note avec l'id '"+ noteId + "' n'a pas été trouvé" , HttpStatus.NOT_FOUND);
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(note.getIdNote())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    /**
     * Supprimer une note de la base de données
     * @param noteId
     * @return
     */
    @RequestMapping(value = "/note/delete/{noteId}", method = { RequestMethod.GET, RequestMethod.PUT })
    public ResponseEntity<Patient> showDeleteNote(@PathVariable("noteId") @RequestBody Integer noteId) {
        Note note = noteService.findNoteById(noteId);
        if (Objects.isNull(note)) {
            throw new ElementIntrouvable("La note n'existe pas ou l'id '" + noteId +"' est faux", HttpStatus.NOT_FOUND);
        }
        noteService.deleteNote(note);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Obetnir un patient depuis le micro-service 1
     */
    @GetMapping("/note/getPatient/{idPatient}")
    public ResponseEntity<Patient> getPatientFromId(@PathVariable("idPatient") Integer idPatient){
        RestTemplate restTemplate = new RestTemplate();
        Patient patient = restTemplate.getForObject(serverPatient + "/patient/getOnePatient/" + idPatient, Patient.class);

        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    /**
     * Trouver une note grâce à son idPatient
     */
    @GetMapping("/note/getOneNoteById/{idNote}")
    public ResponseEntity<Note> getNoteByPatientId(@PathVariable("idNote") Integer idNote){
        Note note = noteService.findNoteById(idNote);
        if(note==null){
            throw new ElementIntrouvable("La note est null ou introuvable", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(note, HttpStatus.OK);
    }
}
