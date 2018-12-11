package application;

	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	
	
	@Override
	public void start(Stage primaryStage) {
		
		
		
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
			Parent root = (Parent)loader.load();
			LoginController controller = (LoginController)loader.getController();
			controller.setStage(primaryStage);
			
			
			
			
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Login");
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args) throws IOException {
		
		
	
		
		launch(args);
		
	}
}
