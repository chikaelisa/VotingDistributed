package server.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class VotesManager {
    private final Map<String, Integer> votes = new HashMap<>();

    public Map<String, Integer> getVotes() {
        return votes;
    }

    public void addVote(String candidate) {
        votes.put(candidate, votes.getOrDefault(candidate, 0) + 1);
    }

    // TODO: Mudar para usar a classe FileUtils
    // TODO: Colocar candidatos sem votos também
    public void saveFinalResults() throws IOException {
        try (FileWriter writer = new FileWriter("resultado_votos.txt")) {
            writer.write("Resultado final dos votos:\n");
            for (Map.Entry<String, Integer> entry : votes.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue() + " votos\n");
            }
        }
    }
}
