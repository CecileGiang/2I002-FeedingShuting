/* Contrairement aux Poissons, la Nourriture ne se déplace pas, et est divisée en deux sous-groupe:
	* Toxique: Si Shuting en consomme, elle perd une vie et son score décrémente significativement
	* Non-Toxique: Si Shuting en consomme, elle gagne une vie et son score augmente significativement */

public abstract class Nourriture extends ElemMer{

    public Nourriture(String nom, int valeurEnerg, int posX, int posY) {
        super(nom, valeurEnerg, posX, posY);
    }

}
