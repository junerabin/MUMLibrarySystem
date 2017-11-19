package ui;

import java.util.ArrayList;
import java.util.List;

import dataaccess.Auth;
import business.Address;
import business.Author;
import business.Book;
import business.BookCopy;
import business.ControllerInterface;
import business.LoginException;
import business.SystemController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class BookWindow extends Stage {
	public static final BookWindow INSTANCE = new BookWindow();

	public static final ControllerInterface controllerInterface = new SystemController();

	private TableView<Book> tableBook;
	private TableView<BookCopy> tableBookCopy;
	private TableView<Author> tableAuthor;

	private List<Book> books = new ArrayList<>();
	private List<Author> authors = new ArrayList<>();
	private List<Address> addresses = new ArrayList<>();
	private List<Author> selectedAuthors = new ArrayList<>();

	private GridPane bookGrid = new GridPane();

	private Book selectedBook;

	private Label label0, label1, label2, label3, label4 = new Label();

	private TextField textField0, textField1, textField2, textField3, textField4 = new TextField("");

	private Button clearBtn = new Button("Clear");
	private Button backBtn = new Button("Back");
	private Button saveBtn = new Button("Save");

	private HBox btnBox = new HBox(10);

	private boolean isInitialized = false;

	private Alert alert = new Alert(AlertType.WARNING, "");

	private BookWindow() {
	}

	public boolean isInitialized() {
		return isInitialized;
	}

	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	public void clear() {
		selectedAuthors.clear();
		textField0 = new TextField("");
		textField1 = new TextField("");
		textField2 = new TextField("");
		textField3 = new TextField("");
		textField4 = new TextField("");
	}

	@SuppressWarnings("unchecked")
	private void setBookTableColumns() {
		tableBook = new TableView<Book>();
		TableColumn<Book, String> bookNameCol = new TableColumn<>("Book Title");
		bookNameCol.setMinWidth(200);
		bookNameCol.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		bookNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		tableBook.getColumns().addAll(bookNameCol);
		TableColumn<Book, String> bookISBNCol = new TableColumn<>("Book ISBN");
		bookISBNCol.setMinWidth(100);
		bookISBNCol.setCellValueFactory(new PropertyValueFactory<Book, String>("isbn"));
		bookISBNCol.setCellFactory(TextFieldTableCell.forTableColumn());
		tableBook.getColumns().addAll(bookISBNCol);
		TableColumn<Book, Integer> bookCopiesCol = new TableColumn<>("Book copies");
		bookCopiesCol.setMinWidth(100);
		bookCopiesCol.setCellValueFactory(new PropertyValueFactory<Book, Integer>("numCopies"));
		tableBook.getColumns().addAll(bookCopiesCol);
	}

	@SuppressWarnings({ "unchecked" })
	private void setBookCopyTableColumns() {
		tableBookCopy = new TableView<BookCopy>();
		TableColumn<BookCopy, Integer> bookISBNCol = new TableColumn<>("Num Copy");
		bookISBNCol.setMinWidth(200);
		bookISBNCol.setCellValueFactory(new PropertyValueFactory<BookCopy, Integer>("copyNum"));
		tableBookCopy.getColumns().addAll(bookISBNCol);
	}

	@SuppressWarnings("unchecked")
	private void setAuthorTableColumns() {
		tableAuthor = new TableView<Author>();
		TableColumn<Author, String> firstNameCol = new TableColumn<>("First Name");
		firstNameCol.setMinWidth(100);
		firstNameCol.setCellValueFactory(new PropertyValueFactory<Author, String>("firstName"));
		firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		tableAuthor.getColumns().addAll(firstNameCol);
		TableColumn<Author, String> lastNameCol = new TableColumn<>("Last Name");
		lastNameCol.setMinWidth(100);
		lastNameCol.setCellValueFactory(new PropertyValueFactory<Author, String>("lastName"));
		lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		tableAuthor.getColumns().addAll(lastNameCol);
		TableColumn<Author, String> bioCol = new TableColumn<>("Bio");
		bioCol.setMinWidth(100);
		bioCol.setCellValueFactory(new PropertyValueFactory<Author, String>("bio"));
		bioCol.setCellFactory(TextFieldTableCell.forTableColumn());
		tableAuthor.getColumns().addAll(bioCol);
		TableColumn<Author, String> phoneCol = new TableColumn<>("Phone");
		phoneCol.setMinWidth(100);
		phoneCol.setCellValueFactory(new PropertyValueFactory<Author, String>("telephone"));
		phoneCol.setCellFactory(TextFieldTableCell.forTableColumn());
		tableAuthor.getColumns().addAll(phoneCol);
	}

	private void showBookCopiesGrid() {
		clear();
		setTitle("Book copy");
		setBookCopyTableColumns();
		tableBookCopy.setItems(FXCollections.observableArrayList(selectedBook.getCopies()));
		backBtn.setOnAction(evt -> {
			createMainGrid();
			setScene(bookGrid, 500, 400);
		});
		setGrid();
		bookGrid.add(tableBookCopy, 0, 0);
		bookGrid.add(backBtn, 0, 1);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addAuthorGrid() {
		clear();
		setTitle("Add book author");

		label0 = new Label("First Name : ");
		label1 = new Label("Last Name : ");
		label2 = new Label("Telephone : ");
		label3 = new Label("Bio : ");
		label4 = new Label("Address : ");

		ComboBox addressComboBox = new ComboBox();
		addressComboBox.setItems(FXCollections.observableArrayList(addresses));

		saveBtn.setOnAction(evt -> {
			String fName = textField0.getText();
			String lName = textField1.getText();
			String tel = textField2.getText();
			String bio = textField3.getText();
			if (fName.isEmpty() || lName.isEmpty() || tel.isEmpty() || bio.isEmpty()) {
				alert = new Alert(AlertType.WARNING, "All field should be nonempty!", ButtonType.YES);
				alert.showAndWait();
				return;
			}
			Address selectedAddress = (Address) addressComboBox.getSelectionModel().getSelectedItem();
			if (selectedAddress == null) {
				alert = new Alert(AlertType.WARNING, "You should choose address!", ButtonType.YES);
				alert.showAndWait();
				return;
			}
			if (!authors.isEmpty()) {
				for (Author author : authors) {
					if (author.getFirstName().equalsIgnoreCase(fName) && author.getLastName().equalsIgnoreCase(lName)) {
						alert = new Alert(AlertType.WARNING,
								"This author has already registered!", ButtonType.YES);
						alert.showAndWait();
						return;
					}
				}
			}

			Author newAuthor = new Author(fName, lName, tel, selectedAddress, bio);
			authors.add(newAuthor);
			controllerInterface.loadAuthorMap(authors);
			addBookGrid();
			setScene(bookGrid, 500, 600);
		});
		clearBtn.setOnAction(evt -> {
			clear();
			addAuthorGrid();
			setScene(bookGrid, 500, 400);
		});
		backBtn.setOnAction(evt -> {
			addBookGrid();
			setScene(bookGrid, 500, 600);
		});

		setGrid();
		bookGrid.add(label0, 0, 0, 2, 1);
		bookGrid.add(textField0, 0, 1, 2, 1);
		bookGrid.add(label1, 0, 2, 2, 1);
		bookGrid.add(textField1, 0, 3, 2, 1);
		bookGrid.add(label2, 0, 4, 2, 1);
		bookGrid.add(textField2, 0, 5, 2, 1);
		bookGrid.add(label3, 0, 6, 2, 1);
		bookGrid.add(textField3, 0, 7, 2, 1);
		bookGrid.add(label4, 0, 8, 2, 1);
		bookGrid.add(addressComboBox, 0, 9, 2, 1);
		btnBox = new HBox(10);
		btnBox.setAlignment(Pos.CENTER_RIGHT);
		btnBox.getChildren().add(saveBtn);
		btnBox.getChildren().add(clearBtn);
		btnBox.getChildren().add(backBtn);
		bookGrid.add(btnBox, 0, 11, 2, 1);
	}

	private void addBookGrid() {
		clear();
		setTitle("Add book");

		label0 = new Label("Title : ");
		label1 = new Label("ISBN : ");
		label2 = new Label("Number of copy : ");
		label3 = new Label("Max Checkout length : ");

		setAuthorTableColumns();
		List<String> listAuthorString = new ArrayList<>();
		if(!authors.isEmpty())
			for(Author author : authors){
				listAuthorString.add(author.toString());
			}
		ComboBox<String> authorComboBox = new ComboBox<String>();
		authorComboBox.setItems(FXCollections.observableArrayList(listAuthorString));

		Label authorsLabel = new Label("Authors : ");

		Button selectAuthorButton = new Button("Select au");
		Button removeAuthorButton = new Button("Remove author");
		Button addAuthorButton = new Button("Add author");

		addAuthorButton.setOnAction(evt -> {
			addresses = controllerInterface.addresses();
			addAuthorGrid();
			setScene(bookGrid, 400, 400);
		});
		selectAuthorButton.setOnAction(evt -> {
			if (authorComboBox.getSelectionModel().getSelectedItem() == null) {
				alert = new Alert(AlertType.WARNING, "Choose author by combo box!", ButtonType.YES);
				alert.showAndWait();
				return;
			}
			if(!selectedAuthors.isEmpty()){
				for(Author author : selectedAuthors){
					if(author.toString().equals(authorComboBox.getSelectionModel().getSelectedItem())){
						alert = new Alert(AlertType.WARNING, "Already chosen author, please choose other author!", ButtonType.YES);
						alert.showAndWait();
						return;
					}
				}
			}
			for(Author author : authors){
				if(author.toString().equals(authorComboBox.getSelectionModel().getSelectedItem())){
					selectedAuthors.add(author);
					break;
				}
			}

			tableAuthor.setItems(FXCollections.observableArrayList(selectedAuthors));
			tableAuthor.refresh();
		});
		removeAuthorButton.setOnAction(evt->{
			if (authorComboBox.getSelectionModel().getSelectedItem() == null) {
				alert = new Alert(AlertType.WARNING, "Choose author!", ButtonType.YES);
				alert.showAndWait();
				return;
			}
			selectedAuthors.remove(tableAuthor.getSelectionModel().getSelectedItem());
			tableAuthor.setItems(FXCollections.observableArrayList(selectedAuthors));
			tableAuthor.refresh();
		});
		saveBtn.setOnAction(evt -> {
			String title = textField0.getText();
			String isbn = textField1.getText();
			int maxLenght = 0;
			if (title.isEmpty() || isbn.isEmpty() || textField3.getText().isEmpty()) {
				alert = new Alert(AlertType.WARNING, "All field should be nonempty!", ButtonType.YES);
				alert.showAndWait();
				return;
			}
			if (!isbn.matches("\\d\\d-\\d\\d\\d\\d\\d")) {
				alert = new Alert(AlertType.WARNING, "ISBN field template (12-34567)!", ButtonType.YES);
				alert.showAndWait();
				return;
			}
			for (char x : isbn.toCharArray()) {
				if (x != '-' && (x < '0' || x > '9')) {
					alert = new Alert(AlertType.WARNING, "ISBN field template (12-34567)!", ButtonType.YES);
					alert.showAndWait();
					return;
				}
			}
			if (!books.isEmpty()) {
				for (Book book : books) {
					if (book.getTitle().equalsIgnoreCase(title)){
						alert = new Alert(AlertType.WARNING,
								"Your title is duplicated, you should change your book title!", ButtonType.YES);
						alert.showAndWait();
						return;
					}
					if(book.getIsbn().equalsIgnoreCase(isbn)) {
						alert = new Alert(AlertType.WARNING,
								"Your isbn is duplicated, you should change your book isbn!", ButtonType.YES);
						alert.showAndWait();
						return;
					}
				}
			}
			try {
				maxLenght = Integer.parseInt(textField3.getText());
			} catch (NumberFormatException ex) {
				alert = new Alert(AlertType.WARNING, "Max length field should be numeric!", ButtonType.YES);
				alert.showAndWait();
				return;
			}
			
//			try {
//				Integer.parseInt(textField2.getText());
//			} catch (NumberFormatException ex) {
//				alert = new Alert(AlertType.WARNING, "Number of copy field should be numeric!", ButtonType.YES);
//				alert.showAndWait();
//				return;
//			}
			if (selectedAuthors.isEmpty()) {
				alert = new Alert(AlertType.WARNING, "You should choose author!", ButtonType.YES);
				alert.showAndWait();
				return;
			}
			Book newBook = new Book(isbn, title, maxLenght, selectedAuthors);
			books.add(newBook);
			controllerInterface.loadBookMap(books);
			createMainGrid();
			setScene(bookGrid, 500, 400);
		});
		clearBtn.setOnAction(evt -> {
			addBookGrid();
			setScene(bookGrid, 500, 600);
		});
		backBtn.setOnAction(evt -> {
			createMainGrid();
			setScene(bookGrid, 500, 400);
		});

		setGrid();
		bookGrid.add(label0, 0, 0, 4, 1);
		bookGrid.add(textField0, 0, 1, 4, 1);
		bookGrid.add(label1, 0, 2, 4, 1);
		bookGrid.add(textField1, 0, 3, 4, 1);
		bookGrid.add(label3, 0, 4, 4, 1);
		bookGrid.add(textField3, 0, 5, 4, 1);
//		bookGrid.add(label2, 0, 6, 4, 1);
//		bookGrid.add(textField2, 0, 7, 4, 1);
		bookGrid.add(authorsLabel, 0, 9, 4, 1);
//		bookGrid.add(authorComboBox, 0, 9, 4, 1);
//		bookGrid.add(selectAuthorButton, 3, 9, 4, 1);
		btnBox = new HBox(10);
		btnBox.setAlignment(Pos.CENTER_RIGHT);
		btnBox.getChildren().add(authorComboBox);
		btnBox.getChildren().add(selectAuthorButton);
		bookGrid.add(btnBox, 0, 10, 4, 1);
		bookGrid.add(tableAuthor, 0, 11, 4, 1);
		btnBox = new HBox(10);
		btnBox.setAlignment(Pos.CENTER);
		btnBox.getChildren().add(saveBtn);
		btnBox.getChildren().add(addAuthorButton);
		btnBox.getChildren().add(removeAuthorButton);
		btnBox.getChildren().add(clearBtn);
		btnBox.getChildren().add(backBtn);
		bookGrid.add(btnBox, 0, 12, 4, 1);
	}

	private void createMainGrid() {
		setTitle("Book list");
		setBookTableColumns();
		tableBook.setItems(FXCollections.observableArrayList(books));
		Label searchLabel = new Label("Search book :");
		TextField searchText = new TextField("");

		Button addBookCopyButton = new Button("Add book copy");
		Button addButton = new Button("Add book");
		Button seeBookCopyButton = new Button("See book copy");

		addBookCopyButton.setOnAction(evt -> {
			selectedBook = tableBook.getSelectionModel().getSelectedItem();
			if (selectedBook == null) {
				alert = new Alert(AlertType.WARNING, "Please select a book!", ButtonType.YES);
				alert.showAndWait();
				return;
			}
			selectedBook.addCopy();
			controllerInterface.loadBookMap(books);
			tableBook.refresh();
		});
		addButton.setOnAction(evt -> {
			addBookGrid();
			setScene(bookGrid, 500, 600);
		});
		seeBookCopyButton.setOnAction(evt -> {
			selectedBook = tableBook.getSelectionModel().getSelectedItem();
			if (selectedBook == null) {
				alert = new Alert(AlertType.WARNING, "Please select a book!", ButtonType.YES);
				alert.showAndWait();
				return;
			}
			if (selectedBook.getNumCopies() == 0) {
				alert = new Alert(AlertType.WARNING, "Book copy not found!", ButtonType.YES);
				alert.showAndWait();
				return;
			}
			showBookCopiesGrid();
			setScene(bookGrid, 500, 400);
		});
		searchText.setOnAction(evt -> {
			if (searchText.getText().isEmpty()) {
				tableBook.setItems(FXCollections.observableArrayList(books));
				alert = new Alert(AlertType.WARNING, "Insert search value!", ButtonType.YES);
				alert.showAndWait();
				return;
			}
			String searchValue = searchText.getText();
			List<Book> filteredBooks = new ArrayList<>();
			if (!books.isEmpty()) {
				for (Book book : books) {
					if (!book.getIsbn().toLowerCase().contains(searchValue.toLowerCase())
							&& !book.getTitle().toLowerCase().contains(searchValue.toLowerCase()))
						continue;

					filteredBooks.add(book);
				}
				if (!filteredBooks.isEmpty()) {
					tableBook.setItems(FXCollections.observableArrayList(filteredBooks));
				} else {
					tableBook.setItems(FXCollections.observableArrayList(books));
					alert = new Alert(AlertType.WARNING, "Book not found!", ButtonType.YES);
					alert.showAndWait();
				}
			}
			searchText.setText("");
		});
		backBtn.setOnAction(evt -> {
			ControllerInterface c = new SystemController();
			HomePage.hideAllWindows();
			try {
				if (c.login(HomePage.loginTextField.getText().trim(), HomePage.pwTextField.getText().trim()).equals(Auth.ADMIN)) {
					if (!AdminWindow.INSTANCE.isInitialized()) {
						AdminWindow.INSTANCE.init();
					}
					AdminWindow.INSTANCE.clear();
					AdminWindow.INSTANCE.show();
				}
				if (c.login(HomePage.loginTextField.getText().trim(), HomePage.pwTextField.getText().trim()).equals(Auth.BOTH)) {
					if (!LibAdminWindow.INSTANCE.isInitialized()) {
						LibAdminWindow.INSTANCE.init();
					}
					LibAdminWindow.INSTANCE.clear();
					LibAdminWindow.INSTANCE.show();
				}
			} catch (LoginException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		});

		setGrid();
		btnBox = new HBox(10);
		btnBox.setAlignment(Pos.CENTER_RIGHT);
		btnBox.getChildren().add(searchLabel);
		btnBox.getChildren().add(searchText);
		bookGrid.add(btnBox, 0, 0, 1, 1);
		bookGrid.add(tableBook, 0, 1, 2, 1);
		btnBox = new HBox(10);
		btnBox.setAlignment(Pos.CENTER);
		btnBox.getChildren().add(addBookCopyButton);
		btnBox.getChildren().add(addButton);
		btnBox.getChildren().add(seeBookCopyButton);
		btnBox.getChildren().add(backBtn);
		bookGrid.add(btnBox, 0, 2);
	}

	private void setGrid() {
		bookGrid = new GridPane();
		bookGrid.setAlignment(Pos.CENTER);
		bookGrid.setVgap(10);
		bookGrid.setHgap(10);
		bookGrid.setPadding(new Insets(10, 15, 10, 15));
	}

	public void init() {
		books = controllerInterface.books();
		authors = controllerInterface.authors();
		createMainGrid();
		setScene(bookGrid, 500, 400);
	}

	private void setScene(GridPane grid, int SCENE_WIDTH, int SCENE_HEIGHT) {
		Scene scene = new Scene(grid, SCENE_WIDTH, SCENE_HEIGHT);
		scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
		setScene(scene);
	}
}
