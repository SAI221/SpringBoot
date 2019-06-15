package com.bridgelabz.springbootform.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.springbootform.model.Label;
import com.bridgelabz.springbootform.service.NoteService;

@RestController
public class LabelController {

	@Autowired
	private NoteService noteService;

	@PostMapping(value = "/createlabel/{token}")
	public Label createLabel(Label label, @PathVariable String token, HttpServletRequest request) {
		return noteService.labelCreate(label, token);

	}

	@PutMapping(value = "/updatelabel/{labelId}/{token}")
	public Label updateLabel(Label label, HttpServletRequest request, @PathVariable int labelId,
			String token) {

		return noteService.labelUpdate(label, token, labelId);
	}

	@DeleteMapping(value = "/deletelabel/{labelId}/{token}")
	public String deleteLabel(@PathVariable int labelId, String token, HttpServletRequest request) {

		return noteService.labelDelete(token, labelId);
	}

	@GetMapping(value = "/fetchlabels/{token}")
	public List<Label> fetchLabels(HttpServletRequest request, @PathVariable String token) {

		return noteService.getLabels(token);
	}

}
