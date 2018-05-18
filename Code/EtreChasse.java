/* On choisit de créer une interface pour permettre aux Poissons d'être chassé, car cette propriété est commune à tout Poisson, mais Shuting est chassé de manière différente. */

public interface EtreChasse{

	public abstract boolean etreChasse(Poisson p);
}
