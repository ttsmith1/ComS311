package pa1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

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
 
  //Time Complexity Cheat Sheet probably correct
  //http://www.souravsengupta.com/cds2016/lectures/Complexity_Cheatsheet.pdf

  /**
   * Creates a web graph for the portion of the web obtained by a BFS of the 
   * web starting with the seed url for this object, subject to the restrictions
   * implied by maxDepth and maxPages.  
   * @return
   *   an instance of Graph representing this portion of the web
   */
  public Graph<String> crawl()
  {
    String currentPage;                                       //O(1)
    int currentDepth = 0, currentWidth = 0;                   //O(1)
    Integer inTemp;                                           //O(1)
    HashMap<> indegree = new HashMap<>();
    indegree.put(SEED_URL, 0);                                //either O(1 or n)
    Graph<String> g = new Graph<Spring>();
    g.add(SEED_URL);                                          //O(1)
    Queue<String> q = new Queue();
    q.add(SEED_URL);                                          //O(1)
    ArrayList<String> discovered = new ArrayList<String>();
    discovered.add(SEED_URL);                                 //O(1)
    List<String> connections = new List<String>();
    while(q.peek != null && currentDepth < MAX_DEPTH){        //O(an amount of time)
      currentPage = q.remove();                               //O(n)
      connections = null;
      Document doc = Jsoup.connect(currentPage).get();
      Elements links = doc.select("a[href]");
      for (Element link : links) {
        if(!connections.contains(link)){
          connections.add(link);
          g.connect(currentPage, link);
          inTemp = indegree.get(link);
          if(inTemp == null){
            indegree.put(link, 1);
          }else{
            indegree.replace(link, inTemp + 1);
          }
        }
        if(!discovered.contains(link) && currentWidth != MAX_PAGES){
          discovered.add(link);
          q.add(link);
          g.add(link);
          currentWidth++;
        }
      }
      currentDepth++;
    }
    return g;
  }
}
