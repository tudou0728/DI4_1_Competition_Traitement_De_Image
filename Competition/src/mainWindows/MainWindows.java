package mainWindows;

import javax.naming.spi.DirStateFactory.Result;

import controlleur.Controlleur;
import controlleur.FileManager;
import modele.Demande;
import modele.Resultat;
import vue.Affichier;

public class MainWindows 
{
	public static void main(String[] args) 
	{	
		//Affichier affichier=new Affichier(); run avec interface graphique	
		//run avec san interface graphique
		//arg[0]: le chemin de requete args[1]: le chemin de document
		Demande demande=new Demande();
		FileManager fileManager=new FileManager();
		demande.setRequetes(fileManager.trouvePhoto(args[0]));
		demande.setDocuments(fileManager.trouvePhoto(args[1]));
//		demande.setRequetes(fileManager.trouvePhoto("E:\\2017miniprojet\\test\\request"));
//		demande.setDocuments(fileManager.trouvePhoto("E:\\2017miniprojet\\test\\image5"));
		Controlleur controlleur=new Controlleur();
		Resultat result=controlleur.run(demande);
		System.out.println("le resultat de document XML est: "+result.getResChemin());
	}

}
