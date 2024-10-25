package server.utils;

import common.utils.CPFValidator;
import server.components.VotingServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final Socket clientSocket;
    private final VotingServer server;

    public ClientHandler(Socket socket, VotingServer server) {
        this.clientSocket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());

            output.writeObject(server.electionData);
            output.flush();

            while (true) {
                String cpf = (String) input.readObject();
                int candidateId = Integer.parseInt((String) input.readObject());

                if (server.cpfManager.isCPFVoted(cpf)) {
                    output.writeObject(1); // Erro: Este CPF já votou.
                } else if (!CPFValidator.isValidCPF(cpf)) {
                    output.writeObject(2); // Erro: CPF inválido.
                } else if (candidateId < 0 || candidateId >= server.electionData.getCandidates().length) {
                    output.writeObject(3); // Erro: Voto inválido.
                } else {
                    server.submitVote(cpf, candidateId);
                    output.writeObject(0); // Voto registrado com sucesso.
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
