package pa1;

public class InvertedIndex{
    private List<String> LIST_WORDS;
    private List<List<TaggedVertex<String>>> LIST_W;

    public InvertedIndex(List<String> words, List<List<TaggedVertex<String>>> listW){
        this.LIST_W = words;
        this.LIST_WORDS = listW;
    }
}