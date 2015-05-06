package ch.makery.address.view;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ch.makery.address.MainApp;
import ch.makery.address.view.AboutViewController;

public class RootLayoutController {
	@FXML
	MenuItem close;
	
	@FXML
	MenuItem about;
	
	@FXML
	MenuBar toolbar;
	
	
	MainApp mainApp;
	
	double xOffset;
	double yOffset;
	
	public RootLayoutController()
	{
		
	}
	
	@FXML
    private void initialize() {
		close.setOnAction(new EventHandler<ActionEvent>() {

	        public void handle(ActionEvent e) {
	            Platform.exit();
	        }
		});
		
		about.setOnAction(new EventHandler<ActionEvent>() {

	        public void handle(ActionEvent e) {
	        	
	        	FXMLLoader loader = new FXMLLoader(getClass().getResource("AboutView.fxml"));
	        	AboutViewController controller = new AboutViewController();
	            loader.setController(controller);
	            Parent root = null;
				try {
					root = (Parent) loader.load();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

	            Stage stage = new Stage();
	            stage.setScene(new Scene(root));
	            stage.getIcons().add(new Image(MainApp.class.getResourceAsStream( "ico.png" ))); 
	            stage.setMinHeight(350);
	            stage.setMinWidth(365);
	            stage.setMaxHeight(350);
	            stage.setMaxWidth(365);
	            stage.show();
	     	    
	        }
		});
		
		
		toolbar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        
		toolbar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	
				mainApp.getStage().setX(event.getScreenX() - xOffset);
            	
				mainApp.getStage().setY(event.getScreenY() - yOffset);
            }
        });
    	
    	

    }
	
	

}
