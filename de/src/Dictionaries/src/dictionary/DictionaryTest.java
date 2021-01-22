/*
 * Test der verscheidenen Dictionary-Implementierungen
 *
 * O. Bittel
 * 28.05.2020
 */
package Dictionaries.src.dictionary;

import dictionary.BinaryTreeDictionary;
import dictionary.Dictionary;
import dictionary.HashDictionary;
import dictionary.SortedArrayDictionary;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Static test methods for different Dictionary implementations.
 * @author oliverbittel
 */
public class DictionaryTest {

	/**
	 * @param args not used.
	 */
	public static void main(String[] args) throws Exception {
		testSortedArrayDictionary();		//various tests
		//testHashDictionary();
		//testBinaryTreeDictionary();

		//tUI();							//TextUI
		//create HashDictionary
		//create BinaryTreeDictionary
		//read [8000]
		//read []

	}

	private static void tUI() throws Exception {
		dictionary.Dictionary<String, String> dict = new dictionary.SortedArrayDictionary<>();

		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader reader;
		final JFileChooser fc = new JFileChooser();

		if (console == null) {
			throw new Exception("Input reader broken");
		}
		System.out.println("Start TUI");
		while (true) {
			List<String> list = scan(console.readLine().toUpperCase());

			if (list.size() > 0) {
		    ///////////////////////////////////////////////////////////////////////////////////////////
				if (list.get(0).equals("CREATE")) {
					if (list.size() == 2) {
						if (list.get(1).equals("HASHDICTIONARY")) {
							dict = new dictionary.HashDictionary<>();
							System.out.println("neues HashDictionary geladen");
							continue;
						} else if (list.get(1).equals("BINARYTREEDICTIONARY")) {
							dict = new dictionary.BinaryTreeDictionary<>();
							System.out.println("neues BinaryTreeDictionary geladen");
							continue;
						} else {
							dict = new dictionary.SortedArrayDictionary<>();
							System.out.println("neues SortedArrayDictionary geladen");
						}
					}else{
						dict = new dictionary.SortedArrayDictionary<>();
						System.out.println("neues SortedArrayDictionary geladen");
					}
			///////////////////////////////////////////////////////////////////////////////////////////
				} else if (list.get(0).equals("READ")) {
					if (list.size() == 2) {
						fc.showOpenDialog(new JFrame());
						File file = fc.getSelectedFile();
						if (list.get(1).matches(".*\\d.*") && list.get(1).contains("[") && list.get(1).contains("]")) {
							int n = Integer.parseInt(list.get(1).substring(1,list.get(1).length()-1));
							if (n >= 0) {
								int countn = 0;
								try {
									reader = new BufferedReader(new FileReader(file));
									String line = reader.readLine();
										long start = System.nanoTime();
									while (line != null && countn < n) {
										countn++;
										dict.insert(scan(line).get(0), scan(line).get(1));
										line = reader.readLine();
									}
										long end = System.nanoTime();
									reader.close();
									System.out.println("File erfolgreich gelesen bis Zeile " + n + " In " + (double)(end-start)/1.0e06 + " msec");
								} catch (Exception e) {
									System.out.println("File Error");
								}
							} else {
								System.out.println("Falsche Eingabe n kleiner 0");
								continue;
							}
						} else if(list.get(1).contains("[]")) {
							try {
								reader = new BufferedReader(new FileReader(file));
								String line = reader.readLine();
									long start = System.nanoTime();
								while (line != null) {
									dict.insert(scan(line).get(0), scan(line).get(1));
									line = reader.readLine();
								}
									long end = System.nanoTime();
								reader.close();
								System.out.println("kompletter File erfolgreich gelesen In " + (double)(end-start)/1.0e06 + " msec");
							} catch (Exception e) {
								System.out.println("File Error");
							}
						}else{
							System.out.println("Falsche Eingabe keine [ ]");
						}
					} else {
						System.out.println("Falsche Eingabe zu viele oder zu wenige Parameter, benötigt 2 Parameter");
					}
				}
		///////////////////////////////////////////////////////////////////////////////////////////
				else if (list.get(0).equals("P")) {
					if(dict.size() > 0){
						for (dictionary.Dictionary.Entry<String, String> e : dict) {
							System.out.println(e.getKey() + ": " + e.getValue());
						}
						System.out.println("-----------------------------------");
					}else{
						System.out.println("Aktuell befinden sich keine Wörter im Wörterbuch");
					}

				}
		///////////////////////////////////////////////////////////////////////////////////////////
				else if (list.get(0).equals("S")) {
					if (list.size() == 2) {
						if (list.get(1).length() > 0) {

								long start = System.nanoTime();
							String temp = dict.search(list.get(1).toLowerCase());
								long end = System.nanoTime();
							if(temp != null){
								System.out.println(temp + "gefunden in : " + (double)(end-start)/1.0e06 + " msec");
							}else{
								System.out.println("Wort " + list.get(1).toLowerCase() + " nicht gefunden" + "benötigte Zeit: " + (double)(end-start)/1.0e06 + " msec");
							}
						} else {
							System.out.println("Falsche Eingabe kein wort übergeben");
						}
					} else {
						System.out.println("Falsche Eingabe zu viele oder zu wenige Parameter, benötigt 1 Parameter");
					}
				}
		///////////////////////////////////////////////////////////////////////////////////////////
				else if (list.get(0).equals("I")) {
					if (list.size() == 3) {
						if (list.get(1).length() > 0 && list.get(2).length() > 0) {
							dict.insert(list.get(1).toLowerCase(), list.get(2).toLowerCase());
							System.out.println("erfolgreich eingefügt");
						} else {
							System.out.println("Falsche Eingabe Wörter müssen mindestens ein buchstaben beinhalten");
						}
					} else {
						System.out.println("Falsche Eingabe zu viele oder zu wenige Parameter, benötigt 2 Parameter");
					}
		///////////////////////////////////////////////////////////////////////////////////////////
				} else if (list.get(0).equals("R")) {
					if (list.size() == 2) {
						dict.remove(list.get(1).toLowerCase());
						System.out.println("erfolgreich gelöscht");
					} else {
						System.out.println("Falsche Eingabe zu viele oder zu wenige Parameter, benötigt 2 Parameter");
					}
		///////////////////////////////////////////////////////////////////////////////////////////
				} else if (list.get(0).equals("EXIT")) {
					System.out.println("Goodbey!");
					console.close();
					System.exit(0);
				} else {
					System.out.println("Falsche eingabe!");
				}
			}
		}

	}




