package soccergame.view;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * The menu bar for the game view to help the user control
 * the game flow.
 */
public class GameMenuBar extends JMenuBar {

    /**
     * Construct a {@code GameMenuBar} with a specified menu listener to
     * track the user interaction.
     *
     * @param menubarListener the menu listener
     */
    public GameMenuBar(ActionListener menubarListener) {
        super();
        JMenu gameMenu = new JMenu("Game");
        gameMenu.add(createMenuItem("New game", "NEW", KeyEvent.VK_N, menubarListener));
        gameMenu.addSeparator();
        gameMenu.add(createMenuItem("Exit", "EXIT", KeyEvent.VK_Q, menubarListener));
        super.add(gameMenu);
        JMenu controlMenu = new JMenu("Control");
        controlMenu.add(createMenuItem("Pause", "PAUSE", KeyEvent.VK_P, menubarListener));
        controlMenu.add(createMenuItem("Resume", "RESUME", KeyEvent.VK_R, menubarListener));
        super.add(controlMenu);
    }

    /**
     * A helper method to create a menu item for our menu bar.
     *
     * @param text          the text to show
     * @param actionCommand the action command name
     * @param accelerator   the key even indicator
     * @param listener      the menu bar listener
     * @return the newly created menu item
     */
    private JMenuItem createMenuItem(String text, String actionCommand, int accelerator, ActionListener listener) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.setActionCommand(actionCommand);
        menuItem.addActionListener(listener);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(accelerator, 0));
        return menuItem;
    }

}
