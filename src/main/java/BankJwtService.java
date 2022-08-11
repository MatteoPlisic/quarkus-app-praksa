


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Singleton;

import io.smallrye.jwt.build.Jwt;


@Singleton
public class BankJwtService {
    public String generatejwt(){
        Set<String> roles = new HashSet<>(Arrays.asList("admin","writer"));
       return  Jwt.issuer("randombank-jwt").subject("accountacces-jwt").groups(roles).expiresAt(System.currentTimeMillis() + 3600).sign();
    }
    
    
}
