package com.diplome.cv.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "diplomes")
public class Diplome implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty(message = "Ce champ ne peut pas être vide")
    @Size(min = 4, max = 150, message = "La taille du nom du diplome doit être entre 4 et 50 character")
    private String nomDiplome;

    @Column(length = 40, nullable = false)
    @NotEmpty(message = "Ce champ ne peut pas être vide")
    private String ecole;

    @Column(length = 30, nullable = false)
    @NotEmpty(message = "Ce champ ne peut pas être vide")
    private String ville;

    @Column(columnDefinition = "Text")
    private String description;

    @Temporal(TemporalType.DATE)
    private Date annee;

    private static final long serialVersionUID = 1L;

}

