/* On choisit de créer une interface pour permettre aux Poissons de se déplacer, car cette propriété est commune à tout Poisson, mais chacun de manière différente:
	* À Gauche et à droite pour Nemo
	* En Haut et en Bas pour Tuna
	* À Gauche, à Droite, en Haut et en Bas pour WhiteShark et BigWhale
	* À Gauche, à Droite, en Haut et en Bas pour Shuting, mais Shuting est dirigé par le joueur. */

public interface SeDeplacer{

	public abstract int seDeplacer(int direction);
}

/* RESPECTIVEMENT:
	* depl = 1: déplacement vers le haut
	* depl = 2: déplacement vers le bas
	* depl = 3: déplacement vers la gauche
	* depl = 4: déplacement vers la droite
*/
	
