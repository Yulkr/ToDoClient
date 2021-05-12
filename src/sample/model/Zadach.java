package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sample.requests.model.ZadachEntity;

public class Zadach {
    private final Integer id;
    private final StringProperty category;
    private final StringProperty important;
    private final StringProperty name;

    public Zadach(Integer id, String category, String important ,String name) {
        this.id = id;
        this.category = new SimpleStringProperty(category);
        this.important = new SimpleStringProperty(important);
        this.name = new SimpleStringProperty(name);
    }
    public Zadach(ZadachEntity zadachEntity) {
        this(zadachEntity.getId(),
                zadachEntity.getCategory(),
                zadachEntity.getImportant(),
                zadachEntity.getName());

    }
    public Zadach(){this(null, null, null, null);}

    public Integer getId() {
        return id;
    }

    public String getCategory() {
        return category.get();
    }

    public StringProperty getCategoryProperty() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public String getImportant() {
        return important.get();
    }

    public StringProperty getImportantProperty() {
        return important;
    }

    public void setImportant(String important) {
        this.important.set(important);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
