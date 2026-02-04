package documentEditor;

public class DocumentEditor {
    private final DocumentFactory factory;
    private Document currentDocument;

    public DocumentEditor(DocumentFactory factory) {
        this.factory = factory;
    }

    public void newDocument(String format) {
        this.currentDocument = factory.create(format);
    }

    public void setContent(String content) {
        ensureDocument();
        currentDocument.setContent(content);
    }

    public void display() {
        ensureDocument();
        currentDocument.display();
    }

    public void save(String path) {
        ensureDocument();
        currentDocument.save(path);
    }

    public String getCurrentFormat() {
        return currentDocument == null ? "" : currentDocument.getFormat();
    }

    private void ensureDocument() {
        if (currentDocument == null) {
            throw new IllegalStateException("No document created. Call newDocument(format) first.");
        }
    }
}
