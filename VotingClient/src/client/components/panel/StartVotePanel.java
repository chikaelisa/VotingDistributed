package client.components.panel;

import client.components.VotingClientFrame;

import javax.swing.*;

public class StartVotePanel extends JPanel {
    public final static String START_VOTE_PANEL_CONSTRAINT = "startVotePanel";

    public StartVotePanel(VotingClientFrame clientFrame) {
        super();
        JButton startButton = new JButton("Iniciar votação");
        startButton.addActionListener(e -> onStartButtonClicked(clientFrame));
        add(startButton);
    }

    private void onStartButtonClicked(VotingClientFrame clientFrame) {
        clientFrame.getVotePanel().startServerConnection(clientFrame);
        clientFrame.switchToVotePanel();
    }
}
