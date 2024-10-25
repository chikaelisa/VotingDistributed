import client.components.VotingClientFrame;

import javax.swing.*;

public class MainClient {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(VotingClientFrame::new);
    }
}