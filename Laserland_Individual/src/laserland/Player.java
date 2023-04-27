package laserland;

import java.util.HashMap;
import java.util.Map;

public class Player {

	private String uniqueID;
	private String name;
	private int level;
	private int teamIndex;
	private int colorCode;
	private String battlesuiteName;
	private Integer absoluteScore;
	private int anzahlSpiele;
	private Integer durschnitt;


	//Laserball
	private int goals;

	private int passes;
	private int steals;
	private int blocks;
	private int clear;
	private int anzahlSpieleLaserball;
	private int assists;
	double ballHoldingInMs;
	

	private Map<String,Integer> score; //= new HashMap<String, Integer>();
	

	public Player(){
		//leer
	}
	
  	public int getAssists() {
		return assists;
	}

	public void setAssists(int assists) {
		this.assists = assists;
	}
	
	public int getAnzahlSpieleLaserball() {
		return anzahlSpieleLaserball;
	}

	public void setAnzahlSpieleLaserball(int anzahlSpieleLaserball) {
		this.anzahlSpieleLaserball = anzahlSpieleLaserball;
	}
	
	public Integer getDurschnitt() {
		return durschnitt;
	}

	public void setDurschnitt(Integer durschnitt) {
		this.durschnitt = durschnitt;
	}
	
	public int getAnzahlSpiele() {
		return anzahlSpiele;
	}

	public void setAnzahlSpiele(int anzahlSpiele) {
		this.anzahlSpiele = anzahlSpiele;
	}
	
	public Map<String, Integer> getScore() {
		return score;
	}

	public Integer getAbsoluteScore() {
		return absoluteScore;
	}

	public void setAbsoluteScore(Integer absoluteScore) {
		this.absoluteScore = absoluteScore;
	}

	public void setScore(Map<String, Integer> score) {
		this.score = score;
	}
	
	public String getUniqueID() {
		return uniqueID;
	}
	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getTeamIndex() {
		return teamIndex;
	}
	public void setTeamIndex(int teamIndex) {
		this.teamIndex = teamIndex;
	}
	public int getColorCode() {
		return colorCode;
	}
	public void setColorCode(int colorCode) {
		this.colorCode = colorCode;
	}


	public String getBattlesuiteName() {
		return battlesuiteName;
	}


	public void setBattlesuiteName(String battlesuiteName) {
		this.battlesuiteName = battlesuiteName;
	}

	public int getGoals() {
		return goals;
	}

	public void setGoals(int goals) {
		this.goals = goals;
	}

	public int getPasses() {
		return passes;
	}

	public void setPasses(int passes) {
		this.passes = passes;
	}

	public int getSteals() {
		return steals;
	}

	public void setSteals(int steals) {
		this.steals = steals;
	}

	public int getBlocks() {
		return blocks;
	}

	public void setBlocks(int blocks) {
		this.blocks = blocks;
	}

	public int getClear() {
		return clear;
	}

	public void setClear(int clear) {
		this.clear = clear;
	}
	
	public double getBallHoldingInMs() {
		return ballHoldingInMs;
	}

	public void setBallHoldingInMs(double ballHoldingInMs) {
		this.ballHoldingInMs = ballHoldingInMs;
	}
}