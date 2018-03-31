package controlleur;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import modele.*;

public class ResultatManager 
{
		public ArrayList<ResultatCoord> transferXML(String nomDeFichier, ArrayList<ResultatCoord> list) 
		{
			File f = new File(nomDeFichier + ".xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			try {
				builder = factory.newDocumentBuilder();
				Document doc = builder.parse(f);
				doc.getDocumentElement().normalize();
				NodeList nl = doc.getElementsByTagName("Result");

				for (int i = 0; i < nl.getLength(); i++) {
					//NodeList nl = doc.getElementsByTagName("Result");
					ResultatCoord resultatCoord = new ResultatCoord();
					Node node = nl.item(i);
					Element ele = (Element) node;
					// NodeList childlist = node.getChildNodes();
					if (node.getNodeType() == Element.ELEMENT_NODE) {
						int id = Integer.parseInt(ele.getAttribute("id"));
						resultatCoord.setcId(id);
						Element image = (Element) ele.getElementsByTagName("Image").item(0);
						resultatCoord.setDocChemin(image.getAttribute("path"));
						Element coordinate = (Element) ele.getElementsByTagName("Coordinate").item(0);
						resultatCoord.setCoordinate(coordinate.getAttribute("coor"));
						Element distance = (Element) ele.getElementsByTagName("Distance").item(0);
						float dis = Float.parseFloat(distance.getAttribute("dis"));
						resultatCoord.setDistance(dis);
						resultatCoord.setReqNom(nomDeFichier.substring(nomDeFichier.lastIndexOf("/")+1,nomDeFichier.length()));
						list.add(resultatCoord);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}

		public ArrayList<ResultatCoord> miseEnOrdre(ArrayList<ResultatCoord> list) {
			ResultatCoord temp1 = new ResultatCoord();
			ResultatCoord temp2 = new ResultatCoord();
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < list.size() - i - 1; j++) {
					if (list.get(j).getDistance() > list.get(j + 1).getDistance()) {
						temp1 = list.get(j);
						temp2 = list.get(j + 1);
						list.add(j + 1, temp1);
						list.remove(j);
						list.add(j, temp2);
						list.remove(j + 2);
					}
				}
			}
			for (int i = 0; i < list.size(); i++) 
			{
				list.get(i).setcId(i+1);
			}
			return list;
		}

		
		public void afficherList(ArrayList<ResultatCoord> list) {
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).getReqNom());
				System.out.println(list.get(i).getcId());
				System.out.println(list.get(i).getCoordinate());
				System.out.println(list.get(i).getDistance());

			}
		}

		// public static void creeTXT(String filepath, ArrayList<ResultatCoord> list) {
		// try {
		// PrintWriter out = new PrintWriter(filepath);
		// for (int i = 0; i < list.size(); i++) {
		// out.write("\n");
		// out.write(list.get(i).getReqNom());
		// out.write("\t");
		// out.write(String.valueOf(list.get(i).getcId()));
		// out.write("\t");
		// out.write(list.get(i).getCoordinate());
		// out.write(" ");
		// out.write(String.valueOf(list.get(i).getDistance()));
		// out.write("\n");
		//
		// }
		//
		// out.flush();
		// out.close();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }

		public Resultat creerXML(ArrayList<ResultatCoord> list, String filepath) {
			DocumentBuilder db;
			Resultat resultat=new Resultat();
			try {
				db = getDocumentBuilder();
				Document document = db.newDocument();
				Element root = document.createElement("root");
				document.appendChild(root);
				

				for (int i = 0; i < list.size(); i++) {
					root.appendChild(document.createTextNode("\n\t"));
					Element result = document.createElement(list.get(i).getReqNom());
					Element image = document.createElement("Image");
					Element coordinate = document.createElement("Coordinate");
					Element distance = document.createElement("Distance");
					root.appendChild(result);
					result.appendChild(document.createTextNode("\n\t\t"));
					result.appendChild(image);
					result.appendChild(document.createTextNode("\n\t\t"));
					result.appendChild(coordinate);
					result.appendChild(document.createTextNode("\n\t\t"));
					result.appendChild(distance);
					result.appendChild(document.createTextNode("\n\t"));

					result.setAttribute("id", String.valueOf(list.get(i).getcId()));
					image.setAttribute("path", list.get(i).getDocChemin());
					coordinate.setAttribute("coor", list.get(i).getCoordinate());
					distance.setAttribute("dis", String.valueOf(list.get(i).getDistance()));
				}
				TransformerFactory tff = TransformerFactory.newInstance();
				Transformer tf = tff.newTransformer();
				tf.setOutputProperty(OutputKeys.INDENT, "yes");
				tf.transform(new DOMSource(document), new StreamResult(new File(filepath)));
				
				resultat=new Resultat("ResultatsParOrdre", filepath);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resultat;
		}

		private DocumentBuilder getDocumentBuilder() throws Exception {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			return db;
		}
		
		public List<Map<String, Object>> coordonnees(ArrayList<ResultatCoord> liste)
		{
			List<Map<String, Object>> resultat=new ArrayList<>();
			for(ResultatCoord resultatCoord:liste)
			{
				Map<String, Object> doc=new HashMap<>();
				doc.put("req", resultatCoord.getReqNom());
				doc.put("doc", resultatCoord.getDocChemin());
				String coor=resultatCoord.getCoordinate();
				String[] coordonnees=coor.split(",");
				int[] coordonnee=new int[5];
				coordonnee[0]=Integer.parseInt(coordonnees[0]);
				coordonnee[1]=Integer.parseInt(coordonnees[1]);
				coordonnee[2]=Integer.parseInt(coordonnees[2]);
				coordonnee[3]=Integer.parseInt(coordonnees[3]);
				coordonnee[4]=resultatCoord.getcId();
				doc.put("coor", coordonnee);
				resultat.add(doc);
			}		
			return resultat;
		}
		
		public List<Map<String, Object>> segCoordonnees(String cheminDeFichier,String cheminDeImage)
		{
			File f = new File(cheminDeFichier);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			List<Map<String, Object>> resultat=new ArrayList<>();
			try {
				builder = factory.newDocumentBuilder();
				Document doc = builder.parse(f);
				doc.getDocumentElement().normalize();
				NodeList nl = doc.getElementsByTagName("Element");

				for (int i = 0; i < nl.getLength(); i++) 
				{
					Map<String, Object> map=new HashMap<>();
					Node node = nl.item(i);
					Element ele = (Element) node;
					if (node.getNodeType() == Element.ELEMENT_NODE) {
						String docNom=ele.getAttribute("index");
						map.put("seg", docNom);
						map.put("xml", cheminDeFichier);
						String req=cheminDeImage;
						map.put("image", req);
						Element coordinate = (Element) ele.getElementsByTagName("Coordinate").item(0);
						String resultatCoord=coordinate.getAttribute("coord");
						String[] coordonnees=resultatCoord.split(",");
						int[] coordonnee=new int[4];
						coordonnee[0]=Integer.parseInt(coordonnees[0]);
						coordonnee[1]=Integer.parseInt(coordonnees[1]);
						coordonnee[2]=Integer.parseInt(coordonnees[2]);
						coordonnee[3]=Integer.parseInt(coordonnees[3]);
						map.put("coor", coordonnee);						
						resultat.add(map);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resultat;
		}
}
