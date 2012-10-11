package fr.nordri.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import fr.nordri.R;

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

    public String getDraw(){
        String str_drawable = "";

        switch (this.couleur){
            case CARREAUX:
                str_drawable+= "ca";
                break;
            case COEUR:
                str_drawable+= "co";
                break;
            case PIQUE:
                str_drawable+= "p";
                break;
            case TREFLE:
                str_drawable+= "t";
                break;
            default:
                return null;
        }
        switch (this.valeur){
            case AS:
                str_drawable+= "1";
                break;
            case DEUX:
                str_drawable+= "2";
                break;
            case TROIS:
                str_drawable+= "3";
                break;
            case QUATTRE:
                str_drawable+= "4";
                break;
            case CINQ:
                str_drawable+= "5";
                break;
            case SIX:
                str_drawable+= "6";
                break;
            case SEPT:
                str_drawable+= "7";
                break;
            case HUIT:
                str_drawable+= "8";
                break;
            case NEUF:
                str_drawable+= "9";
                break;
            case DIX:
                str_drawable+= "10";
                break;
            case VALET:
                str_drawable+= "j";
                break;
            case DAME:
                str_drawable+= "q";
                break;
            case ROI:
                str_drawable+= "k";
                break;
            case JOKER:
                return null;

            default:
                return null;

        }
        return str_drawable;
    }

    /******************************************************************************************************************/


}
