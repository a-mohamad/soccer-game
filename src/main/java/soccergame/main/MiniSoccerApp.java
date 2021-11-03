package soccergame.main;

import com.formdev.flatlaf.FlatDarkLaf;
import soccergame.controller.GameListener;
import soccergame.controller.MenubarListener;
import soccergame.view.GameMenuBar;
import soccergame.view.GamePanel;

import javax.swing.*;

/**
 * The main entry point to the SOCCERGAME application.
 */
public class MiniSoccerApp {

    public static void main(String[] args) {
        enableLookAndFeel();
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

    /**
     * Set a custom look and feel.
     */
    public static void enableLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (final Exception ex) {
            System.err.println("Failed to initialize LaF.");
            ex.printStackTrace();
        }
    }
}
