package application; 
import javafx.scene.Group;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;


public class Level extends Group {
    private static final String LEVEL_FILE_PREFIX = "levels/";
    private static final String LEVEL_FILE_POSTFIX = ".txt";
    private static final double PLATFORM_WIDTH_RATIO = .99;
    public static double levelSpawnx;
    public static double levelSpawny;
    private static final int FREE_ID = 0;
    int id;
    private int levelNumber;
    String levelstring; 
    private double levelWidth, levelHeight;
    private List<Platformer> platformList = new ArrayList<Platformer>();
    public List<Coin> coinList = new ArrayList<Coin>(); ; 
    public List<StaticEnemy> staticList = new ArrayList<StaticEnemy>();
    public List<ShootingEnemy> shootingList = new ArrayList<ShootingEnemy>();
    public List<WalkingEnemy> walkingList = new ArrayList<WalkingEnemy>();
    public List<FlyingEnemy> flyingList = new ArrayList<FlyingEnemy>();
    
  
    
    public static Boolean levelStarted = false; 
    
/**
* Load the specified level from file. Initialize lists of characters to
* keep track of. If levelNum is 1, then level1.txt holds level info.
* @param levelNum is level to load.
* @param height is height of the level.
*/
Level(String levelString, int levelNum, double height) {
	ArrayList<TileInfo> level1Key = new ArrayList<TileInfo>(); 
	level1Key.add(new TileInfo("Empty", "/images/coinGold.png", true));
	level1Key.add(new TileInfo("Coin", "/images/coinGold.png", true));
	level1Key.add(new TileInfo("WalkingEnemy", "images/slimeBlock", true));
	level1Key.add(new TileInfo("StaticEnemy", "images/saw", true));
	level1Key.add(new TileInfo("Platform", "/images/grassMid.png", false));
	level1Key.add(new TileInfo("Platform", "/images/grassCenter.png", false));
	level1Key.add(new TileInfo("Platform", "/images/signRight.png", true));
	level1Key.add(new TileInfo("FlyingEnemy", "images/bee", true));
	level1Key.add(new TileInfo("Platform", "/images/signExit.png", true));
	level1Key.add(new TileInfo("ShootingEnemy", "images/barnacle", true));
	
	ArrayList<TileInfo> level2Key = new ArrayList<TileInfo>(); 
	level2Key.add(new TileInfo("Empty", "/images/coinGold.png", true));
	level2Key.add(new TileInfo("Coin", "/images/coinGold.png", true));
	level2Key.add(new TileInfo("WalkingEnemy", "images/slimeBlock", true));
	level2Key.add(new TileInfo("StaticEnemy", "images/cactus", true));
	level2Key.add(new TileInfo("FlyingEnemy", "images/bee", true));
	level2Key.add(new TileInfo("Platform", "/images/sandCenter.png", false));
	level2Key.add(new TileInfo("Platform", "/images/sandMid.png", false));
	level2Key.add(new TileInfo("Platform", "/images/signRight.png", true));
	level2Key.add(new TileInfo("Platform", "/images/signExit.png", true));
	level2Key.add(new TileInfo("ShootingEnemy", "images/barnacle", true));
 

	ArrayList<TileInfo> level3Key = new ArrayList<TileInfo>(); 
	level3Key.add(new TileInfo("Empty", "/images/coinGold.png", true));
	level3Key.add(new TileInfo("Coin", "/images/coinGold.png", true));
	level3Key.add(new TileInfo("WalkingEnemy", "images/slimeBlock", true));
	level3Key.add(new TileInfo("StaticEnemy", "images/saw", true));
	level3Key.add(new TileInfo("FlyingEnemy", "images/bee", true));
	level3Key.add(new TileInfo("ShootingEnemy", "images/barnacle", true));
	level3Key.add(new TileInfo("Platform", "/images/stoneCenter.png", false));
	level3Key.add(new TileInfo("Platform", "/images/stoneMid.png", false));
	level3Key.add(new TileInfo("Platform", "/images/signRight.png", true));
	level3Key.add(new TileInfo("Platform", "/images/signExit.png", true));
	
	ArrayList<TileInfo> level4Key = new ArrayList<TileInfo>(); 
	level4Key.add(new TileInfo("Empty", "/images/coinGold.png", true));
	level4Key.add(new TileInfo("Coin", "/images/coinGold.png", true));
	level4Key.add(new TileInfo("WalkingEnemy", "images/slimeBlock", true));
	level4Key.add(new TileInfo("StaticEnemy", "images/saw", true));
	level4Key.add(new TileInfo("FlyingEnemy", "images/bee", true));
	level4Key.add(new TileInfo("ShootingEnemy", "images/barnacle", true));
	level4Key.add(new TileInfo("Platform", "/images/snowCenter.png", false));
	level4Key.add(new TileInfo("Platform", "/images/snowMid.png", false));
	level4Key.add(new TileInfo("Platform", "/images/signRight.png", true));
	level4Key.add(new TileInfo("Platform", "/images/signExit.png", true));
 
	ArrayList<TileInfo> holder = new ArrayList<TileInfo>();
	
	levelNumber = levelNum;
    levelstring = levelString;
    String filename = LEVEL_FILE_PREFIX + levelString + levelNum + LEVEL_FILE_POSTFIX;
    
    if (levelString == "level1") {
    	holder = level1Key;
    }
    else if (levelString == "level2") {
    	holder = level2Key;
    }
    else if (levelString == "level3") {
    	holder = level3Key;
    }
    else if (levelString == "level4") {
    	holder =level4Key;
    }
    
    System.out.println("Size of staticList: " + staticList.size()); 
    System.out.println("Size of shooting: " + shootingList.size()); 
    System.out.println("Size of walking: " + walkingList.size()); 
    System.out.println("Size of flying: " + flyingList.size()); 

    System.out.println(filename); 

    InputStream stream = getClass().getClassLoader().getResourceAsStream(filename);
    Scanner input = new Scanner(stream);
    
    int levelBlockHeight = input.nextInt(); 
    input.nextLine(); 
   
    for (int j = 0; j < levelBlockHeight; j ++) {
        String line = input.nextLine(); 
        System.out.println(line); 
        for (int i = 0; i < line.length(); i++) {
            levelHeight = height;
            levelWidth = (double) (line.length()) / levelBlockHeight *
            levelHeight * PLATFORM_WIDTH_RATIO;
            double blockHeight = levelHeight / levelBlockHeight;
            double blockWidth = blockHeight * PLATFORM_WIDTH_RATIO;            
            if (line.charAt(i) != 'p') {
            	id = Character.getNumericValue(line.charAt(i)); 
            }
            else {
            	levelSpawnx = i * PLATFORM_WIDTH_RATIO * blockWidth;
            	levelSpawny = j * PLATFORM_WIDTH_RATIO * blockHeight; 
            	continue; 
            }
            //PLATFORM_WIDTH_RATIO to fix spacing issues
            //Use a map data structure here???
            if (id == FREE_ID) {
                continue;
            }
            else {
            	if (holder.get(id).mType == "Platform") {
            		Platformer platform = new Platformer(i * PLATFORM_WIDTH_RATIO * blockWidth, j * PLATFORM_WIDTH_RATIO * blockHeight,
            				blockWidth, blockHeight, holder.get(id).mNoCollide, holder.get(id).mImageURL);
            		getChildren().add(platform);
            		platformList.add(platform);  
            	}
            	else if (holder.get(id).mType == "Coin") {
            		Coin coin = new Coin(i * PLATFORM_WIDTH_RATIO * blockWidth, j * PLATFORM_WIDTH_RATIO * blockHeight,
            				Main.game);
            		getChildren().add(coin);
            		coinList.add(coin);           		
            	}
            	else if(holder.get(id).mType == "StaticEnemy") {
            		StaticEnemy se = new StaticEnemy(i * PLATFORM_WIDTH_RATIO * blockWidth, j * PLATFORM_WIDTH_RATIO * blockHeight,
            										Main.game, holder.get(id).mImageURL + ".png",holder.get(id).mImageURL + "move.png");
            		getChildren().add(se);
            		staticList.add(se);
            	}
            	else if(holder.get(id).mType == "ShootingEnemy") {
            		ShootingEnemy se = new ShootingEnemy(i * PLATFORM_WIDTH_RATIO * blockWidth, j * PLATFORM_WIDTH_RATIO * blockHeight,
            										Main.game, holder.get(id).mImageURL + ".png",holder.get(id).mImageURL + "move.png");
            		getChildren().add(se);
            		shootingList.add(se);
            	}
            	else if(holder.get(id).mType == "WalkingEnemy") {
            		WalkingEnemy we = new WalkingEnemy(i * PLATFORM_WIDTH_RATIO * blockWidth, j * PLATFORM_WIDTH_RATIO * blockHeight,
            										Main.game, holder.get(id).mImageURL + ".png",holder.get(id).mImageURL + "move.png");
            		getChildren().add(we);
            		walkingList.add(we);
            	}
             	else if(holder.get(id).mType == "FlyingEnemy") {
            		FlyingEnemy fe = new FlyingEnemy(i * PLATFORM_WIDTH_RATIO * blockWidth, j * PLATFORM_WIDTH_RATIO * blockHeight,
            										Main.game, holder.get(id).mImageURL + ".png",holder.get(id).mImageURL + "move.png");
            		getChildren().add(fe);
            		flyingList.add(fe);
            	}
            	else {
            		Platformer platform = new Platformer(i * PLATFORM_WIDTH_RATIO * blockWidth, j * PLATFORM_WIDTH_RATIO * blockHeight,
            				blockWidth, blockHeight, holder.get(id).mNoCollide, holder.get(id).mImageURL);
            		getChildren().add(platform);
            		platformList.add(platform);
            	}
            }
        }
    }
}

double getHeight() {
    return levelHeight;
}

double getWidth() {
    return levelWidth;
}

int getLevelNumber() {
    return levelNumber;
}

List<Platformer> getPlatformList() {
    return platformList;
}

}