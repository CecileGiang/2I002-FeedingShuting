import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Shuting extends Poisson{

	//Le joueur est représenté dans le plateau de jeu par Shuting. Une seule instance de Shuting est donc nécessaire.

	private static int score; //Les variables score et nbVies sont communes ) toutes instances de Shuting (étant donné qu'il n'y en a qu'une).
	private static int nbVies = 5;
	private String direction;

	public Shuting(int posX, int posY){
		super("Shuting", 5, "Red", 0, posX, posY);
		this.score = 0;
		this.direction = "Haut";
		this.setDepl(1); //déplacement par défaut: 1 (correspond à un déplacement vers le haut)
	}

	public boolean chasser(Poisson p){ //override la méthode chasser() de Poisson, venant de l'interface Chasser
		if(p.getTaille() < this.getTaille()){
			score += p.getValEnerg();
			System.out.println(""+score);
			return true;
		}
		else return false;
	}

	/* Si Shuting a perdu toutes ces vies, la méthode retourne le booléen false et signalera
	   ainsi au main qu'il faut appeler gameOver() */

	public boolean etreChasse(Poisson p){ //override la méthode etreChasse() de Poisson, venant de l'interface etreChasse
		if(nbVies>0 && p.getTaille() > this.getTaille()){
			nbVies --;
			if(score >= p.getValEnerg()) score -= p.getValEnerg();
			else score = 0;
			return true;
		}
		else return false; 
	}

	
	public boolean empoisonne(){          // Si Shuting mange un piranha
		if(nbVies>0){
			nbVies --;
			return true;
		}
		else return false; 
	}
	
	public boolean nourri(){             // Si Shuting mange une perle
		if(nbVies<3){
			nbVies++;
			return true;
		}
		else return false;
	}


	/* Shuting peut se déplacer dans les 4 directions */

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
	

	public static int getNbMax(int niveau){ //retourne le nombre maximum d'instances de Shuting dans notre plateau de jeu (Jeu.java)
		return 1;
    	}
	
	public static int getScore(){
		return score;
	}
	
	public void setScore(int score){
		this.score += score;
	}

	public static int getNbVies(){
		return nbVies;
	}

	public void setTaille(int niveau){ //modifie la taille de Shuting à chaque fois qu'elle augmente de niveau. Elle peut alors manger plus de poissons.
		switch(niveau){
			case 2: 
				this.taille = 10;
				break;
			case 3: 
				this.taille = 15;
				break;
		}
	}

	public String toString(){
		return "  👧  ";
        }
}
