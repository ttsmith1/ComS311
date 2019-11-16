package pa1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import api.Graph;



/**
 * Implementation of a basic web crawler that creates a graph of some
 * portion of the world wide web.
 *
 * @author Trevor Smith && Jordan Silvers
 */
public class Crawler
{
  private String SEED_URL;
  private int MAX_DEPTH;
  private int MAX_PAGES;
  /**
   * Constructs a Crawler that will start with the given seed url, including
   * only up to maxPages pages at distance up to maxDepth from the seed url.
   * @param seedUrl
   * @param maxDepth
   * @param maxPages
   */
  public Crawler(String seedUrl, int maxDepth, int maxPages)
  {
    this.SEED_URL = seedUrl;
    this.MAX_DEPTH = maxDepth;
    this.MAX_PAGES = maxPages;
  }
  
  /**
   * Creates a web graph for the portion of the web obtained by a BFS of the 
   * web starting with the seed url for this object, subject to the restrictions
   * implied by maxDepth and maxPages.  
   * @return
   *   an instance of Graph representing this portion of the web
   */
  public Graph<String> crawl()
  {
    String currentPage;
    int currentDepth = 0, currentWidth = 0, count = 0, countHash = 0;
    //Graph<String> g;
    ArrayList<String> vertices = new ArrayList<String>();
    ArrayList<List<String>> outgoing = new ArrayList<List<String>>();
    List<String> currentOutgoing = new ArrayList<String>();
    HashMap<String, Integer> hash = new HashMap<String, Integer>();
    Queue<String> q = new LinkedList<String>();
    vertices.add(SEED_URL);
    q.add(SEED_URL);
    hash.put(SEED_URL, countHash);
    countHash++;
    ArrayList<String> discovered = new ArrayList<String>();
    discovered.add(SEED_URL);
    while(q.peek() != null && currentDepth < MAX_DEPTH){
      currentOutgoing = new ArrayList<String>();
      currentPage = q.remove();
      if(count > 49){
        try{
          Thread.sleep(3000);
        }catch (InterruptedException ignore){
        }
        count = 0;
      }else{
        count++;
      }
      Document doc = null;
	try {
		doc = Jsoup.connect(currentPage).get();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      Elements links = doc.select("a[href]");
      for (Element link : links) {
    	String v = link.attr("abs:href");
        if(!currentOutgoing.contains(v)){
          currentOutgoing.add(v);
          //g.connect(currentPage, link);
        }
        if(!discovered.contains(v) && currentWidth != MAX_PAGES){
          hash.put(v, countHash);
          discovered.add(v);
          q.add(v);
          vertices.add(v);
          currentWidth++;
          countHash++;
        }
      }
      outgoing.add(currentOutgoing);
      currentDepth++;
    }
    Graph2<String> g = new Graph2<String>(vertices, outgoing, hash);
    return g;
  }
}
