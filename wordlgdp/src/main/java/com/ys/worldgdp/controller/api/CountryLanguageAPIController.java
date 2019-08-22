package com.ys.worldgdp.controller.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ys.worldgdp.CountryLanguage;
import com.ys.worldgdp.dao.CountryLanguageDAO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/languages")
@Slf4j
public class CountryLanguageAPIController {
	
	@Autowired
	CountryLanguageDAO	langDao;

	@GetMapping("/{countryCode}")
	public ResponseEntity<?> getLanguages(@PathVariable String countryCode,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
		try {
			return ResponseEntity.ok(langDao.getLanguages(countryCode, pageNo));
		}catch(Exception	ex) {
			log.error("Error while getting ");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error while getting languages");
		}
	}
	
	@PostMapping("/{countryCode}")
	public	ResponseEntity<?> addLanguage(@PathVariable	String	countryCode,
			@RequestBody	@Valid	CountryLanguage	language){
		try {
			if(langDao.languageExists(countryCode, language.getLanguage())) {
				return	ResponseEntity.badRequest().body("The language already exists");
			}
			
			langDao.addLanguage(countryCode, language);
			return	ResponseEntity.ok(language);
			
		}catch(Exception	ex){
			log.error("Error while adding a new language :");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error while adding a new language");
		}
	}
	
	@DeleteMapping("/{countryCode}/language/{lang}")
	public	ResponseEntity<?> deleteLang(@PathVariable	String	countryCode,
			@PathVariable	String	lang){
		try{
			langDao.deleteLanguage(countryCode, lang);
			return	ResponseEntity.ok().build();
		}catch(Exception	ex) {
			log.error("Error while deleting languages");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error while deleting languages");
		}
	}
}
