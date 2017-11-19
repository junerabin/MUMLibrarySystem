package ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LibAdminWindow extends Stage implements LibWindow  {
	public static final LibAdminWindow INSTANCE = new LibAdminWindow();

	private Text messageBar = new Text();
	public void clear() {
		messageBar.setText("");
	}
	
	private boolean isInitialized = false;
	
	public boolean isInitialized() {
		return isInitialized;
	}
	public void isInitialized(boolean val) {
		isInitialized = val;
	}
	
	private LibAdminWindow () {}
	
	public void init() {
		//sample code -- your code here
        GridPane grid = new GridPane();
        grid.setId("top-container");
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Library and Administrator Window");
        scenetitle.setFont(Font.font("Harlow Solid Italic", FontWeight.NORMAL, 20)); //Tahoma
        grid.add(scenetitle, 0, 0, 2, 1);
        //------------------------------ Load member ------------------------
        Button btnLoadMember = new Button();
		Image imageMember = new Image(getClass().getResourceAsStream("members_icon.jpg"));
		btnLoadMember.setGraphic(new ImageView(imageMember));
		btnLoadMember.setPrefWidth(200);
		btnLoadMember.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				HomePage.hideAllWindows();
//        		HomePage.primStage().show();
    			if(!AdminMemberWindow.INSTANCE.isInitialized()) {
    				AdminMemberWindow.INSTANCE.init();
    			}
    			AdminMemberWindow.INSTANCE.clear();
    			AdminMemberWindow.INSTANCE.show();
			}
		});

		
		//-------------------------------- Load book ---------------------------
		Button btnLoadBook = new Button();
		Image imageBook = new Image(getClass().getResourceAsStream("addBook.jpg"));
		btnLoadBook.setGraphic(new ImageView(imageBook));
		btnLoadBook.setPrefWidth(200);
		btnLoadBook.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		
        		HomePage.hideAllWindows();
//        		HomePage.primStage().show();
    			if(!BookWindow.INSTANCE.isInitialized()) {
    				BookWindow.INSTANCE.init();
    			}
    			BookWindow.INSTANCE.clear();
    			BookWindow.INSTANCE.show();
        	}
        });
		//-------------------------------- Load checkout book ---------------------------
		Button btnLoadCheckout = new Button();
		Image imageCheckout = new Image(getClass().getResourceAsStream("checkout.jpg"));
		btnLoadCheckout.setGraphic(new ImageView(imageCheckout));
		btnLoadCheckout.setPrefWidth(200);
		btnLoadCheckout.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		
        		HomePage.hideAllWindows();
//        		HomePage.primStage().show();
    			if(!LibrarianWindow.INSTANCE.isInitialized()) {
    				LibrarianWindow.INSTANCE.init();
    			}
    			LibrarianWindow.INSTANCE.clear();
    			LibrarianWindow.INSTANCE.show();
        	}
        });
		
		//-------------------------------- back to home  ---------------------------
		Button btnBack = new Button("Sign out");
		//Image imageCheckout = new Image(getClass().getResourceAsStream("checkout.jpg"));
		//btnLoadCheckout.setGraphic(new ImageView(imageCheckout));
		btnBack.setPrefWidth(80);
		btnBack.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		HomePage.hideAllWindows();
        		HomePage.primStage().show();
        		HomePage.pwTextField.clear();
         	}
        });
		
		
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(btnLoadMember);
        grid.add(hbBtn, 1, 4);
        
        HBox hbBtn1 = new HBox(10);
        hbBtn1.setAlignment(Pos.CENTER);
        hbBtn1.getChildren().add(btnLoadBook);
        grid.add(hbBtn1, 2, 4);
        
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.CENTER);
        hbBtn2.getChildren().add(btnLoadCheckout);
        grid.add(hbBtn2, 3, 4);
        
        HBox hbBtn3 = new HBox(10);
        hbBtn3.setAlignment(Pos.BASELINE_RIGHT);
        hbBtn3.getChildren().add(btnBack);
        grid.add(hbBtn3, 3, 0);
        
        Scene scene = new Scene(grid, 700, 310);
        scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
        setScene(scene);
        
    }
}
