/*
TP2 Super Puissance 4
Classe Joueur
Jeremy Tarrieu
 */
package super_puissance4_tarrieu_reille;

/**
 *
 * @author tarri
 */
public class Joueur {
    String nom, couleur;//nom et couleur du joueur 
    Jeton [] ListeJetons  = new Jeton [21]; //tableau de jetons, reference aux objets jetons
    int nbJetonsRestants, nbDesintegrateurs;//nombre de jetons restants et de desintegrateurs
    
    
    
    public Joueur(String unNom){// constructeur de la classe
        nom = unNom;
        nbJetonsRestants = 0;
        nbDesintegrateurs = 0;
    }
    
    public void affecterCouleur(String uneCouleur){
        // affecte la couleur passée en paramètres à couleur 
        couleur = uneCouleur;
    }
    
    public void ajouterJeton(Jeton unJeton){
        // Remplie du jeton passé en paramètre la première case null dans le tableau de références ListeJetons
        ListeJetons[nbJetonsRestants++] = unJeton;
    }
    
    public void obtenirDesintegrateur(){
        //Incrémente le nombre de desintegrateurs du joueur
        nbDesintegrateurs ++;
    }   
    
    public boolean utiliserDesintegrateur(){
        //décrémente le nombre de désintegrateurs et confirme l'utilisation de ce dernier
        //renvoie faux s'il ne restait plus de desintegrateurs 
        if(nbDesintegrateurs==0){return false;}
        else{nbDesintegrateurs--; return true;}
    }
    Jeton retirerJeton(){
        nbJetonsRestants = nbJetonsRestants-1;
        return ListeJetons[nbJetonsRestants];
    }
}
