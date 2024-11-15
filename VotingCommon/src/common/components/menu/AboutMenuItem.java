package common.components.menu;

import javax.swing.*;

public class AboutMenuItem extends JMenuItem {
    public AboutMenuItem() {
        super("Sobre");
        addActionListener(e -> onAboutMenuItemSelected());
    }

    public class MainMenu extends JMenuBar {
        public MainMenu() {
            JMenu helpMenu = new JMenu("Ajuda"); 
            helpMenu.add(new AboutMenuItem()); 
            this.add(helpMenu); 
        }
    }

    private void onAboutMenuItemSelected() {
        String message = "Sistema de Votação Distribuída em Java\n" +
                 "Desenvolvido por: \nAugusto Carneiro da Silva 145369\r\n" + 
                                        "Francisca Elisa Carvalho Rosa 167666\r\n" + 
                                        "Luiz Henrique Firmino de Jesus 176204\r\n" + 
                                        "Roberto Kendy Hassobe 224141\r\n" + 
                                        "Vinícius Duarte Cegalla 247095\r\n" + 
                                        "Ana Carolina Ramalho Da Silva 248317\n\n" +
                 "Curso: Projeto II de Programação Orientada a Objetos II - Unicamp\n" +
                 "\n" +
                 "Objetivo:\n" +
                 "Implementação de um sistema distribuído usando TCP/IP\n" +
                 "e Java Swing para uma votação eletrônica segura.\n" +
                 "\n" +
                 "Agradecimentos:\n" +
                 "Professor Prof. Dr. André F. de Angelis\n" +
                 "Ferramentas utilizadas: Java, Swing, TCP/IP\n" +
                 "\n" +
                 "Versão: 1.0";

        JOptionPane.showMessageDialog(null, message, "Sobre", JOptionPane.INFORMATION_MESSAGE);
    }

}
