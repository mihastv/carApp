package documentEditor;

public class WordDocument extends BaseDocument {
    @Override
    public String getFormat() {
        return "word";
    }

    @Override
    public void display() {
        System.out.println("[Word Viewer] Rendering content:\n" + getContent());
    }

    @Override
    public void save(String path) {
        System.out.println("[Word Writer] Saving to " + path + " with content length " + getContent().length());
    }
}
