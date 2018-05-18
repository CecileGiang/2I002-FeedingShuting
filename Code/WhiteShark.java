public class WhiteShark extends Poisson {


    public WhiteShark(int posX, int posY) {
        super("White Shark", 12, "White", 15, posX, posY);
	double random = Math.random(); //On décide si le poisson commence par se déplacer en Haut, en Bas, à Gauche ou à Droite
	if(random < 0.25) this.setDepl(1);
	if(random>=0.25 && random<0.5) this.setDepl(2);
	if(random>=0.5 && random<0.75) this.setDepl(3);
	else this.setDepl(4);
    }

    /* Un requin blanc peut se déplacer vers la droite, la gauche, le haut et le bas */

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
            case 3: //Gauche
                this.setDepl(3);
		this.setPosY(this.getPosY()-1);
                break;
            case 4: //Droite
                this.setDepl(4);
		this.setPosY(this.getPosY()+1);
                break;
        }
        return this.getDepl();
    }

    public static int getNbMax(int niveau){ //retourne le nombre maximal d'instances de WhiteShark que l'on peut trouver dans un tableau de jeu (Jeu.java).
    					    //Bien sûr on n'a pas besoin d'instancier WhiteShark pour connaître cette valeur.
	switch(niveau){
		case 2:
		    return 10;
		case 3:
		    return 5;
	}
	return 0;
    }

    public String toString(){
	return "  🦈 ";
    }

}
