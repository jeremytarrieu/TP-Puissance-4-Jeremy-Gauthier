/*
TP2 Super Puissance 4
Classe Grille
Jeremy Tarrieu
 */
package super_puissance4_tarrieu_reille;

/**
 *
 * @author tarri
 */
public class Grille {
    Cellule [][] cellules = new Cellule [6][7];//tableau au format [nbcolonne][nblignes] ... (7 colonnes / 6 lignes)
    //variables invariantes, 
    
    
    public static final String ANSI_BLACK = "\u001B[30m";//coresspondent à la couleur du texte affiché dans afficherGrilleSurConsole()
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public Grille(){
        for(int i=0; i<6; i++){
            for(int j=0;j<7;j++){
                cellules[i][j] = new Cellule();
            }
        }
    }
    
    boolean ajouterJetonDansColonne(Joueur joueurCourant, int ind_colonne) {
        // si la colonne est remplie, on s'arrete et on retourne false
        if (colonneRemplie(ind_colonne)) return false;
        
        // on recherche l'indice de la ligne où ajouter le jeton
        // forcement cet inddice existe 
        int i = 0;
        while (cellules[i][ind_colonne].jetonCourant != null) {
            i++;
        }
                // on récupére un jeton dans la liste des jetons du joueur 
        Jeton un_jeton = joueurCourant.retirerJeton();
        // on ajoute le jeton dans la case en question
        cellules[i][ind_colonne].jetonCourant = un_jeton;
                // on récupère un potentiel désintegrateur
        if (cellules[i][ind_colonne].presenceDesintegrateur()) {
            cellules[i][ind_colonne].recupererDesintegrateur();
            joueurCourant.nbDesintegrateurs++;
        }
        // on active le potentiel trou noir
        if (cellules[i][ind_colonne].presenceTrouNoir()) {
           cellules[i][ind_colonne].activerTrouNoir();
        }
        return true;
    }
        void activer_trounoir(int column) { // ressort vrai si il y a un desing
        int i = 5;
        while (cellules[i][column].jetonCourant == null) {
            i--;
            if (i == 0) {
                break;
            }
        }
        if (i >= 0 && i < 6) {
            cellules[i][column].activerTrouNoir();
        }
        

    }
    
    public boolean etreRemplie(){
        //renvoie true si la grille est pleine, sinon renvoie false
        // renvoie vrai si la grille est remplie.
        // il nous suffit de tester si chaque colonne est remplie
        int compteur = 0;
        int i=0;
        // tant que la colonne i est remplie, on incrémente i 
        // et on passe a la colonne suivante 
        while (i!=6 && colonneRemplie(i)) { i++;}
        return (i==6); // si i=6 on a toutes les colonens de remplies 
    }
    
    public void viderGrille(){
        // passe chaque valeur du tableau à null
        for(int i = 0; i<6; i++){
            for(int j = 0; i<5; i++){
                cellules[i][j].jetonCourant = null;
                cellules[i][j].trouNoir = false;
                cellules[i][j].desintegrateur = false;
            }
        }
    }
    // ok 
    // órdre 
    public void afficherGrilleSurConsole(){
        
        //pour chaque case de cellule on affiche un O rouge ou jaune s'il y a un jeton rouge ou jaune...
        //... un O noir pour les trous noirs et un O bleu pour une case vide
        for(int i =5; i>-1; i--){//affichage en commençant par le haut du tableau
            for(int j =0; j<7; j++){
                if("jaune".equals(cellules[i][j].lireCouleurDuJeton())){
                    System.out.print(ANSI_YELLOW + "O " + ANSI_BLACK); 
                }else if(cellules[i][j].presenceTrouNoir()){
                    System.out.print(ANSI_BLACK + "O " + ANSI_BLACK);
                }else if("rouge".equals(cellules[i][j].lireCouleurDuJeton())){
                    System.out.print(ANSI_RED + "O " + ANSI_BLACK);
                }else if(cellules[i][j] == null || cellules[i][j].jetonCourant == null ){
                    System.out.print(ANSI_BLUE + "O " + ANSI_BLACK);
                }
            }
            System.out.println("" +(i+1));
        }
        for(int i = 0; i<7; i++){
            System.out.print(""+ (i+1)+" ");
        }
        System.out.println();
    }
    
    public boolean celluleOccupee(int ligne, int colonne){
        // Si la case est null -  renvoie false 
        // Sinon - renvoie true
        return cellules[ligne][colonne].jetonCourant != null;
    }
    
    public String lireCouleurDuJeton(int ligne, int colonne){
        return cellules[ligne][colonne].lireCouleurDuJeton();
    }
    
