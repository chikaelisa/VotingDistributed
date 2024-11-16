package server.utils;

import common.utils.ElectionData;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CPFManager {
    private final Set<String> cpfVoted = new HashSet<>();

    public boolean isCPFVoted(String cpf) {
        return cpfVoted.contains(cpf);
    }

    public void addCPF(String cpf) {
        cpfVoted.add(cpf);
    }

    public void saveFinalResults(ElectionData electionData) throws IOException {
        try (FileWriter writer = new FileWriter("cpfs_votados.txt")) {
            writer.write("Questão da eleição: " + electionData.getQuestion() + "\n");
            writer.write("Resultado final dos CPFs votados:\n");
            for (String cpf : cpfVoted.stream().toList()) {
                writer.write("CPF: " + cpf + "\n");
            }
        }
    }
}
