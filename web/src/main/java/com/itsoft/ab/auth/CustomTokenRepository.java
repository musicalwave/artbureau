package com.itsoft.ab.auth;

import com.itsoft.ab.model.TokenModel;
import com.itsoft.ab.persistence.TokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Павел
 * Date: 08.03.14
 * Time: 20:20
 * To change this template use File | Settings | File Templates.
 */
public class CustomTokenRepository  implements PersistentTokenRepository {

    @Autowired
    private TokenMapper tokenMapper;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        tokenMapper.insertToken(token.getTokenValue(), token.getDate(), token.getSeries(), token.getUsername());
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        tokenMapper.updateToken(tokenValue, lastUsed, series);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        TokenModel token= tokenMapper.getTokenForSeries(seriesId);
        return new PersistentRememberMeToken(token.getUsername(), token.getSeries(),
                token.getTokenValue(), token.getDate());
    }

    @Override
    public void removeUserTokens(String username) {
        tokenMapper.deleteToken(username);
    }
}
