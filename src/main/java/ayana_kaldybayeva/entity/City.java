package ayana_kaldybayeva.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long city_id;
    private String name;
    private String index;

    public Long getCityId() {
        return city_id;
    }

    public void setCityId(Long city_id) {
        this.city_id = city_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndexCode(String index) {
        this.index = index;
    }
}
