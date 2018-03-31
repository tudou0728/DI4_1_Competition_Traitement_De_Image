package controlleur;

import java.io.File;
import java.io.IOException;

public class Outil 
{
	private ThreadControl threadControl=new ThreadControl();
	
	public void binariser(String chemin) 
	{
		try 
		{
			System.out.println("Binarisation commence");
			String[] cmd = { "E:\\2017miniprojet\\Binarization\\Binarization\\Debug\\Binarization.exe", 
					chemin };
			Process process = Runtime.getRuntime().exec(cmd);
			threadControl.threadControl(process);
			System.out.println("Binarisation finit");
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void segmenter(String chemin) 
	{
		try 
		{
			System.out.println("segmenter commence");
			String[] cmd = { "E:/2017miniprojet/iwordspotting/iwordspotting/Trunk/Dev/SegmentationTool/SegmentationTool/bin/Debug/SegmentationTool.exe", 
					chemin};
			Process process = Runtime.getRuntime().exec(cmd);
			threadControl.threadControl(process);
			System.out.println("segmenter finit");
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void extracer (String cheminComponentImg,String cheminComponentImgBinary,String cheminFeatures,String docNom) 
	{
		try 
		{
			System.out.println("ExtracCaracsTool commence");
			String[] cmd = { "E:\\2017miniprojet\\iwordspotting\\iwordspotting\\Trunk\\Dev\\ExtracCaracsTool\\ExtracCaracsTool\\bin\\Debug\\ExtracCaracsTool.exe",
					cheminComponentImg,cheminComponentImgBinary,cheminFeatures,docNom };
			Process process = Runtime.getRuntime().exec(cmd);
			threadControl.threadControl(process);
			System.out.println("ExtracCaracsTool finit");
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void match(String cheminImg,String cheminRequeteTXT)
	{
		try 
		{
			System.out.println("matching commence");
			String[] cmd = { "E:\\2017miniprojet\\iwordspotting\\iwordspotting\\Trunk\\Dev\\iwordspotting\\Bastien\\MatchingToolBoxWindows\\MatchingToolBox\\Debug\\MatchingToolBox.exe",
					cheminImg,cheminRequeteTXT };
			Process process = Runtime.getRuntime().exec(cmd);
			threadControl.threadControl(process);
			//File resultat=new File(cheminImg+"/Resultatscdp1229.xml");
			//File resultatRename=new File(cheminImg+"/"+reqImage.getReqNom()+".xml");
			System.out.println("matching finit");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
