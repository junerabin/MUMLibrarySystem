package dataaccess;

import java.util.HashMap;
import java.util.List;
import business.Address;
import business.Author;
import business.Book;
import business.LibraryMember;

public interface DataAccess { 
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();
	public void saveNewMember(LibraryMember member);
	public void saveNewBook(Book book);
	public Book searchBook(String isbn);
	public LibraryMember searchMember(String id);
	public void saveToStorageMember(List<LibraryMember> member);
	public void saveBookToStorage(List<Book> bookList);
	public void saveAuthorToStorage(List<Author> authorList);
	public HashMap<String,Author> readAuthorMap();
	public HashMap<String,Address> readAddressMap();
}
