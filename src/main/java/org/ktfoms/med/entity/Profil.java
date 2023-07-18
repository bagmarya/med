package org.ktfoms.med.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.ktfoms.med.dto.ProfilDto;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "sp_v002_profil")
public class Profil {
    @Id
    @Column(name = "idpr")
    private Integer idpr;

    @Column(name = "prname")
    private String prName;

    public Profil(ProfilDto dto) {
        this.idpr = dto.getIdpr();
        this.prName = dto.getPrName();
    }
}
