package Bateaux;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.*;

public class Bateaux {

	public final static int CONTRETORPILLEUR = 0;
	public final static int SOUSMARIN = 1;
	public final static int TORPILLEUR = 2;
	public final static int CROISEUR = 3;
	public final static int PORTEAVIONS = 4;

	public final static int HORIZONTAL = 0;
	public final static int VERTICAL = 1;

	public final static int COMPLET = 0;
	public final static int PART1 = 1;
	public final static int PART2 = 2;
	public final static int PART3 = 3;
	public final static int PART4 = 4;
	public final static int PART5 = 5;

	public final static int SANSETAT = 0;
	public final static int FANTOME = 1;
	public final static int TOUCHE = 2;



	protected int nombreCases;

	protected String nomBateau;

	protected int idBateau;

	private static final String CHEMIN_IMAGES = "images";

	private static Map<Integer, Bateaux> tabBateaux;

	private static Map<Bateaux, Map<String, ImageIcon>> mapBateauxListeIcons;

	private static Map<String, ImageIcon> mapStringIcon;

	public static void initImagesBateaux() {

		mapBateauxListeIcons = new HashMap();

		File dossierImages = new File(CHEMIN_IMAGES);

		int count = 0;

		for(final File fichier:dossierImages.listFiles()) {

			if(fichier.getName().charAt(0) == '.')
				continue;

			File dossierImages2 = new File(CHEMIN_IMAGES+"/"+fichier.getName());

			mapStringIcon = new HashMap();

			for(final File fichier2:dossierImages2.listFiles()) {

				if(fichier2.getName().charAt(0) == '.')
					continue;

				mapStringIcon.put(fichier2.getName().substring(0, fichier2.getName().length() - 4),
						new ImageIcon(CHEMIN_IMAGES+"/"+fichier.getName()+"/"+fichier2.getName()));
			}

			mapBateauxListeIcons.put(tabBateaux.get(count), mapStringIcon);



			count++;

			if (count == 5)
				break;
		}
	}

	public static ImageIcon imageBateau(int idBateau,int position, int partie,int etat){
		ImageIcon imageBateau;
		String chemin="";

		switch (idBateau){
			case CONTRETORPILLEUR:
				chemin+="deux";
				break;
			case SOUSMARIN:
				chemin+="trois1";
				break;
			case TORPILLEUR:
				chemin+="trois2";
				break;
			case CROISEUR:
				chemin+="quatre";
				break;
			case PORTEAVIONS:
				chemin+="cinq";
				break;
		}

		switch (position){
			case HORIZONTAL:
				chemin+="_Horizontal";
				break;
			case VERTICAL:
				chemin+="_Vertical";
				break;
		}

		switch (partie){
			case COMPLET:
				break;
			case PART1:
				chemin+="_part1";
				break;
			case PART2:
				chemin+="_part2";
				break;
			case PART3:
				chemin+="_part3";
				break;
			case PART4:
				chemin+="_part4";
				break;
			case PART5:
				chemin+="_part5";
				break;
		}

		switch (etat){
			case SANSETAT:
				break;
			case FANTOME:
				chemin+="_fantome";
				break;
			case TOUCHE:
				chemin+="_touche";
				break;
		}

		imageBateau = mapBateauxListeIcons.get(tabBateaux.get(idBateau))
				.get(chemin);

		return imageBateau;
	}

	public static void initTabBateaux() {

		tabBateaux = new HashMap();

		tabBateaux.put(CONTRETORPILLEUR, new ContreTorpilleurs());
		tabBateaux.put(SOUSMARIN, new SousMarin());
		tabBateaux.put(TORPILLEUR, new Torpilleur());
		tabBateaux.put(CROISEUR, new Croiseur());
		tabBateaux.put(PORTEAVIONS, new PorteAvions());

	}

	public static HashMap<Integer, Bateaux> getTabBateaux() {
		return (HashMap<Integer, Bateaux>) tabBateaux;
	}

	public int getIdBateau() {
		return idBateau;
	}

	public int getNombreCases() {
		return nombreCases;
	}

	public String getNomBateau() {
		return nomBateau;
	}
}