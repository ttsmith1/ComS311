package pa1;

import java.util.HashMap;
import java.util.List;

import api.TaggedVertex;

import pa1.Crawler;

/**
 * Implementation of an inverted index for a web graph.
 * 
 * @author Trevor Smith && Jordan Silvers
 */
public class Index
{
  private List<TaggedVertex<String>> LIST_VERTEX;
  /**
   * Constructs an index from the given list of urls.  The
   * tag value for each url is the indegree of the corresponding
   * node in the graph to be indexed.
   * @param urls
   *   information about graph to be indexed
   */
  public Index(List<TaggedVertex<String>> urls)
  {
    this.LIST_VERTEX = urls;
  }
  
  /**
   * Creates the index.
   */
  public void makeIndex()
  {
    // TODO
    // String seedUrl;
    // int maxDepth, maxPages;
    // //Probably stdin vals
    // Crawler crawl = new Crawler(seedUrl, maxDepth, maxPages);
    // Graph<String> g = crawl();
    // HashMap<> indegree = getIndegree();
    // List<TaggedVertex<String>> listIndegree;
    // for(indegree.Entry<String, Integer> entry : indegree.entrySet()){
    //   TaggedVertex<String> temp = {entry.getKey(), entry.getValue};
    //   listIndegree.add(temp);
    // }
    // Index ind = new Index(listIndegree);
    
  }
  
  /**
   * Searches the index for pages containing keyword w.  Returns a list
   * of urls ordered by ranking (largest to smallest).  The tag 
   * value associated with each url is its ranking.  
   * The ranking for a given page is the number of occurrences
   * of the keyword multiplied by the indegree of its url in
   * the associated graph.  No pages with rank zero are included.
   * @param w
   *   keyword to search for
   * @return
   *   ranked list of urls
   */
  public List<TaggedVertex<String>> search(String w)
  {
    // TODO
    return null;
  }


  /**
   * Searches the index for pages containing both of the keywords w1
   * and w2.  Returns a list of qualifying
   * urls ordered by ranking (largest to smallest).  The tag 
   * value associated with each url is its ranking.  
   * The ranking for a given page is the number of occurrences
   * of w1 plus number of occurrences of w2, all multiplied by the 
   * indegree of its url in the associated graph.
   * No pages with rank zero are included.
   * @param w1
   *   first keyword to search for
   * @param w2
   *   second keyword to search for
   * @return
   *   ranked list of urls
   */
  public List<TaggedVertex<String>> searchWithAnd(String w1, String w2)
  {
    // TODO
    return null;
  }
  
  /**
   * Searches the index for pages containing at least one of the keywords w1
   * and w2.  Returns a list of qualifying
   * urls ordered by ranking (largest to smallest).  The tag 
   * value associated with each url is its ranking.  
   * The ranking for a given page is the number of occurrences
   * of w1 plus number of occurrences of w2, all multiplied by the 
   * indegree of its url in the associated graph.
   * No pages with rank zero are included.
   * @param w1
   *   first keyword to search for
   * @param w2
   *   second keyword to search for
   * @return
   *   ranked list of urls
   */
  public List<TaggedVertex<String>> searchWithOr(String w1, String w2)
  {
    // TODO
    return null;
  }
  
  /**
   * Searches the index for pages containing keyword w1
   * but NOT w2.  Returns a list of qualifying urls
   * ordered by ranking (largest to smallest).  The tag 
   * value associated with each url is its ranking.  
   * The ranking for a given page is the number of occurrences
   * of w1, multiplied by the 
   * indegree of its url in the associated graph.
   * No pages with rank zero are included.
   * @param w1
   *   first keyword to search for
   * @param w2
   *   second keyword to search for
   * @return
   *   ranked list of urls
   */
  public List<TaggedVertex<String>> searchAndNot(String w1, String w2)
  {
    // TODO
    return null;
  }
}
