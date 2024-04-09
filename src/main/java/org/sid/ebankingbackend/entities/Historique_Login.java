package org.sid.ebankingbackend.entities;

import jakarta.persistence.*;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Historique_Login {
    @ManyToOne
    @JoinColumn(name = "ouser_id")
    private User ouser ;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createDateTime;
}
