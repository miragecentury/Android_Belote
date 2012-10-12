package fr.nordri.game;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import fr.nordri.MyPlay;
import fr.nordri.R;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.Semaphore;

/**
 * Created with IntelliJ IDEA.
 * User: miragecentury
 * Date: 10/10/12
 * Time: 08:59
 * To change this template use File | Settings | File Templates.
 */
public class Joueur {

    /**
     * Numéro de l'équipe
     */
    protected final int equipe;

    /**
     * Ordre dans le Jeux
     */
    protected final int ordre;

    /**
     * Nom du Joueur
     */
    protected final String nom;

    /**
     * Cartes dans la main du joueur
     */
    protected ArrayList<Carte> main;

    protected MyPlay myBelote;

    protected Belote belote;

    public Boolean diag;
    public Boolean rep;
    public Couleur rep_couleur;
    /******************************************************************************************************************/

    public String getType(){
        return "Joueur";
    }

    public Joueur(int ordre, String nom,int equipe){
        this.ordre = ordre;
        this.nom = nom;
        this.equipe = equipe;
        this.main = new ArrayList<Carte>();
    }

    public Joueur(int ordre, String nom,int equipe, MyPlay myBelote){
        this.ordre = ordre;
        this.nom = nom;
        this.equipe = equipe;
        this.myBelote = myBelote;
        this.main = new ArrayList<Carte>();
    }

    public Joueur(Joueur j){
       this.ordre = j.getOrdre();
       this.nom = j.getNom();
       this.main = j.getMain();
       this.equipe = j.getEquipe();
    }

    /******************************************************************************************************************/

    public void setBelote(Belote belote){
        this.belote = belote;
    }

    public int getOrdre() {
        return ordre;
    }

    public String getNom() {
        return nom;
    }

    public ArrayList<Carte> getMain() {
        return main;
    }

    public int getEquipe(){
        return this.equipe;
    }

    /******************************************************************************************************************/

    public void addCarte(Carte c){
        this.main.add(c);
        ImageView view_c = (ImageView) this.myBelote.findViewById(this.myBelote.getResources().getIdentifier("carte"+(this.main.size()-1),"id",this.myBelote.getPackageName()));
        int id_c = this.myBelote.getResources().getIdentifier(c.getDraw(),"drawable",this.myBelote.getPackageName());
        view_c.setImageDrawable(this.myBelote.getResources().getDrawable(id_c));
    }

    public void delCarte(Carte c){
        this.delCarte(this.main.indexOf(c));
    }

