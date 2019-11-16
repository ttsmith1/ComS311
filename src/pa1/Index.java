package pa1;

import java.util.HashMap;
import java.util.List;

import api.TaggedVertex;
import api.Util;
import pa1.Crawler;

/**
 * Implementation of an inverted index for a web graph.
 * 
 * @author Trevor Smith && Jordan Silvers
 */
public class Index
{
  private List<TaggedVertex<String>> LIST_VERTEX;
  private List<String> LIST_WORDS;
  private List<List<TaggedVertex<String>>> LIST_W;
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
    int len = LIST_VERTEX.size(), listIndex;
    E temp;
    String str;
    String[] split;
    TaggedVertex<String> taggedVertex;
    List<TaggedVertex<String>> listW;
    List<String> listWords;
    List<List<TaggedVertex<String>>> listTuples;
    List<TaggedVertex<String>> tuples;
    for(int i = 0; i < len; i++){
      listW = null;
      temp = LIST_VERTEX.get(i).getVertexData();
      str = Jsoup.connect(temp).get().body().text();
      split = str.split(" ");
      for(int j = 0; j < split.length; j++){
        Util.stripPunctuation(split[j]);
        if(!isStopWord(split[j])){
          if(listW.contains(split[j])){
            taggedVertex  = listW.get(j);
            listIndex = listW.indexOf(taggedVertex);
            taggedVertex = taggedVertex(taggedVertex.getVertexData(), taggedVertex.getTagValue() + 1);
            listW.set(listIndex, taggedVertex);
          }else{
            taggedVertex = taggedVertex(split[j], 1);
            listW.add(taggedVertex);
          }
        }
      }
      //create I
      for(int j = 0; j < listW.size(); j++){
        if(listWords.contains(listW.get(j).getVertexData())){
          listIndex = listWords.indexof(listW.get(j).getVertexData());
          taggedVertex = taggedVertex(temp, listW.get(j).getTagValue());
          listTuples.get(listIndex).add(taggedVertex);
        }else{
          listWords.add(listW.get(j).getVertexData());
          tuples = null;
          tuples.add(taggedVertex(temp, listW.get(j).getTagValue()));
          listTuples.add(tuples);
        }
      }
    }
    this.LIST_W = listWords;
    this.LIST_WORDS = listTuples;
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
    int index, indexIndex, indexIndexIndex, rank;
    List<TaggedVertex<String>> ret = new List<TaggedVertex<String>>();
    List<TaggedVertex<String>> temp = new List<TaggedVertex<String>>();
    if(!LIST_WORDS.contains(w)){
      return ret;
    }
    index = LIST_WORDS.indexOf(w);
    temp = LIST_W.get(index);
    for(int i = 0; i < temp.size(); i++){
      indexIndex = LIST_VERTEX.indexOf(temp.get(i).getVertexData());
      rank = temp.get(i).getTagValue() * LIST_VERTEX.get(indexIndex).getTagValue();
      for(int j = 0; j < ret.size(); j++){
        if(ret.get(j).getTagValue() <= rank){
          indexIndexIndex = j;
          break;
        }
      }
      ret.add(TaggedVertex(indexIndexIndex, temp.get(i).getVertexData, rank));
    }
    return ret;
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
    int index, index2, indexIndex, indexIndexIndex, rank;
    List<TaggedVertex<String>> ret = new List<TaggedVertex<String>>();
    List<TaggedVertex<String>> temp = new List<TaggedVertex<String>>();
    List<TaggedVertex<String>> temp1 = new List<TaggedVertex<String>>();
    List<TaggedVertex<String>> temp2 = new List<TaggedVertex<String>>();
    if(!LIST_WORDS.contains(w1) || !LIST_WORDS.contains(w2)){
      return ret;
    }
    index = LIST_WORDS.indexOf(w1);
    index2 = LIST_WORDS.indexOf(w2);
    temp1 = LIST_W.get(index);
    temp2 = LIST_W.get(index2);
    for(int i = 0; i < temp2.size(); i++){
      if(temp1.contains(temp2.get(i))){
        temp.add(TaggedVertex(temp2.get(i).getVertexData(), temp2.get(i).getTagValue() + temp1.get(temp1.indexOf(temp2.get(i))).getTagValue()));
      }
    }
    for(int i = 0; i < temp.size(); i++){
      indexIndex = LIST_VERTEX.indexOf(temp.get(i).getVertexData());
      rank = temp.get(i).getTagValue() * LIST_VERTEX.get(indexIndex).getTagValue();
      for(int j = 0; j < ret.size(); j++){
        if(ret.get(j).getTagValue() <= rank){
          indexIndexIndex = j;
          break;
        }
      }
      ret.add(TaggedVertex(indexIndexIndex, temp.get(i).getVertexData, rank));
    }
    return ret;
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
    int index, index2, indexIndex, indexIndexIndex, rank;
    List<TaggedVertex<String>> ret = new List<TaggedVertex<String>>();
    List<TaggedVertex<String>> temp = new List<TaggedVertex<String>>();
    List<TaggedVertex<String>> temp2 = new List<TaggedVertex<String>>();
    if(!LIST_WORDS.contains(w1) && !LIST_WORDS.contains(w2)){
      return ret;
    }if(!LIST_WORDS.contains(w1)){
      return search(w2);
    }if(!LIST_WORDS.contains(w2)){
      return search(w1);
    }
    index = LIST_WORDS.indexOf(w1);
    index2 = LIST_WORDS.indexOf(w2);
    temp = LIST_W.get(index);
    temp2 = LIST_W.get(index2);
    for(int i = 0; i < temp2.size(); i++){
      if(temp.contains(temp2.get(i))){
        temp.set(temp.indexOf(temp2.get(i)), TaggedVertex(temp2.get(i).getVertexData(), temp2.get(i).getTagValue() + temp.get(temp.indexOf(temp2.get(i))).getTagValue()));
      }else{
        temp.add(temp2.get(i));
      }
    }
    for(int i = 0; i < temp.size(); i++){
      indexIndex = LIST_VERTEX.indexOf(temp.get(i).getVertexData());
      rank = temp.get(i).getTagValue() * LIST_VERTEX.get(indexIndex).getTagValue();
      for(int j = 0; j < ret.size(); j++){
        if(ret.get(j).getTagValue() <= rank){
          indexIndexIndex = j;
          break;
        }
      }
      ret.add(TaggedVertex(indexIndexIndex, temp.get(i).getVertexData, rank));
    }
    return ret;
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
    int index, indexIndex, indexIndexIndex, rank;
    List<TaggedVertex<String>> ret = new List<TaggedVertex<String>>();
    List<TaggedVertex<String>> temp = new List<TaggedVertex<String>>();
    List<TaggedVertex<String>> temp2 = new List<TaggedVertex<String>>();
    if(!LIST_WORDS.contains(w1) || w1.equals(w2)){
      return ret;
    }
    index = LIST_WORDS.indexOf(w1);
    index2 = LIST_WORDS.indexOf(w2);
    temp = LIST_W.get(index);
    temp2 = LIST_W.get(index);
    for(int i = 0; i < temp.size(); i++){
      if(temp2.contains(temp.get(i))){
        temp.remove(temp2.get(i));
      }
    }
    for(int i = 0; i < temp.size(); i++){
      indexIndex = LIST_VERTEX.indexOf(temp.get(i).getVertexData());
      rank = temp.get(i).getTagValue() * LIST_VERTEX.get(indexIndex).getTagValue();
      for(int j = 0; j < ret.size(); j++){
        if(ret.get(j).getTagValue() <= rank){
          indexIndexIndex = j;
          break;
        }
      }
      ret.add(TaggedVertex(indexIndexIndex, temp.get(i).getVertexData, rank));
    }
    return ret;
  }
}
