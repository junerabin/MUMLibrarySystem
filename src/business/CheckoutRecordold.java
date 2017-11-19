package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckoutRecordold implements Serializable {

	/**
	 * 
	 */

	public CheckoutRecordold() {
		checkoutRecordEntries = new ArrayList<>();
	}

	private static final long serialVersionUID = 7086395344898382470L;
	private List<CheckoutRecordEntryold> checkoutRecordEntries;

	public List<CheckoutRecordEntryold> getCheckoutRecordEntries() {
		return checkoutRecordEntries;
	}

	public void setCheckoutRecordEntries(List<CheckoutRecordEntryold> checkoutRecordEntries) {
		this.checkoutRecordEntries = checkoutRecordEntries;
	}

}
