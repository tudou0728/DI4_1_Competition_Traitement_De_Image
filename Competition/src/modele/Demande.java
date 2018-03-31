package modele;

import java.util.ArrayList;
import java.util.List;

public class Demande 
{
	private List<String> requetes=new ArrayList<String>();
	private List<String> documents=new ArrayList<String>();
	
	public List<String> getRequetes() {
		return requetes;
	}
	public void setRequetes(List<String> requetes) {
		this.requetes = requetes;
	}
	public List<String> getDocuments() {
		return documents;
	}
	public void setDocuments(List<String> documents) {
		this.documents = documents;
	}

	public void addRequete(String requete)
	{
		requetes.add(requete);
	}
	public void addDocument(String document)
	{
		documents.add(document);
	}
}
