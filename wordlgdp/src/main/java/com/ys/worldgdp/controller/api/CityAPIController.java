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

import com.ys.worldgdp.City;
import com.ys.worldgdp.dao.CityDAO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/cities")
@Slf4j
public class CityAPIController {

	@Autowired
	CityDAO cityDAO;

	@GetMapping("/{countryCode}")
	public ResponseEntity<?> getCity(@PathVariable String countryCode,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
		try {
			return new ResponseEntity<>(cityDAO.getCities(countryCode, pageNo), HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Error while getting city details");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while getting city details");
		}
	}

	
	@PostMapping(value = "/{countryCode}")
	public ResponseEntity<?> createCity(@PathVariable(required = true) String countryCode,
			@RequestBody @Valid City city) {
		try {
			cityDAO.addCity(countryCode, city);

			return new ResponseEntity<>(city, HttpStatus.CREATED);
		} catch (Exception ex) {
			log.error("Error while creating city");
			return new ResponseEntity<>("Error while creating city", 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@DeleteMapping("/{cityId}")
	public ResponseEntity<?> deleteCity(@PathVariable Long cityId) {
		try {
			cityDAO.deleteCity(cityId);
			return	ResponseEntity.ok().build();
		}catch(Exception	ex) {
			return	ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error while deleting the city with city id : "+cityId);
		}
		
	}
	
}
