package application; 
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Vector;

/**
 * Main game class that handles interactions between all other classes. Updates
 * characters on screen, controls injumpput from player, loads and removes images
 * from the Scene.
 * @author Will Long
 */
class Game {
    private static final int NUM_LEVELS = 3;
    private static final KeyCode CHEAT_KEY = KeyCode.CONTROL;

    private  Scene scene;
    public static Group sceneRoot;
    private int sceneWidth, sceneHeight;
    public static int lives; 
    public Player mPlayer;
    private Image bgImage;
    private Background background;
    public Level currentLevel;
    boolean finishedGame;
    double offset; 
    String levelstring;
	
    public static ImageView Heart1 = new ImageView(); 
    public static ImageView Heart2 = new ImageView();
    public static ImageView Heart3 = new ImageView();
	
    private boolean spacePressed;
    
    private Vector<GameCharacter> gameCharacters;
    
    private HashSet<KeyCode> pressedKeys;
    
    public static ArrayList<Projectile> projectileList; 
    /**
     * Creates a new game of specified size, and loads the starting screen. Sets
     * up game to receive player input from keyboard.
     * @param width is width of game screen.
     * @param height is height of game screen.
     */
    Game(int width, int height, String levelString) {
    	lives = 2; 
    	if(isEquipped(3)) {
    		lives = 3;
    	} else {
    		lives = 2;
    	}
    	System.out.println("Player should have " + lives + " lives");
        sceneWidth = width;
        sceneHeight = height;
        levelstring = levelString;
        sceneRoot = new Group();
        scene = new Scene(sceneRoot, sceneWidth, sceneHeight);
        projectileList = new ArrayList<Projectile>();
        bgImage = new Image(getClass().getClassLoader().getResourceAsStream("images/clouds.png"));
        
        finishedGame = false;

        spacePressed = false;
        pressedKeys = new HashSet<KeyCode>();
        scene.setOnKeyPressed(e -> pressedKeys.add(e.getCode()));
        scene.setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));
        
        gameCharacters = new Vector<GameCharacter>();
        mPlayer = new Player(0, 0, this);
    }

    Scene getScene() {
        return scene;
    }
    
    Group getSceneRoot() {
    	return sceneRoot;
    }
    
    Vector<GameCharacter> getGameCharacters() {
    	return gameCharacters;
    }

    /**
     * Main game loop. Resolves options depending on whether start screen, a
     * level, or the end game mode is currently loaded. If level is currently
     * active, it receives player input, updates all characters, and checks
     * whether the current level should be advanced.
     * @param elapsedTime is time spent updating.
     */
    public void update(double elapsedTime) {
    	try {
        if (Main.freezeTime == true) { return; } 
    	if (currentLevel == null) {
            if (!finishedGame) {
            	advanceLevels();
            }
            else {
    			 Platform.runLater(new Runnable() {
                     @Override public void run() {
     		 			Main.restartGame(); 
                     }
                 });
            }
            return;
        }
        resolveKeyPresses(elapsedTime);
        updateGameCharacters(elapsedTime);   
        scrollLevel();
        if (currentLevel != null && mPlayer.getX() + mPlayer.getFitWidth() >= currentLevel.getWidth() ) {
            advanceLevels();
        }
        removeDeadCharacters();
    	} catch (Exception ie) {
    		System.out.println(ie.getMessage());
    		System.exit(1);
    		
    	}
    }

    /**
     * Updates position of all characters in game. Updates police to shoot if
     * they are close to mPlayer.
     * @param elapsedTime is time spent updating.
     */
    void updateGameCharacters(double elapsedTime) {
    	try {
        if (currentLevel == null) {
            return;
        }
        
        for (GameCharacter gc : gameCharacters) {
        	gc.update(currentLevel, elapsedTime);
        }
    	} catch (Exception ie) {
    		System.out.println(ie.getMessage());
    		System.exit(1);
    		
    	}
        
        //mPlayer.update(currentLevel, elapsedTime);
    }
    
   
    boolean doesGCExist(GameCharacter gc) {
    	try {
    	for (GameCharacter gc1 : gameCharacters) {
        	if (gc == gc1) {
        		return true;
        	}
        }
    	} catch (Exception ie) {
    		System.out.println(ie.getMessage());
    		System.exit(1);
    		
    	}
    	return false; 
    	
    }

    /**
     * Resolves player input during start screen. Space advances to game, while
     * H triggers help message.
     */

    void resolveKeyPresses(double elapsedTime) {
    	try {
        if (pressedKeys.contains(CHEAT_KEY)) {
            resolveCheatCode();
            return;
        }

        if (pressedKeys.contains(KeyCode.H)) {
            advanceLevels();
            removeDeadCharacters();
            return;
        }
        
        
        if (pressedKeys.contains(KeyCode.ESCAPE)) {	
        		Main.freezeTime = !Main.freezeTime; 
        }
        
        if (pressedKeys.contains(KeyCode.D) && !pressedKeys.contains(KeyCode.A)) {
            mPlayer.moveRight();
        } else if (pressedKeys.contains(KeyCode.A) && !pressedKeys.contains(KeyCode.D)) {
            mPlayer.moveLeft();
        } else {
            mPlayer.stall();
        }

        if (pressedKeys.contains(KeyCode.W)) {
            mPlayer.jump(elapsedTime);
        }
        
		if (pressedKeys.contains(KeyCode.SPACE)) {
			if (!spacePressed) {
				Projectile projectile = new Projectile(mPlayer.getShooter().getX(), mPlayer.getShooter().getY() + 5, this, mPlayer);
				sceneRoot.getChildren().add(projectile);
				projectileList.add(projectile);
				
			}
			spacePressed = true;
        }
		else {
			spacePressed = false;
		}
    	}catch(Exception ie) {
    		System.out.println(ie.getMessage());
    	}
    }
    
    public static void createProj(double x, double y, Game game, GameCharacter owner) {
	
    }
    
    

    /**
     * Resolves cheat codes. Numerous cheats are available when holding control
     * key, including upgrades to mPlayer, skipping levels, clearing enemies.
     */
    void resolveCheatCode() {
        if (pressedKeys.contains(KeyCode.D)) {
            mPlayer.setX(mPlayer.getX() + 100);
            mPlayer.setY(0);
        }
        if (pressedKeys.contains(KeyCode.A)) {
            mPlayer.setX(mPlayer.getX() - 100);
            mPlayer.setY(0);
        }

        if (pressedKeys.contains(KeyCode.I)) {
            mPlayer.setInvincible();
        }
        if (pressedKeys.contains(KeyCode.F)) {
            mPlayer.superSpeed();
        }
        if (pressedKeys.contains(KeyCode.J)) {
            mPlayer.superJump();
        }
        if (pressedKeys.contains(KeyCode.C)) {
            Player newmPlayer = new Player(mPlayer.getX(), mPlayer.getY(), this);
            newmPlayer.toBack();
            sceneRoot.getChildren().remove(mPlayer);
            mPlayer = newmPlayer;
            sceneRoot.getChildren().add(mPlayer);
        }
        for (KeyCode kc : pressedKeys) {
            if (kc.isDigitKey()) {
                int level = Integer.parseInt(kc.getName());
                skipToLevel(level);
                break;
            }
        }
    }

    /**
     * Checks if any game characters have died, and removes them if they have.
     */
    void removeDeadCharacters() {
    	try {
        if (!mPlayer.isAlive()) {
            skipToLevel(0);
            return;
        }
 
        ArrayList<Projectile> deadProjectileList = new ArrayList<Projectile>();
        for (Projectile projectile : projectileList) {
        	if (!projectile.isAlive()) {
        		deadProjectileList.add(projectile);
        	}
        }
        
        ArrayList<Coin> deadCoinList = new ArrayList<Coin>();
        for (Coin coin : currentLevel.coinList) {
        	if (!coin.isAlive()) {
        		deadCoinList.add(coin);
        		coin.setVisible(false)	;
        		System.out.println("Removing dead coin");
        	}
        }
        
        ArrayList<StaticEnemy> deadStaticList = new ArrayList<StaticEnemy>();
        for (StaticEnemy se : currentLevel.staticList) {
        	if (!se.isAlive()) {
        		deadStaticList.add(se);
        		se.setVisible(false)	;
        		System.out.println("Removing dead static");
        	}
        }
        
        ArrayList<WalkingEnemy> deadWalkingList = new ArrayList<WalkingEnemy>();
        for (WalkingEnemy we : currentLevel.walkingList) {
        	if (!we.isAlive()) {
        		deadWalkingList.add(we);
        		we.setVisible(false)	;
        		System.out.println("Removing dead walking");
        	}
        }
        
        ArrayList<ShootingEnemy> deadShooterList = new ArrayList<ShootingEnemy>();
        for (ShootingEnemy se : currentLevel.shootingList) {
        	if (!se.isAlive()) {
        		deadShooterList.add(se);
        		se.setVisible(false)	;
        		System.out.println("Removing dead static");
        	}
        }
        
        ArrayList<FlyingEnemy> deadFlyingList = new ArrayList<FlyingEnemy>();
        for (FlyingEnemy se : currentLevel.flyingList) {
        	if (!se.isAlive()) {
        		deadFlyingList.add(se);
        		se.setVisible(false)	;
        		System.out.println("Removing dead flying");
        	}
        }


        currentLevel.staticList.removeAll(deadStaticList);
   		sceneRoot.getChildren().removeAll(deadStaticList);
        
        currentLevel.flyingList.removeAll(deadFlyingList);
		sceneRoot.getChildren().removeAll(deadFlyingList);
		
		projectileList.removeAll(deadProjectileList);
		sceneRoot.getChildren().removeAll(deadProjectileList);
       
		currentLevel.coinList.removeAll(deadCoinList);
		sceneRoot.getChildren().removeAll(deadCoinList);
        
        currentLevel.shootingList.removeAll(deadShooterList);
		sceneRoot.getChildren().removeAll(deadShooterList);
       
		currentLevel.walkingList.removeAll(deadWalkingList);
		sceneRoot.getChildren().removeAll(deadWalkingList);
		

        if (currentLevel == null) {
            return;
        }
    	} catch (Exception ie) {
    		System.out.println(ie.getMessage());
    	}
    }

    /**
     * Scrolls level according to mPlayer's position.
     */
    void scrollLevel() {
    	try {
        offset = (sceneWidth * 1.0 / 3) - 5;
      
    	Heart1.setX(mPlayer.getX() + 30);
		Heart1.setY(mPlayer.getY() - 50); 
		   
    	Heart2.setX(mPlayer.getX() + 80);
		Heart2.setY(mPlayer.getY() - 50); 
		   
    	Heart3.setX(mPlayer.getX() + 130);
		Heart3.setY(mPlayer.getY() - 50); 
        
        
        if (mPlayer.getX() < offset) {
            return;
        }
        sceneRoot.setLayoutX(-1 * mPlayer.getX() + offset);
        double minLayout = -1 * currentLevel.getWidth() + sceneWidth;
        if (sceneRoot.getLayoutX() < minLayout + 25) {
            sceneRoot.setLayoutX(minLayout + 25);            
        }
    	} catch (Exception ie) {
    		System.out.println(ie.getMessage());
    		System.exit(1);
    		
    	}
             
    }

    /**
     * Advances to the next level. If on start screen, goes to first main level.
     * If finished last main level, advances to end game. Otherwise, advances to
     * next main level whenever mPlayer reaches end of current level.
     */
    void advanceLevels() {

    	Level.levelStarted = false; 
        sceneRoot.getChildren().clear();
        sceneRoot.setLayoutX(0);

        int levelNum = 0;
      
        
        
        
        
        
        
        
        if (currentLevel != null) {
        	
            for (GameCharacter gc : currentLevel.staticList) {
            	gc.isAlive = false;
            }
         
            
            for (GameCharacter gc : currentLevel.coinList) {
            	gc.isAlive = false;
            }
            
            for (GameCharacter gc : currentLevel.shootingList) {
            	gc.isAlive = false;
            }
            
            for (GameCharacter gc : currentLevel.flyingList) {
            	gc.isAlive = false;
            }
            
            for (GameCharacter gc : currentLevel.walkingList) {
            	gc.isAlive = false;
            }
            
            levelNum = currentLevel.getLevelNumber();
        }
        if (levelNum == NUM_LEVELS) {
        	
            for (GameCharacter gc : currentLevel.staticList) {
            	gc.isAlive = false;
            }
         
            for (GameCharacter gc : currentLevel.flyingList) {
            	gc.isAlive = false;
            }
            
            for (GameCharacter gc : currentLevel.coinList) {
            	gc.isAlive = false;
            }
            
            for (GameCharacter gc : currentLevel.shootingList) {
            	gc.isAlive = false;
            }
            
            for (GameCharacter gc : currentLevel.walkingList) {
            	gc.isAlive = false;
            }
            currentLevel = null;
            finishedGame = true;
            return;
        }

        currentLevel = new Level(levelstring, levelNum + 1, sceneHeight);

        background = new Background(bgImage, currentLevel.getWidth(), currentLevel.getHeight());

        sceneRoot.getChildren().add(background);
        sceneRoot.getChildren().add(currentLevel);
        
        
        mPlayer.setX(Level.levelSpawnx);
        mPlayer.setY(Level.levelSpawny);
        mPlayer.toFront();
        sceneRoot.getChildren().add(mPlayer);
        
        Shooter shooter = new Shooter(45, -50, this);
        mPlayer.setShooter(shooter);
        sceneRoot.getChildren().add(shooter);
            
		Heart1 = new ImageView(); 
		Heart1.setFitWidth(40);
		Heart1.setFitHeight(40);
		Heart1.setX(mPlayer.getX() + 200);
		Heart1.setY(mPlayer.getY() + 20);
		Image image = new Image(getClass().getClassLoader().getResourceAsStream("images/Heart.png"));;
		Heart1.setImage(image);
		if (Game.lives >= 2) {
		Heart2 = new ImageView(); 
		Heart2.setFitWidth(40);
		Heart2.setFitHeight(40);
		Heart2.setX(mPlayer.getX() + 200);
		Heart2.setY(mPlayer.getY() + 20);
		Image image2 = new Image(getClass().getClassLoader().getResourceAsStream("images/Heart.png"));;
		Heart2.setImage(image2);
		}
		if (Game.lives == 3) {
		Heart3 = new ImageView(); 
		Heart3.setFitWidth(40);
		Heart3.setFitHeight(40);
		Heart3.setX(mPlayer.getX() + 200);
		Heart3.setY(mPlayer.getY() + 20);
		Image image3 = new Image(getClass().getClassLoader().getResourceAsStream("images/Heart.png"));;
		Heart3.setImage(image3);
		Main.game.getSceneRoot().getChildren().add(Heart3);
		}
		
		Main.game.getSceneRoot().getChildren().add(Heart1); 
		Main.game.getSceneRoot().getChildren().add(Heart2); 
		 
		
        
    }

    /**
     * Skips to the specified level, or to a start/end screen if a lower/higher
     * level number is provided than is possible in game.
     * @param levelNum is level to move to.
     */
    void skipToLevel(int levelNum) {
        sceneRoot.getChildren().clear();
        sceneRoot.setLayoutX(0);
        currentLevel = null;

        if (levelNum <= 0) {
            finishedGame = false;
        } else if (levelNum > NUM_LEVELS) {
            finishedGame = true;
            
        } else {

            currentLevel = new Level(levelstring, levelNum, sceneHeight);
            background = new Background(bgImage, currentLevel.getWidth(), currentLevel.getHeight());

            sceneRoot.getChildren().add(background);
            sceneRoot.getChildren().add(currentLevel);
            sceneRoot.getChildren().add(mPlayer);
            
            Shooter shooter = new Shooter(45, -50, this);
            mPlayer.setShooter(shooter);
            sceneRoot.getChildren().add(shooter);
        }
    }
    
    public boolean isEquipped(int index) {
    	if(Main.UserId==0) return false;
		try {
			Statement st = Main.conn.createStatement();
			ResultSet rs = st.executeQuery("select * from Userinformation.userinfo where userid=\"" + Main.UserId + "\";");
			String equipStatus = "";
			while(rs.next()) {
				equipStatus = rs.getString("equipstatus");
			}
			//for(int i=0; i<equipStatus.length(); i++) {
				if(equipStatus.charAt(index)=='y') {
					return true;
				} else {
					return false;
				}
			//}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}

