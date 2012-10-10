package fr.nordri.game;

/**
 * Created with IntelliJ IDEA.
 * User: miragecentury
 * Date: 10/10/12
 * Time: 08:28
 *
 */
public class Carte {
    protected Couleur couleur;
    protected CarteValeur valeur;

    /******************************************************************************************************************/

    public Carte() {

    }

    public Carte(Carte carte){
        this.couleur = carte.getCouleur();
        this.valeur = carte.getValeur();
    }

    public Carte(Couleur couleur, CarteValeur valeur){
        this.couleur = couleur;
        this.valeur = valeur;
    }

    /******************************************************************************************************************/

    public Couleur getCouleur() {
        return couleur;
    }

    public Carte setCouleur(Couleur couleur) {
        this.couleur = couleur;
        return this;
    }

    public CarteValeur getValeur() {
        return valeur;
    }

    public Carte setValeur(CarteValeur valeur) {
        this.valeur = valeur;
        return this;
    }

    /******************************************************************************************************************/


}
