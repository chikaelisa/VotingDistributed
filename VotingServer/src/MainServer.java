import server.components.VotingServer;

import javax.swing.*;

public class MainServer {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(VotingServer::new);
    }
}
