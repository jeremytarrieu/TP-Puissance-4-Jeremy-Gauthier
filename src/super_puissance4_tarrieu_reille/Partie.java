/*
TP2 Super Puissance 4
Classe Partie
Jeremy Tarrieu
 */
package super_puissance4_tarrieu_reille;
import java.util.Random;
import java.util.Scanner;

public class Partie {
    Joueur ListeJoueurs [] = new Joueur [2];
    Joueur joueurCourant;
    Grille nouvelleGrille = new Grille();// Creation d'une nouvelle grille
    
    public void attribuerCouleurAuxJoueurs(){
        //attribue des couleurs aux joueurs 
        ListeJoueurs[0].affecterCouleur("jaune");
        ListeJoueurs[1].affecterCouleur("rouge");
    }
    
    Joueur ProchainJoueur(Joueur un_joueur) {
        if (ListeJoueurs[0] == joueurCourant) {
            return ListeJoueurs[1];
        }
        return ListeJoueurs[0];
    }
    
    public void initialiserPartie(){
        // répartis les trous noirs et les desintegrateurs
        Random rd = new Random();
        
        nouvelleGrille.viderGrille(); // on vide la grille
        
                //Création des joueurs
        Scanner sc = new Scanner(System.in);
        System.out.println("Choix du pseudo du J1 :");
        Joueur J1 = new Joueur(sc.nextLine());
        System.out.println("Choix du pseudo du J2 :");
        Joueur J2 = new Joueur(sc.nextLine());
        ListeJoueurs[0] = J1;
        ListeJoueurs[1] = J2;
        
        attribuerCouleurAuxJoueurs();
        
        System.out.println(J1.nom + " est de couleur " + J1.couleur);
        System.out.println(J2.nom + " est de couleur " + J2.couleur);
        
        // On donne des jetons aux joueurs
        for (int i = 0; i < 21; i++) {
            
            Jeton J = new Jeton(ListeJoueurs[0].couleur);
            J1.ajouterJeton(J);
            J2.ajouterJeton(new Jeton(J2.couleur));
        }
        
        // Determine qui est le premier joueur
        Random r = new Random();
        boolean le_premier = r.nextBoolean();
        if (le_premier) {
            joueurCourant = ListeJoueurs[0];
        } else {
            joueurCourant = ListeJoueurs[1];
        }
        
                // Génération des 5 trous noirs et de 2 désintégrateurs sur les trou noirs
        int compteur = 0;
        for (int i = 0; i < 5; i++) {
            int ligne_trou_noir = r.nextInt(6);
            int colonne_trou_noir = r.nextInt(7);
            if (compteur < 2) {
                if (!nouvelleGrille.placerDesintegrateur(ligne_trou_noir, colonne_trou_noir)) {
                    compteur--;
                }
                compteur = compteur + 1;
            }
            if (!nouvelleGrille.placerTrouNoir(ligne_trou_noir, colonne_trou_noir)) {
                i--;
            }
        }

        // On place les trois derniers désintégrateurs
        for (int i = 0; i < 3; i++) {
            int ligne_désin = r.nextInt(6);
            int colonne_désin = r.nextInt(7);
            if (!nouvelleGrille.placerDesintegrateur(ligne_désin, colonne_désin) || nouvelleGrille.cellules[ligne_désin][colonne_désin].presenceTrouNoir()) {
                i--;
            }
        }
        
        nouvelleGrille.afficherGrilleSurConsole();

    }
    
