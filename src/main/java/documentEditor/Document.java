package documentEditor;

public interface Document {
    String getFormat();
    void setContent(String content);
    String getContent();
    void display();
    void save(String path);
}
