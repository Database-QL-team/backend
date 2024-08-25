package ggyuel.ggyuup.DataCrawling;

import ggyuel.ggyuup.global.DBConnection;

import java.io.BufferedReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

@Service
public class DataCrawlingService {
    static String address = "jdbc:mysql://localhost/DB2024Team01";
    static String userid = "root";
    static String passwd = "cs10120";

    private static ArrayList<String> users = new ArrayList<>();
    private static boolean[] solved = new boolean[35000];

    @Scheduled(cron = "0 30 23 * * ?")
    public static void RefreshAllData() throws InterruptedException, IOException {
        long startTime = System.nanoTime();
        crawlSchool();
        long endTime = System.nanoTime();
        System.out.println("전체 학생 목록을 가져오는데 걸린 시간(초): "+(endTime-startTime)/1000000000.0);
        startTime = System.nanoTime();
        for(String user : users) {
            //Thread.sleep(1000);
            crawlUser(user);
        }
        endTime = System.nanoTime();
        System.out.println("학생들이 이미 푼 문제들을 스캔하는데 걸린 시간(초): "+(endTime-startTime)/1000000000.0);
        startTime = System.nanoTime();
        crawlProblems();
        endTime = System.nanoTime();
        System.out.println("안 푼 문제 목록을 가져오는데 걸린 시간(초): "+(endTime-startTime)/1000000000.0);
    }

    public static void crawlProblems() {
        try(
                Connection DBconn = DriverManager.getConnection(address, userid, passwd);
                PreparedStatement pstmtPro = DBconn.prepareStatement("INSERT INTO DB2024_Problems(pid, ptitle, tier, solvednum, link) VALUES (?,?,?,?,?)");
                PreparedStatement pstmtAlgo = DBconn.prepareStatement("INSERT INTO DB2024_Algorithms(pid, tag) VALUES (?,?)");
                Statement stmt = DBconn.createStatement();
        )
        {
            DBconn.setAutoCommit(false);
            stmt.executeUpdate("delete from DB2024_Algorithms");
            stmt.executeUpdate("delete from DB2024_Problems");

            for (int page = 1; page <= 600; page++) {
                try {
                    String path = "https://solved.ac/api/v3/search/problem?query=+&page=" + page + "&sort=id&direction=asc";
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    //Thread.sleep(100);
                    if (conn.getResponseCode() != 200) {
                        continue;
                    }

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    JSONObject jsonResponse = new JSONObject(response.toString());
                    // 데이터 처리는 여기서...

                    JSONArray itemlist = jsonResponse.getJSONArray("items");
                    for(Object item : itemlist) {
                        if(((JSONObject)item).getBoolean("official") == false) continue;

                        int pid = ((JSONObject)item).getInt("problemId");
                        if(solved[pid]) continue;
                        
                        String ptitle = ((JSONObject)item).getString("titleKo");
                        int tier = ((JSONObject)item).getInt("level");
                        int solvednum = ((JSONObject)item).getInt("acceptedUserCount");
                        String link = "https://www.acmicpc.net/problem/"+pid;

                        pstmtPro.setInt(1,pid);
                        pstmtPro.setString(2,ptitle);
                        pstmtPro.setInt(3,tier);
                        pstmtPro.setInt(4,solvednum);
                        pstmtPro.setString(5,link);
                        pstmtPro.executeUpdate();

                        JSONArray tags = ((JSONObject)item).getJSONArray("tags");
                        if (tags.isEmpty()) {
                            // (알고리즘)태그가 없으면 다음 문제로
                            continue;
                        }
                        for(Object tag : tags){
                            JSONArray displayNames = ((JSONObject)tag).getJSONArray("displayNames");
                            String name = displayNames.getJSONObject(0).getString("short");

                            pstmtAlgo.setInt(1, pid);
                            pstmtAlgo.setString(2, name);
                            pstmtAlgo.executeUpdate();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            DBconn.commit();
            DBconn.setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void crawlUser(String user) {
        String URL = "https://www.acmicpc.net/user/"+user;
        try {
            Document Doc = Jsoup.connect(URL).get();
            Element problemListDiv = Doc.selectFirst("div.problem-list");

            // 문제 번호 텍스트 추출
            if (problemListDiv != null) {
                String[] problemNumbers = problemListDiv.text().split("\\s+");

                for (String number : problemNumbers) {
                    if(number.isEmpty()) continue;
                    solved[Integer.parseInt(number)] = true;
                }
            } else {
                System.out.println("문제 목록을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void crawlSchool() {
        int school_id = 352; // EWHA WOMANS UNIVERSITY
        String URL = "https://solved.ac/ranking/o/"+school_id+"?page=";
        for (int page = 1; page < 15; page++) {
            try {
                Document Doc = Jsoup.connect(URL+page).get();
                Elements es = Doc.getElementsByClass("css-oo6qmd");
                for(Element e : es) {
                    users.add(e.text());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
