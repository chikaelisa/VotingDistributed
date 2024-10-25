package client.components.panel;

import client.components.VotingClientFrame;
import common.utils.ElectionData;
import common.utils.ServerSocketUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class VotePanel extends JPanel {
    public final static String VOTE_PANEL_CONSTRAINT = "startVotePanel";

    private final JLabel titleLabel;
    private final JTextField cpfField;
    private final JTextField candidateField;
    private final JButton voteButton;
    private final JPanel formPanel;

    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    // TODO: Melhorar disposição dos componentes
    public VotePanel(VotingClientFrame clientFrame) {
        super(new GridLayout(3, 1));

        titleLabel = new JLabel("Conectando ao servidor...");
        cpfField = new JTextField();
        candidateField = new JTextField();

        voteButton = new JButton("Votar");
        voteButton.addActionListener(e -> onVoteButtonClicked(clientFrame));

        formPanel = new JPanel(new GridLayout(2, 2));
        formPanel.add(new JLabel("CPF:"));
        formPanel.add(cpfField);
        formPanel.add(new JLabel("Nº candidato:"));
        formPanel.add(candidateField);

        add(titleLabel);
    }

    // TODO: Melhorar fluxo caso não consiga se conectar ao servidor
    // TODO: Verificar problema de abrir a tela de votação depois de votar, fechar o servidor e iniciar votação de novo
    public void startServerConnection(VotingClientFrame clientFrame) {
        try {
            socket = new Socket(ServerSocketUtils.SERVER_HOST, ServerSocketUtils.SERVER_PORT);
            input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());

            ElectionData electionData = (ElectionData) input.readObject();
            titleLabel.setText(electionData.getQuestion());

            this.add(formPanel);
            this.add(voteButton);

            clientFrame.getStatusPanel().setStatusLabel("Conexão estabelecida com o servidor!");
        } catch (IOException | ClassNotFoundException e) {
            clientFrame.switchToStartVotePanel();
            clientFrame.getStatusPanel().setStatusLabel("Erro ao estabelecer conexão com o servidor.");
        }
    }

    private void onVoteButtonClicked(VotingClientFrame clientFrame) {
        String cpf = cpfField.getText();
        String candidateId = candidateField.getText();

        try {
            output.writeObject(cpf);
            output.writeObject(candidateId);

            switch ((int) input.readObject()) {
                case 0:
                    output.flush();
                    output.close();
                    input.close();
                    socket.close();

                    cpfField.setText("");
                    candidateField.setText("");

                    clientFrame.getStatusPanel().setStatusLabel("Voto enviado com sucesso!");
                    clientFrame.switchToStartVotePanel();
                    break;
                case 1:
                    clientFrame.getStatusPanel().setStatusLabel("Erro: Este CPF já votou.");
                    break;
                case 2:
                    clientFrame.getStatusPanel().setStatusLabel("Erro: CPF inválido.");
                    break;
                case 3:
                    clientFrame.getStatusPanel().setStatusLabel("Erro: Candidato inexistente.");
                    break;
                default:
                    clientFrame.getStatusPanel().setStatusLabel("Erro inesperado!");
                    break;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao votar: " + e.getMessage());
            clientFrame.getStatusPanel().setStatusLabel("Erro ao enviar o voto.");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
