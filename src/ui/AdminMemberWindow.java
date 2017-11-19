package ui;

import java.util.List;

import dataaccess.Auth;
import business.LoginException;
import business.SystemController;
import business.ControllerInterface;
import business.LibraryMember;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminMemberWindow extends Stage implements LibWindow {
	public static final AdminMemberWindow INSTANCE = new AdminMemberWindow();
	SystemController cont = new SystemController();
	List<String> memList = cont.allMemberIds();
	int index = -1;
	BorderPane border = new BorderPane();
	GridPane gridAdd = new GridPane();
	GridPane gridMain = new GridPane();
	Scene scene;
	Button btnLoadAdd = new Button();
	Button btnLoadCheckout = new Button();
	Button btnBack = new Button();
	boolean isGridFill = false;
	TableView<LibraryMember> tMember = new TableView<LibraryMember>();

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

	private AdminMemberWindow() {
	}

	public void init() {
		gridMain.setId("top-container");
		gridMain.setAlignment(Pos.BASELINE_LEFT);
		gridMain.setHgap(10);
		gridMain.setVgap(10);
		Button backBtn = new Button("Back to Admin");
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				ControllerInterface c = new SystemController();
				HomePage.hideAllWindows();
				try {
					if (c.login(HomePage.loginTextField.getText().trim(), HomePage.pwTextField.getText().trim())
							.equals(Auth.ADMIN)) {
						if (!AdminWindow.INSTANCE.isInitialized()) {
							AdminWindow.INSTANCE.init();
						}
						AdminWindow.INSTANCE.clear();
						AdminWindow.INSTANCE.show();
					}
					if (c.login(HomePage.loginTextField.getText().trim(), HomePage.pwTextField.getText().trim())
							.equals(Auth.BOTH)) {
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
			}
		});

		if (isGridFill == false) {
			scene = new Scene(border, 900, 400);
			scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
			setScene(scene);
			fillAddFields();
		}
	}

	private void fillAddFields() {
		gridAdd.setId("top-container");
		gridAdd.setAlignment(Pos.BASELINE_LEFT);
		gridAdd.setHgap(10);
		gridAdd.setVgap(10);
		gridAdd.setPadding(new Insets(25, 0, 25, 25));
		Text scenetitle = new Text("Admin member Window");
		scenetitle.setFont(Font.font("Harlow Solid Italic", FontWeight.NORMAL, 20)); // Tahoma
		Label lMemberId = new Label("Member ID:");
		TextField txtMemberId = new TextField();
		Label lFirstName = new Label("First name:");
		TextField txtFirstName = new TextField();
		Label lLastName = new Label("Last name:");
		TextField txtLastName = new TextField();
		Label lStreet = new Label("Street:");
		TextField txtStreet = new TextField();
		Label lCity = new Label("City:");
		TextField txtCity = new TextField();
		Label lState = new Label("State:");
		TextField txtState = new TextField();
		Label lZip = new Label("Zip:");
		TextField txtZip = new TextField();
		Label lPhone = new Label("Phone:");
		TextField txtPhone = new TextField();
		tMember.setEditable(false);
		if (isGridFill == false) {
			TableColumn<LibraryMember, String> memberIdCol = new TableColumn<LibraryMember, String>("Member ID");
			TableColumn<LibraryMember, String> firstNameCol = new TableColumn<LibraryMember, String>("First Name");
			TableColumn<LibraryMember, String> lastNameCol = new TableColumn<LibraryMember, String>("Last Name");
			memberIdCol.setMinWidth(60);
			firstNameCol.setMinWidth(120);
			lastNameCol.setMinWidth(120);

			memberIdCol.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>("memberId"));
			memberIdCol.setCellFactory(TextFieldTableCell.forTableColumn());
			firstNameCol.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>("firstName"));
			firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
			lastNameCol.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>("lastName"));
			lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
			tMember.getColumns().addAll(memberIdCol, firstNameCol, lastNameCol);
			isGridFill = true;
		}

		Button btnBack = new Button("Back to Admin");
		btnBack.setPrefWidth(150);
		btnBack.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ControllerInterface c = new SystemController();
				txtMemberId.clear();
				txtFirstName.clear();
				txtLastName.clear();
				txtPhone.clear();
				txtState.clear();
				txtStreet.clear();
				txtCity.clear();
				txtZip.clear();
				HomePage.hideAllWindows();
				try {
					if (c.login(HomePage.loginTextField.getText().trim(), HomePage.pwTextField.getText().trim())
							.equals(Auth.ADMIN)) {
						if (!AdminWindow.INSTANCE.isInitialized()) {
							AdminWindow.INSTANCE.init();
						}
						AdminWindow.INSTANCE.clear();
						AdminWindow.INSTANCE.show();
					}
					if (c.login(HomePage.loginTextField.getText().trim(), HomePage.pwTextField.getText().trim())
							.equals(Auth.BOTH)) {
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
			}
		});
		Button btnClear = new Button("Clear fields");
		btnClear.setPrefWidth(150);
		btnClear.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				txtMemberId.setText("");
				txtFirstName.setText("");
				txtLastName.setText("");
				txtPhone.setText("");
				txtState.setText("");
				txtStreet.setText("");
				txtCity.setText("");
				txtZip.setText("");
			}
		});
		Button btnAddMember = new Button("Add member");
		btnAddMember.setPrefWidth(150);
		btnAddMember.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SystemController cont = new SystemController();

				ObservableList<LibraryMember> data = cont.allMembers();
				boolean isCreatedMember = false;
				for (int i = 0; i < data.size(); i++) {
					if (data.get(i).getMemberId().equals(txtMemberId.getText().trim())) {
						Alert alert = new Alert(AlertType.WARNING, "Duplicated member ID!!!", ButtonType.OK);
						alert.showAndWait();
						isCreatedMember = true;
					}
				}
				if (isCreatedMember == false) {
					String id = txtMemberId.getText();
					String fname = txtFirstName.getText();
					String lname = txtLastName.getText();
					String phone = txtPhone.getText();
					String street = txtStreet.getText();
					String city = txtCity.getText();
					String state = txtState.getText();
					String zip = txtZip.getText();
					System.out.println("name:"+fname.trim());
					if (!id.trim().isEmpty() && !fname.trim().isEmpty() && !lname.trim().isEmpty() && !phone.trim().isEmpty()
							&& !zip.trim().isEmpty() && !street.trim().isEmpty() && !city.trim().isEmpty() && !state.trim().isEmpty()) {
						cont.addLibraryMember(id, fname, lname, phone, cont.createAddress(street, city, state, zip));
						fillTableView();
						txtMemberId.clear();
						txtFirstName.clear();
						txtLastName.clear();
						txtPhone.clear();
						txtState.clear();
						txtStreet.clear();
						txtCity.clear();
						txtZip.clear();
					} else {
						Alert alert = new Alert(AlertType.WARNING, "Please fill out all blank fields!!!",
								ButtonType.OK);
						alert.showAndWait();
					}
				}

			}
		});
		txtMemberId.setOnKeyPressed(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				if (!txtMemberId.getText().matches("[0-9]+"))
					txtMemberId.clear();
			}
		});
		txtZip.setOnKeyPressed(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				if (!txtZip.getText().matches("[0-9]+"))
					txtZip.clear();
			}
		});
		Button btnRemoveMember = new Button("Remove member");
		btnRemoveMember.setPrefWidth(150);
		btnRemoveMember.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure to remove this member?", ButtonType.YES,
						ButtonType.NO, ButtonType.CANCEL);
				alert.showAndWait();
				if (alert.getResult() == ButtonType.YES) {
					SystemController cont = new SystemController();
					cont.removeLibraryMember(txtMemberId.getText().trim());
					fillTableView();
					txtMemberId.clear();
					txtFirstName.clear();
					txtLastName.clear();
					txtPhone.clear();
					txtState.clear();
					txtStreet.clear();
					txtCity.clear();
					txtZip.clear();
				}
			}
		});
		tMember.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {

				SystemController cont = new SystemController();
				if (tMember.getSelectionModel().getSelectedIndex() > -1) {
					index = tMember.getSelectionModel().getSelectedIndex();
					ObservableList<LibraryMember> data = cont.allMembers();
					data.get(index);
					txtMemberId.setText(data.get(index).getMemberId());
					txtFirstName.setText(data.get(index).getFirstName());
					txtLastName.setText(data.get(index).getLastName());
					txtPhone.setText(data.get(index).getTelephone());
					txtState.setText(data.get(index).getAddress().getState());
					txtStreet.setText(data.get(index).getAddress().getStreet());
					txtCity.setText(data.get(index).getAddress().getCity());
					txtZip.setText(data.get(index).getAddress().getZip());
				}
			}
		});
		Button btnEditMember = new Button("Edit member");
		btnEditMember.setPrefWidth(150);
		btnEditMember.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SystemController cont = new SystemController();
				ObservableList<LibraryMember> data = cont.allMembers();

				String id = txtMemberId.getText();
				String fname = txtFirstName.getText();
				String lname = txtLastName.getText();
				String phone = txtPhone.getText();
				String street = txtStreet.getText();
				String city = txtCity.getText();
				String state = txtState.getText();
				String zip = txtZip.getText();
				cont.addLibraryMember(id, fname, lname, phone, cont.createAddress(street, city, state, zip));
				fillTableView();
				txtMemberId.clear();
				txtFirstName.clear();
				txtLastName.clear();
				txtPhone.clear();
				txtState.clear();
				txtStreet.clear();
				txtCity.clear();
				txtZip.clear();
			}
		});
		fillTableView();
		HBox hbox = new HBox();
		hbox.setSpacing(5);
		tMember.setMinWidth(300);
		hbox.setPadding(new Insets(10, 10, 10, 10));
		hbox.getChildren().add(tMember);

		gridAdd.add(lMemberId, 0, 1);
		gridAdd.add(txtMemberId, 1, 1);
		gridAdd.add(lFirstName, 0, 2);
		gridAdd.add(txtFirstName, 1, 2);
		gridAdd.add(lLastName, 0, 3);
		gridAdd.add(txtLastName, 1, 3);
		gridAdd.add(lStreet, 2, 1);
		gridAdd.add(txtStreet, 3, 1);
		gridAdd.add(lCity, 2, 2);
		gridAdd.add(txtCity, 3, 2);
		gridAdd.add(lState, 2, 3);
		gridAdd.add(txtState, 3, 3);
		gridAdd.add(lZip, 2, 4);
		gridAdd.add(txtZip, 3, 4);
		gridAdd.add(lPhone, 2, 5);
		gridAdd.add(txtPhone, 3, 5);
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.CENTER);
		hbBtn.getChildren().add(btnBack);
		gridAdd.add(hbBtn, 1, 6);
		gridAdd.add(btnAddMember, 3, 6);
		gridAdd.add(btnEditMember, 3, 7);
		gridAdd.add(btnRemoveMember, 3, 8);
		gridAdd.add(btnClear, 3, 9);
		border.setLeft(gridAdd);
		border.setRight(hbox);

	}

	private void fillTableView() {
		SystemController cont = new SystemController();
		ObservableList<LibraryMember> data = cont.allMembers();
		tMember.setItems(data);
	}
}
