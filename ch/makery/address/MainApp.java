package ch.makery.address;

import java.io.IOException;












import ch.makery.address.view.MainViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.initStyle(StageStyle.UNDECORATED);
        this.primaryStage.setWidth(850);
        this.primaryStage.getIcons().add(new Image(MainApp.class.getResourceAsStream( "ico.png" ))); 
        this.primaryStage.setTitle("EDEX STOCK ANALYZER");
        this.primaryStage.setMinWidth(870);
        

        initRootLayout();

        showGraficoOverview();
    }

    /**
     * Inizializzo il layout principale (barra File, Edit, About)
     */
    public void initRootLayout() {
        try {
            // carico il layout fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // mostro la scena contenente il layout. 
            rootLayout.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });
            
            rootLayout.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    primaryStage.setX(event.getScreenX() - xOffset);
                    primaryStage.setY(event.getScreenY() - yOffset);
                }
            });
            
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * mostro il corpo principale, grafico e opzioni vaire
     */
    public void showGraficoOverview() {
        try {
            // carico l'FXML
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainView.fxml"));
            AnchorPane MainView = (AnchorPane) loader.load();

            // lo posiziono al centro
            rootLayout.setCenter(MainView);

            // permetto al MainViewControlle di accedere alla main app.
            MainViewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ritorna lo stage principale
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
	public Object getGraficoData() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void closeApp() {
		Platform.exit();
	}
	
	public Stage getStage(){
		return primaryStage;
		
	}
	
}

