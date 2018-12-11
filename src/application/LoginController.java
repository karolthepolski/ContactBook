package application;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController implements Initializable{
	
	@FXML
	private Button btnLogin, btnRegister;
	@FXML
	private TextField inpUsername, inpPassword;
	@FXML
	private Text txtBottom;

	Stage stage;
	static int userID;
	
	
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {	
		
		
		
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	

	
	
	@FXML
	private void btnLogin(ActionEvent event) throws IOException {
		Database db = new Database();		
		String username = inpUsername.getText();
		String password = inpPassword.getText();
		
		txtBottom.setText("");
		
		if(!username.isEmpty() && !password.isEmpty()) {			
			userID = db.getLogin(username, password);		
			if(userID > 0){
				this.stage.close();
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("App.fxml"));
	            Parent root1 = (Parent) loader.load();
	            
	            
	            Stage stage = new Stage();
	            AppController controller2 = (AppController)loader.getController();
				controller2.setStage(stage);
	            
	            //stage.initModality(Modality.APPLICATION_MODAL);
	            //stage.initStyle(StageStyle.UNDECORATED);
	            stage.setTitle("Databas Uploader, Logged in as: " +username);
	            stage.setScene(new Scene(root1));  
	            stage.setResizable(false);
	            stage.show();      
			}  
			else {
				txtBottom.setFill(Color.RED);
				txtBottom.setText("Wrong credentials");
			}			
		}
		else {
			txtBottom.setFill(Color.RED);
			txtBottom.setText("Please fill both fields");
		}
		
		
	                        
	}
	@FXML
	private void btnRegister(ActionEvent event) {
		txtBottom.setText("");
		Database db = new Database();
		Boolean success = false;
		String username = inpUsername.getText();
		String password = inpPassword.getText();
		
		if(!username.isEmpty() && !password.isEmpty()) {		
			System.out.print(username + password);
			success = db.register(username, password);				
			if(success) {
				txtBottom.setFill(Color.GREEN);
				txtBottom.setText("User registered");
			}	
			else {
				txtBottom.setFill(Color.RED);
				txtBottom.setText("User exists already");
			}
		}
		else {
			txtBottom.setFill(Color.RED);
			txtBottom.setText("Some fields are empty");
		}
		
				
	}
	
}
	
	
	
	
	
	
	














