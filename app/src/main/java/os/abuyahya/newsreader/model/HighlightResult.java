package os.abuyahya.newsreader.model;

public class HighlightResult {

    private HighlightTitle title;
    private HighlightUrl url;
    private HighlightAuthor author;

    public HighlightResult() {
    }

    public HighlightResult(HighlightTitle title, HighlightUrl url, HighlightAuthor author) {
        this.title = title;
        this.url = url;
        this.author = author;
    }

    public HighlightTitle getTitle() {
        return title;
    }

    public void setTitle(HighlightTitle title) {
        this.title = title;
    }

    public HighlightUrl getUrl() {
        return url;
    }

    public void setUrl(HighlightUrl url) {
        this.url = url;
    }

    public HighlightAuthor getAuthor() {
        return author;
    }

    public void setAuthor(HighlightAuthor author) {
        this.author = author;
    }
}
