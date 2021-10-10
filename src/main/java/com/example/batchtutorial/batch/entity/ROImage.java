package com.example.batchtutorial.batch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ROImage")
public class ROImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ro_id")
    private RO ro;

    @Column(name = "ai")
    private int ai;

    @Column(name = "doubleAi")
    private int doubleAi;

    @Column(name = "url")
    private String url;

    public void addTo(RO ro){
        this.ro = ro;
        ro.getRoImageList().add(this);
    }

    @Override
    public String toString() {
        return "ROImage{" +
                "id=" + id +
                ", ro=" + ro.toString() +
                ", ai=" + ai +
                ", doubleAi=" + doubleAi +
                ", url='" + url + '\'' +
                '}';
    }
}
