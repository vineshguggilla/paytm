package com.paytm.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.paytm.models.CardInfo;
import com.paytm.models.PaymentStatus;
import com.paytm.models.ReponseException;

public class Test {

	public static void main(String[] args) {
		
		Gson gson = new Gson();
		String url = "http://192.168.0.19:8080/robobank/robo/v1/processPayment";
		CardInfo cardInfo = new CardInfo();
		cardInfo.setCardNumber("545456561234");
		cardInfo.setHolderName("Raju");
		cardInfo.setCvv("419");
		cardInfo.setExpDate("11/23");
		cardInfo.setAmt(20000);
		
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String cardInfoJsonData = gson.toJson(cardInfo);
		
		HttpEntity<String> entity = new HttpEntity<String>(cardInfoJsonData, headers);
		ResponseEntity<String> result = null;
		
		try {
			result = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);			
			System.out.println("Response information : " + result.getBody());
			PaymentStatus status = gson.fromJson(result.getBody(), PaymentStatus.class);
			System.out.println(status.getAvailableBalance());
		} catch(HttpServerErrorException httpException) {
			String resExceptionBody = httpException.getResponseBodyAsString();
			System.out.println("Response information : " + resExceptionBody);
			ReponseException exp = gson.fromJson(resExceptionBody, ReponseException.class);
			System.out.println(exp.getErrorCode() + exp.getErrorMessage());
		}

		
		
	}
}
