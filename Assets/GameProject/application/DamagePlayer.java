package application;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class DamagePlayer extends Thread {
		
	private String mDirection = "";
	private Player owner;
	private double time;
	
	DamagePlayer(String direction, Player mPlayer, double elapsedTime) {
		owner = mPlayer;
		mDirection = direction;
		time = elapsedTime; 
	}

	@Override
	    public void run() {
		 		UpdateHearts(); 
		 		for (int i =0; i < 6; i ++) {
		 			//owner.setVisible(false);
					try {
						Thread.sleep(150);
						//owner.setVisible(true);
						Thread.sleep(150);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		 		}
		 		
				System.out.println("Can be damaged again");
				owner.damaged = false;
				Thread.yield(); 

	}
	
	public void UpdateHearts() {
		if (Game.lives == 3) {
			Game.Heart1.setVisible(true);
			Game.Heart2.setVisible(true);
			Game.Heart3.setVisible(true);
		}
		else if (Game.lives == 2) {
			Game.Heart1.setVisible(true);
			Game.Heart2.setVisible(true);
			Game.Heart3.setVisible(false);
		}
		else if (Game.lives == 1) {
			Game.Heart1.setVisible(true);
			Game.Heart2.setVisible(false);
			Game.Heart3.setVisible(false);
		}
	}
	
	
	
	
	
}
