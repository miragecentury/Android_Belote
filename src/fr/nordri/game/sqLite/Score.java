package fr.nordri.game.sqLite;

/**
 * Création d'un livre tout simple pour un exemple d'utilisation de SQLite sous Android
 * @author Axon
 * http://www.tutomobile.fr
 */
public class Score {

    private int id;
    private String joueur;
    private String score;

    public Score(){}

    public Score(String joueur, String score){
        this.joueur = joueur;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJoueur() {
        return joueur;
    }

    public void setJoueur(String joueur) {
        this.joueur = joueur;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String toString(){
        return "ID : "+id+"\nJoueur : "+ joueur +"\nScore : "+ score;
    }
}