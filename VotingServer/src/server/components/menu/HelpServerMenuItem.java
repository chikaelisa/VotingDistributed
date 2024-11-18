package server.components.menu;

import javax.swing.*;

public class HelpServerMenuItem extends JMenuItem {
    public HelpServerMenuItem() {
        super("Ajuda");
        addActionListener(e -> onHelpServerMenuItemSelected());
    }

    private void onHelpServerMenuItemSelected() {
        JOptionPane.showMessageDialog(null, 
            "Bem-vindo ao sistema de votação eletrônica!\n\n" +
            "Como usar o servidor:\n" +
            "1. Inicie o servidor através do menu principal.\n" +
            "2. Monitore os clientes conectados no painel de administração.\n" +
            "3. Verifique os logs para acompanhar a atividade do sistema.\n" +
            "4. Garanta que a configuração do firewall permita conexões seguras.\n" +
            "Obs: Sempre faça backup regular dos dados e mantenha o servidor atualizado.\n\n" +
            "Para mais informações, entre em contato com o suporte.",
            "Ajuda", JOptionPane.INFORMATION_MESSAGE);
    }
}