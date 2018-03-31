package controlleur;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class FileManager 
{
	public void copierEtEnregister(String origineChemin, String desChemin) {
		Path origine = Paths.get(origineChemin);
		Path des = Paths.get(desChemin);
		if(!verifierDocument(desChemin))
		{
			try {
				Files.copy(origine, des, StandardCopyOption.COPY_ATTRIBUTES);
				System.out.println("-----copier succes -----");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public boolean verifierDocument(String chemin) 
	{
		File file = new File(chemin);
		boolean verifier = false;
		if (file.exists()) 
		{
			verifier = true;
		}
		return verifier;
	}
	
	public boolean verifierTousLesDocuments(String chemin,String nomDeDocument)
	{
		File file = new File(chemin);
		boolean verifier = false;
		if (file.exists()) 
		{
			File[] fileLists=file.listFiles();
			if(fileLists==null || fileLists.length==0)
			{
				System.out.println("dossier null");
			}
			for(File subFile:fileLists)
			{
				if(subFile.isDirectory())
				{
					try 
					{
						String cheminSub=subFile.getCanonicalPath();
						verifierTousLesDocuments(cheminSub, nomDeDocument);
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}		
				}
				else
				{
					if(subFile.getName().substring(0, subFile.getName().indexOf(".")).equals(nomDeDocument))
					{
						System.out.println("le document deja existe");
						verifier=true;
						break;
					}
				}
			}
		}
		else
		{
			System.out.println("le dossier n'existe pas");
		}
		return verifier;
	}

	public void creerDir(String dirChemin) {
		File dir = new File(dirChemin);
		if (dir.exists()) {
			System.out.println("creer le dossier" + dirChemin + " le dossier objectif deja existe");
		}
		if (!dirChemin.endsWith(File.separator)) {
			dirChemin = dirChemin + File.separator;
		}
		// 创建目录
		if (dir.mkdirs()) {
			System.out.println("creer le dossier" + dirChemin + "succes！");
		} else {
			System.out.println("creer le dossier" + dirChemin + "ne pas succes");
		}	
		
	}
	
	public List<String> trouvePhoto(String chemiDeDossier)
	{
		File dir = new File(chemiDeDossier);
		List<String> listes=new ArrayList<String>();
		if (dir.exists()) 
		{
			File[] files = dir.listFiles();
			if(files!=null)
			{
				for(File file:files)
				{
					if(file.isFile() && file.getName().endsWith("jpg"))
					{
						try 
						{
							listes.add(file.getCanonicalPath());
						} 
						catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		else
		{
			System.out.println("ne peut pas trouver ce dossier");
		}
		return listes;
	}
}
