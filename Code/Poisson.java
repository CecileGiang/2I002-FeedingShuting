public abstract class Poisson extends ElemMer implements Chasser, EtreChasse, SeDeplacer{

	protected int taille;
	private String couleur;

	private int depl; // Indique le déplacement effectué par le poisson (la direction dans laquelle le poisson se déplace)

	public Poisson(String nom, int taille, String couleur, int valeurEnerg, int posX, int posY){
		super(nom, valeurEnerg, posX, posY);
		this.taille = taille;
		this.couleur = couleur;
		this.depl = 0;
	}

	/* Si le poisson courant A est plus gros que le poisson passé en argument B, alors A pourra chasser B (boolean true) et B sera supprimé de l'ArrayList auquel il appartient dans le main, s'il ne s'enfuit pas */

	public boolean chasser(Poisson p){
		if(p.taille < this.taille) return true;
		else return false;
	}

	/* Méthode appelée si le poisson courant A est chassé. A a 30% de chances de s'enfuir.
	   S'enfuit: true, ne s'enfuit pas: false */

	public boolean etreChasse(Poisson p){
		double random = Math.random();
		if(random < 0.3) return true;
		else return false;
	}

	public abstract int seDeplacer(int direction); //Méthode venant de l'interface seDeplacer

	public int randomDepl(int min, int max){
		//System.out.println("Entree random");
		int random = depl;
		while(random == depl){
			random = (int)Math.floor(Math.random()*(max-min+1)) + min;
			//System.out.println("depl = "+depl+", min = "+min+" max = "+max+" random = "+random);
		}
		depl = random;
		//System.out.println("Sortie random");
		return depl;
	}
	
	public int getTaille(){
		return taille;
	}

    	public void setTaille(int taille){
	    this.taille = taille;
    	}

	public int getDepl(){
		return depl;
	}

	public void setDepl(int depl){
		this.depl = depl;
	}

}

