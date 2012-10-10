package fr.nordri.game;

/**
 * Created with IntelliJ IDEA.
 * User: miragecentury
 * Date: 10/10/12
 * Time: 10:03
 * To change this template use File | Settings | File Templates.
 */
public class Score {
    private final String nom;
    private final int score;

    public Score(String nom, int score){
        this.nom = nom;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public String getNom() {
        return nom;
    }
}
