package fr.nordri.game;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: miragecentury
 * Date: 10/10/12
 * Time: 08:59
 * To change this template use File | Settings | File Templates.
 */
public class Joueur {

    /**
     * Ordre dans le Jeux
     */
    protected int ordre;

    /**
     * Nom du Joueur
     */
    protected String nom;

    /**
     * Cartes dans la main du joueur
     */
    protected ArrayList<Carte> main;

    /******************************************************************************************************************/

    public Joueur() {

    }

    public Joueur(int ordre, String nom){
        this.ordre = ordre;
        this.nom = nom;
    }

    public Joueur(Joueur j){
       this.ordre = j.getOrdre();
       this.nom = j.getNom();
       this.main = j.getMain();
    }

    /******************************************************************************************************************/

    public int getOrdre() {
        return ordre;
    }

    public Joueur setOrdre(int ordre) {
        this.ordre = ordre;
        return this;
    }

    public String getNom() {
        return nom;
    }

    public Joueur setNom(String nom) {
        this.nom = nom;
        return this;
    }

    public ArrayList<Carte> getMain() {
        return main;
    }

    public Joueur setMain(Set<Carte> main) {

        return this;
    }

    /******************************************************************************************************************/

    public void addCarte(Carte c){
        this.main.add(c);
    }
}