	private static List<String> scan(String input) {
		Scanner scanner = new Scanner(input);
		List<String> list = new ArrayList<String>();
		while(scanner.hasNext()){
			list.add(scanner.next());
		}
		return list;
	}

	private static void testSortedArrayDictionary() {
		dictionary.Dictionary<String, String> dict = new SortedArrayDictionary<>();
		testDict(dict);
	}
	
	private static void testHashDictionary() {
		dictionary.Dictionary<String, String> dict = new HashDictionary<>(3);
		testDict(dict);
	}
	
	private static void testBinaryTreeDictionary() {
		dictionary.Dictionary<String, String> dict = new dictionary.BinaryTreeDictionary<>();
		testDict(dict);
        
        // Test für BinaryTreeDictionary mit prettyPrint 
        // (siehe Aufgabe 10; Programmiertechnik 2).
        // Pruefen Sie die Ausgabe von prettyPrint auf Papier nach.
        dictionary.BinaryTreeDictionary<Integer, Integer> btd = new BinaryTreeDictionary<>();
        btd.insert(10, 0);
        btd.insert(20, 0);
        btd.insert(50, 0);
        System.out.println("insert:");
        btd.prettyPrint();

        btd.insert(40, 0);
        btd.insert(30, 0);
        System.out.println("insert:");
        btd.prettyPrint();

        btd.insert(21, 0);
        System.out.println("insert:");
        btd.prettyPrint();

        btd.insert(35, 0);
        btd.insert(45, 0);
        System.out.println("insert:");
        btd.prettyPrint();

        System.out.println("For Each Loop:");
        for (dictionary.Dictionary.Entry<Integer, Integer> e : btd) {
            System.out.println(e.getKey() + ": " + e.getValue());
        }

        btd.remove(30);
        System.out.println("remove:");
        btd.prettyPrint();

        btd.remove(35);
        btd.remove(40);
        btd.remove(45);
        System.out.println("remove:");
        btd.prettyPrint();
		
		btd.remove(50);
        System.out.println("remove:");
        btd.prettyPrint();
    }
	
	private static void testDict(dictionary.Dictionary<String, String> dict) {

		System.out.println("===== New Test Case ========================");
		System.out.println("test " + dict.getClass());
		System.out.println(dict.insert("gehen", "go") == null);		// true
		String s = new String("gehen");
		System.out.println(dict.search(s) != null);					// true
		System.out.println(dict.search(s).equals("go"));			// true
		System.out.println(dict.insert(s, "walk").equals("go"));	// true
		System.out.println(dict.search("gehen").equals("walk"));	// true
		System.out.println(dict.remove("gehen").equals("walk"));	// true
		System.out.println(dict.remove("gehen")  == null); // true
		dict.insert("starten", "start");
		dict.insert("gehen", "go");
		dict.insert("schreiben", "write");
		dict.insert("reden", "say");
		dict.insert("arbeiten", "work");
		dict.insert("lesen", "read");
		dict.insert("singen", "sing");
		dict.insert("schwimmen", "swim");
		dict.insert("rennen", "run");
		dict.insert("beten", "pray");
		dict.insert("tanzen", "dance");
		dict.insert("schreien", "cry");
		dict.insert("tauchen", "dive");
		dict.insert("fahren", "drive");
		dict.insert("spielen", "play");
		dict.insert("planen", "plan");
		dict.insert("diskutieren", "discuss");
		System.out.println(dict.size());


		for (Dictionary.Entry<String, String> e : dict) {
			System.out.println(e.getKey() + ": " + e.getValue() + "\t search: " + dict.search(e.getKey()));
		}
	}

}
