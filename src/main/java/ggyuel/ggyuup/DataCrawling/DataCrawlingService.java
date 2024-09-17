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

import java.net.URL;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

@Service
public class DataCrawlingService {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private static ArrayList<String> users = new ArrayList<>();
    private static boolean[] solved = new boolean[40000];

    @Scheduled(cron = "15 30 11 * * ?")
    public void RefreshAllData() throws InterruptedException, IOException
    {
        log.info("크롤링 시작...");
        long startTime = System.nanoTime();
        crawlGroups();
        long endTime = System.nanoTime();
        log.info("그룹 랭킹을 가져오는데 걸린 시간(초): " + (endTime-startTime)/1000000000.0);

        startTime = System.nanoTime();
        crawlSchool();
        endTime = System.nanoTime();
        log.info("상위 500명의 학생 목록을 가져오는데 걸린 시간(초): " + (endTime-startTime)/1000000000.0);

        startTime = System.nanoTime();
        for(String user : users) {
            //Thread.sleep(1000);
            crawlUser(user);
        }
        endTime = System.nanoTime();
        log.info("상위 500명이 이미 푼 문제들을 찾는데 걸린 시간(초): " + (endTime-startTime)/1000000000.0);

        int solvedNum = 0;
        for(boolean isSolved: solved){
            if(isSolved) solvedNum++;
        }
        log.info("이미 푼 문제 수: " + solvedNum);

        startTime = System.nanoTime();
        crawlProblems();
        endTime = System.nanoTime();
        log.info("안 푼 문제 목록을 가져오는데 걸린 시간(초): " + (endTime-startTime)/1000000000.0);
        log.info("크롤링 종료");
    }

    void crawlProblems() {
        try(
                Connection DBconn = DBConnection.getDbPool().getConnection();
                PreparedStatement pstmtPro = DBconn.prepareStatement("INSERT INTO Problems(problem_id, title, tier, solved_num, link) VALUES (?,?,?,?,?)");
                PreparedStatement pstmtAlgo = DBconn.prepareStatement("INSERT INTO ProAlgo(pro_algo_id, problem_id, algo_id) VALUES (?,?,?)");
                Statement stmt = DBconn.createStatement();
        )
        {
            DBconn.setAutoCommit(false);
            stmt.executeUpdate("delete from TodayPs");
            stmt.executeUpdate("delete from ProAlgo");
            stmt.executeUpdate("delete from Problems");

            int MaxPage = 1;
            int pro_algo_id = 1;
            for (int page = 1; page <= MaxPage; page++) {
                try {
                    String path = "https://solved.ac/api/v3/search/problem?query=+&page=" + page;
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(path))
                            .header("x-solvedac-language", "")
                            .header("Accept", "application/json")
                            .method("GET", HttpRequest.BodyPublishers.noBody())
                            .build();
                    HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

                    JSONObject jsonResponse = new JSONObject(response.body());
                    // 데이터 처리는 여기서...

                    MaxPage = jsonResponse.getInt("count") / 50 + 1;
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

                            pstmtAlgo.setInt(1, pro_algo_id);
                            pstmtAlgo.setInt(2, pid);
                            pstmtAlgo.setString(3, name);
                            pstmtAlgo.executeUpdate();
                            pro_algo_id++;
                        }
                    }
                } catch (HttpStatusException e) {
                    log.error("HTTP error "+page+" page: "+e.getMessage());
                } catch (SQLException e) {
                    log.error("SQL error "+page+" page: "+e.getMessage());
                }
            }
            insertTodayPs(DBconn);
            DBconn.commit();
            DBconn.setAutoCommit(true);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void insertTodayPs(Connection conn) {
        //deleteTodayPs();
        try(PreparedStatement pstmt = conn.prepareStatement("INSERT INTO TodayPs (problem_id) " +
                     "SELECT p.problem_id " +
                     "FROM Problems p " +
                     "JOIN (" +
                     "    SELECT tier, MAX(solved_num) AS max_solved_num " +
                     "    FROM Problems " +
                     "    WHERE tier >= 1 AND tier <= 19 " +
                     "    GROUP BY tier " +
                     ") max_solved " +
                     "ON p.tier = max_solved.tier AND p.solved_num = max_solved.max_solved_num " +
                     "WHERE p.tier >= 1 AND p.tier <= 19 " +
                     "ORDER BY p.tier");
        ){

            pstmt.executeUpdate();

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    void crawlUser(String user) {
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
                log.error("문제 목록을 찾을 수 없습니다: " + user);
            }
        } catch (Exception e) {
            log.error(e.getMessage() + user);
        }
    }

    void crawlSchool() {
        int school_id = 352; // EWHA WOMANS UNIVERSITY
        String URL = "https://www.acmicpc.net/school/ranklist/"+school_id+"/";

        int page = 1;
        int MaxPage = 5;
        try{
            for (page = 1; page <= MaxPage; page++) {
                Document doc = Jsoup.connect(URL+page).get();

                for(int i=1; i<=100; i++) {
                    Element name = doc.selectFirst("#ranklist > tbody > tr:nth-child("+i+") > td:nth-child(2) > a");
                    users.add(name.text());
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage()+" at page "+page);
        }
    }

    public void crawlGroups()
    {
        String URL = "https://www.acmicpc.net/ranklist/school/";

        try(
                Connection DBconn = DBConnection.getDbPool().getConnection();
                Statement stmt = DBconn.createStatement();
                PreparedStatement pstmt = DBconn.prepareStatement("insert into Organizations (group_name, solved_num, ranking) values(?,?,?)");
        )
        {
            DBconn.setAutoCommit(false);
            stmt.executeUpdate("delete from Organizations");

            for(int i=1; i<=2; i++) {
                Document doc = Jsoup.connect(URL+i).get();
                for(int j=1; j<=100; j++) {
                    Element name = doc.selectFirst("#ranklist > tbody > tr:nth-child("+j+") > td:nth-child(2) > a");
                    Element solvednum = doc.selectFirst("#ranklist > tbody > tr:nth-child("+j+") > td:nth-child(4) > a");
                    pstmt.setString(1,name.text());
                    pstmt.setInt(2,Integer.parseInt(solvednum.text()));
                    pstmt.setInt(3,j + (i-1)*100);
                    pstmt.executeUpdate();
                }
            }

            DBconn.commit();
            DBconn.setAutoCommit(true);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
