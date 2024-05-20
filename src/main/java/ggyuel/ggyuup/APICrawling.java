package ggyuel.ggyuup;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class APICrawling {
	private static ArrayList<String> users = new ArrayList<>();
	private static boolean[] solved = new boolean[35000];
	
	public static void main(String[] args) throws InterruptedException, IOException {
		crawlSchool();
		for(String user : users) {
			Thread.sleep(1000);
			crawlUser(user);
		}
		crawlProblems();
		Thread.sleep(20000);
    }

    public static void crawlProblems() throws IOException {
    	PrintWriter output1 = new PrintWriter("insertintoProblems.txt");
    	PrintWriter output2 = new PrintWriter("insertintoAlgorithms.txt");
    	output2.println("INSERT INTO DB2024_Algorithms(pid, tag) VALUES");
    	output1.println("INSERT INTO DB2024_Problems(pid, ptitle, tier, solvednum, link) VALUES");
        for (int page = 1; page <= 600; page++) {
        	
            try {
                String path = "https://solved.ac/api/v3/search/problem?query=+&page=" + page + "&sort=id&direction=asc";
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                Thread.sleep(100);
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
                	
                	int pid = (int) ((JSONObject)item).get("problemId");
                	if(solved[pid]) continue;
                	
                	String ptitle = (String)((JSONObject)item).get("titleKo");
                	int tier = (int)((JSONObject)item).get("level");
                	int solvednum = (int)((JSONObject)item).get("acceptedUserCount");
                	String link = "https://www.acmicpc.net/problem/"+pid;
                	output1.printf("(%d,'%s',%d,%d,'%s'),\n",pid,ptitle,tier,solvednum,link);
                	JSONArray tags = ((JSONObject)item).getJSONArray("tags");
                	if (tags.isEmpty()) {
                        // 현재 페이지에 데이터가 없으면 반복을 종료합니다.
                       continue;
                    }
                	for(Object tag : tags){
                		JSONArray displayNames = ((JSONObject)tag).getJSONArray("displayNames");
                		String name = (String)displayNames.getJSONObject(0).get("name");
                		output2.printf("(%d, '%s'),", pid, name);
                	}
                	output2.append('\n');
                	
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        output1.flush();
        output2.flush();
        output1.close();
        output2.close();
    }

    public static void crawlUser(String user) {
    	String URL = "https://solved.ac/profile/"+user+"/solved?page=";
    	int page = 1;
        try {
            while (true) {
                Document Doc = Jsoup.connect(URL + page).get();
                Elements pidtitle = Doc.getElementsByClass("css-q9j30p");
                if (pidtitle.isEmpty()) {
                    // 현재 페이지에 데이터가 없으면 반복을 종료합니다.
                    break;
                }
                int i = 0;
                for(Element pid : pidtitle) {
                    if(++i % 2 == 0) continue; // 짝수번째 요소(문제제목)는 무시합니다.
                    solved[Integer.parseInt(pid.text())] = true;
                }
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
