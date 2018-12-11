package application;


import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;




public class AppController implements Initializable{
	
	@FXML
	private MenuItem mBarFileExit, mBarLogout;
	@FXML
	private Button btnAdd, btnRefresh, btnDelete;
	@FXML
	private TextArea txtArea;
	@FXML
	private Text txtBottom;
	@FXML
	private TextField inpFName, inpLName, inpNumber, inpEmail;	
	@FXML
	TableColumn<Contact, String> tcFname, tcLname, tcNumber, tcEmail, tcId;;
	@FXML
    private TableView<Contact> contactsTable;
	
	private Stage stage;
	//
	
	Object object;
	String index;
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public AppController() {
	}
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {				
		tcFname.setCellValueFactory(cellData -> ((Contact) cellData.getValue()).getFirstNameProperty());
        tcLname.setCellValueFactory(cellData -> ((Contact) cellData.getValue()).getLastNameProperty());
        tcId.setCellValueFactory(cellData -> ((Contact) cellData.getValue()).getIdProperty());
        tcEmail.setCellValueFactory(cellData -> ((Contact) cellData.getValue()).getEmailProperty());
        tcNumber.setCellValueFactory(cellData -> ((Contact) cellData.getValue()).getNumberProperty());
        updateTableData();
        btnDelete.setDisable(true);
        
        contactsTable.setRowFactory(tv -> {
		    TableRow<Contact> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY) {
		            Contact clickedRow = row.getItem();
		            index = clickedRow.getId();
		            btnDelete.setDisable(false);
		        }
		    });
		    return row ;
		});
	}

	@FXML
	private void mBarFileExit(ActionEvent event) {			    	
		    	Platform.exit();
		        System.exit(0);
	}
	
	@FXML
	private void mBarLogout(ActionEvent event) throws IOException {		
		LoginController.userID = 0;
		
		this.stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root1 = (Parent) loader.load();
        Stage stage = new Stage();
        LoginController controller = (LoginController)loader.getController();
		controller.setStage(stage);

        stage.setTitle("Login");
        stage.setScene(new Scene(root1));  
        stage.setResizable(false);
        stage.show();       
             
	}
	
	@FXML
	private void btnAdd(ActionEvent event) throws IOException {
		
		inpFName.setStyle(null);
		inpLName.setStyle(null);
		inpNumber.setStyle(null);
		inpEmail.setStyle(null);
		
		String fname = inpFName.getText();
		String lname = inpLName.getText();
		String number = inpNumber.getText();
		String email = inpEmail.getText();
				
		if(checkRegex(fname, lname, number, email) == 4) {
			Database db = new Database();
			db.addContact(LoginController.userID, fname, lname, number, email);		
			updateTableData();
			
			inpFName.setStyle(null);
			inpLName.setStyle(null);
			inpNumber.setStyle(null);
			inpEmail.setStyle(null);
			
			inpFName.setText("");
			inpLName.setText("");
			inpNumber.setText("");
			inpEmail.setText("");
		}
	}
	
	@FXML
	private void btnRefresh(ActionEvent event) throws IOException {
		updateTableData();
	}
	
	@FXML
	private void btnDelete(ActionEvent event) throws IOException {
		if(index != null && Integer.valueOf(index) > 0) {
			
			ButtonType btnYes = new ButtonType("Yes", ButtonData.OK_DONE);
			ButtonType btnNo = new ButtonType("No", ButtonData.CANCEL_CLOSE);
			
			Alert alert = new Alert(AlertType.NONE, "Are you sure you want to delete row with ID " + index, btnYes, btnNo);
			alert.setTitle("Confirm record deletion");
			Optional<ButtonType> result = alert.showAndWait();
			
			if (result.orElse(btnNo) == btnYes) {
				Database db = new Database();
				db.deleteContact(Integer.valueOf(index));
				updateTableData();
				index = null;
				btnDelete.setDisable(true);  
			}	
		}
	}
	
	private void updateTableData() {
		Database db = new Database();		
		contactsTable.setItems(db.getUserContacts(LoginController.userID));	
	}

	private int checkRegex(String fname, String lname, String number, String email) {
		int result = 0;
		String regexText = "[A-Z0-9a-z]*";
		String regexNumber = "[0-9]{9,10}";	
		String regexEmail = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}";
		
		//check firstname
		if (fname.isEmpty() || !fname.matches(regexText)) {
			inpFName.setStyle("-fx-border-color: red;");
			result = 0;
		}
		else {
			inpFName.setStyle("-fx-border-color: green;");
			result++;
		}
		
		//check lastname
		if (lname.isEmpty() || !lname.matches(regexText)) {
			inpLName.setStyle("-fx-border-color: red;");
			result = 0;
		}
		else {
			inpLName.setStyle("-fx-border-color: green;");
			result++;
		}
		
		//check number
		if (number.isEmpty() || !number.matches(regexNumber)) {
			inpNumber.setStyle("-fx-border-color: red;");
			result = 0;
		}
		else {
			inpNumber.setStyle("-fx-border-color: green;");
			result++;
		}
		
		//check email
		if (email.isEmpty() || !email.matches(regexEmail)) {
			inpEmail.setStyle("-fx-border-color: red;");
			result = 0;
		}
		else {
		inpEmail.setStyle("-fx-border-color: green;");
			result++;
		}
		
		return result;	
	} 
	
}
	
	
	
	
	
	
	














