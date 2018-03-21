package pixelssky.managers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileManager {
	public static void SaveFile(String path, ArrayList<String> text) 
	{
		PrintWriter ecri ;
		try
		{
			ecri = new PrintWriter(new FileWriter(path));
			for(String str:text)
			{
				ecri.println(str);	

			}
			ecri.flush();
			ecri.close();
		}
		catch (Exception a)
		{

		}
	}
	public static ArrayList<String> ReadAllText(String fichier) {
		ArrayList<String> txt = new ArrayList<String>();
		
		try{
			InputStream ips=new FileInputStream(fichier); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while ((ligne=br.readLine())!=null){
				txt.add(ligne);
			}
			br.close(); 
		}		
		catch (Exception e){
			//System.out.println(e.toString());
		}
		return txt;
	}
}
