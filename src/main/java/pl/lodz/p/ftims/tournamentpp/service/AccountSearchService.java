package pl.lodz.p.ftims.tournamentpp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.ftims.tournamentpp.entities.AccountEntity;
import pl.lodz.p.ftims.tournamentpp.entities.Role;
import pl.lodz.p.ftims.tournamentpp.repository.AccountRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 2016-06-18.
 */
@Transactional
@Service
public class AccountSearchService {
    @Autowired
    private AccountRepository accountRepository;

    public Page<AccountDto> findBySearchTerm(String searchTerm, Pageable pageRequest) {
        //Obtain search results by invoking the preferred repository method.
        Page<AccountEntity> searchResultPage =
                accountRepository.findBySearchTerm(searchTerm, pageRequest);
        List<AccountEntity> list = searchResultPage.getContent();
        List<AccountDto> accountDtoList = new ArrayList<>();
        for (AccountEntity ae : list) {
            if (ae.getRoles().get(Role.ROLE_COMPETITOR) != null
                    && ae.getRoles().get(Role.ROLE_COMPETITOR).isActive()) {
                accountDtoList.add(convert(ae));
            }
        }
        Page<AccountDto> page = new PageImpl<>(accountDtoList);
        return page;
    }

    private static AccountDto convert(AccountEntity source) {
        AccountDto ad = new AccountDto();
        ad.setName(source.getName());
        ad.setUsername(source.getUsername());
        ad.setDescription(source.getDescription());
        return ad;
    }
}