    int menu_joueur() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Que voulez-vous faire ?");
        System.out.println("1) Jouer un Jeton");
        System.out.println("2) Récuperer un Jeton");
        System.out.println("3) Désintégrer un Jeton");
        int choix = sc.nextInt();
        while (choix > 3 || choix < 1) {
            System.out.println("Erreur : Entrer un choix qui existe :");
            choix = sc.nextInt();
        }
        return choix;
    }
        
    void jouerJeton() {
        Scanner sc = new Scanner(System.in);
        boolean resultatAction;
        System.out.println("Veuillez saisir une colonne :");
        int colonne = sc.nextInt() - 1;
        while (colonne > 6 || colonne < 0) {
            System.out.println("Erreur : veuillez saisir une colonne :");
            colonne = sc.nextInt() - 1;
        }

        resultatAction = nouvelleGrille.ajouterJetonDansColonne(joueurCourant, colonne);
        while (!resultatAction) {
            System.out.println("La collone est pleine veuillez saisir une autre colonne :");
            colonne = sc.nextInt() - 1;
            resultatAction = nouvelleGrille.ajouterJetonDansColonne(joueurCourant, colonne);
        }
    }
    
    boolean recup_jeton() {
        int colonne;
        int ligne;
        Scanner sc = new Scanner(System.in);
        System.out.println("Veuillez saisir les coordonnées du jeton a récup :");
        System.out.println("Veuillez saisir la colonne :");
        colonne = sc.nextInt() - 1;
        while (colonne > 6 || colonne < 0) {
            System.out.println("Erreur : veuillez saisir une colonne valide :");
            colonne = sc.nextInt() - 1;
        }
        System.out.println("Veuillez saisir la ligne :");
        ligne = sc.nextInt() - 1;
        while (ligne > 5 || ligne < 0) {
            System.out.println("Erreur : veuillez saisir une ligne valide :");
            ligne = sc.nextInt() - 1;
        }
        if (nouvelleGrille.cellules[ligne][colonne].jetonCourant != null && nouvelleGrille.cellules[ligne][colonne].lireCouleurDuJeton().equals(joueurCourant.couleur)) {
            //G.recupererJeton(ligne, colonne);
            joueurCourant.ajouterJeton(nouvelleGrille.recupererJeton(ligne, colonne));
            nouvelleGrille.tasserGrille();
            //JoueurCourant.ajouterJeton(G.cellules[ligne][colonne].recupererJeton());

            return true;
        } else {
            return false;
        }
    }
    
    boolean tour_de_jeux() {
        System.out.println("C'est a " + joueurCourant.nom + " de jouer (" + joueurCourant.couleur + ")");
        System.out.println("Il vous reste " + joueurCourant.nbJetonsRestants + " jetons");
        System.out.println("Il vous reste " + joueurCourant.nbDesintegrateurs + " désintégrateurs");
        int choix = menu_joueur();
        switch (choix) {
            case 1:
                jouerJeton();
                return true;
                case 2:
                if (!recup_jeton()) {
                    System.out.println("Vous avez soit saisi un jeton qui n'est pas le vôtre ou un endroit sans jeton");
                    return false;
                }

                break;
            case 3:
                if (!désing_jeton()) {
                    System.out.println("Vous avez soit saisi un jeton qui est le vôtre ou vous n'avez pas de désintégrateur");
                    return false;
                }
                break;
        }
        return true;
    }
    
        boolean désing_jeton() {
        if (joueurCourant.nbDesintegrateurs == 0) {
            return false;
        }
        int colonne;
        int ligne;
        Scanner sc = new Scanner(System.in);
        System.out.println("Veuillez saisir les coordonnées du jeton a désintégrer :");
        System.out.println("Veuillez saisir la colonne :");
        colonne = sc.nextInt() - 1;
        while (colonne > 6 || colonne < 0) {
            System.out.println("Erreur : veuillez saisir une colonne valide :");
            colonne = sc.nextInt() - 1;
        }
        System.out.println("Veuillez saisir la ligne :");
        ligne = sc.nextInt() - 1;
        while (ligne > 5 || ligne < 0) {
            System.out.println("Erreur : veuillez saisir une ligne valide :");
            ligne = sc.nextInt() - 1;
        }

        if (nouvelleGrille.cellules[ligne][colonne].jetonCourant != null && !nouvelleGrille.cellules[ligne][colonne].lireCouleurDuJeton().equals(joueurCourant.couleur)) {
            nouvelleGrille.supprimerJeton(ligne, colonne);
            nouvelleGrille.tasserGrille();
            joueurCourant.utiliserDesintegrateur();
            return true;
        } else {
            return false;
        }
    }
    


    void debuterPartie() {
        initialiserPartie();

        do {
            while (!tour_de_jeux()) {
                System.out.println("Recommencez votre tour");
            }
            nouvelleGrille.afficherGrilleSurConsole();

            joueurCourant = ProchainJoueur(joueurCourant);

        } while (nouvelleGrille.etreGagnantePourJoueur(ListeJoueurs[0]) != true 
                && nouvelleGrille.etreGagnantePourJoueur(ListeJoueurs[1]) != true 
                && nouvelleGrille.etreRemplie() != true);

        if (nouvelleGrille.etreGagnantePourJoueur(ListeJoueurs[0]) == true 
                && nouvelleGrille.etreGagnantePourJoueur(ListeJoueurs[1]) == true) {
            System.out.println("C'est " + joueurCourant.nom + " qui a gagné !");
        } else {
            System.out.println("C'est " + ProchainJoueur(joueurCourant).nom + " qui a gagné !");
        }

    }

}