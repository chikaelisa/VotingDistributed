import server.components.VotingServer;

import javax.swing.*;
import java.awt.*;

public class MainServer {
    public static void main(String[] args) {
        try {
            SwingUtilities.invokeLater(VotingServer::new);
        } catch (HeadlessException headlessException) {
            System.out.println("Operating System Graphic Environment (window system) not detected.");
        } catch (Exception exceptionValue) {
            System.out.println("An unexpected exception has been launched.");
            System.out.println("Message: " + exceptionValue.getMessage() + "\n\n");
        }
    }
}
