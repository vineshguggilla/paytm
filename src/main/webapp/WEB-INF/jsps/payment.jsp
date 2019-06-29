<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<font color="red">${exception.errorMessage}</font>
<form action="doPayment">
Card Number : <input type="text" name="cardNumber"/></br>
Card Holder Name : <input type="text" name="holderName"/> </br>
CVV : <input type="password" name="cvv" maxlength="3" size="3"/>
Expiration Data : <input type="text" name="expDate"/>(MM/YY) </br>
Amount : <input type="text" name="amt"/> </br>
<input type="submit" value ="Pay"/>
</form>
</body>
</html>