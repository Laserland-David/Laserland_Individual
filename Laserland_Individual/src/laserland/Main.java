package laserland;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors; 

public class Main {

	public static void main(String[] args) {
		String pathToWatch = "/Users/david/Desktop/LaserballFiles/LaserballStats";
		
		/**try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	        JFileChooser chooser = new JFileChooser();
	        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	        int returnVal = chooser.showOpenDialog(null);
	        if(returnVal == JFileChooser.APPROVE_OPTION) {
	        	pathToWatch = chooser.getSelectedFile().getAbsolutePath();
	        } */
	        
		//		Map<Integer,Integer> mapTeamIndex = new HashMap<Integer, Integer>();
		Map<String,Player> mapPlayer = new HashMap<String, Player>();

		//		Map<Integer,Integer> teamScore = new HashMap<Integer,Integer>(); //Colorcode,Score

		String gameID = null;
		//	Map<String, Integer> playerScoreMap = null;
		BufferedReader reader = null;
		boolean laserball = false;
		File folder = new File(pathToWatch);
		File[] files = folder.listFiles();
		for (File file : files) {
			System.out.println(file.getAbsolutePath());
			try {
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_16LE));
				String line;
				boolean gameEnd = false;
				while((line = reader.readLine()) != null ) {
					if(line != null && !line.equals("")) {
						String start = line.trim();


						if(start.startsWith("1")){
							gameID = defineGameID(line);
							laserball = isLaserball(line);
						}

						//						if(!laserball) {
						//							continue;
						//						}

						if(start.startsWith("3")){
							definePlayers(mapPlayer, line, laserball);
						}

						if(start.startsWith("4") && laserball){
							defineGameEventLaserball(mapPlayer, line);
						}

						if(start.startsWith("6")){
							String[] splitted = line.split("\t");
							String score = splitted[4].trim();
							String playerCode = splitted[2].trim();

							Player player = mapPlayer.get(playerCode);
							if(player != null) {
								//				System.out.println(player.getName() + " - " + score);
								Map<String, Integer> playerScoreMap = player.getScore();
								if(playerScoreMap == null) {
									playerScoreMap = new HashMap<String, Integer>();
								}

								if(!playerScoreMap.containsKey(gameID)) {
									playerScoreMap.put(gameID, Integer.parseInt(score));
									player.setScore(playerScoreMap);
								}
								mapPlayer.put(playerCode, player);

							}

							//defineScore(curLine,mapPlayer,teamScore);
							//System.out.println(start);
						}


						//							if(gameEnd){
						//								//Missionsende
						//								reader.close();
						//								System.out.println(mapPlayer.size());
						//								return;
						//							}


						//					
						//							if(start.startsWith("2")){
						//								defineTeams(mapTeamIndex, curLine);
						//							}
						//
						//							if(start.startsWith("3")){
						//								definePlayers(mapTeamIndex, mapPlayer, curLine,apiClient);
						//							}
						//
						//							if(start.startsWith("4")){
						//								gameEnd = defineGameEvent(curLine,mapPlayer,teamScore,apiClient);
						//							}
						//
						//							if(start.startsWith("5")){
						//								defineScore(curLine,mapPlayer,teamScore);
						//							}
					}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				// Stelle sicher, dass der BufferedReader geschlossen wird
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		FileWriter myWriterNormal;
		FileWriter myWriterLaserball;
		try {
			myWriterLaserball = new FileWriter(pathToWatch + "\\Laserball_Scores.txt");
			myWriterNormal = new FileWriter(pathToWatch + "\\Normal_Scores.txt");
			
			sortAndPrintAndWriteByNormalScore(mapPlayer,myWriterNormal);
			printAndWriteByLaserballScore(mapPlayer, myWriterLaserball); 
		
			myWriterLaserball.close();
			myWriterNormal.close();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}


	private static void printAndWriteByLaserballScore(Map<String, Player> mapPlayer, FileWriter myWriterLaserball)
			throws IOException {
		
		System.out.println("------------ SCORE LASERBALL GAMES ------------");
		myWriterLaserball.write("------------ SCORE LASERBALL GAMES ------------" + "\n");
		
		for (var entry : mapPlayer.entrySet()) {
			//   System.out.println(entry.getKey() + "/" + entry.getValue());
			Player player = entry.getValue();

			if(player.getAnzahlSpieleLaserball() > 0) {

				String name = "Name: " + player.getName();
				String games = "Games: " + player.getAnzahlSpieleLaserball();
				String goals = "Goals: " + player.getGoals();
				String passes = "Passes: " + player.getPasses();
				String steals = "Steals: " + player.getSteals();
				String blocks = "Blocks: " + player.getBlocks();
				String clear = "Clears: " + player.getClear();
				String cutter = "---------------------------------------------------";

				System.out.println(name);
				System.out.println(games);
				System.out.println(goals);
				System.out.println(passes);
				System.out.println(steals);
				System.out.println(blocks);
				System.out.println(clear);
				System.out.println(cutter);


				//myWriter = new FileWriter("C:\\LL_Bautzen\\Programming\\Files\\filename.txt");
				myWriterLaserball.write(name + "\n");
				myWriterLaserball.write(games+ "\n");
				myWriterLaserball.write(goals+ "\n");
				myWriterLaserball.write(passes+ "\n");
				myWriterLaserball.write(steals+ "\n");
				myWriterLaserball.write(blocks+ "\n");
				myWriterLaserball.write(clear+ "\n");
				myWriterLaserball.write(cutter+ "\n");
				

			}

		}
	}


	private static void sortAndPrintAndWriteByNormalScore(Map<String, Player> mapPlayer, FileWriter myWriterNormal) throws IOException {
		System.out.println("------------ SCORE NORMAL GAMES ------------");
		myWriterNormal.write("------------ SCORE NORMAL GAMES ------------" + "\n");
		
		for (var entry : mapPlayer.entrySet()) {
			//   System.out.println(entry.getKey() + "/" + entry.getValue());
			Player player = entry.getValue();
			//System.out.println(player.getScore().size());
			int absoluteScore = 0;
			int countGames = 1;
			for (var entryScore : player.getScore().entrySet()) {
				String fixGameID = entryScore.getKey();
				Integer fixGameScore = entryScore.getValue();
				absoluteScore = absoluteScore + fixGameScore;
				player.setAbsoluteScore(absoluteScore);
				player.setAnzahlSpiele(countGames++);

				//				    	if(player.getName().equals("[Toxic] Fussel"))
				//				    	System.out.println("Name: " + player.getName() + " / SpielID: " + fixGameID + " / Score: " + fixGameScore + " / ScoreGesamt:" +  absoluteScore);
			}

			//System.out.println("Name: " + player.getName() + " | Anzahl Spiele: " + player.getAnzahlSpiele() + " | Score: " + absoluteScore);
			
			if(player.getAnzahlSpiele() > 0) {
				if(player.getAnzahlSpiele() > player.getAnzahlSpieleLaserball() && player.getAnzahlSpieleLaserball() > 0) {
					player.setDurschnitt(absoluteScore / (player.getAnzahlSpiele() - player.getAnzahlSpieleLaserball()));
				}
				else {
					player.setDurschnitt(absoluteScore / player.getAnzahlSpiele());
				}
			
				player.setAnzahlSpiele(player.getAnzahlSpiele() - player.getAnzahlSpieleLaserball());
			}
		
		}

		Comparator<Player> byName = (Player obj1, Player obj2) -> obj2.getDurschnitt().compareTo(obj1.getDurschnitt());

		Map<Object, Object> sortedMap = mapPlayer.entrySet().stream()
				.sorted(Map.Entry.<String, Player>comparingByValue(byName))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		Set set = sortedMap.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry me2 = (Map.Entry) iterator.next();
			if(mapPlayer.get(me2.getKey()).getUniqueID().startsWith("#")) {
				if(mapPlayer.get(me2.getKey()).getDurschnitt() == 0 && mapPlayer.get(me2.getKey()).getAnzahlSpiele() > 0 ) {
					continue;
				}
				
				String name = "Name: " + mapPlayer.get(me2.getKey()).getName();
				String games = "Anzahl Spiele: " + mapPlayer.get(me2.getKey()).getAnzahlSpiele();
				String score = "Absoluter Score " + mapPlayer.get(me2.getKey()).getAbsoluteScore();
				String average = "Durschnitt: " + mapPlayer.get(me2.getKey()).getDurschnitt();
				String cutter = "---------------------------------------------------";
				
				System.out.println(games);
				System.out.print(name);
				System.out.println(score);
				System.out.println(average);
				System.out.println(cutter);
				
				myWriterNormal.write(name + "\n");
				myWriterNormal.write(games+ "\n");
				myWriterNormal.write(score+ "\n");
				myWriterNormal.write(average+ "\n");
				myWriterNormal.write(cutter+ "\n");
				
			}

			// System.out.println("Anzal"mapPlayer.get(me2.getKey()).getAbsoluteScore() + "| Durschnitt: " + (mapPlayer.get(me2.getKey()).getDurschnitt()));
		}
	}

	
	private static boolean gameEventEnd(String curLine) {
		String[] splitted = curLine.split("\t");
		String gameEventID = splitted[2].trim();
		if("0101".equals(gameEventID)){
			return true;
		}
		return false;
	}


	private static void definePlayers(Map<String, Player> mapPlayer,String curLine, boolean laserball) {
		String[] splitted = curLine.split("\t");
		String a1 = splitted[0].trim();
		String split3 = splitted[2].trim(); //Player Game ID
		String ObjectID = splitted[3].trim(); //player,target etc
		String playerName = splitted[4].trim(); //Player name
		String split6 = splitted[5].trim(); //Team Index
		String level = splitted[6].trim(); //lvl
		String category = splitted[7].trim(); //category
		String battlesuiteName = splitted[8].trim(); //Battlesuite Name
		
		Integer teamIndex = Integer.valueOf(split6);
		
		if("player".equals(ObjectID)) {
			Player player = null;
			if(!mapPlayer.containsKey(split3)) {
				 player = new Player();
				 if(laserball) {
					 player.setAnzahlSpieleLaserball(1);
				 }
				 else
					 player.setAnzahlSpiele(1);
			}
			else {
				player = mapPlayer.get(split3);
				if(laserball) {
					player.setAnzahlSpieleLaserball(player.getAnzahlSpieleLaserball()+1);
				}
				else
					player.setAnzahlSpiele(player.getAnzahlSpiele()+1);
			}
			
			player.setUniqueID(split3);
			player.setTeamIndex(teamIndex);
			player.setName(playerName);
			player.setBattlesuiteName(battlesuiteName);
			player.setLevel(Integer.valueOf(level) + 1); //0 basiert --> +1
			mapPlayer.put(split3, player);
		}
		
	}
	
	/**
	 * 	EventPass 1100
	  	EventScore 1101
		EventScoreAt 1102
		EventSteal 1103
		EventBlock 1104
		EventRoundStart 1105
		EventRoundEnd 1106
		EventReceiveBall 1107
		EventShotClock 1108
		EventClear 1109
	 * @param mapPlayer
	 * @param curLine
	 */
	private static void defineGameEventLaserball(Map<String, Player> mapPlayer,String curLine) {
		String[] splitted = curLine.split("\t");
		String a1 = splitted[0].trim();
		String gameEvent = splitted[2].trim(); //Player GameEvent
		String ObjectID = splitted[3].trim(); //player,target etc
		
		if(ObjectID.startsWith("#") || ObjectID.startsWith("@")) {
			Player player = null;
			if(!mapPlayer.containsKey(ObjectID)) {
				 player = new Player();
			}
			else {
				player = mapPlayer.get(ObjectID);
			}
			
			switch (gameEvent) {
			  case "1100": //passes
				  player.setPasses(player.getPasses()+1);
			    break;
			  case "1101":
			    player.setGoals(player.getGoals()+1);
			    break;
			  case "1103":
				player.setSteals(player.getSteals()+1);
			    break;
			  case "1104":
			    player.setBlocks(player.getBlocks()+1);
			    break;
			  case "1109":
			    player.setClear(player.getClear()+1);
			    break;
			}
			
			mapPlayer.put(ObjectID, player);
		}
	}
		
	private static String defineGameID(String curLine) {
		String[] splitted = curLine.split("\t");
		return splitted[3].trim();
	}
	
	private static boolean isLaserball(String curLine) {
		String[] splitted = curLine.split("\t");
		String mode = splitted[2].trim();
		if(mode.contains("Laserball")) {
			return true;
		}
		return false;
	}
	
	
	private static void defineScore(String curLine, Map<String, Player> mapPlayer) {
		String[] splitted = curLine.split("\t");
		String time = splitted[1].trim();
		String playerID = splitted[2].trim(); //Player Game ID
		String old = splitted[3].trim();	//old Score
		String delta = splitted[4].trim(); //delta
		String newScore = splitted[5].trim(); //new Score
		
		
		String ohneNull = Integer.parseInt(time)+"";
		Integer sekunde = Integer.valueOf(ohneNull) / 1000;
		Integer Minute = sekunde / 60;
		long restsecs = sekunde % 60;
//		System.out.println(Minute +":"+ restsecs);
		Player player = mapPlayer.get(playerID);
	}
	
}



