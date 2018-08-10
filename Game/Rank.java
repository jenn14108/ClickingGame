import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.io.File;

public class Rank{

  HashMap<String,ArrayList<Integer>> history = new HashMap<String,ArrayList<Integer>>();
  TreeMap<String,Integer> highestScore = new TreeMap<String,Integer>();
  TreeMap<String,Integer> userRank = new TreeMap<String,Integer>();
  ArrayList<Integer> userScore = new ArrayList<Integer>();
  JTable table;

  public Rank(){

    loadData();
    sortAndStoreHighest();
    String[] columnNames = {"Rank","Player Name","Score"};
    Object[][] data = getRankings();
    table = new JTable(data,columnNames);
    this.table.setEnabled(false);
  }

  public void loadData(){

    ArrayList<Integer> scores;

    try{

      File rank = new File("rank.txt");
      Scanner scanner = new Scanner(rank);
      while (scanner.hasNextLine()){

        String[] words = scanner.nextLine().split(" +");
        if (!history.containsKey(words[0])){
          scores = new ArrayList<Integer>();
        } else {
          scores = history.get(words[0]);
        }
        scores.add(Integer.parseInt(words[1]));
        history.put(words[0],scores);

      }
    } catch(Exception e) {
      System.out.println("Exception:  "+e);
    }
  }

  public void sortAndStoreHighest(){

    for (Map.Entry<String, ArrayList<Integer>> entry : history.entrySet()) {
      String name = entry.getKey();
      ArrayList<Integer> scores = entry.getValue();
      Collections.sort(scores,Collections.reverseOrder());
      highestScore.put(name,scores.get(0));
      userScore.add(scores.get(0));
    }
  }

  public Object[][] getRankings(){

    Object[][] data = new Object[highestScore.size()][3];
    Collections.sort(userScore,Collections.reverseOrder());
    int rank = 1;
    for (Integer s: userScore){
      for (Map.Entry<String, Integer> entry : highestScore.entrySet()) {
        if (s == entry.getValue()){
          if (!userRank.containsKey(entry.getKey())){
            userRank.put(entry.getKey(),rank);
            rank++;
          }
        }
      }
    }
    Set<String> names = highestScore.keySet();
    for (String name: names){
      rank = userRank.get(name);
      int i = rank - 1;
      data[i][0] = rank;
      data[i][1] = name;
      data[i][2] = highestScore.get(name);
    }
    return data;
  }


}
