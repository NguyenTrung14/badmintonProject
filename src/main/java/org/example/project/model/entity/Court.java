package org.example.project.model.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "court")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Court {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courtName;
    private String type;
    private String imageUrl;
    private Boolean isAvailable;
    private Double price;
    @ManyToOne
    @JoinColumn(name = "cluster_id")
    private BadmintonCluster cluster;
}
