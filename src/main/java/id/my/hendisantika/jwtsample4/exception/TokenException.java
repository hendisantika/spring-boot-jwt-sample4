package id.my.hendisantika.jwtsample4.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-jwt-sample4
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 12/02/26
 * Time: 06.40
 * To change this template use File | Settings | File Templates.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenException extends RuntimeException {

    public TokenException(String token, String message) {
        super(String.format("Failed for [%s]: %s", token, message));
    }
}