    public boolean etreGagnantePourJoueur(Joueur unJoueur){
    /*  On vérifie pour chaque case si les n+1, n+2, et n+3 sont égales en horizontal, verticale, et les deux diagonales...
        on le fait pour les intervalles de cellules suivants :    
                - horizontale : colonne[0,3] ligne[0,5]
                - verticale : colonne[0,6] ligne[0,2]
                - diagonale 1 : colonne[0,3] ligne[0,2](bas gauche vers haut droite)
                - diagonale 2 : colonne[0,3] ligne[3,5](haut gauche vers bas droite)
    On prendra i pour les lignes et j pour les colonnes : cellules[i][j]
    */
    int i, j;
    String pion = unJoueur.couleur;// valeur de la couleur de la cellule
    
    for(i = 0; i<6; i++){// vertical
        for(j = 0; j<4; j++){
            if(cellules[i][j] != null && cellules[i][j].lireCouleurDuJeton().equals(pion) 
                    && cellules[i][j+1] != null && cellules[i][j+1].lireCouleurDuJeton().equals(pion)
                    && cellules[i][j+2] != null && cellules[i][j+2].lireCouleurDuJeton().equals(pion) 
                    &&cellules[i][j+3] != null && cellules[i][j+3].lireCouleurDuJeton().equals(pion)){
                return true;
            }   
        }   
    }
    for(i = 0; i<3; i++){// horizontal
        for(j = 0; j<7; j++){         
            if(cellules[i][j] != null && cellules[i][j].lireCouleurDuJeton().equals(pion) 
                    && cellules[i+1][j] != null && cellules[i+1][j].lireCouleurDuJeton().equals(pion)
                    && cellules[i+2][j] != null && cellules[i+2][j].lireCouleurDuJeton().equals(pion) 
                    &&cellules[i+3][j] != null && cellules[i+3][j].lireCouleurDuJeton().equals(pion)){
                return true;
            }
        }
    }
    for(i = 0; i<3; i++){//diagonale 1
        for(j = 3; j<4;j++){
            if(cellules[i][j] != null && cellules[i][j].lireCouleurDuJeton().equals(pion) 
                    && cellules[i+1][j+1] != null && cellules[i+1][j+1].lireCouleurDuJeton().equals(pion)
                    && cellules[i+2][j+2] != null && cellules[i+2][j+2].lireCouleurDuJeton().equals(pion) 
                    &&cellules[i+3][j+3] != null && cellules[i+3][j+3].lireCouleurDuJeton().equals(pion)){
            return true;
            }
        }
    }
        for(i = 3; i<6; i++){//diagonale 2
            for(j = 0; j<4;j++){
                if(cellules[i][j] != null && cellules[i][j].lireCouleurDuJeton().equals(pion) 
                        && cellules[i-1][j+1] != null && cellules[i-1][j+1].lireCouleurDuJeton().equals(pion)
                        && cellules[i-2][j+2] != null && cellules[i-2][j+2].lireCouleurDuJeton().equals(pion) 
                        && cellules[i-3][j+3] != null && cellules[i-3][j+3].lireCouleurDuJeton().equals(pion)){
                return true;
            }
        }
    }
        return false;// Si aucun des test n'a renvoyé true, c'est que le joueur n'a pas gagné, on renvoie donc false
    }
    
       void tasserColonne(int colonne) {
        for (int i = 0; i < 6; i++) {
            if (i == 5) {
                cellules[i][colonne].jetonCourant = null;
            } else {
                if (cellules[i][colonne].jetonCourant  == null) {
                  cellules[i][colonne].jetonCourant = cellules[i + 1][colonne].jetonCourant;
                  cellules[i + 1][colonne].jetonCourant=null;
                }
            }

        }
    }
    public void tasserGrille(){// tasse chaque colonne de la grille
            for (int i = 0; i < 7; i++) {
           tasserColonne(i);
        }
    }
    
    public boolean colonneRemplie(int colonne){
        //Si une colonne de cellules n'a aucun élément null, on renvoie vrai, sinon faut 
        return (cellules[5][colonne].recupererJeton() != null);
    }
    
    public boolean placerTrouNoir(int ligne, int colonne){
        if (!cellules[ligne][colonne].trouNoir) {
            cellules[ligne][colonne].trouNoir = true;
            return true;
        }
        return false;
  
    }
    
    public boolean placerDesintegrateur(int ligne, int colonne){
    if (!cellules[ligne][colonne].desintegrateur) {
            cellules[ligne][colonne].desintegrateur = true;
            return true;
    }
    return false;
    }
    
    public boolean supprimerJeton(int ligne, int colonne){
        // si le jeton n'existait pas on renvoie false
        //sinon on retire sa référence et renvoie true
        if (cellules[ligne][colonne].jetonCourant == null) {
            return false;
        }
        cellules[ligne][colonne].jetonCourant = null;
        return true;
    }
    
    public Jeton recupererJeton(int ligne, int colonne){
        Jeton jetonRenvoi = cellules[ligne][colonne].recupererJeton();
        cellules[ligne][colonne].supprimerJeton();
        return jetonRenvoi;
    }
    }
