package client.components.panel;

import client.components.VotingClientFrame;

import javax.swing.*;
import java.awt.*;

public class StartVotePanel extends JPanel {
    public final static String START_VOTE_PANEL_CONSTRAINT = "startVotePanel";

    public StartVotePanel(VotingClientFrame clientFrame) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Bem-vindo ao sistema de votação!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descriptionLabel = new JLabel("Pressione o botão abaixo para iniciar o processo de votação.");
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startButton = new JButton("Iniciar voto individual");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(_ -> onStartButtonClicked(clientFrame));

        add(Box.createVerticalGlue());
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(descriptionLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(startButton);
        add(Box.createVerticalGlue());
    }

    private void onStartButtonClicked(VotingClientFrame clientFrame) {
        clientFrame.getVotePanel().startServerConnection(clientFrame);
        clientFrame.switchToVotePanel();
    }
}
