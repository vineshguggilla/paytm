package com.paytm.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.paytm.models.CardInfo;
import com.paytm.models.PaymentStatus;
import com.paytm.models.ReponseException;

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
		String url = "http://192.168.0.19:8080/robobank/robo/v1/processPayment";
		
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
			model.addAttribute("paymentStatus", status);
			return "statucCheck";
		} catch(HttpServerErrorException httpException) {
			String resExceptionBody = httpException.getResponseBodyAsString();
			System.out.println("Response information : " + resExceptionBody);
			ReponseException exp = gson.fromJson(resExceptionBody, ReponseException.class);
			System.out.println(exp.getErrorCode() + exp.getErrorMessage());
			model.addAttribute("exception", exp);
			return "payment";
		}

		
		
	}
}
