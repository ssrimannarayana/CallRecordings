package com.kgl.KglServices.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.kgl.KglServices.pojo.CallObj;

@Service
public class PhonePeServcies {
	private static final Logger logger = LoggerFactory.getLogger(PhonePeServcies.class);

	@Value("${Exotel_auth_token_CRM}")
	private String Exotel_auth_token_CRM;

	@Value("${ExotelUrl_CRM}")
	private String ExotelUrl_CRM;

	@Value("${Exotel_auth_token}")
	private String Exotel_auth_token;

	@Value("${ExotelUrl}")
	private String ExotelUrl;

	@Value("${CALLBACK_URL}")
	private String callbackUrl;

	@Value("${Exotel_Campaign_Url}")
	private String exotel_Campaign_Url;

	@Value("${AP_Exotel_No}")
	private String ap_exotel_no;

	@Value("${TS_Exotel_No}")
	private String ts_exotel_no;

	@Value("${TN_Exotel_No}")
	private String tn_exotel_no;

	@Value("${GJ_Exotel_No}")
	private String gj_exotel_no;

	@Value("${KA_Exotel_No}")
	private String ka_exotel_no;

	@Value("${AP_CAMPAIGN_JSON_RESPONSE_URL}")
	private String AP_googlesheet_data_restapi;

	@Value("${ROAP_CAMPAIGN_JSON_RESPONSE_URL}")
	private String ROAP_googlesheet_data_restapi;

	@Value("${CA_URL}")
	private String CA_URLs;

	@Value("${NA_URL}")
	private String NA_URLs;

	@Value("${RLA_URL}")
	private String RLA_URLs;

	@Value("${KA_URL}")
	private String KA_URLs;

	@Value("${GJ_URL}")
	private String GJ_URLs;

	@Value("${TS_URL}")
	private String TS_URLs;

	@Value("${TN_URL}")
	private String TN_URLs;

	@Value("${HO_CALL_URL}")
	private String HO_CALL_URLs;

	@Value("${ENQ_CALL_URL}")
	private String ENQ_CALL_URL;

	@Value("${CRM_AP}")
	private String CRM_AP;

	@Value("${CRM_ROA}")
	private String CRM_ROA;

	@Value("${Exotel_sid_URL}")
	private String Exotel_sid_URL;

	@Value("${PHONEPE_SUBSCRIPTION_URL}")
	private String Subscription_url;

	@Value("${PHONEPE_VPA_URL}")
	private String verify_vpa_URL;

	@Value("${VPA_PATH_URL}")
	private String vpa_path_url;

	@Value("${PHONEPE_AUTH_REQUEST_URL}")
	private String AUTH_REQUEST_URL;

	@Value("${PHONEPE_AUTH_REQUEST_STATUS_URL}")
	private String auth_request_status_url;

	@Value("${PHONEPE_PAYMENT_LINK_URL}")
	private String phonepe_paymentlink_url;

	@Value("${SALTKEY}")
	private String salt_key;

	@Value("${SMS_API_URL}")
	private String Sms_Api_url;

	@Value("${AP_CAMPAIGN_UPDATE_DATA_INTO_GOOGLESHEET_URL}")
	private String AP_CAMPAIGN_UPDATE_GOOGLESHEET_URL;

	@Value("${ROAP_CAMPAIGN_UPDATE_DATA_INTO_GOOGLESHEET_URL}")
	private String ROAP_CAMPAIGN_UPDATE_GOOGLESHEET_URL;

	public ResponseEntity<String> updateRecordingApiIntoAppSheet(String tid, String sid, String table, String Call_url,
			String call_status) {
		String url = null;
		if (table.equalsIgnoreCase("REC_CA")) {
			url = CA_URLs;
		} else if (table.equalsIgnoreCase("REC_NA")) {
			url = NA_URLs;
		} else if (table.equalsIgnoreCase("REC_RAL")) {
			url = RLA_URLs;
		} else if (table.equalsIgnoreCase("REC_KA")) {
			url = KA_URLs;
		} else if (table.equalsIgnoreCase("REC_GJ")) {
			url = GJ_URLs;
		} else if (table.equalsIgnoreCase("REC_TS")) {
			url = TS_URLs;
		} else if (table.equalsIgnoreCase("REC_TN")) {
			url = TN_URLs;
		} else if (table.equalsIgnoreCase("CRM_AP")) {
			url = CRM_AP;
		} else if (table.equalsIgnoreCase("CRM_ROA")) {
			url = CRM_ROA;
		} else if (table.equalsIgnoreCase("TVR_CALL_RECORDING")) {
			url = HO_CALL_URLs;
		} else if (table.equalsIgnoreCase("Enquiry_call_data")) {
			url = ENQ_CALL_URL;
		} else {
			System.out.println("NO TABLE FOUND");
		}
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("TID", tid);
		map.add("SID", sid);
		map.add("STATUS", call_status);
		map.add("Call_URL", Call_url);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> responseEntity = null;
		try {
			responseEntity = new RestTemplate().exchange(url, HttpMethod.POST, entity, String.class);
			logger.info(":::::call ends here::::::");
			return new ResponseEntity<>("Result:: ", HttpStatus.OK);
		}
		catch (Exception e) {
			logger.info("F....table name:: " + e.getMessage());
		}
		return new ResponseEntity<>("Result:: ", HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<String> ExotelCallApi(CallObj callObj) {
		logger.info("call starts here...." + callObj);
		String CallerId = null;
		String Exotel_token = null;
		String ExotelUrls = null;
		if (callObj.getTable().equalsIgnoreCase("CRM_AP") || callObj.getTable().equalsIgnoreCase("CRM_ROA")) {
			CallerId = "0".concat(callObj.getExotel());
			Exotel_token = Exotel_auth_token_CRM;
			ExotelUrls = ExotelUrl_CRM;
		} else {
			CallerId = "0".concat(callObj.getExotel());
			Exotel_token = Exotel_auth_token;
			ExotelUrls = ExotelUrl;
		}
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		String Call_back_Url = callbackUrl + callObj.getTid();
		map.add("From", callObj.getUserph());
		map.add("To", callObj.getCustph());
		map.add("CallerId", CallerId);
		map.add("StatusCallback", Call_back_Url);
		map.add("StatusCallbackEvents[0]", "terminal");
		map.add("CustomField", callObj.getTable());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Authorization", Exotel_token);
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> responseEntity = null;
		try {
			responseEntity = new RestTemplate().exchange(ExotelUrls, HttpMethod.POST, entity, String.class);
			logger.info(":::::call ends here::::::");
			return new ResponseEntity<>("Result:: ", HttpStatus.OK);
		}
		catch (Exception e) {
			logger.info("call failed to due to this reason-->::" + e.getMessage());
		}
		return new ResponseEntity<>("Result:: ", HttpStatus.BAD_REQUEST);
	}

}
