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
    private final JComboBox<String> candidateComboBox;
    private final JButton cancelButton;
    private final JButton voteButton;
    private final JPanel buttonsPanel;
    private final JPanel formPanel;

    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public VotePanel(VotingClientFrame clientFrame) {
        super(new GridLayout(3, 1));

        titleLabel = new JLabel("Conectando ao servidor...");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        cpfField = new JTextField();
        candidateComboBox = new JComboBox<>();

        cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(_ -> onCancelButtonClicked(clientFrame));

        voteButton = new JButton("Votar");
        voteButton.addActionListener(_ -> onVoteButtonClicked(clientFrame));

        buttonsPanel = new JPanel(new GridLayout(1, 2));
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(voteButton);

        formPanel = new JPanel(new GridLayout(2, 2));
        formPanel.add(new JLabel("CPF:"));
        formPanel.add(cpfField);
        formPanel.add(new JLabel("Candidato:"));
        formPanel.add(candidateComboBox);

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

            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.addElement("Selecione um candidato...");
            for (String candidate : electionData.getCandidates()) {
                model.addElement(candidate);
            }
            candidateComboBox.setModel(model);

            this.add(formPanel);
            this.add(buttonsPanel);

            clientFrame.getStatusPanel().setStatusLabel("Conexão estabelecida com o servidor!");
        } catch (IOException | ClassNotFoundException e) {
            clientFrame.switchToStartVotePanel();
            clientFrame.getStatusPanel().setStatusLabel("Erro ao estabelecer conexão com o servidor.");
        }
    }

    private void onVoteButtonClicked(VotingClientFrame clientFrame) {
        String cpf = cpfField.getText();
        String candidateId = Integer.toString(candidateComboBox.getSelectedIndex() - 1);

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
                    candidateComboBox.setSelectedIndex(0);

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

    private void onCancelButtonClicked(VotingClientFrame clientFrame) {
        try {
            if (socket != null && !socket.isClosed()) {
                output.flush();
                output.close();
                input.close();
                socket.close();
            }
        } catch (IOException e) {
            clientFrame.getStatusPanel().setStatusLabel("Erro ao encerrar conexão: " + e.getMessage());
        }

        cpfField.setText("");
        candidateComboBox.setSelectedIndex(0);
        clientFrame.switchToStartVotePanel();
    }
}
