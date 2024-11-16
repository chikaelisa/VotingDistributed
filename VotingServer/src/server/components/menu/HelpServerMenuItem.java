package server.components.menu;

import javax.swing.*;

public class HelpServerMenuItem extends JMenuItem {
    public HelpServerMenuItem() {
        super("Ajuda");
        addActionListener(e -> onHelpServerMenuItemSelected());
    }

    private void onHelpServerMenuItemSelected() {
        // TODO: Implemente o menu de ajudar para o servidor
    }
}
