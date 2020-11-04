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
    Cellule [][] cellules = new Cellule [7][6];//tableau au format [nbcolonne][nblignes] ... (7 colonnes / 6 lignes)
    //variables invariantes, 
    
    
    public static final String ANSI_BLACK = "\u001B[30m";//coresspondent à la couleur du texte affiché dans afficherGrilleSurConsole()
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public Grille(){
        for(int i=0; i<7; i++){
            for(int j=0;j<6;j++){
                cellules[i][j] = new Cellule();
            }
        }
    }
    
    public boolean ajouterJetonDansColonne(Jeton unJeton, int colonne){
        // ajoute le jeton dans la colonne ciblée sur la cellule la plus basse et renvoie true
        // renvoie false si la colonne est pleine
        boolean b = true;// variable locale prenant la valeur à renvoyer
        if(cellules[colonne][5] != null){//cas ou la colonne est pleine (la dernière ligne contient une référence Jeton)
            b = false;
        }else{//cas ou la colonne n'est pas pleine
        for(int i =0;i<5;i++){// Dès qu'on rencontre une case vide, on ajoute un jeton
            if(cellules[colonne][i] == null){
                cellules[colonne][i].affecterJeton(unJeton);
                b = true;
                
                break;
            }
            
        }
        }
    return b;
    }
    
    public boolean etreRemplie(){
        //renvoie true si la grille est pleine, sinon renvoie false
        for(int i = 0; i<6; i++){
            if(cellules[i][5] == null){return false;}
            //on vérifie pour la dernière case de chaque colonne 
        }
        return true;
    }
    
    public void viderGrille(){
        // passe chaque valeur du tableau à null
        for(int i = 0; i<6; i++){
            for(int j = 0; i<5; i++){
                cellules[i][j] = null;
            }
        }
    }
    
    public void afficherGrilleSurConsole(){
        //pour chaque case de cellule on affiche un O rouge ou jaune s'il y a un jeton rouge ou jaune...
        //... un O noir pour les trous noirs et un O bleu pour une case vide
        for(int i =5; i>-1; i--){//affichage en commençant par le haut du tableau
            for(int j =0; j<7; j++){
                if("rouge".equals(cellules[i][j].lireCouleurDuJeton())){
                    System.out.print(ANSI_RED + "O" + ANSI_BLACK);
                }else if("jaune".equals(cellules[i][j].lireCouleurDuJeton())){
                    System.out.print(ANSI_YELLOW + "O" + ANSI_BLACK);  
                }else if(cellules[i][j].jetonCourant == null){
                    System.out.print(ANSI_BLUE + "O" + ANSI_BLACK);
                }else if(cellules[i][j].presenceTrouNoir()){
                    System.out.print(ANSI_BLACK + "O" + ANSI_BLACK);
                }
            }
        }
    }
    
    public boolean celluleOccupee(int colonne, int hauteur){
        // Si la case est null -  renvoie false 
        // Sinon - renvoie true
        return cellules[colonne][hauteur] != null;
    }
    
    public String lireCouleurDuJeton(int colonne, int hauteur){
        return cellules[colonne][hauteur].lireCouleurDuJeton();
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
    
    for(i = 0; i<4; i++){// vertical
        for(j = 0; j<5; j++){
            if(cellules[i][j].jetonCourant.couleur.equals(pion) && cellules[i][j+1].jetonCourant.couleur.equals(pion)&& cellules[i][j+2].jetonCourant.couleur.equals(pion) && cellules[i][j+3].jetonCourant.couleur.equals(pion)){
                return true;
            }   
        }   
    }
    for(i = 0; i<6; i++){// horizontal
        for(j = 0; j<3; j++){         
            if(cellules[i][j].jetonCourant.couleur.equals(pion) && cellules[i+1][j].jetonCourant.couleur.equals(pion)&& cellules[i+2][j].jetonCourant.couleur.equals(pion) && cellules[i+3][j].jetonCourant.couleur.equals(pion)){
                return true;
            }
        }
    }
    for(i = 0; i<4; i++){//diagonale 1
        for(j = 3; j<6;j++){
            if(cellules[i][j].jetonCourant.couleur.equals(pion) && cellules[i+1][j+1].jetonCourant.couleur.equals(pion)&& cellules[i+2][j+2].jetonCourant.couleur.equals(pion) && cellules[i+3][j+3].jetonCourant.couleur.equals(pion)){
            return true;
            }
        }
    }
        for(i = 0; i<4; i++){//diagonale 1
        for(j = 0; j<3;j++){
            if(cellules[i][j].jetonCourant.couleur.equals(pion) && cellules[i+1][j-1].jetonCourant.couleur.equals(pion)&& cellules[i+2][j-2].jetonCourant.couleur.equals(pion) && cellules[i+3][j-3].jetonCourant.couleur.equals(pion)){
            return true;
            }
        }
    }
        return false;// Si aucun des test n'a renvoyé true, c'est que le joueur n'a pas gagné, on renvoie donc false
    }
    
    public void tasserGrille(int colonne){
        //tasse la grille pour un colonne donnée 
        for(int i =0;i<6;i++){//pour chaque ligne de la colonne
            if(cellules[colonne][i]==null){//on vérifie si une cellule ne référence pas dejeton
                for(int j = i;j<5;j++){//auqeul cas on décale toutes les valeurs vers le bas 
                    cellules[colonne][j] = cellules[colonne][j+1];
                }
            cellules[colonne][5] = null;//Rajoute une référence nulle sur la dèrnière case car il n'y a pas encire de jeton
            }
            }
        }
    
    public boolean colonneRemplie(int colonne){
        //Si une colonne de cellules n'a aucun élément null, on renvoie vrai, sinon faut 
        for(int i = 0; i<5;i++){
            if(cellules[colonne][i]==null){return false;}
        }
        return true;
    }
    
    public boolean placerTrouNoir(int colonne, int ligne){
        /*appelle la fonction TrouNoir de Cellule, 
        s'il n'y avait pas de trou noir alors on en ajoute un et on renvoie true, 
        s'il n'y en avait pas on renvoie false*/
        return cellules[colonne][ligne].placerTrouNoir();
    }
    
    public boolean placerDesintegrateur(int colonne, int ligne){
    //même démarche que pour placer TrouNoir...
    return cellules[colonne][ligne].placerDesintegrateur();
    }
    
    public boolean supprimerJeton(int colonne, int ligne){
        //appelle la fonction supprimer Jeton de Cellule
        return cellules[colonne][ligne].supprimerJeton();
    }
    
    public Jeton recupererJeton(int colonne, int ligne){
        return cellules[colonne][ligne].recupererJeton();
    }
    }
