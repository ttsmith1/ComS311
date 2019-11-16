package pa1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;

import api.TaggedVertex;
import api.Util;

/**
 * Implementation of an inverted index for a web graph.
 * 
 * @author Trevor Smith && Jordan Silvers
 */
public class Index {
	private List<TaggedVertex<String>> LIST_VERTEX;
	private List<String> LIST_WORDS;
	private List<List<TaggedVertex<String>>> LIST_W;

	/**
	 * Constructs an index from the given list of urls. The tag value for each url
	 * is the indegree of the corresponding node in the graph to be indexed.
	 * 
	 * @param urls information about graph to be indexed
	 */
	public Index(List<TaggedVertex<String>> urls) {
		this.LIST_VERTEX = urls;
	}

	/**
   * Creates the index.
   */
  public void makeIndex()
  {
    int len = LIST_VERTEX.size(), listIndex;
    String temp;
    String str = "";
    String[] split;
    TaggedVertex<String> taggedVertex;
    List<TaggedVertex<String>> listW = new ArrayList<TaggedVertex<String>>();
    List<String> listWords = new ArrayList<String>();
    List<List<TaggedVertex<String>>> listTuples = new ArrayList<List<TaggedVertex<String>>>();
    List<TaggedVertex<String>> tuples = new ArrayList<TaggedVertex<String>>();
    for(int i = 0; i < len; i++){
    	if(!listW.isEmpty()) {
    		listW.clear();
    	}
      temp = LIST_VERTEX.get(i).getVertexData();
      if((i % 50) == 49){
        try{
          Thread.sleep(3000);
        }catch (InterruptedException ignore){
        }
      }
      try {
		str = Jsoup.connect(temp).get().body().text();
	} catch (IOException e) {
		e.printStackTrace();
	}
      split = str.split(" ");
      for(int j = 0; j < split.length; j++){
        Util.stripPunctuation(split[j]);
        if(!Util.isStopWord(split[j])){
          if(listWords.contains(split[j])){
            taggedVertex  = listW.get(j);
            listIndex = listW.indexOf(taggedVertex);
            taggedVertex = new TaggedVertex<String>(taggedVertex.getVertexData(), taggedVertex.getTagValue() + 1);
            listW.set(listIndex, taggedVertex);
          }else{
        	listWords.add(split[i]);
            taggedVertex = new TaggedVertex<String>(split[j], 1);
            listW.add(taggedVertex);
          }
        }
      }
      //create I
      for(int j = 0; j < listW.size(); j++){
        if(listWords.contains(listW.get(j).getVertexData())){
          listIndex = listWords.indexOf(listW.get(j).getVertexData());
          taggedVertex = new TaggedVertex<String>(temp, listW.get(j).getTagValue());
          listTuples.get(listIndex).add(taggedVertex);
        }else{
          listWords.add(listW.get(j).getVertexData());
          taggedVertex = new TaggedVertex<String>(temp, listW.get(j).getTagValue());
          tuples.clear();
          tuples.add(taggedVertex);
          listTuples.add(tuples);
        }
      }
    }
    this.LIST_W = listTuples;
    this.LIST_WORDS = listWords;
  }

	/**
	 * Searches the index for pages containing keyword w. Returns a list of urls
	 * ordered by ranking (largest to smallest). The tag value associated with each
	 * url is its ranking. The ranking for a given page is the number of occurrences
	 * of the keyword multiplied by the indegree of its url in the associated graph.
	 * No pages with rank zero are included.
	 * 
	 * @param w keyword to search for
	 * @return ranked list of urls
	 */
	public List<TaggedVertex<String>> search(String w) {
		int index, indexIndex, indexIndexIndex = 0, rank;
		TaggedVertex<String> tempTV;
		List<TaggedVertex<String>> ret = new ArrayList<TaggedVertex<String>>();
		List<TaggedVertex<String>> temp = new ArrayList<TaggedVertex<String>>();
		if (!LIST_WORDS.contains(w)) {
			return ret;
		}
		index = LIST_WORDS.indexOf(w);
		temp = LIST_W.get(index);
		for (int i = 0; i < temp.size(); i++) {
			indexIndex = LIST_VERTEX.indexOf(temp.get(i));
			rank = temp.get(i).getTagValue() * LIST_VERTEX.get(indexIndex).getTagValue();
			for (int j = 0; j < ret.size(); j++) {
				indexIndexIndex = j;
				if (ret.get(j).getTagValue() <= rank) {
					break;
				}
			}
			tempTV = new TaggedVertex<String>(temp.get(i).getVertexData(), rank);
			ret.add(indexIndexIndex, tempTV);
		}
		return ret;
	}

