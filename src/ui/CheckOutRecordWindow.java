package ui;

import java.time.LocalDate;

import business.BookCart;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CheckOutRecordWindow extends Stage {
	public static final CheckOutRecordWindow INSTANCE = new CheckOutRecordWindow();
	
	private TableView<BookCart> table = new TableView<BookCart>();
	
	Text actiontarget = new Text();
	public void clear() {
		actiontarget.setText("");
	}
	
	private boolean isInitialized = false;
	
	public boolean isInitialized() {
		return isInitialized;
	}
	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	public void setData(ObservableList<BookCart> bookData) {
		table.getItems().clear();
		ObservableList<BookCart> current = table.getItems();
		if(current != null) {
			current.addAll(bookData);
		}
		table.setItems(current);
		
	}
	
	@SuppressWarnings("unchecked")
	public CheckOutRecordWindow() {
		setTitle("Book List");
		actiontarget.setFill(Color.FIREBRICK);
		
		final Label label = new Label(String.format("Member's Record"));
        label.setFont(new Font("Arial", 16));
        HBox labelHbox = new HBox(10);
        labelHbox.setAlignment(Pos.CENTER);
        labelHbox.getChildren().add(label);
        
        TableColumn<BookCart, String> ISBNcol 
		  = new TableColumn<>(String.format("ISBN"));
        ISBNcol.setMinWidth(200);
        ISBNcol.setCellValueFactory(
          new PropertyValueFactory<BookCart, String>("ISBN"));
        ISBNcol.setCellFactory(TextFieldTableCell.forTableColumn());
		
		TableColumn<BookCart, String> bookNameCol 
		  = new TableColumn<>(String.format("Book Name"));
		bookNameCol.setMinWidth(200);
		bookNameCol.setCellValueFactory(
            new PropertyValueFactory<BookCart, String>("title"));
		bookNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		
		TableColumn<BookCart, LocalDate> dateDue
		  = new TableColumn<>("Checkout Date");
		dateDue.setMinWidth(150);
		dateDue.setCellValueFactory(
          new PropertyValueFactory<BookCart, LocalDate>("dueDate"));
		
		table.getColumns().addAll(ISBNcol, bookNameCol, dateDue);
		
		Button backButton = new Button("Back");
		//Button continueButton = new Button("Add Books"); // can be used a sign out button soon
		
		backButton.setOnAction(evt -> {
			LibrarianWindow.INSTANCE.show();
			actiontarget.setText("");
			hide();
		});

		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(10); 
		grid.setHgap(10);
		grid.add(labelHbox, 0, 0);
		grid.add(table, 0, 1);
		grid.add(actiontarget, 0, 2);
		HBox btnBox = new HBox(10);
		btnBox.setAlignment(Pos.CENTER);
		//btnBox.getChildren().add(proceedButton);
		btnBox.getChildren().add(backButton);
		grid.add(btnBox,0,4);
		grid.add(new HBox(10), 0, 5);
		
		Scene scene = new Scene(grid, GuiConstants.SCENE_WIDTH, GuiConstants.SCENE_HEIGHT);  
		scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
		setScene(scene);
	}
}
