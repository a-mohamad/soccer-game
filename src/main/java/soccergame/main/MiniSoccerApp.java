package soccergame.main;
import soccergame.controller.GameListener;
import soccergame.controller.MenubarListener;
import soccergame.view.GameMenuBar;
import soccergame.view.GamePanel;

import javax.swing.*;

public class MiniSoccerApp {

	public static void main(String[] args) {
		JFrame gameFrame = new JFrame("Mini Soccer");
		GamePanel gamePanel = new GamePanel();
		GameListener gameListener = new GameListener(gamePanel);
		MenubarListener menubarListener = new MenubarListener(gamePanel);
		GameMenuBar gameMenuBar = new GameMenuBar(menubarListener);
		gameFrame.add(gamePanel);
		gameFrame.addKeyListener(gameListener);
		gameFrame.setJMenuBar(gameMenuBar);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setSize(600, 600);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setResizable(false);
		gameFrame.setVisible(true);
	}
}
