package controlleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import modele.Demande;
import modele.Document;
import modele.Requete;
import modele.Resultat;
import modele.ResultatCoord;
import verifier.VerifierResultat;
import vue.Affichier;

public class Controlleur {
	private Outil outil = new Outil();
	private String cheminDeQuete="G:/workspace_eclipse/Competition/requete";
	private String cheminDeDocuemnt="G:/workspace_eclipse/Competition/base";
	
	public Resultat runAvecInterfaceGraphique(Demande demande,JTextArea textArea) { 

		FileManager fileManager=new FileManager();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");  //
		String userNom=format.format(System.currentTimeMillis());
		fileManager.creerDir(cheminDeDocuemnt);
		fileManager.creerDir(cheminDeQuete);
		fileManager.creerDir(cheminDeDocuemnt+"/"+userNom);
		fileManager.creerDir(cheminDeQuete+"/"+userNom);
		List<Requete> requetes=new ArrayList<Requete>();
		List<Document> documents=new ArrayList<Document>();
		
		for(String requete:demande.getRequetes())
		{
			String requeteNom=requete.substring(requete.lastIndexOf("\\") + 1, requete.indexOf("."));
			
			Requete requeteImage=new Requete();
			requeteImage.setReqNom(requeteNom+"_0");
			fileManager.creerDir(cheminDeQuete+"/"+userNom+"/"+requeteNom);
			requeteImage.setReqChemin(cheminDeQuete+"/"+userNom+"/"+requeteNom);
			requetes.add(requeteImage);
			
			fileManager.copierEtEnregister(requete,
					cheminDeQuete+"/"+userNom+"/"+requeteNom + "/" + requeteNom + "_0.jpg");
			
		}
		for(String document:demande.getDocuments())
		{
			String documentNom=document.substring(document.lastIndexOf("\\") + 1, document.indexOf("."));
			
			Document documentImage=new Document();
			documentImage.setDocNom(documentNom);
			documentImage.setDocChemin(cheminDeDocuemnt+"/"+userNom);
			documents.add(documentImage);
			
			fileManager.copierEtEnregister(document, cheminDeDocuemnt+"/"+userNom + "/" + documentNom + ".jpg");
		}
		
		traiterDoc(documents);
		int i=0;
		
		ArrayList<ResultatCoord> bigList = new ArrayList<ResultatCoord>();
		for(Requete reqImage:requetes)
		{
			String temp=demande.getRequetes().get(i);
			File file = traiterReq(reqImage.getReqChemin(), temp.substring(temp.lastIndexOf("\\")+1, temp.indexOf(".")), reqImage);			
			try {
				outil.match(cheminDeDocuemnt+"/"+userNom, file.getCanonicalPath());
				File resultat=new File(cheminDeDocuemnt+"/"+userNom+"/Resultatscdp1229.xml");
				File resultatRename=new File(cheminDeDocuemnt+"/"+userNom+"/"+reqImage.getReqNom()+".xml");
				resultat.renameTo(resultatRename);
				
				//开始排序
				ArrayList<ResultatCoord> liste = new ArrayList<ResultatCoord>();
				ResultatManager resultatManager=new ResultatManager();
				resultatManager.transferXML(cheminDeDocuemnt+"/"+userNom+"/"+reqImage.getReqNom(), liste);
				resultatManager.miseEnOrdre(liste);
				bigList.addAll(liste);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			i++;
		}
		ResultatManager resultatManager2=new ResultatManager();
		//resultatManager2.afficherList(bigList);
		Resultat resultat=resultatManager2.creerXML(bigList, cheminDeDocuemnt+"/"+userNom+"/"+"ResultatsParOrdre.xml");
		ResultatManager resultatManager=new ResultatManager();
		List<Map<String, Object>> slistes=resultatManager.coordonnees(bigList);
		VerifierResultat verifierResultat=new VerifierResultat();
		verifierResultat.dessinerPhoto(slistes);
		
		System.out.println("fini");
		textArea.append("----- matching tool box fini -----"); 
		System.out.println("----- matching tool box fini -----");
		return resultat;
	}
	
	public Resultat run(Demande demande) { 
		// creer le dossier
		FileManager fileManager=new FileManager();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");  //
		String userNom=format.format(System.currentTimeMillis());
		fileManager.creerDir(cheminDeDocuemnt);
		fileManager.creerDir(cheminDeQuete);
		fileManager.creerDir(cheminDeDocuemnt+"/"+userNom);
		fileManager.creerDir(cheminDeQuete+"/"+userNom);
		List<Requete> requetes=new ArrayList<Requete>();
		List<Document> documents=new ArrayList<Document>();
		VerifierResultat verifierResultat=new VerifierResultat();
		
		for(String requete:demande.getRequetes())
		{
			String requeteNom=requete.substring(requete.lastIndexOf("\\") + 1, requete.indexOf("."));
			
			Requete requeteImage=new Requete();
			requeteImage.setReqNom(requeteNom+"_0");
			fileManager.creerDir(cheminDeQuete+"/"+userNom+"/"+requeteNom);
			requeteImage.setReqChemin(cheminDeQuete+"/"+userNom+"/"+requeteNom);
			requetes.add(requeteImage);
			
			fileManager.copierEtEnregister(requete,
					cheminDeQuete+"/"+userNom+"/"+requeteNom + "/" + requeteNom + "_0.jpg");
			
		}
		for(String document:demande.getDocuments())
		{
			String documentNom=document.substring(document.lastIndexOf("\\") + 1, document.indexOf("."));
			
			Document documentImage=new Document();
			documentImage.setDocNom(documentNom);
			documentImage.setDocChemin(cheminDeDocuemnt+"/"+userNom);
			documents.add(documentImage);
			
			fileManager.copierEtEnregister(document, cheminDeDocuemnt+"/"+userNom + "/" + documentNom + ".jpg");
		}
		
		traiterDoc(documents);
		int i=0;
		
		ArrayList<ResultatCoord> bigList = new ArrayList<ResultatCoord>();
		for(Requete reqImage:requetes)
		{
			String temp=demande.getRequetes().get(i);
			File file = traiterReq(reqImage.getReqChemin(), temp.substring(temp.lastIndexOf("\\")+1, temp.indexOf(".")), reqImage);			
			try {
				outil.match(cheminDeDocuemnt+"/"+userNom, file.getCanonicalPath());
				File resultat=new File(cheminDeDocuemnt+"/"+userNom+"/Resultatscdp1229.xml");
				File resultatRename=new File(cheminDeDocuemnt+"/"+userNom+"/"+reqImage.getReqNom()+".xml");
				resultat.renameTo(resultatRename);
				
				//开始排序
				ArrayList<ResultatCoord> liste = new ArrayList<ResultatCoord>();
				ResultatManager resultatManager=new ResultatManager();
				resultatManager.transferXML(cheminDeDocuemnt+"/"+userNom+"/"+reqImage.getReqNom(), liste);
				resultatManager.miseEnOrdre(liste);
				bigList.addAll(liste);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			i++;
		}
		ResultatManager resultatManager2=new ResultatManager();
		//resultatManager2.afficherList(bigList);
		Resultat resultat=resultatManager2.creerXML(bigList, cheminDeDocuemnt+"/"+userNom+"/"+"ResultatsParOrdre.xml");
		ResultatManager resultatManager=new ResultatManager();
		List<Map<String, Object>> slistes=resultatManager.coordonnees(bigList);
		verifierResultat.dessinerPhoto(slistes);
		
		System.out.println("fini");
		System.out.println("----- matching tool box fini -----");
		return resultat;
	}

	

	public void traiterDoc(List<Document> list) {
		outil.segmenter(list.get(0).getDocChemin());
		VerifierResultat verifierResultat=new VerifierResultat();
		ResultatManager resultatManager=new ResultatManager();
		
		
		outil.binariser(list.get(0).getDocChemin() + "/Training" + "/componentImg");

		for (int i = 0; i < list.size(); i++) {
			outil.extracer(list.get(i).getDocChemin() + "/Training/componentImg/",
					list.get(i).getDocChemin() + "/Training/componentImg/Binary/",
					list.get(i).getDocChemin() + "/Training" + "/features", list.get(i).getDocNom());
			String imageNom=list.get(i).getDocNom();
			List<Map<String, Object>> slistes=resultatManager.segCoordonnees(list.get(i).getDocChemin() + "/Training" + "/Coordonnees"+"/"+imageNom+"/"+imageNom+".xml", list.get(i).getDocChemin()+"/"+imageNom+".jpg");
			verifierResultat.segDessinerPhoto(slistes);
		}
	}

	public File traiterReq(String chemin, String reqNom, Requete requete) {
		FileManager fileManager=new FileManager();
		fileManager.creerDir(requete.getReqChemin() + "/" + "txtfile");
		//if(!verifierDocument(chemin+"/Binary/"+requete.getReqNom()+".jpg"))
		//{
			outil.binariser(chemin);
			fileManager.creerDir(chemin + "/features");
			outil.extracer(chemin + "/", chemin + "/Binary/", chemin + "/features", reqNom);
		//}		
		return requete.ecrireTXT();
	}
	
	public String getCheminDeDocuemnt() {
		return cheminDeDocuemnt;
	}

	public void setCheminDeDocuemnt(String cheminDeDocuemnt) {
		this.cheminDeDocuemnt = cheminDeDocuemnt;
	}

	public String getCheminDeQuete() {
		return cheminDeQuete;
	}

	public void setCheminDeQuete(String cheminDeQuete) {
		this.cheminDeQuete = cheminDeQuete;
	}
	
}
