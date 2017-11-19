package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ui.CheckOutRecordWindow;
import ui.LibrarianWindow;
import ui.OverdueRecordWindow;
import business.Book;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;

	private LibraryMember member;
	private Book book;

	public Auth login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if (!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if (!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}

		return currentAuth = map.get(id).getAuthorization();
	}

	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}

	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

	public void librarianLogin(String id, String isbn) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> map = da.readMemberMap();
		if (!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}

		HashMap<String, LibraryMember> membersHashMap = new HashMap<String, LibraryMember>();
		membersHashMap = da.readMemberMap();

		if (membersHashMap != null) {
			Collection<LibraryMember> libraryMembersCollection = membersHashMap.values();

			member = libraryMemberExists(libraryMembersCollection, id);
			if (member == null) {
				throw new LoginException("ID " + id + " not found");
			}
		}

		HashMap<String, Book> bookMap = da.readBooksMap();
		if (!bookMap.containsKey(isbn)) {
			throw new LoginException("ISBN " + isbn + " not found");
		}

		HashMap<String, Book> booksHashMap = new HashMap<String, Book>();
		booksHashMap = da.readBooksMap();

		if (booksHashMap != null) {
			Collection<Book> booksCollection = booksHashMap.values();

			book = bookExists(booksCollection, isbn);
			if (book == null) {
				throw new LoginException("ISBN " + isbn + " not found");
			}
		}

		BookCopy nextAvailBookCopy = book.getNextAvailableCopy();
		if (nextAvailBookCopy == null) {
			LibrarianWindow.actiontarget.setText("No available copies for ISBN " + isbn);
			return;
		}

		LocalDate today = LocalDate.now();
		LocalDate dueDate = LocalDate.now().plusDays(book.getMaxCheckoutLength());
		member.checkout(nextAvailBookCopy, today, dueDate);

		da.saveNewMember(member);
		da.saveNewBook(book);

		LibrarianWindow.actiontarget.setText("Checkout Successful, your due date is " + dueDate.toString() + ".");
	}

	private LibraryMember libraryMemberExists(Collection<LibraryMember> libraryMembersCollection, String memberId) {
		for (LibraryMember libraryMember : libraryMembersCollection) {
			if (libraryMember.getMemberId().equals(memberId)) {
				return libraryMember;
			}
		}
		return null;
	}

	private Book bookExists(Collection<Book> booksCollection, String bookISBNNo) {
		for (Book book : booksCollection) {
			if (book.getIsbn().equals(bookISBNNo)) {
				return book;
			}
		}
		return null;
	}

	public void memberRecord(String id) throws LoginException {
		DataAccess da = new DataAccessFacade();
		List<BookCart> memberRecordsList = new ArrayList<BookCart>();
		HashMap<String, LibraryMember> map = da.readMemberMap();

		if (!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}

		LibraryMember existedLibraryMember = da.searchMember(id);

		if (existedLibraryMember == null) {
			LibrarianWindow.actiontarget.setText("Member does not exist with ID " + id);

		}

		for (LibraryMember member : map.values()) {
			if (member.getMemberId().equals(id)) {
				System.out.println(member.getCheckoutRecord().getCheckoutRecordEntries());
				List<CheckoutRecordEntryold> checkoutRecordEntries = member.getCheckoutRecord()
						.getCheckoutRecordEntries();
				for (CheckoutRecordEntryold memberCheckoutRecordEntry : checkoutRecordEntries) {
					BookCopy memberBookCopy = memberCheckoutRecordEntry.getBookCopy();
					// System.out.println(memberBookCopy.getBook().getIsbn());
					memberRecordsList.add(new BookCart(memberBookCopy.getBook().getIsbn(),
							memberBookCopy.getBook().getTitle(), memberBookCopy.getCopyNum(), "",
							memberCheckoutRecordEntry.getCheckoutDate().toString(),
							memberCheckoutRecordEntry.getDueDate().toString()));
				}
			}
		}

		if (memberRecordsList.isEmpty()) {
			LibrarianWindow.actiontarget.setText("No data found for member " + id);

		}
		showEntries(memberRecordsList);
	}

	private void showEntries(List<BookCart> list) {
		ObservableList<BookCart> data = FXCollections.observableArrayList(list);
		CheckOutRecordWindow cartWindow = CheckOutRecordWindow.INSTANCE;
		cartWindow.setData(data);
		cartWindow.show();
	}
	
	public void bookRecord(String isbn) {
		DataAccess da = new DataAccessFacade();
		List<BookCart> availableBooks = new ArrayList<BookCart>();
		book = da.searchBook(isbn);
		if (book != null) {
			HashMap<String, LibraryMember> members = da.readMemberMap();
			for (LibraryMember member : members.values()) {
				List<CheckoutRecordEntryold> checkoutRecordEntries = member.getCheckoutRecord().getCheckoutRecordEntries();
				for (CheckoutRecordEntryold memberCheckoutRecordEntry : checkoutRecordEntries) {
					BookCopy memberBookCopy = memberCheckoutRecordEntry.getBookCopy();
					if (memberBookCopy.getBook().getIsbn() .equals(isbn)) {
						if (isBookCopyOverDue(memberCheckoutRecordEntry.getDueDate())) {
							availableBooks.add(new BookCart(memberBookCopy
									.getBook().getIsbn(), memberBookCopy
									.getBook().getTitle(), memberBookCopy
									.getCopyNum(), member.getFirstName() + " "
									+ member.getLastName(),
									memberCheckoutRecordEntry.getDueDate()
											.toString(), "Book Overdue"));
						} else {
							availableBooks.add(new BookCart(memberBookCopy
									.getBook().getIsbn(), memberBookCopy
									.getBook().getTitle(), memberBookCopy
									.getCopyNum(), member.getFirstName() + " "
									+ member.getLastName(),
									memberCheckoutRecordEntry.getDueDate()
											.toString(), "Currently Checked-out"));
						}
					}
				}
			}
			printEntries(availableBooks);
		} else {
			LibrarianWindow.actiontarget.setText("Book does not exist with ISBN "
					+ isbn);
		}
	}
	
	private void printEntries(List<BookCart> list) {
		ObservableList<BookCart> data = FXCollections
				.observableArrayList(list);
		OverdueRecordWindow cartWindow = OverdueRecordWindow.INSTANCE;
			cartWindow.setData(data);
			cartWindow.show();
	}
	
	private boolean isBookCopyOverDue(LocalDate dueDate) {
		return dueDate.isBefore(LocalDate.now());
	}

	@Override
	public ObservableList<LibraryMember> allMembers() {
		DataAccess da = new DataAccessFacade();
		ObservableList<LibraryMember> retval = FXCollections.observableArrayList();
		retval.addAll(da.readMemberMap().values());
		return retval;
	}

	@Override
	public void saveLibraryMember(List<LibraryMember> memberList) {
		DataAccessFacade data = new DataAccessFacade();
		data.saveToStorageMember(memberList);
	}

	@Override
	public List<Book> books() {
		DataAccess da = new DataAccessFacade();
		List<Book> books = new ArrayList<>();
		books.addAll(da.readBooksMap().values());
		return books;
	}

	@Override
	public List<Author> authors() {
		DataAccess da = new DataAccessFacade();
		List<Author> authors = new ArrayList<>();
		authors.addAll(da.readAuthorMap().values());
		return authors;
	}

	@Override
	public void loadBookMap(List<Book> bookList) {
		DataAccess da = new DataAccessFacade();
		da.saveBookToStorage(bookList);
	}

	@Override
	public List<Address> addresses() {
		DataAccess da = new DataAccessFacade();
		List<Address> addresses = new ArrayList<>();
		addresses.addAll(da.readAddressMap().values());
		return addresses;
	}

	@Override
	public void loadAuthorMap(List<Author> authorList) {
		DataAccess da = new DataAccessFacade();
		da.saveAuthorToStorage(authorList);
	}

	@Override
	public Address createAddress(String street, String city, String state, String zip) {
		Address addr = new Address(street, city, state, zip);
		return addr;
	}

	@Override
	public void addLibraryMember(String memId, String fName, String lName, String phone, Address addr) {
		LibraryMember lmember = new LibraryMember(memId, fName, lName, phone, addr);
		DataAccess da = new DataAccessFacade();
		List<LibraryMember> list = new ArrayList<LibraryMember>();
		list.addAll(da.readMemberMap().values());
		list.add(lmember);
		saveLibraryMember(list);
	}

	@Override
	public void removeLibraryMember(String memId) {
		DataAccess da = new DataAccessFacade();
		List<LibraryMember> list = new ArrayList<LibraryMember>();
		list.addAll(da.readMemberMap().values());
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getMemberId().equals(memId))
				list.remove(i);
		}
		saveLibraryMember(list);
	}

	@Override
	public void updateLibraryMember(String memId, String fName, String lName, String phone, Address addr) {
		DataAccess da = new DataAccessFacade();
		List<LibraryMember> list = new ArrayList<LibraryMember>();
		list.addAll(da.readMemberMap().values());
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getMemberId().equals(memId)){
				list.get(i).setFirstName(fName);
				list.get(i).setLastName(lName);
				list.get(i).setTelephone(phone);
				list.get(i).setAddress(addr);
			}
				
		}
		saveLibraryMember(list);
	}

}
