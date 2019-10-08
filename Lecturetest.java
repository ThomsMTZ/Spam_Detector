import java.io.*;
import java.util.*;

public class Lecturetest {
	
    
	public static void main(String[] args) throws InterruptedException{
		 JSONObject jo = new JSONObject(); //dictionnaire des mots avec p_huam en premier et p_spam en second 	
		 analyseur(args[0],jo); //modification
	}
	
	
	public static void nombreToProba (JSONObject jo, int nbHam, int nbSpam ) {
		
		for(String k: jo.keySet()){
 	   float phuam= jo.get(k)[0]/nbHam;
 	   float pspam= jo.get(k)[1]/nbSpam;
 	   float[]prob={phuam,pspam};
 	   jo.put(k,prob);
 	  
    }
    try {
		FileWriter fw = new FileWriter(new File ("ProbabilitesBamate.json"));
		fw.write(jo.toString());  
		fw.close();
	} catch (IOException e) {
		System.out.println ("fichier non trouve");
		e.printStackTrace();
	}
	}
	
	public static void analyseur ( String chemin, JSONObject jo ) throws InterruptedException{
		
		int compt_spam=0;
	    int compt_huam=0;
	    
		File dossier = new File( chemin );
	    File[] listeDesDossiers= dossier.listFiles();
	    if ( listeDesDossiers == null ) {
	       System.out.println("C'est un message pertinent");
	    }
	    
	    File dossierHam  = listeDesDossiers[0];
	    File dossierSpam = listeDesDossiers[1];
	    
	   File[] hams = dossierHam.listFiles();
	   File[] spams = dossierSpam.listFiles();
	   
	   for (File ham : hams) {
		  
		  
		   Hashtable<String,float[]> local = new Hashtable<String, float[]>();
		   
		  
	 	   String body = ouverture(ham);
	 	  
	 	   String[] entireBody = separe(body);
	 	   ArrayList<String> tabMots = new ArrayList<String>();
	 	 
	 	   for (String mot : entireBody){
	 		   if (estmot(mot)){
	 			   tabMots.add(mot);
	 			   }
	 		   }
	 	  
	 	  compt_huam++;
		   
	 	  float[]tab={1,0};
		  for (int i=0; i<tabMots.size(); i++){
     	    if (!(local.containsKey(tabMots.get(i)))){
     	     	local.put(tabMots.get(i),tab);}
     	     }
     	     	for(String k:local.keySet()){
     	     	
     	     		if (!(jo.containsKey(k))) {
     	     			jo.put(k,tab);}
     	     		else{
     	     			float v = jo.get(k)[0]+1;
     	     			float[]t={v,jo.get(k)[1]};
     	     			jo.remove(k);
   	     			jo.put(k, t);
                  }
     	     	}
     	     	
	   }
	   
	   
	   for (File spam : spams) {

		   Hashtable<String,float[]> local = new Hashtable<String, float[]>();
	 	   String body = ouverture(spam);
	 	   String[] entireBody = separe(body);
	 	   ArrayList<String> tabMots = new ArrayList<String>();

	 	   for (String mot : entireBody){
	 		   if (estmot(mot)){
	 			   tabMots.add(mot);
	 			   }
	 		   }
	 	   
	 	   
	 	   
	 	  compt_spam++;
		   float[]tab={0,1};
		   
	 	  for (int i=0; i<tabMots.size(); i++){
     	     if (!(local.containsKey(tabMots.get(i)))){
     	     	local.put(tabMots.get(i),tab);}
     	     }
     	     	for(String k:local.keySet()){
     	     	
     	     		if (!(jo.containsKey(k))) {
     	     		jo.put(k,tab);}
     	     		else{
     	     			
     	     			float v = jo.get(k)[1]+1; 
     	     			float[]t={jo.get(k)[0],v}; 
     	     			jo.remove(k);
     	     			jo.put(k, t);
     	     		}
     	     			
                  }
     	   }
	 	   
	   
	 	  nombreToProba (jo, compt_huam, compt_spam);
	   
	 	  
	   }
	
	
	
	public static String ouverture (File f) throws InterruptedException {
		
		StringBuilder sb= new StringBuilder();
		if (f.exists ()) {
	try { 
		
		
		BufferedReader br = new BufferedReader(new FileReader(f));
		String ligne = br.readLine();
		String cle = ligne;
		

		
		while (!(ligne.startsWith("Content-Type: text/html"))){
			cle = ligne;
			ligne=br.readLine();
			
		}
		ligne=br.readLine();
		ligne=br.readLine();
		
		
		while (!(ligne=br.readLine()).contains(cle)) {
			sb.append(ligne);	
			
		}
		
		
		br.close();
		
	}
	
	catch (IOException e) {
		e.printStackTrace();
	}
}
	String chaine = sb.toString();	
	chaine = htmlToTxt (chaine);
	chaine = accent (chaine);
	return chaine;
	
	
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