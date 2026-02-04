package documentEditor;

public class HtmlDocument extends BaseDocument {
    @Override
    public String getFormat() {
        return "html";
    }

    @Override
    public void display() {
        System.out.println("[HTML Viewer] Rendering content:\n" + getContent());
    }

    @Override
    public void save(String path) {
        System.out.println("[HTML Writer] Saving to " + path + " with content length " + getContent().length());
    }
}
