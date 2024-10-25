package server.utils;

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

    // TODO: Mudar para usar a classe FileUtils
    public void saveFinalResults() throws IOException {
        try (FileWriter writer = new FileWriter("cpfs_votados.txt")) {
            writer.write("Resultado final dos CPFs votados:\n");
            for (String cpf : cpfVoted.stream().toList()) {
                writer.write("CPF: " + cpf + "\n");
            }
        }
    }
}