	/**
	 * Searches the index for pages containing both of the keywords w1 and w2.
	 * Returns a list of qualifying urls ordered by ranking (largest to smallest).
	 * The tag value associated with each url is its ranking. The ranking for a
	 * given page is the number of occurrences of w1 plus number of occurrences of
	 * w2, all multiplied by the indegree of its url in the associated graph. No
	 * pages with rank zero are included.
	 * 
	 * @param w1 first keyword to search for
	 * @param w2 second keyword to search for
	 * @return ranked list of urls
	 */
	public List<TaggedVertex<String>> searchWithAnd(String w1, String w2) {
		int index, index2, indexIndex, indexIndexIndex = 0, rank;
		TaggedVertex<String> tempTV;
		List<TaggedVertex<String>> ret = new ArrayList<TaggedVertex<String>>();
		List<TaggedVertex<String>> temp = new ArrayList<TaggedVertex<String>>();
		List<TaggedVertex<String>> temp1 = new ArrayList<TaggedVertex<String>>();
		List<TaggedVertex<String>> temp2 = new ArrayList<TaggedVertex<String>>();
		if (!LIST_WORDS.contains(w1) || !LIST_WORDS.contains(w2)) {
			return ret;
		}
		index = LIST_WORDS.indexOf(w1);
		index2 = LIST_WORDS.indexOf(w2);
		temp1 = LIST_W.get(index);
		temp2 = LIST_W.get(index2);
		for (int i = 0; i < temp2.size(); i++) {
			if (temp1.contains(temp2.get(i))) {
				tempTV = new TaggedVertex<String>(temp2.get(i).getVertexData(), temp2.get(i).getTagValue() + temp1.get(temp1.indexOf(temp2.get(i))).getTagValue());
				temp.add(tempTV);
			}
		}
		for (int i = 0; i < temp.size(); i++) {
			indexIndex = LIST_VERTEX.indexOf(temp.get(i));
			rank = temp.get(i).getTagValue() * LIST_VERTEX.get(indexIndex).getTagValue();
			for (int j = 0; j < ret.size(); j++) {
				indexIndexIndex = j;
				if (ret.get(j).getTagValue() <= rank) {
					break;
				}
			}
			tempTV = new TaggedVertex<String>(temp.get(i).getVertexData(), rank);
			ret.add(indexIndexIndex, tempTV);
		}
		return ret;
	}

	/**
	 * Searches the index for pages containing at least one of the keywords w1 and
	 * w2. Returns a list of qualifying urls ordered by ranking (largest to
	 * smallest). The tag value associated with each url is its ranking. The ranking
	 * for a given page is the number of occurrences of w1 plus number of
	 * occurrences of w2, all multiplied by the indegree of its url in the
	 * associated graph. No pages with rank zero are included.
	 * 
	 * @param w1 first keyword to search for
	 * @param w2 second keyword to search for
	 * @return ranked list of urls
	 */
	public List<TaggedVertex<String>> searchWithOr(String w1, String w2) {
		int index, index2, indexIndex, indexIndexIndex = 0, rank;
		TaggedVertex<String> tempTV;
		List<TaggedVertex<String>> ret = new ArrayList<TaggedVertex<String>>();
		List<TaggedVertex<String>> temp = new ArrayList<TaggedVertex<String>>();
		List<TaggedVertex<String>> temp2 = new ArrayList<TaggedVertex<String>>();
		if (!LIST_WORDS.contains(w1) && !LIST_WORDS.contains(w2)) {
			return ret;
		}
		if (!LIST_WORDS.contains(w1)) {
			return search(w2);
		}
		if (!LIST_WORDS.contains(w2)) {
			return search(w1);
		}
		index = LIST_WORDS.indexOf(w1);
		index2 = LIST_WORDS.indexOf(w2);
		temp = LIST_W.get(index);
		temp2 = LIST_W.get(index2);
		for (int i = 0; i < temp2.size(); i++) {
			if (temp.contains(temp2.get(i))) {
				tempTV = new TaggedVertex<String>(temp2.get(i).getVertexData(), temp2.get(i).getTagValue() + temp.get(temp.indexOf(temp2.get(i))).getTagValue());
				temp.set(temp.indexOf(temp2.get(i)), tempTV);
			} else {
				temp.add(temp2.get(i));
			}
		}
		for (int i = 0; i < temp.size(); i++) {
			indexIndex = LIST_VERTEX.indexOf(temp.get(i));
			rank = temp.get(i).getTagValue() * LIST_VERTEX.get(indexIndex).getTagValue();
			for (int j = 0; j < ret.size(); j++) {
				indexIndexIndex = j;
				if (ret.get(j).getTagValue() <= rank) {
					break;
				}
			}
			tempTV = new TaggedVertex<String>(temp.get(i).getVertexData(), rank);
			ret.add(indexIndexIndex, tempTV);
		}
		return ret;
	}

	/**
	 * Searches the index for pages containing keyword w1 but NOT w2. Returns a list
	 * of qualifying urls ordered by ranking (largest to smallest). The tag value
	 * associated with each url is its ranking. The ranking for a given page is the
	 * number of occurrences of w1, multiplied by the indegree of its url in the
	 * associated graph. No pages with rank zero are included.
	 * 
	 * @param w1 first keyword to search for
	 * @param w2 second keyword to search for
	 * @return ranked list of urls
	 */
	public List<TaggedVertex<String>> searchAndNot(String w1, String w2) {
		int index, index2, indexIndex, indexIndexIndex = 0, rank;
		TaggedVertex<String> tempTV;
		List<TaggedVertex<String>> ret = new ArrayList<TaggedVertex<String>>();
		List<TaggedVertex<String>> temp = new ArrayList<TaggedVertex<String>>();
		List<TaggedVertex<String>> temp2 = new ArrayList<TaggedVertex<String>>();
		if (!LIST_WORDS.contains(w1) || w1.equals(w2)) {
			return ret;
		}
		index = LIST_WORDS.indexOf(w1);
		index2 = LIST_WORDS.indexOf(w2);
		temp = LIST_W.get(index);
		temp2 = LIST_W.get(index2);
		for (int i = 0; i < temp.size(); i++) {
			if (temp2.contains(temp.get(i))) {
				temp.remove(temp2.get(i));
			}
		}
		for (int i = 0; i < temp.size(); i++) {
			indexIndex = LIST_VERTEX.indexOf(temp.get(i));
			rank = temp.get(i).getTagValue() * LIST_VERTEX.get(indexIndex).getTagValue();
			for (int j = 0; j < ret.size(); j++) {
				indexIndexIndex = j;
				if (ret.get(j).getTagValue() <= rank) {
					break;
				}
			}
			tempTV = new TaggedVertex<String>(temp.get(i).getVertexData(), rank);
			ret.add(indexIndexIndex, tempTV);
		}
		return ret;
	}
}
