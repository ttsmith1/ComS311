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
    int currentDepth = 0, currentWidth = 0, count = 0;
    Graph<String> g = new Graph<Spring>();
    g.add(SEED_URL);
    Queue<String> q = new Queue();
    q.add(SEED_URL);
    ArrayList<String> discovered = new ArrayList<String>();
    discovered.add(SEED_URL);
    List<String> connections = new List<String>();
    while(q.peek != null && currentDepth < MAX_DEPTH){
      currentPage = q.remove();
      connections = null;
      if(count > 49){
        try{
          Thread.sleep(3000);
        }catch (InterruptedException ignore){
        }
        count = 0;
      }else{
        count++;
      }
      Document doc = Jsoup.connect(currentPage).get();
      Elements links = doc.select("a[href]");
      for (Element link : links) {
        if(!connections.contains(link)){
          connections.add(link);
          g.connect(currentPage, link);
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