    public void delCarte(int index){
        ImageView view_c = (ImageView) this.myBelote.findViewById(this.myBelote.getResources().getIdentifier("carte"+index,"id",this.myBelote.getPackageName()));
        int id_c = this.myBelote.getResources().getIdentifier("c0","drawable",this.myBelote.getPackageName());
        view_c.setImageDrawable(this.myBelote.getResources().getDrawable(id_c));
        //TODO nettoyage
        view_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pas d'Action
            }
        });
        this.main.set(index,null);
    }

    public Carte getCarte(int indice){
        return this.main.get(indice);
    }

    public int getSizeMain(){
        return this.main.size();
    }

    /******************************************************************************************************************/

    public void aToiJouer(ArrayList<Carte> Tapis){
        ImageView view_c;
        Couleur couleur;
        if(Tapis.size() == 0){
            for(int i = 0; i<8;i++){
                if(this.main.get(i) != null){
                    view_c = (ImageView) this.myBelote.findViewById(this.myBelote.getResources().getIdentifier("carte"+i,"id",this.myBelote.getPackageName()));
                    view_c.setClickable(true);
                    view_c.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Joueur.this.Choisie(view.getId());
                        }
                    });
                }
            }
        }else{
            ArrayList<Integer> id = new ArrayList<Integer>();

            couleur = Tapis.get(0).getCouleur();
            if(couleur == this.belote.getAtout()){
                // Atout Sup
                for(int i = 0; i<8;i++){
                    if(this.main.get(i) != null){
                        if(this.main.get(i).getCouleur() == this.belote.getAtout()){
                            boolean most = true;
                            for(int j=0; j<Tapis.size();j++){
                                if(Tapis.get(j).getCouleur() == this.belote.getAtout()){
                                    if(this.main.get(i).getValeur().getForceAtout() > Tapis.get(j).getValeur().getForceAtout()){
                                        id.add(i);
                                    }
                                }
                            }
                        }
                    }
                }

                if(id.size() == 0){
                    //Si Aucun Atout Sup -> Atout Inf
                    for(int i = 0; i<8;i++){
                        if(this.main.get(i) != null){
                            if(this.main.get(i).getCouleur() == this.belote.getAtout()){
                                id.add(i);
                            }
                        }
                    }
                }

            }else{
                //De même couleur
                for(int i = 0; i<8;i++){
                    if(this.main.get(i) != null){
                        if(this.main.get(i).getCouleur() == Tapis.get(0).getCouleur()){
                            id.add(i);
                        }
                    }
                }
                if(id.size() == 0){
                    //Si Aucun de la même Couleur -> Atout
                    for(int i = 0; i<8;i++){
                        if(this.main.get(i) != null){
                            if(this.main.get(i).getCouleur() == this.belote.getAtout()){
                                id.add(i);
                            }
                        }
                    }
                }
            }

            if(id.size() == 0){
                for(int i = 0; i<8;i++){
                    if(this.main.get(i) != null){
                        view_c = (ImageView) this.myBelote.findViewById(this.myBelote.getResources().getIdentifier("carte"+i,"id",this.myBelote.getPackageName()));
                        view_c.setClickable(true);
                        view_c.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Joueur.this.Choisie(view.getId());
                            }
                        });
                    }
                }
            }else{
                while(id.size() != 0){
                    view_c = (ImageView) this.myBelote.findViewById(this.myBelote.getResources().getIdentifier("carte"+id.get(0),"id",this.myBelote.getPackageName()));
                    view_c.setClickable(true);
                    view_c.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Joueur.this.Choisie(view.getId());
                        }
                    });
                    id.remove(0);
                }
            }
        }
        Toast.makeText(this.myBelote,"A Vous de Jouer !!!!!!!!",Toast.LENGTH_LONG);
    }

    public void Choisie(int id){
        //TODO: clean

        int id0 = R.id.carte0;    // ^^
        int id1 = this.myBelote.getResources().getIdentifier("carte1","id",this.myBelote.getPackageName());
        int id2 = this.myBelote.getResources().getIdentifier("carte2","id",this.myBelote.getPackageName());
        int id3 = this.myBelote.getResources().getIdentifier("carte3","id",this.myBelote.getPackageName());
        int id4 = this.myBelote.getResources().getIdentifier("carte4","id",this.myBelote.getPackageName());
        int id5 = this.myBelote.getResources().getIdentifier("carte5","id",this.myBelote.getPackageName());
        int id6 = this.myBelote.getResources().getIdentifier("carte6","id",this.myBelote.getPackageName());
        int id7 = this.myBelote.getResources().getIdentifier("carte7","id",this.myBelote.getPackageName());

        if(id == id0){
            this.Jouer(this.main.get(0));
        }
        if(id == id1){
            this.Jouer(this.main.get(1));
        }
        if(id == id2){
            this.Jouer(this.main.get(2));
        }
        if(id == id3){
            this.Jouer(this.main.get(3));
        }
        if(id == id4){
            this.Jouer(this.main.get(4));
        }
        if(id == id5){
            this.Jouer(this.main.get(5));
        }
        if(id == id6){
            this.Jouer(this.main.get(6));
        }
        if(id == id7){
            this.Jouer(this.main.get(7));
        }
    }

    public void Jouer(Carte c){
        int index = this.main.indexOf(c);
        this.main.set(index,null);
        if(this.getType() == "Joueur"){
            ImageView view_c;
            // on Clean les OnClick;
            for(int i=0; i<8; i++){
                view_c = (ImageView) this.myBelote.findViewById(this.myBelote.getResources().getIdentifier("carte"+i,"id",this.myBelote.getPackageName()));
                view_c.setClickable(false);
                view_c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }
                });
            }
            // on enlève la cartes de la main (Graphiquement)
            view_c = (ImageView) this.myBelote.findViewById(this.myBelote.getResources().getIdentifier("carte"+index,"id",this.myBelote.getPackageName()));
            int id_c = this.myBelote.getResources().getIdentifier("c0","drawable",this.myBelote.getPackageName());
            view_c.setImageDrawable(this.myBelote.getResources().getDrawable(id_c));
        }
        this.belote.aJouer(c, this);
    }

    /******************************************************************************************************************/

    protected Carte getMyBestCarteByCouleur(Couleur couleur, boolean atout){
        Carte c_max = null;
        Carte c_tmp = null;
        CarteValeur v;

        for(int i=0; i<8; i++){
            if(this.main.get(i) != null){
                c_tmp = this.main.get(i);
                if(c_tmp.getCouleur() == couleur){
                    if(c_max == null){
                        c_max = c_tmp;
                    }else{
                        if(atout == true){
                            if(c_max.getValeur().getForceAtout() < c_tmp.getValeur().getValeurAtout()){
                                c_max = c_tmp;
                            }
                        }else{
                            if(c_max.getValeur().getForceHorsAtout() < c_tmp.getValeur().getValeurHorsAtout()){
                                c_max = c_tmp;
                            }
                        }
                    }
                }
            }
        }
        return c_max;
    }

    protected Carte getMyBestCarte(){
        Carte c_max = null;
        Carte c_tmp = null;

        for(int i=0; i<8; i++){
            if(this.main.get(i) != null){
                c_tmp = this.main.get(i);
                if(c_max == null){
                    c_max = c_tmp;
                }else{
                    if(c_max.getValeur().getForceHorsAtout() < c_tmp.getValeur().getValeurHorsAtout()){
                        c_max = c_tmp;
                    }
                }
            }
        }
        return c_max;
    }

    protected Carte getMyLowerCarte(){
        Carte c_min = null;
        Carte c_tmp = null;

        for(int i=0; i<8; i++){
            if(this.main.get(i) != null){
                c_tmp = this.main.get(i);
                if(c_min == null){
                    c_min = c_tmp;
                }else{
                    if(c_min.getValeur().getForceHorsAtout() > c_tmp.getValeur().getValeurHorsAtout()){
                        c_min = c_tmp;
                    }
                }
            }
        }
        return c_min;
    }

    /******************************************************************************************************************/

}

