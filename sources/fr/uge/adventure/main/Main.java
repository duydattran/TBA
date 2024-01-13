package fr.uge.adventure.main;

import java.awt.Color;
import java.io.IOException;
import fr.umlv.zen5.Application;

public class Main {
	public static void main(String[] args) throws IOException {
		
		Application.run(Color.BLACK, context -> {
			Game game = null;
			try {
				game = new Game(context, "demo");
			} catch (IOException e) {
				e.printStackTrace();
			};
			
			// Game loop
			while (true) {
				game.update();
				
				game.render();
			}
		});
	}
}