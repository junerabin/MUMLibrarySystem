package ui;

import dataaccess.Auth;
import business.ControllerInterface;
import business.LoginException;
import business.SystemController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomePage extends Application {
	static PasswordField pwTextField = new PasswordField();
	static TextField loginTextField = new TextField();
	
	final Text actiontarget = new Text();
	
	public void clear() {
		actiontarget.setText("");
	}

	public static class Colors {
		static Color green = Color.web("#034220");
		static Color red = Color.FIREBRICK;
	}
	
	private static Stage primStage = null;
	public static Stage primStage() {
		return primStage;
	}
	
	private static Stage[] allWindows = { 
		LibrarianWindow.INSTANCE,
		AdminWindow.INSTANCE,	
		LibAdminWindow.INSTANCE,
		CheckOutRecordWindow.INSTANCE,
		AdminMemberWindow.INSTANCE,
		BookWindow.INSTANCE
	};
	
	public static void hideAllWindows() {
		primStage.hide();
		for(Stage st: allWindows) {
			st.hide();
		}
	}

	public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		 // not yet done with css part
		primStage = primaryStage;
		primaryStage.setTitle("Home Page");
		GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        //grid.setGridLinesVisible(true);

        Text scenetitle = new Text("Library System");
        //setting id for CSS styling
        scenetitle.setFont(Font.font("Century", FontWeight.NORMAL, 20)); //css
        grid.add(scenetitle, 12, 1, 5, 1);

        Label login = new Label("LoginID:");
        grid.add(login, 30, 1);

        grid.add(loginTextField, 31, 1);

        Label password = new Label("Password:");
        grid.add(password, 32, 1);

        
        grid.add(pwTextField, 33, 1);

        grid.add(actiontarget, 30, 2,5,1);
        //setting id for CSS styling
        actiontarget.setId("actiontarget"); //css

        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 34, 1);

        VBox topContainer = new VBox();
		topContainer.setId("top-container");
        VBox imageHolder = new VBox();
		Image image = new Image("ui/books.jpg", 600, 100, false, false);

		// simply displays in ImageView the image as is
        ImageView iv = new ImageView();
        iv.setImage(image);
        imageHolder.getChildren().add(iv);
        imageHolder.setAlignment(Pos.CENTER);

        topContainer.getChildren().add(grid);
		topContainer.getChildren().add(imageHolder);

		btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
            	try {
            		clear();
        			ControllerInterface c = new SystemController();
        			c.login(loginTextField.getText().trim(), pwTextField.getText().trim());
        			
        			if(c.login(loginTextField.getText().trim(), pwTextField.getText().trim()).equals(Auth.LIBRARIAN)) {
        				hideAllWindows();
            			if(!LibrarianWindow.INSTANCE.isInitialized()) {
            				LibrarianWindow.INSTANCE.init();
            			}
            			LibrarianWindow.INSTANCE.clear();
            			LibrarianWindow.INSTANCE.show();
        			}
        			
        			if(c.login(loginTextField.getText().trim(), pwTextField.getText().trim()).equals(Auth.ADMIN)) {
        				hideAllWindows();
            			if(!AdminWindow.INSTANCE.isInitialized()) {
            				AdminWindow.INSTANCE.init();
            			}
            			AdminWindow.INSTANCE.clear();
            			AdminWindow.INSTANCE.show();
        			}
        			
        			if(c.login(loginTextField.getText().trim(), pwTextField.getText().trim()).equals(Auth.BOTH)) {
        				hideAllWindows();
            			if(!LibAdminWindow.INSTANCE.isInitialized()) {
            				LibAdminWindow.INSTANCE.init();
            			}
            			LibAdminWindow.INSTANCE.clear();
            			LibAdminWindow.INSTANCE.show();
        			}
        			
        		} catch(LoginException ex) {
        			actiontarget.setFill(HomePage.Colors.red);
        			actiontarget.setText("Error! " + ex.getMessage());
        		}
            }
        });

        Scene scene = new Scene(topContainer, 900, 300 );
		primaryStage.setScene(scene);
		scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
		primaryStage.show();


	}

}
