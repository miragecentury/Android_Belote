package fr.nordri.game;

/**
 * Created with IntelliJ IDEA.
 * User: miragecentury
 * Date: 10/10/12
 * Time: 08:36
 * To change this template use File | Settings | File Templates.
 */
public enum CarteValeur{
    AS,
    DEUX,
    TROIS,
    QUATTRE,
    CINQ,
    SIX,
    SEPT,
    HUIT,
    NEUF,
    DIX,
    VALET,
    DAME,
    ROI,
    JOKER;

    private static Couleur atout;

    public static void setCouleur(Couleur couleur){
        CarteValeur.atout = couleur;
    }

    public int getValeurHorsAtout(){
        return CarteValeur.getValeurHorsAtout(this);
    }
    public int getValeurAtout(){
        return CarteValeur.getValeurAtout(this);
    }
    public int getForceHorsAtout(){
        return CarteValeur.getForceHorsAtout(this);
    }
    public int getForceAtout(){
        return CarteValeur.getForceAtout(this);
    }
    public int getForce(Couleur couleur){
        if(couleur == CarteValeur.atout){
            return this.getForceAtout();
        }else{
            return this.getForceHorsAtout();
        }
    }
    public int getValeur(Couleur couleur){
        if(couleur == CarteValeur.atout){
            return this.getValeurAtout();
        }else{
            return this.getValeurHorsAtout();
        }
    }

    //Retourn la valeur en point de la carte hors atout
    public static int getValeurHorsAtout(CarteValeur v){
        switch (v){
            case AS:
                return 11;
            case DIX:
                return 10;
            case VALET:
                return 2;
            case DAME:
                return 3;
            case ROI:
                return 4;
            default:
                return 0;
        }
    }

    //Retourn la valeur en point de la carte en atout
    public static int getValeurAtout(CarteValeur v){
        switch (v){
            case AS:
                return 11;
            case DIX:
                return 10;
            case VALET:
                return 20;
            case DAME:
                return 3;
            case ROI:
                return 4;
            case NEUF:
                return 14;
            default:
                return 0;
        }
    }
    //Retourne la force de la carte hors atout
    public static int getForceHorsAtout(CarteValeur v){
        switch (v){
            case AS:
                return 14;
            case DIX:
                return 10;
            case VALET:
                return 11;
            case DAME:
                return 12;
            case ROI:
                return 13;
            case NEUF:
                return 9;
            case HUIT:
                return 8;
            case SEPT:
                return 7;
            default:
                return 0;
        }
    }
    //Retourne la force de la carte en atout
    public static int getForceAtout(CarteValeur v){
        switch (v){
            case AS:
                return 32;
            case DIX:
                return 29;
            case VALET:
                return 34;
            case DAME:
                return 30;
            case ROI:
                return 31;
            case NEUF:
                return 33;
            case HUIT:
                return 28;
            case SEPT:
                return 27;
            default:
                return 26;
        }
    }

}