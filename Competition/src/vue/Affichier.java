package vue;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import controlleur.Controlleur;
import modele.Demande;
import modele.Resultat;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;

public class Affichier extends JFrame 
{
	private Demande demande=new Demande();
	private Resultat resultat=new Resultat();
	
	public Affichier() 
	{	
		this.setSize(500, 500);  
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 500, 500);
		this.add(panel);
		
		JButton btnRequete = new JButton("requete");
		btnRequete.setBounds(10,10,200,50);
		panel.add(btnRequete);
		btnRequete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> chemins=getFiles();
				if(chemins!=null || chemins.size()!=0)
				{
					System.out.println(chemins);
					for(String chemin:chemins)
					{
						String reqNom=chemin.substring(chemin.lastIndexOf("\\")+1, chemin.indexOf("."));
//						Controlleur controlleur=new Controlleur();
//						controlleur.copierEtEnregister(chemin, "G:\\workspace_eclipse\\Competition\\testimage\\"+reqNom+".jpg");
						demande.addRequete(chemin);
					}	
				}						
			}
		});
		
		//JPanel panel_2 = new JPanel();
		//scrollPane.setRowHeaderView(panel_2);
		JButton btnDocument = new JButton("document");
		btnDocument.setBounds(250,10,200,50);
		panel.add(btnDocument);
		btnDocument.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> chemins=getFiles();
				if(chemins!=null|| chemins.size()!=0)
				{
					System.out.println(chemins);
					for(String chemin:chemins)
					{
						String docNom=chemin.substring(chemin.lastIndexOf("\\")+1, chemin.indexOf("."));
//						Controlleur controlleur=new Controlleur();
//						controlleur.copierEtEnregister(chemin, "G:\\workspace_eclipse\\Competition\\testimage\\"+docNom+".jpg");
						
						demande.addDocument(chemin);
					}	
				}
						
			}
		});
		
		JButton btnTraiter = new JButton("traiter");
		btnTraiter.setBounds(10,100,200,50);
		panel.add(btnTraiter);
				
		JButton btnQuitter = new JButton("quitter");
		btnQuitter.setBounds(250,100,200,50);
		panel.add(btnQuitter);
		btnQuitter.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);			
			}
		});	
		
		JTextArea textArea=new JTextArea();
		textArea.setText("----- le processus matching -----\r\n");
		textArea.setBounds(10, 200,450, 100);
		//panelResultat.add(textArea);
		panel.add(textArea);
		
				
		btnTraiter.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {				
				textArea.append("le processus en cours......\r\n");
				textArea.update(textArea.getGraphics());
				Controlleur controlleur=new Controlleur();				
				resultat=controlleur.runAvecInterfaceGraphique(demande,textArea);
			}
		});	
		
		JButton btnResultat=new JButton("ouvrire le resultat document XML");
		btnResultat.setBounds(180, 350, 300, 50);
		panel.add(btnResultat);
		btnResultat.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
			try 
			{
				String[] cmd = {"notepad",resultat.getResChemin()};
					Process ouvrirResultat=Runtime.getRuntime().exec(cmd);
			} 
				catch (IOException e1) {
				e1.printStackTrace();
				}
			}
		});
		
		this.setVisible(true);  
	}
	
	public List<String> getFiles()
	{
		List<String> paths=new ArrayList<String>();
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("choisir le document pour transfert...");
		fc.setApproveButtonText("valider");
		fc.setMultiSelectionEnabled(true);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(this)) 
		{
		    //path=fc.getSelectedFile().getPath();
			File[] files=fc.getSelectedFiles();
			for(File file:files)
			{
				String chemin=file.getPath();
				paths.add(chemin);
			}
		    return paths;
		}
		else
		{
			return null;
		}
	}

}
