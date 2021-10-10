package com.example.batchtutorial.batch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "RO")
public class RO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="text", nullable = true)
    private String text;

    @OneToMany(mappedBy = "ro", cascade = CascadeType.ALL)
    public List<ROImage> roImageList=new ArrayList<>();

    public void add(ROImage roImage){
//        this.roImageList = new ArrayList<>();
        this.roImageList.add(roImage);
        roImage.setRo(this);
    }

    @Override
    public String toString() {
        return "RO{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
