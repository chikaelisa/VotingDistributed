package server.utils;

import common.utils.ElectionData;

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

    public void clearVotes() {
        votes.clear();
    }

    public void saveFinalResults(ElectionData electionData) throws IOException {
        for (String candidate : electionData.getCandidates()) {
            if (!votes.containsKey(candidate)) {
                votes.put(candidate, 0);
            }
        }

        try (FileWriter writer = new FileWriter("resultado_votos.txt")) {
            writer.write("Questão da eleição: " + electionData.getQuestion() + "\n");
            writer.write("Resultado final da votação:\n");
            for (Map.Entry<String, Integer> entry : votes.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue() + " votos\n");
            }
        }
    }
}
