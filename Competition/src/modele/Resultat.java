package modele;

public class Resultat 
{
	private String resNom;
	private  String resChemin;
	
	public String getResNom() {
		return resNom;
	}
	public void setResNom(String resNom) {
		this.resNom = resNom;
	}
	public String getResChemin() {
		return resChemin;
	}
	public void setResChemin(String resChemin) {
		this.resChemin = resChemin;
	}
	
	public Resultat(String resNom,String resChemin)
	{
		this.resChemin=resChemin;
		this.resNom=resNom;
	}
	
	public Resultat()
	{
	}
}
