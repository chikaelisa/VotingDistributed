package common.utils;

import java.io.Serializable;

public class ElectionData implements Serializable {
    private String question;
    private String[] candidates;

    public ElectionData(String question, String[] candidates) {
        this.question = question;
        this.candidates = candidates;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getCandidates() {
        return candidates;
    }
}