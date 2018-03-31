package verifier;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class VerifierResultat 
{
	public void dessinerPhoto(List<Map<String, Object>> coordonnes)
	{
		for(int i=0;i<coordonnes.size();i++)
		{
			try {
				String reqChemin=(String)coordonnes.get(i).get("req");
				String docChemin=(String)coordonnes.get(i).get("doc");
				int[] rectangle=(int[]) coordonnes.get(i).get("coor");
				InputStream in = new FileInputStream(docChemin); //"E:\\2017miniprojet\\test\\CarnetPlaniol-42XIII.jpg"
				String docNom=docChemin.substring(docChemin.lastIndexOf("/")+1, docChemin.indexOf("."));
				String reqNom=reqChemin.substring(reqChemin.lastIndexOf("\\")+1, reqChemin.length());
				BufferedImage image;
				try {
					image = ImageIO.read(new File(docChemin));
					 Graphics g = image.getGraphics();
				        g.setColor(Color.RED);	        
				        Graphics2D graphics2d=(Graphics2D)g;
				        graphics2d.setStroke(new BasicStroke(10f));
				        g.drawRect(rectangle[0],rectangle[1],rectangle[3],rectangle[2]);
				        String string=docChemin.substring(0, docChemin.lastIndexOf("/")+1);
				        //String nouveau="E:\\2017miniprojet\\test\\image3"+"sss"+rectangle[4]+".jpg";
				        String nouveau=docChemin.substring(0, docChemin.lastIndexOf("/")+1)+"resultat"+"/"+reqNom+"---"+docNom+"---"+rectangle[4]+".jpg";
				        FileOutputStream out = new FileOutputStream(nouveau);
				        try {
							ImageIO.write(image, "jpg", out);
						} catch (IOException e) {
							e.printStackTrace();
						}
				} catch (IOException e1) {
					e1.printStackTrace();
				}	       
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}	   
		}
	}
	
	public void segDessinerPhoto(List<Map<String, Object>> coordonnes)
	{
		for(int i=0;i<coordonnes.size();i++)
		{
			try {
				String imageChemin=(String) coordonnes.get(i).get("image");
				String seg=(String) coordonnes.get(i).get("seg");
				int[] rectangle=(int[]) coordonnes.get(i).get("coor");
				InputStream in = new FileInputStream(imageChemin); //"E:\\2017miniprojet\\test\\CarnetPlaniol-42XIII.jpg"
				BufferedImage image;
				try {
					image = ImageIO.read(new File(imageChemin));
					 Graphics g = image.getGraphics();
				        g.setColor(Color.RED);		        
				        Graphics2D graphics2d=(Graphics2D)g;
				        graphics2d.setStroke(new BasicStroke(10f));
				        g.drawRect(rectangle[0],rectangle[1],rectangle[3],rectangle[2]);
				        String xmlChemin=(String) coordonnes.get(i).get("xml");
				        String nouveau=xmlChemin.substring(0, xmlChemin.lastIndexOf("/")+1)+seg+".jpg";
				        FileOutputStream out = new FileOutputStream(nouveau);
				        try {
							ImageIO.write(image, "jpg", out);
						} catch (IOException e) {
							e.printStackTrace();
						}
				} catch (IOException e1) {
					e1.printStackTrace();
				}	       
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}	   
		}
	}
}
