package fr.uge.adventure.input;

import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;

public class InputHandler {
	public boolean upPressed, downPressed, leftPressed, rightPressed, idle, 
				exitPressed, debug, inventory, keyHold, 
				upTouch, downTouch, rightTouch, leftTouch,
				chooseInventory;
	
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
			keyTouch(event.getKey());
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
		case I:
			inventory = inventory == true ? false : true;
			break;
		case SPACE:
			if (inventory)
				chooseInventory = true;
			break;
		default:
			break;
		}
	}

	public void keyReleased(KeyboardKey key) {
		switch(key) {
		case UP:
			upPressed = false;
			keyHold = false;
			break;
		case DOWN:
			downPressed = false;
			keyHold = false;
			break;
		case RIGHT:
			rightPressed = false;
			keyHold = false;
			break;
		case LEFT:
			leftPressed = false;
			keyHold = false;
			break;
		case SPACE:
			chooseInventory = false;	
			break;
		case Z:
			keyHold = false;
			break;
		default:
			break;
		}
	}
	
	public void keyTouch(KeyboardKey key) {
		switch(key) {
		case UP:
			if (keyHold == false) {
				keyHold = true;
				upTouch = true;
			}
			break;
		case DOWN:
			if (keyHold == false) {
				keyHold = true;
				downTouch = true;
			}
			break;
		case RIGHT:
			if (keyHold == false) {
				keyHold = true;
				rightTouch = true;
			}
			break;
		case LEFT:
			if (keyHold == false) {
				keyHold = true;
				leftTouch = true;
			}
			break;
		case Z:
			if (keyHold == false) {
				keyHold = true;
				debug = true;
			}
			break;
		default:
			break;
		}
	}
}
