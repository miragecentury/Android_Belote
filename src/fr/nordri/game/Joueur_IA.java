package fr.nordri.game;

import android.app.Activity;

import java.util.ArrayList;

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
        //Rien
        //TODO: Implement
    }

    public void QuestionCoP(){
        //Rien
        //TODO: Implement
    }

    @Override
    public void aToiJouer(ArrayList<Carte> Tapis) {
        Carte c_tmp = null;
        int index = 0;
        if(Tapis.size() == 0){
            if((c_tmp = this.getMyBestCarteByCouleur(this.belote.getAtout(),true)) == null){
                c_tmp = this.getMyBestCarte();
            }
            this.Jouer(c_tmp);
        }else{
            if(Tapis.get(0).getCouleur() == this.belote.getAtout()){
                if((c_tmp = this.getMyBestCarteByCouleur(this.belote.getAtout(),true)) == null){
                    c_tmp = this.getMyBestCarte();
                }
            }else{
                if((c_tmp = this.getMyBestCarteByCouleur(this.belote.getAtout(),false)) == null){
                    c_tmp = this.getMyLowerCarte();
                }
            }
            this.Jouer(c_tmp);
        }
    }
}
