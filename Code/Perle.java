public class Perle extends NonToxique {

    public Perle(int posX, int posY) {
        super("Perle", 15, posX, posY);
    }

    public static int getNbMax(int niveau){ //retourne le nombre maximal d'instances de Perle que l'on peut trouver dans un tableau de jeu (Jeu.java).
    					    //Bien sûr on n'a pas besoin d'instancier Perle pour connaître cette valeur.
	switch(niveau){
		case 2:
		    return 3;
		case 3:
		    return 4;
	}
	return 0;
    }
 	public String toString(){
		return " ⚪️  ";
    }   

}
