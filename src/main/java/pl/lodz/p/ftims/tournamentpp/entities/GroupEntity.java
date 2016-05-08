/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.ftims.tournamentpp.entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 *
 * @author r4pt0r
 */
@Entity(name = "Group")
@Table(name = "group")
@SequenceGenerator(name = "group_sequence", sequenceName = "group_sequence",
        allocationSize = 1)
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "group_sequence")
    @Column(name = "group_id", nullable = false, updatable = false)
    private long id;

    @Version
    @Column(name = "version")
    private long version;

    @ManyToMany
    @JoinTable(name = "group_competitor",
            joinColumns = @JoinColumn(
                    name = "group_id",
                    referencedColumnName = "group_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "competitor_id",
                    referencedColumnName = "role_id"
            )
    )
    List<CompetitorRoleEntity> competitors;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private TournamentEntity tournament;

}
