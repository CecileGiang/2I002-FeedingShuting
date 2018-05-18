import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Mer {

	Shuting shuting;

	/* Plateau de jeu, de dimensions 10*10 */
	private ElemMer[][] tabMer;

	/* ArrayList de proies et de prédateurs qui change selon le niveau de Shuting. Ces ArrayList sont affichés à chaque déplacement de Shuting. */
	private ArrayList<String> proies;
	private ArrayList<String> predateurs;	

	/* Population maximale par type */
	/* Dans l'ordre: Nemo, Tuna, WhiteShark, BigWhale, Perle, Piranha */
	private int[] nbMax;
	private int[] nbElemMer; //Tableau contenant le nombre d'instances de chaque élément présent dans tabMer

	public Mer(){

		shuting = new Shuting(4,4);
		tabMer = new ElemMer[10][];
		nbElemMer = new int[6];
		nbMax = new int[6];
		
		/* Initialisation des ArrayList de proies et de prédateurs */
		proies = new ArrayList<String>();
		predateurs = new ArrayList<String>();


		/* Niveau 1 */
		proies.add("Nemo");
		predateurs.add("Tuna");

		System.out.println(affichageTitreJeu());
		try{
			Thread.sleep(2500);
		} catch (InterruptedException e){
			e.printStackTrace();
		}

		affichageReglesDuJeu();
		initTableauMer();
		
		while(shuting.getScore()<50){
		
			updateNbMax(Niveau.getNiveau());
			remplirTabMer();
			
			try{
				deplacerMer();
			}catch(ArrayIndexOutOfBoundsException out){
				System.out.println("Game over. Vous ne pouvez pas respirer dehors de la mer :( ");
			}
			System.out.println(this.toString());
			for(int i=0; i<2; i++) System.out.println();

			try{
				Thread.sleep(1000);
			} catch (InterruptedException e){
				e.printStackTrace();
			}

			if(shuting.getNbVies()==0) affichageGameOver();
		}


		/* Niveau 2 */
		affichageNiveau2();
		Niveau.augmenteNiveau();
		shuting.setTaille(2);

		proies.add("Tuna");
		proies.add("Perle");

		predateurs.remove("Tuna");
		predateurs.add("WhiteShark");
		predateurs.add("Piranha");

		while(shuting.getScore()<150){
		
			updateNbMax(Niveau.getNiveau());
			remplirTabMer();
			
			try{
				deplacerMer();
			}catch(ArrayIndexOutOfBoundsException out){
				System.out.println("Game over. Vous ne pouvez pas respirer dehors de la mer :( ");
			}
			System.out.println(this.toString());
			for(int i=0; i<2; i++) System.out.println();

			try{
				Thread.sleep(1000);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
			if(shuting.getNbVies()==0) affichageGameOver();
		}
		

		/* Niveau 3 */
		affichageNiveau3();
		Niveau.augmenteNiveau();
		shuting.setTaille(3);

		proies.add("WhiteShark");

		predateurs.remove("Tuna");
		predateurs.remove("WhiteShark");
		predateurs.add("BigWhale");  

		while(shuting.getScore()<300){
		
			updateNbMax(Niveau.getNiveau());
			remplirTabMer();
			
			try{
				deplacerMer();
			}catch(ArrayIndexOutOfBoundsException out){
				System.out.println("Game over. Vous ne pouvez pas respirer dehors de la mer :( ");
			}
			System.out.println(this.toString());
			for(int i=0; i<2; i++) System.out.println();

			try{
				Thread.sleep(1000);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
			if(shuting.getNbVies()==0) affichageGameOver();
		}

		/* Achèvement: Vicoire et Fin de Jeu*/
		affichageAchevement();
	}



	public void initTableauMer(){
	
		for(int i=0; i<tabMer.length; i++){
			tabMer[i] = new ElemMer[10];
		}
		/* On place Shuting au centre du tableau */;
		tabMer[4][4] = shuting;
	}
	


	/* Méthode appelée lorsque l'on rafraîchit le tableau de jeu. Elle compte le nombre de chaque élements de Mer présents, 
	   stocke ces données dans le tableau nbElemMer puis rajoute des éléments de Mer s'il en manque. */

	public void remplirTabMer(){

		//On réinitialise toutes les cases de nbElemMer à 0
		for(int i=0; i<nbElemMer.length; i++) nbElemMer[i] = 0;

		// On compte le nombre d'instances de chaque élément dans tabMer
		for(int i=0; i<tabMer.length; i++){
			for(int j=0; j< tabMer[i].length; j++){
				if(tabMer[i][j] != null){
					switch((tabMer[i][j]).getNom()){
						case "Nemo":
						    nbElemMer[0]++;
						    break;
						case "Tuna":
						    nbElemMer[1]++;
						    break;
						case "WhiteShark":
						    nbElemMer[2]++;
						    break;
						case "BigWhale":
						    nbElemMer[3]++;
						    break;
						case "Perle":
						    nbElemMer[4]++;
						    break;
						case "Piranha":
						    nbElemMer[5]++;
						    break;
					}
				}
			}
		}

	
		/* On rajoute le nombre manquant d'éléments de Mer: si le nombre d'instances de chaque élément est inférieur au max,
		   alors on en rajoute à une position non occupée */

		for(int i=0; i<nbMax.length; i++){

			int diff = nbMax[i]-nbElemMer[i];
			int posElemX = (int)(Math.random()*10);
			int posElemY = (int)(Math.random()*10);

			switch(i){
				case 0: //Cas Nemo
				    while(diff>0){
					    int cpt = 0; //On fait 25 essais au max 
					    while(tabMer[posElemX][tabMer.length-1]!=null && cpt < 25){
						posElemX = (int)(Math.random()*10);
						cpt++;
					    }				    
					    tabMer[posElemX][tabMer.length-1] = new Nemo(posElemX, tabMer.length-1);
					    diff--;
				    }
				    break;

				case 1: //Cas Tuna 
				      while(diff>0){
					    int cpt = 0; //On fait 25 essais au max 
					    while(tabMer[tabMer.length-1][posElemY]!=null && cpt < 25){
						posElemY = (int)(Math.random()*10);
						cpt++;
					    }				    
					    tabMer[tabMer.length-1][posElemY] = new Tuna(tabMer.length-1, posElemY);
					    diff--;
				    }
				    break;
		
				case 2: //Cas WhiteShark 
				     while(diff>0){
					    int cpt = 0; //On fait 25 essais au max 
					    while(tabMer[posElemX][tabMer.length-1]!=null && cpt < 25){
						posElemX = (int)(Math.random()*10);
						cpt++;
					    }				    
					    tabMer[posElemX][tabMer.length-1] = new WhiteShark(posElemX, tabMer.length-1);
					    diff--;
				    }
				    break;

				case 3: //Cas BigWhale 
				      while(diff>0){
					    int cpt = 0; //On fait 25 essais au max 
					    while(tabMer[posElemX][tabMer.length-1]!=null && cpt < 25){
						posElemX = (int)(Math.random()*10);
						cpt++;
					    }				    
					    tabMer[posElemX][tabMer.length-1] = new BigWhale(posElemX, tabMer.length-1);
					    diff--;
				    }
				    break;
	
				case 4: //Cas Perle 
				     while(diff>0){
					    int cpt = 0; //On fait 25 essais au max 
					    while(tabMer[posElemX][tabMer.length-1]!=null && cpt < 25){
						posElemX = (int)(Math.random()*10);
						cpt++;
					    }				    
					    tabMer[posElemX][tabMer.length-1] = new Perle(posElemX, tabMer.length-1);
					    diff--;
				    }
				    break;

				case 5: //Cas Piranha 
				     while(diff>0){
					    int cpt = 0; //On fait 25 essais au max 
					    while(tabMer[posElemX][tabMer.length-1]!=null && cpt < 25){
						posElemX = (int)(Math.random()*10);
						cpt++;
					    }				    
					    tabMer[posElemX][tabMer.length-1] = new Piranha(posElemX, tabMer.length-1);
					    diff--;
				    }
				    break;
			}
		}
	}
	
	/* À  chaque changement de niveau, on rafraîchit le tableau nbMax contenant le nombre laximal d'instances de chaque 
	élément de Mer dans le tableau de Jeu. Les méthodes getNbMax(int niveau) sont statiques donc on n'a pas besoin de créer 
	d'instance de Mer pour les appeler.*/
	
	public void updateNbMax(int niveau){
		nbMax[0] = Nemo.getNbMax(niveau);
		nbMax[1] = Tuna.getNbMax(niveau);
		nbMax[2] = WhiteShark.getNbMax(niveau);
		nbMax[3] = BigWhale.getNbMax(niveau);
		nbMax[4] = Perle.getNbMax(niveau);
		nbMax[5] = Piranha.getNbMax(niveau);
	}


	public void deplacerMer(){

		/* ---------------------DÉPLACEMENT DE SHUTING----------------------------- */

		/* On récupère les déplacements entrés au clavier par le joueur:
			- Pour aller vers le Haut: touche 5
			- Pour aller vers le Bas: touche 2
			- Pour aller vers la Gauche: touche 1
			- Pour aller vers la Droite: touche 3
		*/
		
		String dirShuting;
		Scanner scanIn = new Scanner(System.in);
		dirShuting = scanIn.nextLine();

		if(dirShuting.equals("5")){
			System.out.println("KEY_UP");
			shuting.setDepl(1);
		}
		if(dirShuting.equals("2")){
			System.out.println("KEY_DOWN");
			shuting.setDepl(2);
		}
		if(dirShuting.equals("1")){
			System.out.println("KEY_LEFT");
			shuting.setDepl(3);
		}
		if(dirShuting.equals("3")){
			System.out.println("KEY_RIGHT");
			shuting.setDepl(4);
		}
		
		int decalageX = 0, decalageY = 0; //nombre de pas que le poisson va faire

		switch(shuting.getDepl()){
			case 1: //Haut
			    decalageX = -1;
			    decalageY = 0;
			    break;
			case 2: //Bas
			    decalageX = 1;
			    decalageY = 0;
			    break;
			case 3: //Gauche
			    decalageX = 0;
			    decalageY = -1;
			    break;
			case 4: //Droite
			    decalageX = 0;
			    decalageY = 1;
			    break;
		}

		int posX = shuting.getPosX(); int posY = shuting.getPosY();

		
		// Si shuting ne dépasse pas les bornes
		if((posX+decalageX>=0)&&(posX+decalageX < tabMer.length)||(posY+decalageY >= 0)||(posY+decalageY < tabMer.length)){

			//Si la position suivante est libre
			if(tabMer[posX+decalageX][posY+decalageY] == null){
				tabMer[posX][posY] = null;
				tabMer[posX+decalageX][posY+decalageY] = shuting;
				shuting.seDeplacer(shuting.getDepl());			
			}

			//Si la position suivante est occupée
			else{
				if(tabMer[posX+decalageX][posY+decalageY] instanceof Poisson){
					if(!(shuting.chasser((Poisson)tabMer[posX+decalageX][posY+decalageY]))){
								//tabMer[posY][posX] = null;
								//tabMer[posX+decalageX][posY+decalageY] = shuting;
					}else shuting.etreChasse((Poisson)tabMer[posX+decalageX][posY+decalageY]);
				}
				else{ // la position suivante est une nourriture
					if(tabMer[posX+decalageX][posY+decalageY] instanceof Perle) shuting.nourri();  // si c'est une perle
					else shuting.empoisonne();  // si Piranha
				}
			}

		}//fin if depasse bornes



		/* ------------------------ DÉPLACEMENT DES ÉLÉMENTS DE MER ---------------------------------- */

		for(int i=0; i<tabMer.length; i++){
			for(int j=0; j<tabMer[i].length; j++){
				ElemMer m = tabMer[i][j];

				if(m!=null && m instanceof Poisson && !(m instanceof Shuting)){
					int depl = ((Poisson)m).getDepl();		
					
					decalageX = 0; decalageY = 0; //nombre de pas que le poisson va faire

					switch(depl){
						case 1: //Haut
						    decalageX = -1;
						    decalageY = 0;
						    break;
						case 2: //Bas
						    decalageX = 1;
						    decalageY = 0;
						    break;
						case 3: //Gauche
						    decalageX = 0;
						    decalageY = -1;
						    break;
						case 4: //Droite
						    decalageX = 0;
						    decalageY = 1;
						    break;
					}


					//Si la position testée est en dehors du tableau, le poisson disparaît

					if((i+decalageX<0 && depl==1)||(i+decalageX >= tabMer.length && depl==2)
					   ||(j+decalageY < 0 && depl == 3)||(j+decalageY >= tabMer.length && depl==4)){
					/*	if(m instanceof Nemo){
							((Poisson)m).randomDepl(3,4);
						}
						if(m instanceof Tuna){
							((Poisson)m).randomDepl(1,2);
						}
						if(m instanceof WhiteShark) ((Poisson)m).randomDepl(1,4);
						if(m instanceof BigWhale) ((Poisson)m).randomDepl(1,4);
					*/
						tabMer[i][j] = null;
					}
					// Si la position suivante n'est pas occupée
					else if(tabMer[i+decalageX][j+decalageY] == null){
						if(m instanceof Nemo) ((Nemo)m).seDeplacer(depl);
						if(m instanceof Tuna) ((Tuna)m).seDeplacer(depl);
						if(m instanceof WhiteShark) ((WhiteShark)m).seDeplacer(depl);
						if(m instanceof BigWhale) ((BigWhale)m).seDeplacer(depl);
						tabMer[i][j] = null;
						tabMer[i+decalageX][j+decalageY] = m;
					}else{ // Si la position suivante est occupée
						if(tabMer[i+decalageX][j+decalageY] instanceof Poisson){
							if(((Poisson)m).chasser((Poisson)tabMer[i+decalageX][j+decalageY])){
								if(tabMer[i+decalageX][j+decalageY] instanceof Shuting){
									((Poisson)tabMer[i+decalageX][j+decalageY]).etreChasse((Poisson)m); 
								}
								if(!(tabMer[i+decalageX][j+decalageY] instanceof Shuting)) {
									tabMer[i][j] = null;
									tabMer[i+decalageX][j+decalageY] = m;
								}
							} else{
								boolean estChasse = ((Poisson)m).etreChasse((Poisson)tabMer[i+decalageX][j+decalageY]);
								if(estChasse && !(tabMer[i+decalageX][j+decalageY] instanceof Shuting)){
									tabMer[i][j] = tabMer[i+decalageX][j+decalageY];
									tabMer[i+decalageX][j+decalageY] = null;
								}
								else {
									if(m instanceof Nemo) ((Poisson)m).randomDepl(3,4);
									if(m instanceof Tuna) ((Poisson)m).randomDepl(1,2);
									if(m instanceof WhiteShark) ((Poisson)m).randomDepl(1,4);
									if(m instanceof BigWhale) ((Poisson)m).randomDepl(1,4);
								}									
							}
						}else if(tabMer[i+decalageX][j+decalageY] instanceof Nourriture){
									tabMer[i+decalageX][j+decalageY] = m;
									tabMer[i][j] = null;
								}
					}
				}//fin if m instance of Poisson
			}//fin for
		}//fin for
	}
	
	/* ----------------------------------------- FONCTIONS D'AFFICHAGE DU JEU SUR LE TERMINAL ----------------------------------------- */
	

	public String affichageTitreJeu(){
		String s = "\n\n\n\n\n*******************************************************************************\n";
		s += "            *****  *****  *****  ****   *****  **   *   ****\n";
		s += "            *      *      *      *   *    *    * *  *  *   \n";
		s += "            ***    ***    ***    *   *    *    *  * *  *  ***\n";
		s += "            *      *      *      *   *    *    *   **  *   * \n";
		s += "            *      *****  *****  ****   *****  *    *   ****\n\n\n";
		
		
		s += "             ***   *   *  *   *  *****  *****  **   *   ****\n";
		s += "            *      *   *  *   *    *      *    * *  *  *\n";
		s += "             ***   *****  *   *    *      *    *  * *  *  ***\n";
		s += "                *  *   *  *   *    *      *    *   **  *   *\n";
		s += "            ****   *   *   ***     *    *****  *    *   ****\n\n\n";
		
		
		s += "		---------- Feeding Shuting 👧 ----------\n\n";
		
		s += "*******************************************************************************\n";

		return s;
	}


	public void affichageReglesDuJeu(){


		String s = "*******************************************************************************\n";
		s += "			3 NIVEAUX DE JEU\n";

		s += "  * NIVEAU 1: Proies: Nemo (max: 20)\n";
		s += "	      Predateurs: Tuna (max: 10)\n";
		s += "	      Pas de Nourriture.\n";
		s += "	      Shuting: * taille au début: 5\n";
		s += "		       * taille fin: 10\n";
		s += "	      Niveau atteint lorsque le score atteint est de 50.\n";

		s += "  * NIVEAU 2: Proies: Nemo (max: 15), Tuna (max: 10)\n";
		s += "	      Predateurs: White Shark (max: 15)\n";
		s += "	      Nourriture: Perle (non toxique, max: 2), Piranha (Toxique, max: 3).\n";
		s += "	      Shuting: * taille au début: 10\n";
		s += "		       * taille fin: 15\n";
		s += "	      Niveau atteint lorsque le score atteint est de 150.\n";

		s += "  * NIVEAU: Proies: Nemo (max: 10), Tuna (max: 10), WhiteShark (max: 10)\n";
		s += "	      Predateurs : BigWhale (max: 2)\n";
		s += "	      Nourriture: Perle (non toxique, max: 3), Piranha (Toxique, max: 4).\n";
		s += "	      Shuting: taille au début: 15\n";
		s += "	      Niveau atteint lorsque le score atteint est de 300.\n";

		s += "*******************************************************************************";

		System.out.println(s);

		String attente;
		Scanner scanIn = new Scanner(System.in);
		attente = scanIn.nextLine();

		s = "\n\n\n*******************************************************************************\n\n\n";


		s += "				COMMANDES:\n\n\n";


		s += "		Pour aller:\n\n";

		s += "			* En haut: touche 5\n";
		s += "			* En bas: touche 2\n";
		s += "			* À gauche: touche 1\n";
		s += "			* À droite: touche 3\n\n\n\n";



		s += "			  ~~~ BON JEU ! ~~~\n\n\n\n";



		s += "*******************************************************************************\n";

		s += "\n\n\n\n\n*******************************************************************************\n";
		s += "                                                            \n";
		s += "                  **   *  ***  *   *  *****   ***    *    *  \n";
		s += "                  * *  *   *   *   *  *      *   *   *    *  \n";
		s += "                  *  * *   *   *   *  ***    *****   *    *  \n";
		s += "                  *   **   *    * *   *      *   *   *    *  \n";
		s += "                  *    *  ***    *    *****  *   *   ******  \n\n";
		
		
		s += "                                        **                    \n";
		s += "                                       * *                    \n";
		s += "                                         *                    \n";
		s += "                                         *                    \n";
		s += "                                       *****                  \n\n\n";
		
		
		s += "	        Vous êtes: 👧 |  Vous mangez: 🐠 | Attention aux 🐟\n";

		s += "	                 Score pour le prochain niveau: 50\n\n";
		
		s += "*******************************************************************************\n";

		System.out.println(s);
	}


	public void affichageNiveau2(){
		String s = "\n\n\n\n\n*******************************************************************************\n";
		s += "                                                            \n";
		s += "                              Félicitation ! ! !            \n";

		s += "                  **   *  ***  *   *  *****   ***    *    *  \n";
		s += "                  * *  *   *   *   *  *      *   *   *    *  \n";
		s += "                  *  * *   *   *   *  ***    *****   *    *  \n";
		s += "                  *   **   *    * *   *      *   *   *    *  \n";
		s += "                  *    *  ***    *    *****  *   *   ******  \n\n";
		
	
		s += "                                     *****                    \n";
		s += "                                         *                    \n";
		s += "                                     *****                    \n";
		s += "                                     *                    \n";
		s += "                                     *****                  \n\n";
		
		
		s += "	   Vous êtes: 👧 |  Vous mangez: 🐠+🐟+⚪️  | Attention aux 🦈+🦀\n";
		
		s += "	             Score pour le prochain niveau: 150\n";
		
		s += "*******************************************************************************\n";

		System.out.println(s);
	}


	public void affichageNiveau3(){
		String s = "\n\n\n\n\n*******************************************************************************\n";
		s += "                                                            \n";
		s += "                              Félicitation ! ! !            \n";

		s += "                  **   *  ***  *   *  *****   ***    *    *  \n";
		s += "                  * *  *   *   *   *  *      *   *   *    *  \n";
		s += "                  *  * *   *   *   *  ***    *****   *    *  \n";
		s += "                  *   **   *    * *   *      *   *   *    *  \n";
		s += "                  *    *  ***    *    *****  *   *   ******  \n\n";
		
	
		s += "                                     *****                    \n";
		s += "                                         *                    \n";
		s += "                                     *****                    \n";
		s += "                                         *                \n";
		s += "                                     *****                  \n\n";
		
		
		s += "	     Vous êtes: 👧 |  Vous mangez: 🐠+🐟+🐬+⚪️  | Attention aux 🐳+🦀 \n";
		
		s += "	                      Score pour réussir: 300\n";
		
		s += "*******************************************************************************\n";

		System.out.println(s);
	}

	public void affichageAchevement(){
		String s = "\n\n\n\n\n*******************************************************************************\n";
		s += "                                                            \n";
		s += "                             Félicitations ! ! !            \n\n";

		s += "                               Vous êtes                     \n";

		s += "                          ****    *****   ***                   \n";
		s += "                          *   *   *   *    *                     \n";
		s += "                          * **    *   *    *                   \n";
		s += "                          *   *   *   *    *                     \n";
		s += "                          *    *  *****   ***                    \n\n";
		
	
		s += "                            de la merrrrrrrrrr                         \n";
		s += "                                                                    \n";
		s += "                                                                  \n";
		s += "                                                                   \n\n";
		
		s += "*******************************************************************************\n";

		System.out.println(s);
	}


	public void affichageGameOver(){
		String s = "\n\n\n\n\n*******************************************************************************\n";
		s += "                                                            \n";
		s += "                                 Oh non ! ! !            \n\n";

		s += "                       ****     ****    *     *   *****  \n";
		s += "                      *        *    *   **   **   *      \n";
		s += "                      *  ***   ******   * * * *   ****   \n";
		s += "                      *    *   *    *   *  *  *   *      \n";
		s += "                       *****   *    *   *     *   *****  \n\n";
		
	
		s += "                       ****     *   *    *****    ****    \n";
		s += "                      *    *    *   *    *        *   *   \n";
		s += "                      *    *    *   *    *****    ****    \n";
		s += "                      *    *     * *     *        *   *    \n";
		s += "                       ****       *      *****    *    *     \n\n";
		
		
		s += "*******************************************************************************\n";
		System.out.println(s);
		try{
			Thread.sleep(4000);
		} catch (InterruptedException e){
			e.printStackTrace();
		}

		System.exit(0);
	}


	public String affichageProiesPredateurs(){
		String s = "\nProies: ";
		for(int i=0; i<proies.size(); i++) s += proies.get(i)+" ";
		s += "\nPredateurs: ";
		for(int j=0; j<predateurs.size(); j++) s += predateurs.get(j)+" ";
		return s;
	}

	public String toString(){
		String s = "Niveau "+Niveau.getNiveau()+affichageProiesPredateurs()+"\nSCORE: ";
		s += shuting.getScore()+ "\nVIE: ";
		s += shuting.getNbVies();
		s += "\n\n   ___________________________________________________________________________\n\n";
		for(int i=0; i<tabMer.length; i++){
			s += "| ";
			for(int j=0; j<tabMer[i].length; j++){
				if(tabMer[i][j]==null) s += "  *  ";
				else s += (tabMer[i][j]).toString();
				s += "	";
			}
			s += "|\n";
		}
		s += "   ___________________________________________________________________________\n\n";
		return s;
	}



}

/* ------------------------------------------------------- RÈGLES DU JEU: -------------------------------------------------------------

Le but du jeu est de permettre à Shuting d'atteindre 300 points d'énergie en se nourrissant de poissons plus petits que soi.
  * Manger un poisson de valeur énergétique n rapporte n points s'il est plus petit que soi.
  * Manger un poisson de valeur énergétique n nous enlève n points ainsi qu'une vie s'il est plus grand que soi.
  * Shuting a 3 vies.

NOTES:
 
3 niveaux de jeux:
  * Niveau 1: Proies: Nemo (max: 20);
	      Predateurs: Tuna (max: 10);
	      Pas de Nourriture.
	      Shuting: * taille au début: 5
		       * taille fin: 10
	      Niveau atteint lorsque le score atteint est de 50.
  * Niveau 2: Proies: Nemo (max: 15), Tuna (max: 10);
	      Predateurs: White Shark (max: 15);
	      Nourriture: Perle (non toxique, max: 2), Piranha (Toxique, max: 3).
	      Shuting: * taille au début: 10
		       * taille fin: 15
	      Niveau atteint lorsque le score atteint est de 150.
  * Niveau 3: Proies: Nemo (max: 10), Tuna (max: 10), WhiteShark (max: 10)
	      Predateurs : BigWhale (max: 2)
	      Nourriture: Perle (non toxique, max: 3), Piranha (Toxique, max: 4).
	      Shuting: taille au début: 15
	      Niveau atteint lorsque le score atteint est de 300.





*/
