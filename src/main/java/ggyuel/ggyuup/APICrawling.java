package ggyuel.ggyuup;
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
import java.util.ArrayList;
import java.util.List;

public class APICrawling {
	private static ArrayList<problem> problems = new ArrayList<problem>();
	private static ArrayList<String> users = new ArrayList<>();
	private static boolean[] solved = new boolean[40000];

	public static void main(String[] args) {
		crawlSchool();
		System.out.println(users.size());
		for(int i=100;i<users.size();i++) {
			String user = users.get(i);
			System.out.println(user);
			crawlUser(user);
		}
		crawlProblems();
    }

    public static void crawlProblems() {
        for (int page = 1; page <= 300; page++) {
            try {
                String path = "https://solved.ac/api/v3/search/problem?query=+&page=" + page + "&sort=id&direction=asc";
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                
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
                
                JSONArray itemlist = (JSONArray) jsonResponse.get("items");
                for(Object item : itemlist) {
                	int pid = (int) ((JSONObject)item).get("problemId");
                	if(solved[pid]) continue;
                	String ptitle = (String)((JSONObject)item).get("titleKo");
                	int tier = (int)((JSONObject)item).get("level");
                	int solvednum = (int)((JSONObject)item).get("acceptedUserCount");
                	String link = "https://www.acmicpc.net/problem/"+pid;
                	System.out.print(pid+"\t");
                	System.out.print(ptitle+"\t");
                	System.out.print(tier+"\t");
                	System.out.print(solvednum+"\t");
                	System.out.print(link+"\t");
                	problems.add(new problem(pid,ptitle,tier,solvednum,link));
                	System.out.println();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void crawlUser(String user) {
    	String URL = "https://solved.ac/profile/"+user+"/solved?page=";
    	int page = 1;
        try {
        	Thread.sleep(100);
            while (true) {
                Document Doc = Jsoup.connect(URL + page).get();
                Elements pid = Doc.getElementsByClass("css-q9j30p");
                if (pid.isEmpty()) {
                    // 현재 페이지에 데이터가 없으면 반복을 종료합니다.
                    break;
                }
                int i = 0;
                for(Element e : pid) {
                    if(++i % 2 == 0) continue; // 짝수번째 요소(문제제목)는 무시합니다.
                    solved[Integer.parseInt(e.text())] = true;
                    System.out.print(" " + e.text());
                }
                System.out.println();
                page++; // 다음 페이지로 이동합니다.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void crawlSchool() {
		int school_id = 352; // EWHA
		String URL = "https://solved.ac/ranking/o/"+school_id+"?page=";
		for (int page = 1; page < 15; page++) {
			try {
                Document Doc = Jsoup.connect(URL+page).get();
                Elements es = Doc.getElementsByClass("css-oo6qmd");
                for(Element e : es) {
                	users.add(e.text());
                	//System.out.print(e.text());
                	//crawlUser(e.text());
                	//System.out.println();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
    }
}
class problem{
	int pid;
	String ptitle;
	int tier;
	int solvednum;
	String link;
	problem(int pid, String ptitle, int tier, int solvednum, String link){
		this.pid = pid;
		this.ptitle = ptitle;
		this.tier = tier;
		this.solvednum = solvednum;
		this.link = link;
	}
}