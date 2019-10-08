
import java.io.*;
import java.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Jugement {
	
	public static void main(String[]arg) throws IOException, InterruptedException{

		juge(arg[0]);
		
	}
	
	private static void juge(String chemin) throws IOException, InterruptedException {
		File dossier = new File( chemin );
	    File[] listeDesMails = dossier.listFiles();
	    
	    for ( File f : listeDesMails ) {
	    	
	    	if ( f.isFile() ) {
		
	    	if (estspam(f)){
	    		System.out.println("Le mail: "+ f.getName()+ " est un spam");
	    		
	    		 JFrame fenetre = new JFrame();
 			    //Définit un titre pour notre fenêtre
 			    fenetre.setTitle("Un Spam détecté");
 			    //Définit sa taille : 400 pixels de large et 100 pixels de haut
 			    fenetre.setSize(400, 200);
 			    //Nous demandons maintenant à notre objet de se positionner au centre
 			    fenetre.setLocationRelativeTo(null);
 			    //Termine le processus lorsqu'on clique sur la croix rouge
 			    fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
 			    fenetre.setLayout(new BorderLayout());
 			    Panel p = new Panel(new GridLayout(1,2));
 			    
 			    Label question = new Label ("Le mail "+f.getName()+" est un spam : "+"\n"+" Voulez-vous le supprimer?");
 				Button yes= new Button("Oui");
 				yes.addActionListener((ActionListener) new Oui(f));
 				Button non= new Button("Non");
 				non.addActionListener((ActionListener) new Non(fenetre));
 				
 				p.add(yes);p.add(non);
 				fenetre.add(p,BorderLayout.SOUTH);
 				fenetre.add(question,BorderLayout.CENTER);
 				
 				fenetre.setVisible(true);

	    	}
	    	else{System.out.println("Le mail: "+ f.getName()+ " est un ham");
	    	
	    	}	    	
	    								}	    	
	    }
		
	}


	public static boolean estspam(File f) throws IOException, InterruptedException{
		boolean spam = false;
		try {
			BufferedReader br = new BufferedReader(new FileReader("ProbabilitesBamate.json"));
			String codage = br.readLine();
			JSONObject jo = new JSONObject();
			Hashtable <String, float[]>  bd = jo.stringToHashTable(codage);
			ArrayList<String> mots_fichier= new ArrayList<String>();
			mots_fichier=nettoyage(f);
		
			if (tauxSpam(mots_fichier,bd)>=0.5){
				spam = true;
			}
			else{
				spam = false; 
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return spam ;
	}


	private static double tauxSpam(ArrayList<String> mots_fichier,Hashtable<String, float[]> bd) {
		double facteur1 =1;
		double facteur2=1;
		for (String mot: mots_fichier){
			if (bd.containsKey(mot)){
				double p_m = bd.get(mot)[1]/(bd.get(mot)[1]+bd.get(mot)[0]);
				
				if ((p_m != 1) && (p_m !=0)){
			 facteur1=facteur1*p_m;
			 facteur2=facteur2*(1-p_m);
			}		
			}
		}
		return facteur1/(facteur1 + facteur2);
	}


	private static ArrayList<String> nettoyage(File f) throws InterruptedException {
		String body=ouverture(f);
		String[]tout=separe(body);
 	    ArrayList<String> mots = new ArrayList<String>();

 	   for (String mot: tout){
             if (estmot(mot)){
             	mots.add(mot);
             }
 	   }
 	   return mots;
	}

public static String ouverture (File f) throws InterruptedException {
		
		String essai ="";
		if (f.exists ()) {
	try { 
		BufferedReader br = new BufferedReader(new FileReader(f));
		String ligne = br.readLine();
		String balise = ligne;
		String adrsMail="";
		
		
		while (!(ligne.startsWith("From:"))){
			ligne=br.readLine();
		}
		
		int debut = ligne.indexOf("<");
		int fin = ligne.indexOf(">");
		adrsMail += ligne.substring(debut + 1, fin); 		// Dans ligne.substring (x, y) : x inclusif, y exclusif
		
		while (!(ligne.startsWith("Content-Type: text/html"))){
			balise = ligne;
			ligne=br.readLine();
		}
		ligne=br.readLine();
		ligne=br.readLine();
		
		while (!(ligne=br.readLine()).contains(balise)) {
			essai += ligne;				
		}
		br.close();
	}
	
	catch (IOException e) {
	}
}
		
	essai = htmlToTxt (essai);
	essai = accent (essai);
	return essai;
	
	
}


	public static String accent (String mail) {

		Map <String, String> mp =  new HashMap <String, String>();

//DICTIONNAIRE UTF8
mp.put("=C3=80", "À");
mp.put("=C3=82", "Â");	
mp.put("=C3=A0", "à");
mp.put("=C3=A2", "â");

mp.put("=C3=87", "Ç");
mp.put("=C3=A7", "ç");

mp.put("=C3=88", "È");
mp.put("=C3=89", "É");
mp.put("=C3=8A", "Ê");
mp.put("=C3=8B", "Ë");
mp.put("=C3=A8", "è");
mp.put("=C3=A9", "é");
mp.put("=C3=AA", "ê");
mp.put("=C3=AB", "ë");           

mp.put("=C3=94", "Ô");
mp.put("=C3=B4", "ô");

mp.put("=C3=AE", "î");
mp.put("=C3=8E", "Î");
mp.put("=C3=AF", "ï");

mp.put("=C3=B9", "ù");
mp.put("=C3=BB", "û");

mp.put("=E2=80=99", "'");

//DICTIONNAIRE ISO
mp.put("=C0", "À");
mp.put("=C2", "Â");	
mp.put("=E0", "à");
mp.put("=E2", "â");

mp.put("=C7", "Ç");
mp.put("=E7", "ç");

mp.put("=C8", "È");
mp.put("=C9", "É");
mp.put("=CA", "Ê");
mp.put("=CB", "Ë");
mp.put("=E8", "è");
mp.put("=E9", "é");
mp.put("=EA", "ê");
mp.put("=EB", "ë");           

mp.put("=D4", "Ô");
mp.put("=F4", "ô");

mp.put("=CE", "Î");
mp.put("=CE", "î");
mp.put("=EE", "ï");

mp.put ("=F9", "ù");
mp.put("=FB", "û");

// On parcourt tout le "dictionnaire" et on remplace dans la chaine
for (String k : mp.keySet()){
	String v = mp.get(k);
	mail = mail.replaceAll(k,v);
}
mail = mail.replaceAll("=", "");
return mail;
}


public static String htmlToTxt (String mail) {
		
		// Cette fonction va permettre d'enlever toute les balises HTML d'un texte passé en argument
		
		/*
		 * Ces deux booleans vont permettre de garder en mémoire un état du texte
		 * Le boolean "balise" va permettre de savoir si le caractère courant se trouve dans une balise
		 * HTML ou pas. Si oui on ne le garde pas.
		 * Le boolean "espace" va permettre que les mots ne se touchent pas lorsqu'ils sont concaténés et 
		 * qu'ils n'étaient pas de base entre les même balises.
		 */
		
		boolean balise, espace = false;
		String txt = "";
		balise  = mail.substring(0,1).equals ("<");
		
		// On parcourt tout le mail passé en argument
		for (int i = 0; i < mail.length() -2; i++) {
			// Si on est pas dans des balises HTML on peut ajouter la lettre
			if (!balise) {
				
				//On met un espace uniquement après la fin d'une balise HTML pour ne pas que les mots se touchent
				if (espace) {
					txt += " " + mail.substring(i, i+1) ;
					espace = false;
				}
				else {
					txt += mail.substring(i, i+1) ;
				}
				
				
			}
			
			// On vérifie pour le tour suivant si on rentre dans une balise HTML
			 if (mail.substring(i+1, i+2).equals("<")) {
				balise = true;
			// Si on sort d'une balise HTML et qu'on ne rentre pas dans une autre directement 
			// On peut ajouter le caractère suivant en ajoutant un espace avant ce-dernier.	
			} else if (mail.substring(i,  i+1).equals(">") && !mail.substring(i+1, i+2).equals("<")) {
				balise = false;
				espace = true;
			}
			
		}
		
		return txt;
}


		   public static boolean estmot(String mot){
			boolean reponse=true;
			if (mot.length()>25){
				reponse= false;
				}
			
			else { 
				for (int i=0; i<mot.length(); i++){
				char lettre= mot.charAt(i);
				if ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzàÀâÂéÉèÈêÊëÊîÎïÏôÔöÖùÙûÛ'-".indexOf(lettre)==-1){
					reponse= false;}
													}
								}
				return reponse;
			}

		
		public static String[] separe(String text){
			
			return text.split("\\s+");}	

}
