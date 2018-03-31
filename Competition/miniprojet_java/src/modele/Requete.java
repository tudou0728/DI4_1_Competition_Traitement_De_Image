package modele;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Requete {
	private String reqNom;
	private String reqChemin;

	public String getReqNom() {
		return reqNom;
	}

	public void setReqNom(String reqNom) {
		this.reqNom = reqNom;
	}

	public String getReqChemin() {
		return reqChemin;
	}

	public void setReqChemin(String reqChemin) {
		this.reqChemin = reqChemin;
	}

	public Requete() {
	}

	public File ecrireTXT() 
	{
		File requeteTXT = new File(reqChemin+"\\txtfile\\"+reqNom+".txt");
		try 
		{
			if (!requeteTXT.exists()) 
			{
				requeteTXT.createNewFile();
			}
			BufferedWriter bWriter=new BufferedWriter(new FileWriter(requeteTXT));
			bWriter.write("image\r\n");
			String rc=reqChemin.replaceAll("/", "\\\\");
			bWriter.write(rc+"\\"+reqNom+".jpg\r\n");
			bWriter.write(rc+"\\"+"features"+"\\"+reqNom+".csv\r\n");
			bWriter.flush();
			bWriter.close();
			System.out.println("-----document txt enregistrer-----");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return requeteTXT;
	}
}
