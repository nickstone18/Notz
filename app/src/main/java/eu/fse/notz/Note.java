package eu.fse.notz;

/**
 * Created by Amministratore on 12/04/2018.
 */

public class Note {
    private String title,description;
    private int id;
    private boolean isShownOnTop;


    public Note(String title, String description){
        this.title = title;
        this.description = description;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isShownOnTop() {
        return isShownOnTop;
    }

    public void setShownOnTop(boolean shownOnTop) {
        isShownOnTop = shownOnTop;
    }
}
