package business;

import java.util.List;
import javafx.collections.ObservableList;
import dataaccess.Auth;

public interface ControllerInterface {
	public Auth login(String id, String password) throws LoginException;
	public void librarianLogin(String memberId, String isbn) throws LoginException;
	public void memberRecord(String id) throws LoginException;
	public void bookRecord(String isbn) throws LoginException; 
	public List<String> allMemberIds();
	public List<String> allBookIds();
	public ObservableList<LibraryMember> allMembers();
	public void saveLibraryMember(List<LibraryMember> memberList);
	public List<Book> books();
	public List<Author> authors();
	public List<Address> addresses();
	public void loadBookMap(List<Book> books);
	public void loadAuthorMap(List<Author> authorList);
	public void addLibraryMember(String memId, String fName,String lName, String phone,Address addr);
	public Address createAddress(String street, String city, String state, String zip);
	public void removeLibraryMember(String memId);
	public void updateLibraryMember(String memId, String fName,String lName, String phone,Address addr);
}
