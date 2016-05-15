package pl.lodz.p.ftims.tournamentpp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.lodz.p.ftims.tournamentpp.entities.Role;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableAspectJAutoProxy
public class TournamentPpSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    public TournamentPpSecurityConfigurer() {
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder)
            throws Exception {
        builder.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery(
                        "SELECT username, password, active"
                     + " FROM account"
                     + " WHERE username = ?"
                )
                .authoritiesByUsernameQuery(
                        "SELECT account.username, role.type"
                     + " FROM role"
                     + " INNER JOIN account"
                     + " ON role.account_id = account.account_id"
                     + " WHERE account.username = ?"
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/organizer/**").hasAuthority(Role.ORGANIZER)
                .antMatchers("/support/**").hasAuthority(Role.SUPPORT)
                .antMatchers("/competitor/**").hasAuthority(Role.COMPETITOR)
                .antMatchers("/profile").authenticated()
                .antMatchers("/editprofile").authenticated()
                .anyRequest().permitAll();
        http.formLogin().loginPage("/signin");
    }

}
