package com.paytm.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.paytm.models.CardInfo;

@Controller
public class PaymentController {

	@RequestMapping(value="/gotoPaymentPage")
	public String goToPaymentPage() {
		System.out.println("goToPaymentPage triggered!!");
		return "payment";
	}
	
	@RequestMapping(value="/doPayment")
	public String doPayment(CardInfo cardInfo, Model model) {
		
		System.out.println(cardInfo.getCardNumber());
		System.out.println(cardInfo.getHolderName());
		//send this card info to bank they validate and update you status of payment.
		Gson gson = new Gson();
		String url = "http://192.168.0.2:8080/robobank/robo/v1/processPayment";
		
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String cardInfoJsonData = gson.toJson(cardInfo);
		
		HttpEntity<String> entity = new HttpEntity<String>(cardInfoJsonData, headers);

		System.out.println("URL formed is : " + url);
		System.out.println("Requested payload is : " + cardInfoJsonData);
		ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

		System.out.println(result.getBody());
		model.addAttribute("msg", result.getBody());
		return "statucCheck";
	}
}
