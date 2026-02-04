package documentEditor;

public class PdfDocument extends BaseDocument {
    @Override
    public String getFormat() {
        return "pdf";
    }

    @Override
    public void display() {
        System.out.println("[PDF Viewer] Rendering content:\n" + getContent());
    }

    @Override
    public void save(String path) {
        System.out.println("[PDF Writer] Saving to " + path + " with content length " + getContent().length());
    }
}
