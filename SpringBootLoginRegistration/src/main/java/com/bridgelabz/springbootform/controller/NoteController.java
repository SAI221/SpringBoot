package com.bridgelabz.springbootform.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.springbootform.model.Note;
import com.bridgelabz.springbootform.service.NoteService;

@RestController
@RequestMapping("/")
public class NoteController {

	@Autowired
	NoteService noteService;

	@PostMapping(value = "/notecreate/{token}")
	public Note noteCreate( Note note, HttpServletRequest request, @PathVariable String token) {

		return noteService.createNote(note, token);

	}

	@PutMapping(value = "/noteupdate/{token}")
	public Note noteUpdate( Note note, HttpServletRequest request, @PathVariable String token) {

		return noteService.updateNote(note, token);

	}

	@DeleteMapping(value = "/notedelete/{noteId}/{token}")
	public String noteDelete(@PathVariable int noteId, HttpServletRequest request, @PathVariable String token) {

		return noteService.deleteNote(noteId, token);

	}

	@GetMapping(value = "/note/{noteId}")
	public Note noteInfo(@PathVariable int noteId) {
		return noteService.getNoteInfo(noteId);

	}

	@GetMapping(value = "/notes")
	public List<Note> noteList() {
		return noteService.getAllNotes();
	}

	@GetMapping(value = "/notelist/{token}")
	public List<Note> noteList(HttpServletRequest request, @PathVariable String token) {

		return noteService.getNotes(token);
	}

}
