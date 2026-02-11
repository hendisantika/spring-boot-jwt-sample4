package id.my.hendisantika.jwtsample4.service;

import id.my.hendisantika.jwtsample4.repository.RefreshTokenRepository;
import id.my.hendisantika.jwtsample4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-jwt-sample4
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 12/02/26
 * Time: 06.55
 * To change this template use File | Settings | File Templates.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;
    @Value("${application.security.jwt.refresh-token.cookie-name}")
    private String refreshTokenName;
}
