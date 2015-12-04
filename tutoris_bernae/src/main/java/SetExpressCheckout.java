import java.util.ArrayList;
import java.util.List;

import org.sample.general.Constants;

import urn.ebay.api.PayPalAPI.SetExpressCheckoutResponseType;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentActionCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsType;
import urn.ebay.apis.eBLBaseComponents.SellerDetailsType;
import urn.ebay.apis.eBLBaseComponents.SetExpressCheckoutRequestDetailsType;

public class SetExpressCheckout {
	public SetExpressCheckoutResponseType setExpressCheckout() {
		SetExpressCheckoutRequestDetailsType setExpressCheckoutRequestDetails = new SetExpressCheckoutRequestDetailsType();
		setExpressCheckoutRequestDetails.setReturnURL("http://localhost/return");
		setExpressCheckoutRequestDetails.setCancelURL("http://localhost/cancel");
		List<PaymentDetailsType> paymentDetailsList = new ArrayList<PaymentDetailsType>();
		PaymentDetailsType tutorshipPayment = new PaymentDetailsType();
		BasicAmountType tutorshipFee = new BasicAmountType(CurrencyCodeType.CHF, Constants.TUTORSHIP_FEE);
		tutorshipPayment.setOrderTotal(tutorshipFee);
		tutorshipPayment.setPaymentAction(PaymentActionCodeType.ORDER);
		
		SellerDetailsType sellerDetails1 = new SellerDetailsType();
		sellerDetails1.setPayPalAccountID(Constants.PAYPAL_SELLER_ID);
		tutorshipPayment.setSellerDetails(sellerDetails1);
		tutorshipPayment.setPaymentRequestID("PaymentRequest1");

	}
}
