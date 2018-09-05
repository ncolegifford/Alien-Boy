package application;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class DamageEnemy extends Thread {
		
	private GameCharacter owner;
	private double time;
	
	DamageEnemy(GameCharacter mCharacter, double elapsedTime) {
		owner = mCharacter;
		time = elapsedTime; 
	}

	@Override
	    public void run() {
		try {
		if (owner.health > 0) {
		 		for (int i =0; i < 2; i ++) {
		 			//owner.setVisible(false);
					try {
						Thread.sleep(20);
						//owner.setVisible(true);
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		 		}
		 		
				System.out.println("Can be damaged again");
				owner.damaged = false;
				Thread.yield(); 
		}
		} catch (Exception ie) {
			System.out.println(ie.getMessage());
			System.exit(1);
		}

	}
}
