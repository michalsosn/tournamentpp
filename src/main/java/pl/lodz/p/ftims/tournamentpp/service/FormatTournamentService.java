package pl.lodz.p.ftims.tournamentpp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.ftims.tournamentpp.repository.TournamentRepository;

import javax.transaction.Transactional;

/**
 * Created by Daniel on 2016-05-11.
 */
@Transactional
@Service
public class FormatTournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;

}
