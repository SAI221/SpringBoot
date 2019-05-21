package com.bridgelabz.fundonoteapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundonoteapp.model.Note;
import com.bridgelabz.fundonoteapp.service.NoteService;

@RestController
public class NoteController {
	@Autowired
	
	NoteService noteService;
	


	@PostMapping(value = "/notecreate")
	public Note noteCreate(@RequestBody Note note, HttpServletRequest request) {
		String token = request.getHeader("token");

		return noteService.createNote(note, token);

	}

	@PutMapping(value = "/noteupdate")
	public Note noteUpdate(@RequestBody Note note,HttpServletRequest request) {
		String token=request.getHeader("token");
		return noteService.updateNote(note, token);

	}

	@DeleteMapping(value = "/notedelete/{noteId}")
	public String noteDelete(@PathVariable int noteId,HttpServletRequest request) {
		String token=request.getHeader("token");
		return noteService.deleteNote(noteId,token);

	}
	
	

	@GetMapping(value = "/note/{noteId}")
	public Note noteInfo(@PathVariable int noteId) {
		return noteService.getNoteInfo(noteId);

	}

	@GetMapping(value = "/notes")
	public List<Note> noteList() {
		return noteService.getAllNotes();
	}
	
	@GetMapping(value="/notelist")
	public List<Note> noteList(HttpServletRequest request){
		String token=request.getHeader("token");
		return noteService.getNotes(token);
	}
}
