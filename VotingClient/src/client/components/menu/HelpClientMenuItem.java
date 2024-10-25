package client.components.menu;

import javax.swing.*;

public class HelpClientMenuItem extends JMenuItem {
    public HelpClientMenuItem() {
        super("Ajuda");
        addActionListener(e -> onHelpServerMenuItemSelected());
    }

    private void onHelpServerMenuItemSelected() {
        // TODO: Implementar o menu de ajuda para o cliente
    }
}
