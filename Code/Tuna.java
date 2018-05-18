public class Tuna extends Poisson{

    public Tuna(int posX, int posY) {
        super("Tuna", 7, "Pink", 10, posX, posY);
	double random = Math.random(); //On décide si le poisson commence par se déplacer en Haut ou en Bas
	if(random < 0.5) this.setDepl(1);
	else this.setDepl(2);
    }

    /* Un thon ne peut se déplacer que de haut en bas et vice versa */

    public int seDeplacer(int direction){
        switch(direction){
            case 1: //Haut
                this.setDepl(1);
		this.setPosX(this.getPosX()-1);
                break;
            case 2: //Bas
                this.setDepl(2);
		this.setPosX(this.getPosX()+1);
                break;
        }
        return this.getDepl();
    }


    public static int getNbMax(int niveau){ //retourne le nombre maximal d'instances de Tuna que l'on peut trouver dans un tableau de jeu (Jeu.java)
    					    //Bien sûr on n'a pas besoin d'instancier Tuna pour connaître cette valeur.
	return 5; 
    }

    public String toString(){
	return "  🐟  ";
    }
}
