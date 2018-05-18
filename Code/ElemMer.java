/* La classe mer regroupe toutes les entités que l'on peut retrouver dans la mer (ici: Poisson et Nourriture).
   On choisit de la créer afin d'être capables par la suite de créer des ArrayList qui contiennent à la fois les Poissons 
   et la Nourriture */

public abstract class ElemMer {

    private String nom;
    private final int valeurEnerg;

    /* Attributs de position de l'élément de Mer */
    private int posX;
    private int posY; 

    public ElemMer(String nom, int valeurEnerg, int posX, int posY){
        this.nom = nom;
        this.valeurEnerg = valeurEnerg;
	this.posX = posX;
	this.posY = posY;
    }

    public String getNom(){
	return nom;
    }

    public int getValEnerg(){
	return valeurEnerg;
    }

    public int getPosX(){
	return posX;
    }

    public int getPosY(){
	return posY;
    }

    public void setPosX(int posX){
	this.posX = posX;
    }

    public void setPosY(int posY){
  	this.posY = posY;
    }

    public String toString(){
    	return nom;
    }
}
