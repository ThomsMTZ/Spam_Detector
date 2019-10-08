import java.util.*;

public class JSONObject extends Hashtable<String, float[]> {
	private static final long serialVersionUID = 1L;
	
			private String jos;
			
			public Hashtable <String, float[]> stringToHashTable (String jos) {
				
				int cpt = 0;
				char cc= jos.charAt(cpt);
				
				while (cc !='}'){
				String cle = "";
				String val1= "", val2= "";
				
				// On passe accolade
				if (!jos.startsWith("{")) {
					System.out.println ("ne commence pas par accolade");
				}
				
				cpt++;
				cc = jos.charAt(cpt);
				
				//On passe les espaces eventuels
						while (cc == ' ') {
							cpt++;
							cc = jos.charAt(cpt);
						}
				
				

				// premier guillemet
				if (jos.charAt(cpt)!= '"' ) {
					System.out.println ("Clé pas sous forme de string");
				}
				
				cpt++;
				cc = jos.charAt(cpt);	
				
				//On passe les espaces eventuels
				while (cc == ' ') {
					cpt++;
					cc = jos.charAt(cpt);
				}
						
				
				// deuxieme guillemet
				while (cc != '"') {
					cle += cc;
					cpt ++;
					cc = jos.charAt(cpt);
				}
				
				cpt++;
				cc = jos.charAt(cpt);
				
				//On passe les espaces eventuels
						while (cc == ' ') {
							cpt++;
							cc = jos.charAt(cpt);
						}
						
				// :
				cpt++;
				cc = jos.charAt(cpt);
				
				//On passe les espaces eventuels
						while (cc == ' ') {
							cpt++;
							cc = jos.charAt(cpt);
						}
				
				// [
				if (cc != '[') {
					System.out.println("tab commence pas par crochet");
				}
			
				cpt++;
				cc = jos.charAt(cpt);
				
				//On passe les espaces eventuels
				while (cc == ' ') {
					cpt++;
					cc = jos.charAt(cpt);
				}
						
				
				while (cc != ',') {
					val1 += cc;
					cpt++;
					cc = jos.charAt(cpt);
				}
				
				cpt++;
				cc = jos.charAt(cpt);
				
				//On passe les espaces eventuels
				while (cc == ' ') {
					cpt++;
					cc = jos.charAt(cpt);
				}
						
				//]
				while (cc != ']') {
					val2 += cc;
					cpt++;
					cc = jos.charAt(cpt);
				}

				
				cpt++;
				cc = jos.charAt(cpt);
				
				//On passe les espaces eventuels
				while (cc == ' ') {
					cpt++;
					cc = jos.charAt(cpt);
				}
						
				// , 
				
				float[] tabP = {Float.parseFloat(val1), Float.parseFloat(val2)};
				put (cle, tabP);
				
				
				}
				return this;
			}
			
			
			//transforme l'objet json en string pour l'écrire dans un fichier
			public String toString() {
				StringBuilder chaine = new StringBuilder();
				Set<String> cles = keySet();
				Iterator<String> it = cles.iterator();
				chaine.append ("{");
				
				while (it.hasNext()) {
					String cle = it.next().toString();
					float[] tab = get(cle);
					chaine.append ("\""  + cle + "\" : " + "["  + tab[0] + ", " + tab[1] + "]");
					
					
					if (it.hasNext()) {
						chaine.append (",");
					}
					
					
				}
				
				
				chaine.append ("}");
				jos = chaine.toString();
				return jos;	
			}
			
		}