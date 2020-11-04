/*
TP2 SUper Puissance 4 
Classe Jeton
Jeremy Tarrieu
 */
package super_puissance4_tarrieu_reille;

/**
 *
 * @author tarri
 */
public class Jeton {
    String couleur;//couleur du jeton
    
    public Jeton(String jauneOuRouge){//constructeur de la classe Jeton
        couleur = jauneOuRouge;
    }
    public String lireCouleur(){
        return couleur;
    }
}
