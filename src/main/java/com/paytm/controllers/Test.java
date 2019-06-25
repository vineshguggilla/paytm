package com.paytm.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.paytm.models.CardInfo;

public class Test {

	public static void main(String[] args) {
		
		Gson gson = new Gson();
		String url = "http://192.168.0.2:8080/robobank/robo/v1/processPayment";
		CardInfo cardInfo = new CardInfo();
		cardInfo.setCardNumber("545487896321");
		cardInfo.setHolderName("Raju");
		
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String cardInfoJsonData = gson.toJson(cardInfo);
		
		HttpEntity<String> entity = new HttpEntity<String>(cardInfoJsonData, headers);

		ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

		System.out.println(result.getBody());
		
	}
}
