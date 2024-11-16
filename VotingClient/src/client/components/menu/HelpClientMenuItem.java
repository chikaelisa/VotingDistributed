package client.components.menu;

import javax.swing.*;

public class HelpClientMenuItem extends JMenuItem {
    public HelpClientMenuItem() {
        super("Ajuda");
        addActionListener(e -> onHelpServerMenuItemSelected());
    }

    private void onHelpServerMenuItemSelected() {
        String message =   "Ajuda do Software\n" +
                "Este software permite ao usuário realizar uma votação distribuída.\n\n" +
                "1. Escolha sua opção de voto:\n" +
                "Selecione a alternativa de sua preferência.\n\n"+
                "2. Confirme seu voto:\n" +
                "Verifique sua escolha e confirme para registrar.\n\n"+
                "Para mais informações, entre em contato com o suporte.";

        JOptionPane.showMessageDialog(null, message, "Ajuda", JOptionPane.INFORMATION_MESSAGE);
    }
}
