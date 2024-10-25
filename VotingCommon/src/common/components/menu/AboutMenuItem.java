package common.components.menu;

import javax.swing.*;

public class AboutMenuItem extends JMenuItem {
    public AboutMenuItem() {
        super("Sobre");
        addActionListener(e -> onAboutMenuItemSelected());
    }

    private void onAboutMenuItemSelected() {
        // TODO: Implemente o menu de créditos/sobre
    }
}
