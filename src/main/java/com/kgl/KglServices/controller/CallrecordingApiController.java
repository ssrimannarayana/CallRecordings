package com.kgl.KglServices.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.kgl.KglServices.exceptions.InvalidPhoneNumber;
import com.kgl.KglServices.pojo.CallObj;
import com.kgl.KglServices.services.PhonePeServcies;

@RestController
@RequestMapping({ "/call" })
public class CallrecordingApiController {

	private static final Logger logger = LoggerFactory.getLogger(CallrecordingApiController.class);

	@Autowired
	private PhonePeServcies phpeServices;

	@GetMapping("/testApi")
	public String test() {
		return "hi";
	}

	//calls API
	@PostMapping("/api")
	public ResponseEntity<String> recordingApi(@RequestBody CallObj callObj) throws InvalidPhoneNumber {
		return phpeServices.ExotelCallApi(callObj);
	}

	// call back url for updating Call status
	@RequestMapping(path = "/SidCallbackApi/{tid}", method = RequestMethod.POST, consumes = {
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<String> sendSMS(@PathVariable String tid, @RequestPart String CallSid, @RequestPart String Status,
			@RequestPart(required = false) String RecordingUrl, @RequestPart String DateUpdated, @RequestPart String To,
			@RequestPart String CustomField) {
		logger.info("Call back URL started....table name:: " + CustomField);
		if (RecordingUrl == null) {
			RecordingUrl = "NA";
		}
		return phpeServices.updateRecordingApiIntoAppSheet(tid, CallSid, CustomField, RecordingUrl, Status);
	}

}
