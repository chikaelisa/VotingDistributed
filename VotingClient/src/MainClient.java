import client.components.VotingClientFrame;

import javax.swing.*;
import java.awt.*;

public class MainClient {
    public static void main(String[] args) {
        try {
            SwingUtilities.invokeLater(VotingClientFrame::new);
        } catch (HeadlessException headlessException) {
            System.out.println("Operating System Graphic Environment (window system) not detected.");
        } catch (Exception exceptionValue) {
            System.out.println("An unexpected exception has been launched.");
            System.out.println("Message: " + exceptionValue.getMessage() + "\n\n");
        }
    }
}