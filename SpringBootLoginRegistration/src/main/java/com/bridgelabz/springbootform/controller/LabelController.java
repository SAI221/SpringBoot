package com.bridgelabz.springbootform.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.springbootform.model.Label;
import com.bridgelabz.springbootform.service.NoteService;

@RestController
public class LabelController {

	@Autowired
	private NoteService noteService;

	@RequestMapping(value = "/createlabel/{token}", method = RequestMethod.POST)
	public Label createLabel(@RequestBody Label label,@PathVariable String token, HttpServletRequest request) {
		//String token = request.getHeader("token");
		return noteService.labelCreate(label, token);

	}

	@RequestMapping(value = "/updatelabel/{labelId}/{token}", method = RequestMethod.PUT)
	public Label updateLabel(@RequestBody Label label, HttpServletRequest request,@PathVariable int labelId,String token ) {
		//String token = request.getHeader("token");
		return noteService.labelUpdate(label, token,labelId);
	}

	@RequestMapping(value = "/deletelabel/{labelId}/{token}", method = RequestMethod.DELETE)
	public String deleteLabel(@PathVariable int labelId,String token , HttpServletRequest request) {
		//String token = request.getHeader("token");
		return noteService.labelDelete(token, labelId);
	}

	@RequestMapping(value = "/fetchlabels/{token}", method = RequestMethod.GET)
	public List<Label> fetchLabels(HttpServletRequest request,@PathVariable String token ) {
		//String token = request.getHeader("token");
		return noteService.getLabels(token);
	}

}
