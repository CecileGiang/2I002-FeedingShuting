/* On choisit de créer une interface pour permettre aux Poissons de chasser,
car cette propriété est commune à tout Poisson, mais Shuting chasse de manière différente. */

public interface Chasser{
	public abstract boolean chasser(Poisson p);
}
