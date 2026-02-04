package documentEditor;

public abstract class BaseDocument implements Document {
    private String content;

    @Override
    public void setContent(String content) {
        this.content = content == null ? "" : content;
    }

    @Override
    public String getContent() {
        return content;
    }
}
