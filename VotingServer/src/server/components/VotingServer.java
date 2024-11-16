package server.components;

import common.components.menu.AboutMenuItem;
import common.components.panel.StatusPanel;
import common.utils.ElectionData;
import common.utils.ServerSocketUtils;
import server.components.menu.EndVotingMenuItem;
import server.components.menu.HelpServerMenuItem;
import server.components.menu.StartVotingMenuItem;
import server.components.panel.ResultPanel;
import server.utils.CPFManager;
import server.utils.ClientHandler;
import server.utils.VotesManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class VotingServer extends JFrame {
    private boolean running = false;
    private ServerSocket serverSocket;
    private Thread connectionThread;

    public final ElectionData electionData = new ElectionData(
            "Quem deve ser o próximo presidente?",
            new String[]{"Candidato A", "Candidato B", "Candidato C", "Candidato D", "Candidato E"}
    );

    public final CPFManager cpfManager = new CPFManager();
    public final VotesManager votesManager = new VotesManager();

    private final ResultPanel resultPanel;
    private final StatusPanel statusPanel;
    private final JMenu serverMenu;
    private final JMenuItem startServerMenuItem = new StartVotingMenuItem(this);
    private final JMenuItem endServerMenuItem = new EndVotingMenuItem(this);

    public VotingServer() {
        setTitle("Servidor de votação");
        setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.5),
                (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.5));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        resultPanel = new ResultPanel();
        statusPanel = new StatusPanel();

        serverMenu = new JMenu("Menu");
        serverMenu.add(new HelpServerMenuItem());
        serverMenu.add(new AboutMenuItem());
        serverMenu.addSeparator();

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(serverMenu);

        setJMenuBar(menuBar);
        add(resultPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);

        setVisible(true);
        setupWindowsListener();
    }

    public boolean isServerRunning() {
        return running;
    }

    public synchronized void submitVote(String cpf, int voteIndex) {
        cpfManager.addCPF(cpf);

        String candidate = electionData.getCandidates()[voteIndex];
        votesManager.addVote(candidate);
        resultPanel.updateResults(votesManager.getVotes());
    }

    private void startServer() {
        try {
            serverSocket = new ServerSocket(ServerSocketUtils.SERVER_PORT);

            running = true;
            statusPanel.setStatusLabel("Servidor em execução!");

            connectionThread = new Thread(() -> {
                while (running) {
                    try {
                        Socket clientSocket = serverSocket.accept();
                        new Thread(new ClientHandler(clientSocket, this)).start();
                    } catch (SocketException e) {
                        if (!running) {
                            statusPanel.setStatusLabel("Servidor parou!");
                        } else {
                            statusPanel.setStatusLabel("Erro: " + e.getMessage() + "!");
                        }
                    } catch (IOException e) {
                        statusPanel.setStatusLabel("Erro: " + e.getMessage() + "!");
                    }
                }
            });

            connectionThread.start();
        } catch (IOException e) {
            statusPanel.setStatusLabel("Erro ao iniciar servidor: " + e.getMessage() + "!");
        }
    }

    private void stopServer() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) serverSocket.close();
            if (connectionThread != null && connectionThread.isAlive()) connectionThread.join();
        } catch (IOException | InterruptedException e) {
            statusPanel.setStatusLabel("Erro ao parar servidor: " + e.getMessage() + "!");
        }
    }

    public void startVoting() {
        cpfManager.clearCPFs();
        votesManager.clearVotes();
        resultPanel.startElection();

        serverMenu.remove(startServerMenuItem);
        serverMenu.add(endServerMenuItem);
        serverMenu.revalidate();
        serverMenu.repaint();

        startServer();
    }

    public void endVoting() {
        try {
            stopServer();
            votesManager.saveFinalResults(electionData);
            cpfManager.saveFinalResults(electionData);
            resultPanel.endElection();

            serverMenu.remove(endServerMenuItem);
            serverMenu.add(startServerMenuItem);
            serverMenu.revalidate();
            serverMenu.repaint();
        } catch (IOException e) {
            statusPanel.setStatusLabel("Erro ao encerrar votação: " + e.getMessage() + "!");
        }
    }

    private void setupWindowsListener() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                startVoting();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                endVoting();
            }
        });
    }
}
