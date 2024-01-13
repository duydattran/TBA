package fr.uge.adventure.input;

import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;

public class InputHandler {
	public boolean upPressed, downPressed, leftPressed, rightPressed, idle, 
				exitPressed, debug;
	
	public InputHandler() {
		this.idle = true;
	}
	
	public void eventType(Event event) {
		if (event == null) {
			return;
		}
	
		Action action = event.getAction();
		switch(action) {
		case KEY_PRESSED: 
			idle = false;
			keyPressed(event.getKey());
			break;
		case KEY_RELEASED: 
			idle = true;
			keyReleased(event.getKey());
			break;
		default:
			break;
		}
	}
	
	public void keyTyped() {
		
	}

	public void keyPressed(KeyboardKey key) {
		switch(key) {
		case UP:
			upPressed = true;
			break;
		case DOWN:
			downPressed = true;
			break;
		case RIGHT:
			rightPressed = true;
			break;
		case LEFT:
			leftPressed = true;
			break;
		case Q:
			exitPressed = true;
			break;
		case Z:
			debug = debug == true ? false : true;
			break;
		default:
			break;
		}
	}

	public void keyReleased(KeyboardKey key) {
		switch(key) {
		case UP:
			upPressed = false;
			break;
		case DOWN:
			downPressed = false;
			break;
		case RIGHT:
			rightPressed = false;
			break;
		case LEFT:
			leftPressed = false;
			break;
		default:
			break;
		}
	}
}
