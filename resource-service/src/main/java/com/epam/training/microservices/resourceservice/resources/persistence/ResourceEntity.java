package com.epam.training.microservices.resourceservice.resources.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "resources")
public class ResourceEntity {

    public ResourceEntity(byte[] content) {
        this.content = content;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resource_sequence")
    @SequenceGenerator(name = "resource_sequence", sequenceName = "resource_id_sequence", allocationSize = 1)
    private Integer id;

    @Lob
    private byte[] content;
}
