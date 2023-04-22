package com.singlelab.gpf.interactive_games.piano_tiles.model;

public class PianoGameScore {
    protected int id;
    protected int score;
    protected String name;
    protected String datetime;

    public PianoGameScore(int id, int score, String name, String datetime){
        this.id = id;
        this.score = score;
        this.name = name;
        this.datetime = datetime;
    }

    public PianoGameScore(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
