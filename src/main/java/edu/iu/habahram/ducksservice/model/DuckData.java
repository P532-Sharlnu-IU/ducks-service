package edu.iu.habahram.ducksservice.model;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;
import java.util.Objects;

@Entity
@Table(schema = "ducks")
public class DuckData{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    private  String name;
    private  String type;

    public DuckData() {
    }

    public DuckData(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toLine() {
        return String.format("%1$s,%2$s,%3$s", id, name, type);
    }

    public String toLine(int id) {
        return String.format("%1$s,%2$s,%3$s", id, name, type);
    }

    public static DuckData fromLine(String line) {
        String[] tokens = line.split(",");
        return new DuckData(Integer.parseInt(tokens[0]), tokens[1], tokens[2]);
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        DuckData duckData = (DuckData) o;
//        return id == duckData.id && Objects.equals(name, duckData.name) && Objects.equals(type, duckData.type);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, name, type);
//    }
//
//    @Override
//    public String toString() {
//        return "DuckData{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", type='" + type + '\'' +
//                '}';
//    }

}
