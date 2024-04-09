package org.sid.ebankingbackend.entities;


import jakarta.annotation.PreDestroy;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.sid.ebankingbackend.helpers.Utils;


import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "uuid", nullable = true)
    private String uuid;

    @Column(name = "code", nullable = true)
    private String code;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "is_delete", nullable = true,columnDefinition = "boolean default false")
    private boolean isDelete;

    @Column(name = "is_statut", nullable = true,columnDefinition = "boolean default true")
    private boolean isStatut;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

	@PrePersist
	public void prePersist() {
		this.isDelete = false;
		this.isStatut = true;
	}

	@PreUpdate
	public void preUpdate() {

	}

	@PreRemove
	public void preRemove() {

	}

	@PreDestroy
	public void preDestroy() {

	}

	@PostPersist
	public void postPersist() {
		String uuid = Utils.getHashedUuid(this.createDateTime, this.getId());
		this.setUuid(uuid);

	}
}
