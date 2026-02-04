package documentEditor;

public class DocumentEditorApp {
    public static void main(String[] args) {
        DocumentFactory factory = DocumentFactory.createDefaultFactory();
        DocumentEditor editor = new DocumentEditor(factory);

        editor.newDocument("pdf");
        editor.setContent("Hello from the document editor.");
        editor.display();
        editor.save("output/document.pdf");
    }
}
