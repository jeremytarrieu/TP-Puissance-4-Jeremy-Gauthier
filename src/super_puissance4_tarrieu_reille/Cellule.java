/*
TP2 Super Puissance 4 
Classe Jeton
Jeremy Tarrieu

 */
package super_puissance4_tarrieu_reille;

/**
 *
 * @author tarri
 */
public class Cellule {
    Jeton jetonCourant;//Jeton occupant la cellule
    boolean trouNoir, desintegrateur;
    
    public Cellule(){
        jetonCourant = null;
        trouNoir = false;
        desintegrateur = false;
    }
    
    
   public boolean affecterJeton(Jeton unJeton){
       // affecte un jeton au jetonCourant, s'il n'y en a pas et renvoie true, sinon (s'il y a deja un jeton) ne fait rien et renvoie false 
       if(jetonCourant == null){
           jetonCourant = unJeton;
           return true;
       }else{
           return false;
       }
   }
   
   public Jeton recupererJeton(){
       // Renvoie la référence du jeton de la Cellule
       // supprime le jeton de la cellule, s'il y en avait un sinon renvoie null
       Jeton jetonRenvoi = jetonCourant;
       jetonCourant = null;
       return jetonRenvoi;
   }
   
   public boolean supprimerJeton(){
   // supprime le jeton de la cellule et renvoie vrai
   // s'il n'y a pas de jeton, renvoie false
   if(jetonCourant == null){
       return false;
   }else{
   jetonCourant = null;
   return true;
   }
   }
   
   public boolean placerTrouNoir(){
       //Ajoute un trouNoir s'il n'y en avait pas et renvoie true
       //S'il y en avait un renvoie false
       if(trouNoir == false){
           trouNoir = true;
           return true;
       }else{return false;}
   }
   
   public boolean placerDesintegrateur(){
       //Ajoute un desintegrateur s'il n'y en avait pas et renvoie true
       //S'il y en avait un renvoie false
       if(desintegrateur == false){
           desintegrateur = true;
           return true;
       }else{return false;}
   }
   
    String lireCouleurDuJeton(){
        //renvoie la couleur du jeton de la cellule 
        //renvoie vide s'il n'y en a pas 
        if(jetonCourant == null){
            return "vide";
        }
        return jetonCourant.couleur;
    }
   
   public boolean recupererDesintegrateur(){
       // recupère un desintegrateur s'il y en a un et renvoie true
       //s'il n'y en a pas renvoie false
       if(desintegrateur == true){
           desintegrateur = false;
           return true;
       }else{return false;}
   }

   public boolean activerTrouNoir(){
       //S'il y a un trou noir, l'active en le suprimmant ainsi que le jeton, et renvoie true
       //S'il n'y en a pas renvoie false
       if(trouNoir == true){
           trouNoir = false;
           jetonCourant = null;
           return true;
       }else{return false;}
   }
   
   public boolean presenceTrouNoir(){
       return trouNoir;//renvoie true s'il y a un trouNoir, false sinon
   }
   
   public boolean presenceDesintegrateur(){
       return desintegrateur;
   }
}
