package org.sample.general;

public class Constants {
	public final static String TUTORSHIP_FEE = "10.00";
	
	/*
	 * PayPal informations
	 */
	public final static String PAYPAL_URL = "https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=";
	public final static String PAYPAL_API_URL = "https://api-3t.sandbox.paypal.com/nvp";
	public final static String PAYPAL_ID = "guillaume.corsini-facilitator_api1.students.unibe.ch";
	public final static String PAYPAL_PW = "4V55B6SACLTWANVF";
	public final static String PAYPAL_TOKEN = "AFcWxV21C7fd0v3bYYYRCpSSRl31Ah0fgHK0IqC5b5148zttqBdaS.U3";
	public final static String PAYPAL_AUTHENTICATION_NVP = "USER=" + PAYPAL_ID + "&PWD=" + PAYPAL_PW + "&SIGNATURE=" + PAYPAL_TOKEN;
}
