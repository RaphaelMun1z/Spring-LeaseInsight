package com.rm.leaseinsight.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rm.leaseinsight.services.ResidenceFeatureService;

@RestController
@RequestMapping(value = "/residence-feature")
public class ResidenceFeatureResource {
	@Autowired
	private ResidenceFeatureService service;

	@DeleteMapping(value = "/{residence_id}/{feature_id}")
	public ResponseEntity<Void> delete(@PathVariable String residence_id, @PathVariable String feature_id) {
		service.delete(residence_id, feature_id);
		return ResponseEntity.noContent().build();
	}
}