package com.bridgelabz.springbootform.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.springbootform.model.Note;
import com.bridgelabz.springbootform.service.NoteService;
import com.bridgelabz.springbootform.service.UserService;

@RestController
public class NoteController {
	@Autowired
	NoteService noteService;
	
	@Autowired
	UserService userService;

	@RequestMapping(value="/notecreate" ,method=RequestMethod.POST)
	public Note noteCreate(@RequestBody Note note,HttpServletRequest request) 
	{
		String token=request.getHeader("token");
		
		return noteService.createNote(note,token);
		
	}
	@RequestMapping(value="/noteupdate/{noteId}",method=RequestMethod.PUT)
	public Note noteUpdate(@RequestBody Note note,@PathVariable int noteId) {
		
		
		return noteService.updateNote(note,noteId);
		
	}
	
	@RequestMapping(value="/notedelete/{noteId}",method=RequestMethod.DELETE)
	public String noteDelete(@PathVariable int noteId) {
		
		return noteService.deleteNote(noteId);
		
	}
	
	@RequestMapping(value="/note/{noteId}",method=RequestMethod.GET)
	public Note noteInfo(@PathVariable int noteId) {
		return noteService.getNoteInfo(noteId);
	
	}
	
	@RequestMapping(value="/notes",method=RequestMethod.GET)
	public List<Note> noteList(){
		return noteService.getAllNotes();
	}
}
