package server.components.menu;

import server.components.VotingServer;

import javax.swing.*;

public class StartVotingMenuItem extends JMenuItem {
    public StartVotingMenuItem(VotingServer server) {
        super("Iniciar votação");
        addActionListener(_ -> onStartVotingMenuItemSelected(server));
    }

    private void onStartVotingMenuItemSelected(VotingServer server) {
        int confirmOption = JOptionPane.showConfirmDialog(
                this,
                "Deseja iniciar a eleição novamente?\n\nOs dados da votação anterior serão apagados",
                "Iniciar votação",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.ERROR_MESSAGE
        );

        if (confirmOption == JOptionPane.YES_OPTION) server.startVoting();
    }
}
