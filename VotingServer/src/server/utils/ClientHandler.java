package server.utils;

import common.utils.CPFValidator;
import server.components.VotingServer;

import java.io.EOFException;
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
                try {
                    String cpf = (String) input.readObject();
                    int candidateId = Integer.parseInt((String) input.readObject());

                    if (server.cpfManager.isCPFVoted(cpf)) {
                        output.writeObject(1); // Error: CPF already voted.
                    } else if (!CPFValidator.isValidCPF(cpf)) {
                        output.writeObject(2); // Error: Invalid CPF.
                    } else if (candidateId < 0 || candidateId >= server.electionData.getCandidates().length) {
                        output.writeObject(3); // Error: Invalid vote.
                    } else if (!server.isServerRunning()) {
                        output.writeObject(4); // Error: Election ended.
                        break;
                    } else {
                        server.submitVote(cpf, candidateId);
                        output.writeObject(0); // Success: Vote registered.
                        break;
                    }
                } catch (EOFException eof) {
                    System.out.println("Conexão encerrada pelo cliente.");
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }
        }
    }
}
