package net.MobileApp.Ingredo;

public class Product {
    private String note;
    private String image;

    public Product(String note,  String image) {

        this.note = note;
        this.image = image;
    }

    public String getTitle() {
        return note;
    }

    public String getImage() {
        return image;
    }
}
