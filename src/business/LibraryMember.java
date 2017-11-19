package business;

import java.io.Serializable;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

final public class LibraryMember extends Person implements Serializable {
	private String memberId;
	private CheckoutRecordold checkoutRecord;
	public LibraryMember(String memberId, String fname, String lname, String tel,Address add) {
		super(fname,lname, tel, add);
		this.memberId = memberId;	
		checkoutRecord = new CheckoutRecordold();
	}

	
	public void checkout(BookCopy bookCopy, LocalDate today, LocalDate dueDate) {
		bookCopy.changeAvailability();
		CheckoutRecordEntryold checkoutRecordEntry = new CheckoutRecordEntryold(today, dueDate, bookCopy);
		List<CheckoutRecordEntryold> checkoutRecordEntries = new ArrayList<CheckoutRecordEntryold>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				add(checkoutRecordEntry);
			}
		};
		checkoutRecord.getCheckoutRecordEntries().addAll(checkoutRecordEntries);
	}

	
	public CheckoutRecordold getCheckoutRecord() {
		return checkoutRecord;
	}

	public void setCheckoutRecord(CheckoutRecordold checkoutRecord) {
		this.checkoutRecord = checkoutRecord;
	}
	
	
	public String getMemberId() {
		return memberId;
	}
	
	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() + 
				", " + getTelephone() + " " + getAddress();
	}

	private static final long serialVersionUID = -2226197306790714013L;
}
