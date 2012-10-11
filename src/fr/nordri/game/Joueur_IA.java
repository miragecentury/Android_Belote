package fr.nordri.game;

import android.app.Activity;

/**
 * Created with IntelliJ IDEA.
 * User: miragecentury
 * Date: 10/10/12
 * Time: 11:08
 * To change this template use File | Settings | File Templates.
 */
public class Joueur_IA extends Joueur {

    @Override
    public String getType() {
        return "Joueur_IA";
    }

    public Joueur_IA(int ordre, String nom, int equipe) {
        super(ordre, nom, equipe);
    }

    @Override
    public void addCarte(Carte c) {
        this.main.add(c);
    }

    public Joueur_IA(int ordre, String nom, int equipe, Activity myBelote) {
        super(ordre, nom, equipe);
    }

    public void QuestionPoP(){

    }

    public void QuestionCoP(){

    }
}
