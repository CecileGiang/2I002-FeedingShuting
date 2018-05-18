public class Piranha extends Toxique{

    public Piranha(int posX, int posY) {
        super("Piranha", -15, posX, posY);
    }

    public static int getNbMax(int niveau){ //retourne le nombre maximal d'instances de Piranha que l'on peut trouver dans un tableau de jeu (Jeu.java).
    					    //Bien sûr on n'a pas besoin d'instancier Piranha pour connaître cette valeur.
	switch(niveau){
		case 2:
		    return 2;
		case 3:
		    return 3;
	}
	return 0;
    }
 	public String toString(){
		return " 🦀 ";
    } 
}
