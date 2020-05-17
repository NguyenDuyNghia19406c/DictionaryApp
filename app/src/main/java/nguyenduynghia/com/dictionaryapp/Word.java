package nguyenduynghia.com.dictionaryapp;

import java.io.Serializable;
import java.util.Comparator;

public class Word implements Serializable {
    private int id;
    private String word;
    private String mean;
    private boolean isLove;

    public Word() {
    }

    public Word(int id, String word, String mean) {
        this.id = id;
        this.word = word;
        this.mean = mean;
        this.isLove = false;
    }

    public Word(int id, String word, String mean, boolean isLove) {
        this.id = id;
        this.word = word;
        this.mean = mean;
        this.isLove = isLove;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public boolean isLove() {
        return isLove;
    }

    public void setLove(boolean love) {
        isLove = love;
    }
}
