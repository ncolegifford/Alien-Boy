package application;

import java.nio.file.Paths;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.sql.Statement;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SampleController extends Thread {
	public static KeyFrame frame;
	public static Timeline animation; 
	//FXML Attributes to connect with fx:id in SceneBuilder
	
	//Login Page Attributes
	@FXML
    private Pane BackgroundPane;
	
	@FXML
	private Pane LevelPane; 
    
    @FXML
    private Pane LoginPane;
    
    @FXML
    private Pane ShopPane; 
    
    @FXML
    private Pane LeaderboardPane;
    
    @FXML
    private Pane ProfilePane; 
    
    @FXML
    private Pane MainMenuPane;
    
    @FXML
    private Text ErrorMessage; 
    
    @FXML
    private ImageView UsernameError;
    
    @FXML
    private ImageView PasswordError;
    
    @FXML
    private JFXTextField usernameField;
    
    @FXML
    private JFXPasswordField passwordField;
    
    @FXML
    private JFXTextField newUsername;
    
    @FXML
    private JFXPasswordField newPassword1;
    
    @FXML
    private JFXPasswordField newPassword2;
  
    //Registration Page Attributes
    @FXML
    private ImageView DuplicateUsernameError;
    
    @FXML
    private ImageView PasswordConfirmationError;
    
    @FXML
    private Pane RegisterPane; 
    
    @FXML
    private Text UserErrorMessage; 
    
    @FXML
    private Text PasswordErrorMessage; 
    
    private Stage stager; 
    
    //MainMenu Attributes
    
    @FXML
    private ImageView ShopError;
    
    @FXML
    private ImageView ProfileError;
    
    @FXML
    private Text ShopErrorMessage; 
    
    @FXML
    private Text ProfileErrorMessage;
    
    @FXML
    private JFXButton ToShop;
    
    @FXML
    private JFXButton ToProfile;
    
    //Profile Attributes
    
    @FXML
    private ImageView Avatar;
    
    @FXML
    private Text CurrentCoins; 
    
    @FXML
    private Text PlayerName; 
    
    @FXML
    private Text Score;
    
    @FXML
    private ImageView Skin1;
    
    @FXML
    private ImageView Skin2;
    
    @FXML
    private ImageView Skin3;
    
    @FXML
    private ImageView HealthUpgrade;
    
    @FXML
    private ImageView SpeedUpgrade;
    
    @FXML
    private ImageView DamageUpgrade;
    
    @FXML
    private ImageView Skin1Buy;
    
    @FXML
    private ImageView Skin2Buy;
    
    @FXML
    private ImageView Skin3Buy;
    
    @FXML
    private ImageView HealthUpgradeBuy;
    
    @FXML
    private ImageView SpeedUpgradeBuy;
    
    @FXML
    private ImageView DamageUpgradeBuy;
	
    
    //Shop Attributes
    
    @FXML
    private Text ShopCurrency;
    
    @FXML
    private Text ShopMessage;
    
    @FXML
    private JFXButton SkinMessage;
    
    @FXML
    private ImageView ShopSkin1;
    
    @FXML
    private ImageView ShopSkin2;
    
    @FXML
    private ImageView ShopSkin3;
    
    @FXML
    private ImageView ShopHealthUpgrade;
    
    @FXML
    private ImageView ShopSpeedUpgrade;
    
    @FXML
    private ImageView ShopDamageUpgrade;
    
    @FXML
    private ImageView ShopSkin1Buy;
    
    @FXML
    private ImageView ShopSkin2Buy;
    
    @FXML
    private ImageView ShopSkin3Buy;
    
    @FXML
    private ImageView ShopHealthUpgradeBuy;
    
    @FXML
    private ImageView ShopSpeedUpgradeBuy;
    
    @FXML
    private ImageView ShopDamageUpgradeBuy;
    
    @FXML
    private Text ErrorMessage11;
    
    //Leaderboard Attributes
    
    @FXML
    private Text Place1;
    
    @FXML
    private Text Place2;
    
    @FXML
    private Text Place3;
    
    @FXML
    private Text Place4;
    
    @FXML
    private Text Place5;
    
    @FXML
    private Text Place6;
    
    @FXML
    private Text Place7;
    
    //Levels Attributes
    @FXML
    private JFXButton Level1;
    
    @FXML
    private JFXButton Level2;
    
    @FXML
    private JFXButton Level3;
    
    @FXML
    private JFXButton Level4;
    

	public void typeClick(KeyEvent event) {
		Media media = new Media(Paths.get("bin/application/Audio/click1.wav").toUri().toString()); //replace /Movies/test.mp3 with your file
	    MediaPlayer player = new MediaPlayer(media);
	    player.play();
	}
	
	public void DisplayLoginStatus() {
		if (Main.UserId != 0) {
			System.out.println("User is logged in.");
		}
		else {
			System.out.println("User is not logged in.");
		}
	}
	
	public void Login(MouseEvent event) {
		Statement st = null;
		ResultSet rs = null;
		boolean ready = false;
		try {
			st = Main.conn.createStatement();
			
			String username = usernameField.getText();
			String password = passwordField.getText();			
			
			System.out.println("user: " + username);
			System.out.println("pass: " + password);
			
			boolean validFields = true;
			if(username.equals("")) {
				ErrorMessage("EmptyUser");
				validFields=false;
				return;
			}
			if(password.equals("")) {
				ErrorMessage("EmptyPassword");
				validFields=false;
			}
			if(!validFields) return;
			
			boolean foundUser = false;
			rs = st.executeQuery("select * from Userinformation.userinfo where username=\"" + username + "\";");
			while(rs.next()) {
				foundUser = true;
				String realPassword = rs.getString("password");
				if(password.equals(realPassword)) {
					ready = true;
					Main.UserId = rs.getInt("UserID");
					System.out.println("Logged in!");
				} else {
					ErrorMessage("Password");
				}
			}
			if(!foundUser) ErrorMessage("NoUser");
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(ready) {
			ProfileErrorMessage.setVisible(false);
			ShopErrorMessage.setVisible(false);
			MainMenuSwitch();
		}
	}
	
	public void GuestLogin(MouseEvent event) {
		ProfileErrorMessage.setVisible(true);
		ShopErrorMessage.setVisible(true);
		MainMenuSwitch();
		Main.UserId=0;
	}
	
	public void ClearMessage(MouseEvent event) {
		ShopMessage.setText(" ");
	}
	
	public void HoverItem(MouseEvent event) {	    
		String source1 = event.getSource().toString(); //yields complete string
	    String source2 = event.getPickResult().getIntersectedNode().getId(); //returns JUST the id of the object that was clicked
		
		switch (source2) {
	    case "ShopSkin1": 
	    	ShopMessage.setText("Change your character to the green alien! - 10 Coins");
	    	break;
	    case "ShopSkin2": 
	    	ShopMessage.setText("Change your character to the blue alien! - 10 Coins");
	    	break;
	    case "ShopSkin3": 
	    	ShopMessage.setText("Change your character to the yellow alien! - 10 Coins");
	    	break;
	    case "ShopHealthUpgrade": 
	    	ShopMessage.setText("Increases your health - 10 Coins");
	    	break;
	    case "ShopSpeedUpgrade": 
	    	ShopMessage.setText("Increases your movement speed - 10 Coins");
	    	break;
	    case "ShopDamageUpgrade": 
	    	ShopMessage.setText("Increases your damage - 10 Coins");
	    	break;
	    }
	}
	
	public void SwitchSkin(MouseEvent event) {	    
		String source1 = event.getSource().toString(); //yields complete string
	    String source2 = event.getPickResult().getIntersectedNode().getId(); //returns JUST the id of the object that was clicked
		
		switch (source2) {
	    case "ShopSkin1Buy": 
	    	SkinMessage.setText("Current Skin Equipped: Green - Click to Reset");
	    	equipItem(0);
	    	unequipItem(1);
	    	unequipItem(2);
	    	break;
	    case "ShopSkin2Buy": 
	     	SkinMessage.setText("Current Skin Equipped: Blue - Click to Reset");
	     	equipItem(1);
	     	unequipItem(0);
	    	unequipItem(2);
	    	break;
	    case "ShopSkin3Buy": 
	     	SkinMessage.setText("Current Skin Equipped: Yellow - Click to Reset");
	     	equipItem(2);
	     	unequipItem(0);
	    	unequipItem(1);
	    	break;
	    }
	}
	
	public void ResetSkin(MouseEvent event) {
		unequipItem(0);
		unequipItem(1);
		unequipItem(2);
		SkinMessage.setText("Current Skin Equipped: Pink - Click to Reset");
	}
		
	public void buyItemDisplay(MouseEvent event) {
		String source1 = event.getSource().toString(); //yields complete string
	    String source2 = event.getPickResult().getIntersectedNode().getId(); //returns JUST the id of the object that was clicked
		
		Media media = new Media(Paths.get("bin/application/Audio/purchase.wav").toUri().toString()); //replace /Movies/test.mp3 with your file
	    MediaPlayer player = new MediaPlayer(media);
	    player.setVolume(0.2);
	    player.play();
	    
	    ErrorMessage11.setVisible(false);
	    switch (source2) {
	    case "ShopSkin1": 
		    buyItem(0);
		    player.play();
	    	break;
	    case "ShopSkin2": 
		    buyItem(1);
		    player.play();
	    	break;
	    case "ShopSkin3": 
	    	buyItem(2);
	    	player.play();
	    	break;
	    case "ShopHealthUpgrade": 
	    	buyItem(3);
	    	equipItem(3);
	    	player.play();
	    	break;
	    case "ShopSpeedUpgrade": 
	    	buyItem(4);
	    	equipItem(4);
	    	player.play();
	    	break;
	    case "ShopDamageUpgrade": 
	    	buyItem(5);
	    	equipItem(5);
	    	player.play();
	    	break;
	    }
	
	}
	
	public void registerUser(MouseEvent event) {
		Statement st = null;
		ResultSet rs = null;
		boolean ready = false;
		try {
			st = Main.conn.createStatement();
			
			String username = newUsername.getText();
			String password1 = newPassword1.getText();
			String password2 = newPassword2.getText();
			
			System.out.println("user: " + username);
			System.out.println("pass1: " + password1);
			System.out.println("pass2: " + password2);
			
			boolean validFields = true;
			if(username.equals("")) {
				ErrorMessage("EmptyUser");
				validFields=false;
				return;
			}
			if(password1.equals("")) {
				ErrorMessage("EmptyPassword");
				validFields=false;
			}
			if(password2.equals("")) {
				ErrorMessage("EmptyPassword");
				validFields=false;
			}
			if(!validFields) return;
			
			boolean foundUser = false;
			rs = st.executeQuery("select * from Userinformation.userinfo where username=\"" + username + "\";");
			while(rs.next()) {
				foundUser = true;
			}
			if(foundUser) {
				ErrorMessage("DuplicateUsernameError");
				return;
			}
			if(!password1.equals(password2)) {
				ErrorMessage("PasswordConfirmationError");
				return;
			}
			st.execute("insert into userinfo (Username, Password) values ('" + username + "', '" + password1 + "');");
			rs = st.executeQuery("select * from Userinformation.userinfo where username=\"" + username + "\";");
			while(rs.next()) {
				Main.UserId = rs.getInt("UserID");
				ready = true;
			}
			MainMenuSwitch();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(ready) {
			MainMenuSwitch();
		}
		
	}
	
	
	public void StartGame(MouseEvent event) {

		
		String source1 = event.getSource().toString(); //yields complete string
	    String source2 = event.getPickResult().getIntersectedNode().getId(); //returns JUST the id of the object that was clicked
		String levelString = ""; 
		
	    switch (source2) {
	    case "Level1":
	    	levelString = "level1"; 
	    	break;
	    case "Level2":
	    	levelString = "level2"; 
	    	break;
	    case "Level3":
	    	levelString = "level3"; 
	    	break;
	    case "Level4":
	    	levelString = "level4"; 
	    	break;
	    }
	    
		Main.game = new Game(1024,780, levelString);
		frame = new KeyFrame(Duration.millis(Main.MILLISECOND_DELAY),
             e -> Main.game.update(Main.SECOND_DELAY));
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
		
		Main.FirePlayer.stop(); 
		ChangeMusic(); 	
		Main.getPrimaryStage().setScene(Main.game.getScene());
	}

	
	public void RegisterMenu(MouseEvent event) {
		LoginPane.setVisible(false);
		RegisterPane.setVisible(true); 
		Media media = new Media(Paths.get("bin/application/Audio/click2.wav").toUri().toString()); //replace /Movies/test.mp3 with your file
	    MediaPlayer player = new MediaPlayer(media);
	    player.play();
	    DisplayLoginStatus();
	}
	
	public void ShopMenu(MouseEvent event) {
		updateShop();
		MainMenuPane.setVisible(false);
		ShopPane.setVisible(true); 
		Media media = new Media(Paths.get("bin/application/Audio/click2.wav").toUri().toString()); //replace /Movies/test.mp3 with your file
	    MediaPlayer player = new MediaPlayer(media);
	    player.play();
	    DisplayLoginStatus(); 
	    
	    ErrorMessage11.setVisible(false);
	    int coins = 0;
	    try {
	    	Statement st = Main.conn.createStatement();
			ResultSet rs = st.executeQuery("select * from Userinformation.userinfo where userid=\"" + Main.UserId + "\";");
			while(rs.next()) {
				coins = rs.getInt("coins");
			}
	    } catch (SQLException e) {
	    	
	    }
	    ShopCurrency.setText(coins + "");
	}
	
	public void LevelMenu(MouseEvent event) {
		MainMenuPane.setVisible(false);
		LevelPane.setVisible(true); 
		Media media = new Media(Paths.get("bin/application/Audio/click2.wav").toUri().toString()); //replace /Movies/test.mp3 with your file
	    MediaPlayer player = new MediaPlayer(media);
	    player.play();
	    DisplayLoginStatus(); 
	}
	
	
	public void ProfileMenu(MouseEvent event) {
		MainMenuPane.setVisible(false);
		ProfilePane.setVisible(true); 
		Media media = new Media(Paths.get("bin/application/Audio/click2.wav").toUri().toString()); //replace /Movies/test.mp3 with your file
	    MediaPlayer player = new MediaPlayer(media);
	    player.play();
	    DisplayLoginStatus(); 
	    
	    String pName = "";
	    int coins = 0;
	    int score = 0;
	    try {
			Statement st = Main.conn.createStatement();
			ResultSet rs = st.executeQuery("select * from Userinformation.userinfo where userid=\"" + Main.UserId + "\";");
			while(rs.next()) {
				pName = rs.getString("username");
				coins = rs.getInt("coins");
				score = rs.getInt("score");
			}
	    } catch (SQLException e) {
	    	
	    }
	    
	    if(isEquipped(0)) {
	    	Image image1 = new Image(getClass().getClassLoader().getResourceAsStream("images/alienGreen_stand.png"));
	    	Avatar.setImage(image1);
		} else if (isEquipped(1)) {
			Image image1 = new Image(getClass().getClassLoader().getResourceAsStream("images/alienBlue_stand.png"));
			Avatar.setImage(image1);
		} else if (isEquipped(2)) {
			Image image1 = new Image(getClass().getClassLoader().getResourceAsStream("images/alienYellow_stand.png"));
			Avatar.setImage(image1);
		} else {
			Image image1 = new Image(getClass().getClassLoader().getResourceAsStream("images/alienPink_stand.png"));
			Avatar.setImage(image1);
		}	    
	    DamageUpgrade.setVisible(true);
	    boolean[] shop = deserializeShopStatus(Main.UserId);
	    if(shop[0]) Skin1Buy.setVisible(true);
	    else Skin1Buy.setVisible(false);
	    if(shop[1]) Skin2Buy.setVisible(true);
	    else Skin2Buy.setVisible(false);
	    if(shop[2]) Skin3Buy.setVisible(true);
	    else Skin3Buy.setVisible(false);
	    if(shop[3]) HealthUpgradeBuy.setVisible(true);
	    else HealthUpgradeBuy.setVisible(false);
	    if(shop[4]) SpeedUpgradeBuy.setVisible(true);
	    else SpeedUpgradeBuy.setVisible(false);
	    if(shop[5]) DamageUpgradeBuy.setVisible(true);
	    else DamageUpgradeBuy.setVisible(false);
	    
	    PlayerName.setText(pName);
	    CurrentCoins.setText(coins + " coins");
	    Score.setText("Score: " + score);
	}
	
	public void LeaderboardMenu(MouseEvent event) {
		MainMenuPane.setVisible(false);
		LeaderboardPane.setVisible(true); 
		Media media = new Media(Paths.get("bin/application/Audio/click2.wav").toUri().toString()); //replace /Movies/test.mp3 with your file
	    MediaPlayer player = new MediaPlayer(media);
	    player.play();
	    DisplayLoginStatus(); 
	    
	    try {
	    	String tempName = "";
	    	int score = 0;
			Statement st = Main.conn.createStatement();
			ResultSet rs = st.executeQuery("select * from Userinformation.userinfo order by score desc;");
			if(rs.next()) {
				tempName = rs.getString("username");
				score = rs.getInt("score");
				Place1.setText(tempName + " - " + score);
			}
			if(rs.next()) {
				tempName = rs.getString("username");
				score = rs.getInt("score");
				Place2.setText(tempName + " - " + score);
			}
			if(rs.next()) {
				tempName = rs.getString("username");
				score = rs.getInt("score");
				Place3.setText(tempName + " - " + score);
			}
			if(rs.next()) {
				tempName = rs.getString("username");
				score = rs.getInt("score");
				Place4.setText(tempName + " - " + score);
			}
			if(rs.next()) {
				tempName = rs.getString("username");
				score = rs.getInt("score");
				Place5.setText(tempName + " - " + score);
			}
			if(rs.next()) {
				tempName = rs.getString("username");
				score = rs.getInt("score");
				Place6.setText(tempName + " - " + score);
			}
			if(rs.next()) {
				tempName = rs.getString("username");
				score = rs.getInt("score");
//				Place7.setText(tempName + " - " + score);
			}
			
	    } catch (SQLException e) {
	    	
	    }
	}
	
	public void backMenu(MouseEvent event) {
		LoginPane.setVisible(true);
		RegisterPane.setVisible(false); 
		ClearErrors(); 
		Media media = new Media(Paths.get("bin/application/Audio/click2.wav").toUri().toString()); //replace /Movies/test.mp3 with your file
	    MediaPlayer player = new MediaPlayer(media);
	    player.play();
	}
	
	public void leaderboardBack(MouseEvent event) {
		MainMenuPane.setVisible(true);
		LeaderboardPane.setVisible(false); 
		Media media = new Media(Paths.get("bin/application/Audio/click2.wav").toUri().toString()); //replace /Movies/test.mp3 with your file
	    MediaPlayer player = new MediaPlayer(media);
	    player.play();
	}
	
	public void levelBack(MouseEvent event) {
		MainMenuPane.setVisible(true);
		LevelPane.setVisible(false); 
		ClearErrors(); 
		Media media = new Media(Paths.get("bin/application/Audio/click2.wav").toUri().toString()); //replace /Movies/test.mp3 with your file
	    MediaPlayer player = new MediaPlayer(media);
	    player.play();
	}
	
	public void profileBack(MouseEvent event) {
		ProfilePane.setVisible(false);
		MainMenuPane.setVisible(true); 
		ClearErrors(); 
		Media media = new Media(Paths.get("bin/application/Audio/click2.wav").toUri().toString()); //replace /Movies/test.mp3 with your file
	    MediaPlayer player = new MediaPlayer(media);
	    player.play();
	}
	
	public void shopBack(MouseEvent event) {
		ShopPane.setVisible(false);
		MainMenuPane.setVisible(true); 
		ClearErrors(); 
		Media media = new Media(Paths.get("bin/application/Audio/click2.wav").toUri().toString()); //replace /Movies/test.mp3 with your file
	    MediaPlayer player = new MediaPlayer(media);
	    player.play();
	}
	
	
	public void MainMenuSwitch() {
		
		if(Main.UserId == 0) {
			ToShop.setDisable(true);
			ToProfile.setDisable(true);
		} else {
			ToShop.setDisable(false);
			ToProfile.setDisable(false);
		}
		
		
		ClearErrors(); 
		Media media = new Media(Paths.get("bin/application/Audio/click2.wav").toUri().toString()); //replace /Movies/test.mp3 with your file
	    MediaPlayer player = new MediaPlayer(media);
	    player.play();
		MainMenuPane.setVisible(true);
	    RegisterPane.setVisible(false); 
		LoginPane.setVisible(false);
	}
	
	
	public void Logout(MouseEvent event) {	    
		LoginPane.setVisible(true);
		RegisterPane.setVisible(false); 
		MainMenuPane.setVisible(false);
		ClearErrors(); 
		Media media = new Media(Paths.get("bin/application/Audio/click2.wav").toUri().toString()); //replace /Movies/test.mp3 with your file
	    MediaPlayer player = new MediaPlayer(media);
	    player.play();
	    Main.UserId = 0;
	}
	

	public void ChangeMusic() {
	    Main.AmbientPlayer.stop(); 
		Main.AmbientMusic = new Media(Paths.get("bin/application/Audio/shoulder_angelv2_novocals.mp3").toUri().toString());  
	    Main.AmbientPlayer = new MediaPlayer(Main.AmbientMusic);
	    Main.AmbientPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
            	 Main.AmbientPlayer.seek(Duration.ZERO);
            }
        });
	    Main.AmbientPlayer.play();
	}
	
	public void ClearErrors() {	//Clear errors so future errors can be reevaluated
		PasswordError.setVisible(false);
		ErrorMessage.setVisible(false);
		UsernameError.setVisible(false);
		PasswordConfirmationError.setVisible(false);
		PasswordErrorMessage.setVisible(false);
		DuplicateUsernameError.setVisible(false);
		UserErrorMessage.setVisible(false);	
	}
	
	public void ErrorMessage(String type) {
		
		ClearErrors();
		
		switch (type) {
		case "Password":
			PasswordError.setVisible(true);
			ErrorMessage.setVisible(true);
			ErrorMessage.setText("Incorrect password");
			break;
			
		case "Username":
			UsernameError.setVisible(true);
			ErrorMessage.setVisible(true);
			ErrorMessage.setText("Incorrect username");
			break;		
		
		case "NoUser":
			UsernameError.setVisible(true);
			ErrorMessage.setVisible(true);
			ErrorMessage.setText("User does not exist");
			break;
			
		case "DuplicateUsernameError":
			DuplicateUsernameError.setVisible(true);
			UserErrorMessage.setVisible(true);
			UserErrorMessage.setText("User already exists");
			break;
		
		case "PasswordConfirmationError":
			PasswordConfirmationError.setVisible(true);
			PasswordErrorMessage.setVisible(true);
			PasswordErrorMessage.setText("Password does not match");
			break; 	
		
		case "EmptyUser":
			UsernameError.setVisible(true);
			ErrorMessage.setVisible(true);
			ErrorMessage.setText("Please enter a valid username");
			break;	
			
		case "EmptyPassword":
			PasswordError.setVisible(true);
			ErrorMessage.setVisible(true);
			ErrorMessage.setText("Please enter a valid password");
			break;
		}
	}
	
	//WONT WORK IF USER IS NOT LOGGED IN
	public boolean[] deserializeShopStatus(int userID) {
		boolean[] results = new boolean[6];
		try {
			Statement st = Main.conn.createStatement();
			ResultSet rs = st.executeQuery("select * from Userinformation.userinfo where userid=\"" + userID + "\";");
			String shopStatus = "";
			while(rs.next()) {
				shopStatus = rs.getString("shopstatus");
			}
			for(int i=0; i<shopStatus.length(); i++) {
				if(shopStatus.charAt(i)=='y') {
					results[i] = true;
				} else {
					results[i] = false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	//WONT WORK IF USER IS NOT LOGGED IN
	public boolean isEquipped(int index) {
		try {
			Statement st = Main.conn.createStatement();
			ResultSet rs = st.executeQuery("select * from Userinformation.userinfo where userid=\"" + Main.UserId + "\";");
			String equipStatus = "";
			while(rs.next()) {
				equipStatus = rs.getString("equipstatus");
			}
			for(int i=0; i<equipStatus.length(); i++) {
				if(equipStatus.charAt(index)=='y') {
					return true;
				} else {
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	//WONT WORK IF USER IS NOT LOGGED IN
	public void buyItem(int itemIndex) {
		
		System.out.println("buying item " + itemIndex);
		try {
			Statement st = Main.conn.createStatement();
			ResultSet rs = st.executeQuery("select * from Userinformation.userinfo where userid=\"" + Main.UserId + "\";");
			String shopStatus = "";
			int coins = 0;
			while(rs.next()) {
				shopStatus = rs.getString("shopstatus");
				coins = rs.getInt("coins");
				System.out.println("old status: " + shopStatus);
			}
			if(coins < 10) {
				//CANT AFFORD
				ErrorMessage11.setVisible(true);
				return;
			}
			coins -= 10;
			StringBuilder newShopStatus = new StringBuilder(shopStatus);
			newShopStatus.setCharAt(itemIndex, 'y');
			System.out.println("new status: " + newShopStatus);
			st.execute("update userinfo set shopstatus = '" + newShopStatus + "' where UserID = " + Main.UserId + ";");
			st.execute("update userinfo set coins = '" + coins + "' where UserID = " + Main.UserId + ";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		updateShop();
		
	}
	
	//WONT WORK IF USER IS NOT LOGGED IN
	public void equipItem(int itemIndex) {
		try {
			Statement st = Main.conn.createStatement();
			ResultSet rs = st.executeQuery("select * from Userinformation.userinfo where userid=\"" + Main.UserId + "\";");
			String equipStatus = "";
			while(rs.next()) {
				equipStatus = rs.getString("equipstatus");
			}
			StringBuilder newEquipStatus = new StringBuilder(equipStatus);
			newEquipStatus.setCharAt(itemIndex, 'y');
			st.execute("update userinfo set equipstatus = '" + newEquipStatus + "' where UserID = " + Main.UserId + ";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		updateShop();
	}
	
	//WONT WORK IF USER IS NOT LOGGED IN
	public void unequipItem(int itemIndex) {
		try {
			Statement st = Main.conn.createStatement();
			ResultSet rs = st.executeQuery("select * from Userinformation.userinfo where userid=\"" + Main.UserId + "\";");
			String equipStatus = "";
			while(rs.next()) {
				equipStatus = rs.getString("equipstatus");
			}
			StringBuilder newEquipStatus = new StringBuilder(equipStatus);
			newEquipStatus.setCharAt(itemIndex, 'n');
			st.execute("update userinfo set equipstatus = '" + newEquipStatus + "' where UserID = " + Main.UserId + ";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		updateShop();
	}
	
	public void updateShop() {
		String shopStatus = "";
		String equipStatus = "";
		int coins = 0;
		try {
			Statement st = Main.conn.createStatement();
			ResultSet rs = st.executeQuery("select * from Userinformation.userinfo where userid=\"" + Main.UserId + "\";");		
			while(rs.next()) {
				shopStatus = rs.getString("shopstatus");
				equipStatus = rs.getString("equipstatus");
				coins = rs.getInt("coins");
			}
		} catch (SQLException e) {
			
		}
		
		ShopCurrency.setText(coins + "");
		
		if(shopStatus.charAt(0)=='y') {ShopSkin1Buy.setVisible(true);}
		else { ShopSkin1Buy.setVisible(false);}
		if(shopStatus.charAt(1)=='y') {ShopSkin2Buy.setVisible(true);}
		else { ShopSkin2Buy.setVisible(false);}
		if(shopStatus.charAt(2)=='y') {ShopSkin3Buy.setVisible(true);}
		else { ShopSkin3Buy.setVisible(false);}
		if(shopStatus.charAt(3)=='y') {ShopHealthUpgradeBuy.setVisible(true);}
		else { ShopHealthUpgradeBuy.setVisible(false);}
		if(shopStatus.charAt(4)=='y') {ShopSpeedUpgradeBuy.setVisible(true);}
		else { ShopSpeedUpgradeBuy.setVisible(false);}
		if(shopStatus.charAt(5)=='y') {ShopDamageUpgradeBuy.setVisible(true);}
		else { ShopDamageUpgradeBuy.setVisible(false);}
		/*
		if(equipStatus.charAt(0)=='y') {ShopSkin1Buy.setVisible(true);}
		else { ShopSkin1Buy.setVisible(false);}
		if(equipStatus.charAt(1)=='y') {ShopSkin2Buy.setVisible(true);}
		else { ShopSkin2Buy.setVisible(false);}
		if(equipStatus.charAt(2)=='y') {ShopSkin3Buy.setVisible(true);}
		else { ShopSkin3Buy.setVisible(false);}
		if(equipStatus.charAt(3)=='y') {ShopHealthUpgradeBuy.setVisible(true);}
		else { ShopHealthUpgradeBuy.setVisible(false);}
		if(equipStatus.charAt(4)=='y') {ShopSpeedUpgradeBuy.setVisible(true);}
		else { ShopSpeedUpgradeBuy.setVisible(false);}
		if(equipStatus.charAt(5)=='y') {ShopDamageUpgradeBuy.setVisible(true);}
		else { ShopDamageUpgradeBuy.setVisible(false);}
		*/
	}
}
