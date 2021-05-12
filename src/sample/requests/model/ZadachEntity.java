package sample.requests.model;

import sample.model.Zadach;

import java.util.SplittableRandom;

public class ZadachEntity {
    private Integer id;
    private String category;
    private String important;
    private String name;
    public ZadachEntity(Zadach zadach) {

        id = zadach.getId();
        category = zadach.getCategory();
        important = zadach.getImportant();
        name = zadach.getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImportant() {
        return important;
    }

    public void setImportant(String important) {
        this.important = important;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ZadachEntity{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", important='" + important + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
