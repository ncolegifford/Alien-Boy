package application;
	
import java.nio.file.Paths;
import java.sql.DriverManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

//Public Variables
public static int UserId = 0; //0=not logged in
public static Media AmbientMusic, FireSound;
public static MediaPlayer AmbientPlayer, FirePlayer;
public static Scene scene;
private static final int WIDTH = 1280;
private static final int HEIGHT = 720;
private static final int FRAMES_PER_SECOND = 60;
public static int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
public static boolean freezeTime = false; 

private static Stage primaryStage; // **Declare static Stage**

//SQL stuff
public static Connection conn = null;

private void setPrimaryStage(Stage stage) {
    Main.primaryStage = stage;
}

static public Stage getPrimaryStage() {
    return Main.primaryStage;
}

static public Game game; 


static public void restartGame() {
   	primaryStage.setScene(scene);
	primaryStage.show();
	game.finishedGame = true;
	SampleController.animation.stop(); 
	
	AmbientPlayer.stop(); 
	
	AmbientMusic = new Media(Paths.get("bin/application/Audio/harps_introduce.mp3").toUri().toString()); //kill me
	FireSound = new Media(Paths.get("bin/application/Audio/Fire.mp3").toUri().toString());
	AmbientPlayer = new MediaPlayer(AmbientMusic);
	AmbientPlayer.setOnEndOfMedia(new Runnable() {
        public void run() {
        	AmbientPlayer.seek(Duration.ZERO);
        }
    });
	
	AmbientPlayer.play();
	
	
	
	
	
	
}


@Override
public void start(Stage primaryStage) {
	primaryStage.setTitle("alphaBuildV1");
	setPrimaryStage(primaryStage);
	try {
		AmbientMusic = new Media(Paths.get("bin/application/Audio/harps_introduce.mp3").toUri().toString()); //kill me
		FireSound = new Media(Paths.get("bin/application/Audio/Fire.mp3").toUri().toString());
		AmbientPlayer = new MediaPlayer(AmbientMusic);
		FirePlayer = new MediaPlayer(FireSound);
		FirePlayer.setVolume(0.1);
    	AmbientPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
            	FirePlayer.seek(Duration.ZERO); 
            	AmbientPlayer.seek(Duration.ZERO);
            }
        });
    	
    	FirePlayer.play(); 
    	AmbientPlayer.play();

    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/MainMenu.fxml"));
    	AnchorPane root = (AnchorPane) loader.load();	//Load the root AnchorPane
    	       
    	scene = new Scene(root);

    	primaryStage.setScene(scene);
    	primaryStage.show();
   	
    	
  
	} catch(Exception e) {
        e.printStackTrace();
    }
}


public static void main(String[] args) {
    try {
    	conn = DriverManager.getConnection("jdbc:mysql://localhost/UserInformation?user=root&password=root&useSSL=false");
    } catch (SQLException e) {
		e.printStackTrace();
	}
    launch(args);
}

}





