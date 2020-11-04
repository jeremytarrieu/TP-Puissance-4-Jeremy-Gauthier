/*
TP2 Super Puissance 4
Classe Partie
Jeremy Tarrieu
 */
package super_puissance4_tarrieu_reille;
import java.util.Random;
import java.util.Scanner;
/**
GrilleJeu : Grille --> Grille de jeu et Jeton à créer avant Partie
 */
public class Partie {
    Joueur ListeJoueurs [] = new Joueur [2];
    Joueur joueurCourant;
    Grille nouvelleGrille = new Grille();// Creation d'une nouvelle grille
    public void attribuerCouleurAuxJoueurs(){
        //attribue des couleurs aux joueurs 
        ListeJoueurs[0].affecterCouleur("jaune");
        ListeJoueurs[1].affecterCouleur("rouge");
    }
    
    public void initialiserPartie(){
        // répartis les trous noirs et les desintegrateurs
        Random rd = new Random();
        
        nouvelleGrille.viderGrille(); // on vide la grille
        
        for(int i = 0; i<42; i++){
            if(i<21){ // ajout de 21 jetons du joueur 1
                joueurCourant = ListeJoueurs[0];//on définie le joueur courant
                String couleur = joueurCourant.couleur;//on isole la couleur du joueur...
                joueurCourant.ListeJetons[i] = new Jeton(couleur);//... pour la donner à son jeton
                joueurCourant.ajouterJeton(joueurCourant.ListeJetons[i]);//
            }else{//même démarche pour le deuxième joueur
                joueurCourant = ListeJoueurs[1];
                String couleur = joueurCourant.couleur;
                joueurCourant.ListeJetons[i-21] = new Jeton(couleur);
                joueurCourant.ajouterJeton(joueurCourant.ListeJetons[i-21]);
            }
        }
    }
    public void débuterPartie(){
        
        boolean finPartie = false; 
        Joueur joueurGagnant;
        Random r = new Random();
        Scanner sc;
        
        for(int i = 0; i <2; i++){// Saisie des noms des joueurs 
            System.out.print("Saisisez le nom du joueur "+(i+1)+": ");// saisie du nom des joueurs
            sc = new Scanner(System.in);
            ListeJoueurs[i] = new Joueur(sc.nextLine());
        }
        
        attribuerCouleurAuxJoueurs();
        initialiserPartie();
        
        while(finPartie == false){//tant que la partie n'est pas finie on continue de jouer des tours 
            for(int i = 0; i<2; i++){// joue le tour pour chaque joueur(deux actions par tour, une par joueur)
                
                joueurCourant = ListeJoueurs[i];// On définit le joueur courant pour son tour, 1 ou 2
                
                
                nouvelleGrille.afficherGrilleSurConsole();//on affiche la grille de jeu
                System.out.println("\t"+ListeJoueurs[i].nom+" entrez le n° de la colonne à jouer : ");
                
                int colonne = (sc.nextInt() - 1);// on récupère la colonne que le joueur à saisie (-1 pour la valeur dans le tableau)
                nouvelleGrille.ajouterJetonDansColonne(joueurCourant.ListeJetons[joueurCourant.nbJetonsRestants], colonne);// on ajoute le jeton dans la grille
                joueurCourant.nbJetonsRestants --;//On diminue de 1 le nombre de jetons du joueur qui vient de jouer 
                
                if(nouvelleGrille.etreGagnantePourJoueur(joueurCourant)==true){
                    //si un joueur est gagnant on affiche sa victoire et on arrête la partie 
                    finPartie = true;// arrêt de la partie à la fin du tour (while finPartie = false)
                    joueurGagnant = joueurCourant;
                    System.out.println("VICTOIRE de "+joueurCourant.nom);
                    
                }
                
                if(nouvelleGrille.etreRemplie()== true){
                    //si la grille est pleine on arrête la partie
                    finPartie = true;
                }
            }
        }
    
    }
    
}
