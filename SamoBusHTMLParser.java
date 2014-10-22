import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class SamoBusHTMLParser {

	public static void main(String[] args) throws IOException {
		boolean inTag = false;
		boolean write = false;
		BufferedReader reader = null;
		BufferedWriter writer = null;
		String line;
		String cleanLine;
		String[] charsInLine;
		
		try {
			reader = new BufferedReader(new FileReader("vozniRedHTML.txt"));
			writer = new BufferedWriter(new FileWriter("vozniRedOutput.txt"));
			//writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("vozniRedOutput.txt"), "UTF-8"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("pre While");
		try {
			while((line = reader.readLine()) != null){	// Mice nasa slova, lakse je bez njih
				line = line.replaceAll("š", "s").replaceAll("è", "c").replaceAll("æ", "c")
						.replaceAll("ž", "c").replaceAll("ð", "d").replaceAll("Š", "S").replaceAll("È", "C")
						.replaceAll("Æ", "C").replaceAll("Ž", "C").replaceAll("Ð", "D");
				if(line.contains("prometu")){			// MIce linije tipa "U prometu od *datum*
					continue;
				}
				if(! write){
					if(line.contains("ZIMSKI VOZNI RED") || line.contains("LJETNI VOZNI RED")){		// Krece pisat tekar kad dodje do zimskog voznog reda
						write = true;
					}
					else{
						continue;
					}
				}
				
				if(line.contains("162")){		// da ne zapisuje medugradske linije
					write = false;
					continue;
				}
				charsInLine = line.split("");
				cleanLine = "";
				for(String character : charsInLine){
					if(inTag){									// inTag oznacava da smo unutar HTML taga i to ne treba prepisivat
						if(character.equals(">")){				// kraj taga
							inTag = false;
						}
					}
					else{
						if(character.equals("<")){				// Pocetak taga
							inTag = true;
						}
						else{
							cleanLine = cleanLine + character;	// gradi cleanLine -> ono izvan taga u liniji
						}
					}
				}
				// Zapisivanje u novu datoteku ako linija ima nesto u sebi i razlicita je od "&nbsp;"
				if(! cleanLine.equals("") && !cleanLine.equals("&nbsp;") && !cleanLine.equals(" ")){
					cleanLine = cleanLine.replaceAll("&#8211", "");
					//Zbog loseg formatiranja na stranici, negdje je u html-u u 1 liniji
					//vise polazaka... npt kod linije KLAKE
					//Pa treba to odvojiti.. vidi javaDoc fce za primjer
					if(findNumOfOccurences("Polasci", cleanLine) > 1){
						String[] subPolasci = cleanLine.split("Polasci");
						for(String polazak : subPolasci){
							writer.write("Polasci" + polazak + '\n');
						}						
					}
					//A ako je sve ok onda samo zapisemo
					else{
						writer.write(cleanLine + '\n');
					}					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("post while");
		reader.close();
		reader = null;
		writer.close();
		writer = null;
		
		reader = new BufferedReader(new FileReader("vozniRedOutput.txt"));
		String doba = "GRESKA";
		String path = "OutputFiles/";
		int brojLinije;
		while((line = reader.readLine()) != null){
			if(line.contains("ZIMSKI VOZNI RED")){
				doba = "zima";
			}
			if(line.contains("LJETNI VOZNI RED")){
				doba = "ljeto";
			}
			
			try{
				brojLinije = Integer.parseInt(line.substring(0, 3));		// Pokusam dobit broj linije s pocetka stringa
				String fileName = path + brojLinije + doba + ".txt";
				System.out.println("pisem file -> " + path + brojLinije + doba + ".txt");
				if(writer == null){											// Samo za pocetak kad je null
					writer = new BufferedWriter(new FileWriter(fileName));
				}
				else{														// Dosli smo do druge linije,
					writer.close();											//zatvaramo sari file i zapoinjemo novi							
					writer = new BufferedWriter(new FileWriter(fileName));
				}				
			}																
			catch(Exception e){					// Ne pocinje sa brojem i onda znaci da imamo samo podatke koje upisujemo
				try{
					writer.write(line + '\n');		
					continue;
				}
				catch(Exception e1){			// Za kad je writer null
					continue;
				}
			}				
		}
		try{
			writer.close();
			writer = null;
		}
		catch(Exception e){
			System.out.println("Error closing writer");
		}
		
	}
	
	/**
	 * Bug kod KLAKE 
	 * "Polasci  iz  SAMOBORA:8.30  11.00  16.30  19.30Polasci  iz  SAMOBORA:  7.20"
	 * @param target
	 * @param source
	 * @return
	 */
	private static int findNumOfOccurences(String target, String source){
		int lastIndex = 0;
		int count = 0;

		while(lastIndex != -1){

		       lastIndex = source.indexOf(target,lastIndex);

		       if( lastIndex != -1){
		             count++;
		             lastIndex+=target.length();
		      }
		}		
		return count;
	}

}
