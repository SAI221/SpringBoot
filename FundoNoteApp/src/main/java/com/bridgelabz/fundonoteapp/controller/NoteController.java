package com.bridgelabz.fundonoteapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundonoteapp.model.Note;
import com.bridgelabz.fundonoteapp.service.NoteService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*") 
@RequestMapping(value ="/note")
public class NoteController {

	@Autowired
	NoteService noteService;

	@PostMapping(value = "/create")
	public ResponseEntity<Note> noteCreate(@RequestBody Note note, HttpServletRequest request) {
		String token = request.getHeader("token");

		return new ResponseEntity<Note>(noteService.createNote(note, token),HttpStatus.CREATED);

	}

	@PutMapping(value = "/update")
	public ResponseEntity<Note> noteUpdate(@RequestBody Note note, HttpServletRequest request) {
		String token = request.getHeader("token");
		return new ResponseEntity<Note>(noteService.updateNote(note, token),HttpStatus.ACCEPTED);

	}

	@DeleteMapping(value = "/delete/{noteId}")
	public String noteDelete(@PathVariable int noteId, HttpServletRequest request) {
		String token = request.getHeader("token");
		return noteService.deleteNote(noteId,token);

	}

	@GetMapping(value = "/{noteId}")
	public Note noteInfo(@PathVariable int noteId) {
		return noteService.getNoteInfo(noteId);

	}

	@GetMapping(value = "/notes")
	public List<Note> noteList() {
		return noteService.getAllNotes();
	}

	@GetMapping(value = "/notelist")
	public List<Note> noteList(HttpServletRequest request) {
		String token = request.getHeader("token");
		return noteService.getNotes(token);
	}
}
