package com.ys.worldgdp.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ys.worldgdp.dao.CityDAO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/cities")
@Slf4j
public class CityAPIController {

	@Autowired
	CityDAO cityDAO;

	@GetMapping("/{countryCode}")
	public	ResponseEntity<?>	getCity(@PathVariable	String	countryCode,
			@RequestParam(name="pageNo",defaultValue="1")Integer	pageNo){
		try {
			return new ResponseEntity<>(cityDAO.getCities(countryCode, pageNo), HttpStatus.OK);
		}catch(Exception	ex) {
			log.error("Error while getting city details");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error while getting city details");
		}
	}
}
