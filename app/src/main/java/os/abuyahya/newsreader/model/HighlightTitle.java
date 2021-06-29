package os.abuyahya.newsreader.model;

import java.util.ArrayList;

public class HighlightTitle {

    private String value;
    private String matchLevel;
    private ArrayList<String> matchedWords;

    public HighlightTitle() {
    }

    public HighlightTitle(String value, String matchLevel, ArrayList<String> matchedWords) {
        this.value = value;
        this.matchLevel = matchLevel;
        this.matchedWords = matchedWords;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMatchLevel() {
        return matchLevel;
    }

    public void setMatchLevel(String matchLevel) {
        this.matchLevel = matchLevel;
    }

    public ArrayList<String> getMatchedWords() {
        return matchedWords;
    }

    public void setMatchedWords(ArrayList<String> matchedWords) {
        this.matchedWords = matchedWords;
    }
}
