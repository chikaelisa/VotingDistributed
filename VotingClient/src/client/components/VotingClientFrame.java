package client.components;

import client.components.menu.HelpClientMenuItem;
import client.components.panel.StartVotePanel;
import client.components.panel.VotePanel;
import common.components.menu.AboutMenuItem;
import common.components.panel.StatusPanel;

import javax.swing.*;
import java.awt.*;

public class VotingClientFrame extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final StatusPanel statusPanel;
    private final VotePanel votePanel;
    private final StartVotePanel startVotePanel;

    public VotingClientFrame() {
        setTitle("Client de votação");
        setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.5),
                (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.5));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        statusPanel = new StatusPanel();
        startVotePanel = new StartVotePanel(this);
        votePanel = new VotePanel(this);

        JMenu menu = new JMenu("Menu");
        menu.add(new HelpClientMenuItem());
        menu.add(new AboutMenuItem());

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);

        setJMenuBar(menuBar);

        add(mainPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);

        mainPanel.add(startVotePanel, StartVotePanel.START_VOTE_PANEL_CONSTRAINT);
        cardLayout.show(mainPanel, StartVotePanel.START_VOTE_PANEL_CONSTRAINT);
        setVisible(true);
    }

    public void switchToVotePanel() {
        statusPanel.setStatusLabel("Iniciando votação!");
        mainPanel.remove(startVotePanel);
        mainPanel.add(votePanel, VotePanel.VOTE_PANEL_CONSTRAINT);
        cardLayout.show(mainPanel, VotePanel.VOTE_PANEL_CONSTRAINT);
        revalidate();
        repaint();
    }

    public void switchToStartVotePanel() {
        mainPanel.remove(votePanel);
        mainPanel.add(startVotePanel, StartVotePanel.START_VOTE_PANEL_CONSTRAINT);
        cardLayout.show(mainPanel, StartVotePanel.START_VOTE_PANEL_CONSTRAINT);
        revalidate();
        repaint();
    }

    public CardLayout getCardLayout() {
        return this.cardLayout;
    }

    public JPanel getMainPanel() {
        return this.mainPanel;
    }

    public StatusPanel getStatusPanel() {
        return this.statusPanel;
    }

    public VotePanel getVotePanel() {
        return this.votePanel;
    }
}
