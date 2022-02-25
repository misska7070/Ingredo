package net.MobileApp.Ingredo;

public class ListItem {

    public String value;
    public int hasBackground = 3;

    public ListItem(String value, int background) {
        this.value = value;
        this.hasBackground = background;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
