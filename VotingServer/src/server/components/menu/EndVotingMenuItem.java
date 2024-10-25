package server.components.menu;

import server.components.VotingServer;

import javax.swing.*;

public class EndVotingMenuItem extends JMenuItem {
    public EndVotingMenuItem(VotingServer server) {
        super("Encerrar votação");
        addActionListener(e -> onEndVotingMenuItemSelected(server));
    }

    private void onEndVotingMenuItemSelected(VotingServer server) {
        server.endVoting();
    }
}
