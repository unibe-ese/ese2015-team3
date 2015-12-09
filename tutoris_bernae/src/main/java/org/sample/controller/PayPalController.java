package org.sample.controller;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.sample.controller.exceptions.InvalidTutorShipException;
import org.sample.controller.service.TutorShipService;
import org.sample.general.Constants;
import org.sample.model.TutorShip;
import org.sample.model.dao.TutorShipDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;


/**
 * Controls the payment flow regarding PayPal
 *
 */
@Controller
public class PayPalController {
	
	@Autowired 
	private TutorShipDao tutorShipDao;
	@Autowired 
	private TutorShipService tutorShipService;
	
    /**
     * Starts the PayPal process and generates a link for payments on PayPal.
     * @param tutorshipId - a required mapping to the ID of the tutorship as a PathVariable
     * @return a ModelAndView with ViewName "paypal", containing a link to PayPal
     * 			if the API authentication was successful.
     */
    @SuppressWarnings("deprecation")
	@RequestMapping(value = "/paypal", method = RequestMethod.GET)
    public ModelAndView paypal(@RequestParam(value = "tutorshipId", required = true) Long tutorshipId) {
    	ModelAndView model = new ModelAndView("paypal");
    	TutorShip ts = tutorShipDao.findOne(tutorshipId);
    	if(ts==null)
    	{
    		model = new ModelAndView("paypalFailed");
    		model.addObject("error", "TutorShip not found");
    		return model;
    	}
    	if(ts.getConfirmed())
    	{
    		model = new ModelAndView("paypalFailed");
    		model.addObject("error", "TutorShip is already confirmed");
    		return model;
    	}
    	model.addObject("user", ts.getStudent().getFirstName());
    	model.addObject("tutor", ts.getTutor().getStudent().getFirstName());
    	
    	// Add parameters for the API request to PayPal
    	String paypalParams = "?METHOD=SetExpressCheckout&version=124.0&" + Constants.PAYPAL_AUTHENTICATION_NVP;
    	String nvp = "&PAYMENTREQUEST_0_AMT="+Constants.TUTORSHIP_FEE;
    	nvp = nvp + "&PAYMENTREQUEST_0_CURRENCYCODE=" + CurrencyCodeType.CHF;
    	nvp = nvp + "&PAYMENTREQUEST_0_DESC=Tutorshipfee";
    	nvp = nvp + "&PAYMENTREQUEST_0_INVNUM=1";
    	nvp = nvp + "&PAYMENTREQUEST_0_PAYMENTACTION=Sale";
    	nvp = nvp + "&item_name=Tutorshipfee";
    	nvp = nvp + "&item_number=" + tutorshipId;
    	nvp = nvp + "&amount=1";
    	nvp = nvp + "&no_shipping=1";
    	nvp = nvp + "&ALLOWNOTE=0";
    	nvp = nvp + "&RETURNURL=" + java.net.URLEncoder.encode("http://localhost:8080/tutoris_baernae/finishPaypal?tutorshipId=" + tutorshipId);
    	nvp = nvp + "&CANCELURL=" + java.net.URLEncoder.encode("http://localhost:8080/tutoris_baernae/paypal/");

    	// Connect to the PayPal API. The response is a token that is needed for the user.
    	// Note that the token is only valid for three hours.
    	String responseParams="";   	
    	try {
    		URL postURL = new URL(Constants.PAYPAL_API_URL+paypalParams + nvp);
    		HttpURLConnection conn = (HttpURLConnection)postURL.openConnection();
    		
    		// Set the connection parameters. Input and output are required, therefore both are set to true.
    		conn.setDoInput (true);
    		conn.setDoOutput (true);
    		conn.setRequestMethod("GET");
    		
    		int responseCode = conn.getResponseCode();
    		if (responseCode != -1) {
        		// Read the input from the input stream.
	    		BufferedReader is = new BufferedReader(new InputStreamReader( conn.getInputStream(), "UTF-8"));
	    		String line = null;
	    		while (((line = is.readLine()) !=null)) {
	    			responseParams = responseParams + line;
	    		}	    		
			}
    		
    		// Get the parameters from the response
    		String[] splitted = responseParams.split("&");
    		String token = "";
    		for (String s : splitted){
    			if (s.contains("TOKEN"))
    				token = s;
    		}
    		if(token != "")
    			splitted = token.split("=");
    		token = java.net.URLDecoder.decode(splitted[1]);
    		// The user can use the link generated below to pay the fee.
    		String link = Constants.PAYPAL_URL + token + "&useraction=commit";
			model.addObject("link", link);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}  	
        return model;
    }

    /**
     * Ends the PayPal process and updates the tutorship confirming the payment on PayPal.
     * @param tutorshipId - a required mapping to the ID of the tutorship as a PathVariable
     * @return a ModelAndView with ViewName "paypal_success", that will redirect to the homescreen
     * 				after a few seconds.
     */
    @RequestMapping(value = "/finishPaypal", method = RequestMethod.GET)
    public ModelAndView response(@RequestParam(value = "tutorshipId", required = true) Long tutorshipId, @RequestParam(value = "token", required = true) String token,
    		@RequestParam(value = "PayerID", required = true) String payerId) {
    	try{
    		tutorShipService.confirmTutorShip(tutorshipId);
    		return new ModelAndView("paypalSuccess");
    	}
    	catch(InvalidTutorShipException e){
    		ModelAndView model = new ModelAndView("confirmFailed");
    		model.addObject("page_error", e.getMessage());
    		return model;
    	}
    }

 
}
  
