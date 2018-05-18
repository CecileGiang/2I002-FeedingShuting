public class Niveau{ //Classe statique

	private static int niveau = 1;
	
	public static int getNiveau(){
		return niveau;
	}
	
	public static void augmenteNiveau(){
		niveau++;
	}
}
