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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

@Service
public class DataCrawlingService {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private static ArrayList<String> users = new ArrayList<>();
    private static boolean[] solved = new boolean[35000];

    @Scheduled(cron = "00 30 11 * * ?")
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
        log.info("전체 학생 목록을 가져오는데 걸린 시간(초): " + (endTime-startTime)/1000000000.0);

        startTime = System.nanoTime();
        for(String user : users) {
            //Thread.sleep(1000);
            crawlUser(user);
        }
        endTime = System.nanoTime();
        log.info("학생들이 이미 푼 문제들을 찾는데 걸린 시간(초): " + (endTime-startTime)/1000000000.0);

        int solvedNum = 0;
        for(boolean isSolved: solved){
            if(isSolved) solvedNum++;
        }
        log.info("학생들이 푼 문제 수: " + solvedNum);

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
                PreparedStatement pstmtAlgo = DBconn.prepareStatement("INSERT INTO ProAlgo(problem_id, algo_id) VALUES (?,?)");
                Statement stmt = DBconn.createStatement();
        )
        {
            DBconn.setAutoCommit(false);
            stmt.executeUpdate("delete from ProAlgo");
            stmt.executeUpdate("delete from Problems");

            int MaxPage = 1;
            for (int page = 1; page <= MaxPage; page++) {
                try {
                    String path = "https://solved.ac/api/v3/search/problem?query=+&page=" + page + "&sort=id&direction=asc";
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

                            pstmtAlgo.setInt(1, pid);
                            pstmtAlgo.setString(2, name);
                            pstmtAlgo.executeUpdate();
                        }
                    }
                } catch (HttpStatusException e) {
                    log.error(e.getMessage());
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
            DBconn.commit();
            DBconn.setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
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
                log.error("문제 목록을 찾을 수 없습니다: " + URL);
            }
        } catch (HttpStatusException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    void crawlSchool() {
        int school_id = 352; // EWHA WOMANS UNIVERSITY
        String URL = "https://solved.ac/api/v3/ranking/in_organization?organizationId="+school_id;

        int page = 1;
        try(
                Connection DBconn = DBConnection.getDbPool().getConnection();
                Statement stmt = DBconn.createStatement();
                PreparedStatement pstmt = DBconn.prepareStatement("INSERT INTO Students(handle, link, solved_num, ranking, tier) VALUES (?,?,?,?,?)");
        )
        {
            DBconn.setAutoCommit(false);
            stmt.executeUpdate("delete from Students");

            int MaxPage = 1;
            for (page = 1; page <= MaxPage; page++) {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(URL+"&page="+page))
                        .header("Accept", "application/json")
                        .method("GET", HttpRequest.BodyPublishers.noBody())
                        .build();
                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                JSONObject jsonResponse = new JSONObject(response.body());
                MaxPage = jsonResponse.getInt("count") / 50 + 1;
                JSONArray items = jsonResponse.getJSONArray("items");

                for(Object item : items) {
                    JSONObject user = (JSONObject)item;

                    String handle = user.getString("handle");
                    users.add(handle);

                    pstmt.setString(1, handle);
                    pstmt.setString(2, "https://solved.ac/profile/" + handle);
                    pstmt.setInt(3, user.getInt("solvedCount"));
                    pstmt.setInt(4, user.getInt("rank"));
                    pstmt.setInt(5, user.getInt("tier"));
                    pstmt.executeUpdate();
                }
            }

            DBconn.commit();
            DBconn.setAutoCommit(true);
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
