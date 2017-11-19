package ui;

import dataaccess.Auth;
import business.ControllerInterface;
import business.LoginException;
import business.SystemController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class LibrarianWindow extends Stage implements LibWindow {
	public static final LibrarianWindow INSTANCE = new LibrarianWindow();
	
	public static Text actiontarget = new Text();
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
	
	private LibrarianWindow () {}
	
	public void init() {
        GridPane grid = new GridPane();
        grid.setId("top-container");
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        //grid.setGridLinesVisible(true);

        Text scenetitle = new Text("Check-out Counter");
        scenetitle.setFont(Font.font("Harlow Solid Italic", FontWeight.NORMAL, 20)); //Tahoma
        grid.add(scenetitle, 0, 0, 2, 1);
        
        Label id = new Label("Member ID:");
        grid.add(id, 0, 1);

        TextField idTextField = new TextField();
        grid.add(idTextField, 1, 1);
        
        Label isbn = new Label("ISBN:");
        grid.add(isbn, 0, 2);

        TextField isbnTextField = new TextField();
        grid.add(isbnTextField, 1, 2);

        grid.add(actiontarget, 0,3,3,1);
        //setting id for CSS styling
        actiontarget.setId("actiontarget"); //css
        
        Button checkOutBtn = new Button("Check out");
        checkOutBtn.setMinWidth(120);
        HBox hbCheckBtn = new HBox(10);
        hbCheckBtn.setAlignment(Pos.CENTER);
        hbCheckBtn.getChildren().add(checkOutBtn);
        grid.add(hbCheckBtn, 2, 2);
        
        checkOutBtn.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		clear();
        		ControllerInterface c = new SystemController();
        		try {
					c.librarianLogin(idTextField.getText().trim(), isbnTextField.getText().trim());

				} catch (LoginException ex) {
					actiontarget.setFill(HomePage.Colors.red);
        			actiontarget.setText("Error! " + ex.getMessage());
				}
        	}
        });
        
        Button checkRecord = new Button("Member Record");
        checkRecord.setMinWidth(120);
        HBox hbCheckMember = new HBox(10);
        hbCheckMember.setAlignment(Pos.CENTER);
        hbCheckMember.getChildren().add(checkRecord);
        grid.add(hbCheckMember, 2, 1);
        
        checkRecord.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {;
        		clear();
        		ControllerInterface c = new SystemController();
        		try {
        				c.memberRecord(idTextField.getText().trim());

				} catch (LoginException ex) {
					actiontarget.setFill(HomePage.Colors.red);
        			actiontarget.setText("Error! " + ex.getMessage());
				}
        	}
        });
        
        Button checkOverdue = new Button("Books on loan");
        checkOverdue.setMinWidth(120);
        HBox hbCheckOverdue = new HBox(10);
        hbCheckOverdue.setAlignment(Pos.CENTER);
        hbCheckOverdue.getChildren().add(checkOverdue);
        grid.add(hbCheckOverdue, 3, 1);

        checkOverdue.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {;
        		clear();
        		ControllerInterface c = new SystemController();
        		try {
        				c.bookRecord(isbnTextField.getText().trim());

				} catch (LoginException ex) {
					actiontarget.setFill(HomePage.Colors.red);
        			actiontarget.setText("Error! " + ex.getMessage());
				}
        	}
        });
        
        Button backBtn = new Button("Sign out");
        backBtn.setMinWidth(120);
        HBox hbBackBtn = new HBox(10);
        hbBackBtn.setAlignment(Pos.CENTER);
        hbBackBtn.getChildren().add(backBtn);
        grid.add(hbBackBtn, 3, 2);
             
        backBtn.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		ControllerInterface c = new SystemController();
				HomePage.hideAllWindows();
				try {
					if (c.login(HomePage.loginTextField.getText().trim(), HomePage.pwTextField.getText().trim()).equals(Auth.BOTH)) {
						if (!LibAdminWindow.INSTANCE.isInitialized()) {
							LibAdminWindow.INSTANCE.init();
						}
						LibAdminWindow.INSTANCE.clear();
						LibAdminWindow.INSTANCE.show();
					}
					else {
		        		HomePage.hideAllWindows();
		        		HomePage.primStage().show();
		        		HomePage.pwTextField.clear();
					}
				} catch (LoginException ex) {
					ex.printStackTrace();
				}
        	}
        });
        

        Scene scene = new Scene(grid, 900, 300);
        scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
        setScene(scene);
        
    }

}
