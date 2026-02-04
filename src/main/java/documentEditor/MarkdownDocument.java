package documentEditor;

public class MarkdownDocument extends BaseDocument {
    @Override
    public String getFormat() {
        return "markdown";
    }

    @Override
    public void display() {
        System.out.println("[Markdown Viewer] Rendering content:\n" + getContent());
    }

    @Override
    public void save(String path) {
        System.out.println("[Markdown Writer] Saving to " + path + " with content length " + getContent().length());
    }
}
