package com.pixelTrice.firstSpringBootApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration 
public class ConfigOAuth2 extends AuthorizationServerConfigurerAdapter{
	
	   private String clientId = "pixeltrice";
	   private String clientSecret = "pixeltrice-secret-key";
	   private String privateKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
			   "MIIEpAIBAAKCAQEAyJzxpx3wDZQ3sDP/LmxVMShPvniAFjS9OU7QnACbB09vlKhY\n" +
			   "gV4BGRE1Sgh/YAyEaSGI4SZrpW1SWwS/X+A07wzpqB3kEJDpgEAQUzuN94JmKwmU\n" +
			   "5ZEj7XbKas3eQKi796VOhV4K9ECgD72O13KojkQLNNcI3Sw/dBDBsMOA39Sq1+Os\n" +
			   "vOUUAwZTFcTUtgWJ1bU78rXzZ9wQAcXjpghAGsG6gEYWhu5j3fRpRNEvnpw0NlO1\n" +
			   "3hTE0OomvDaYMKZgqnvqb34ktnRtis2OoZhGNyp3z90qfeRfExxsMzVKg50nBYyJ\n" +
			   "MqVt1UpKPNK7y7s78HZa1/qe1jc3AyDENz7+UQIDAQABAoIBAFeP0RYNRiLXJ7tb\n" +
			   "5qyfkkujebL8K4QFvvXNhdYhmYkGTgCHviGxIy+KyESpEtecfwrYHUOQnnmVDtY1\n" +
			   "a0ZwuQoCC9ZaPZpRdemJjNaXbu6yVt/ADeJpiFH0gIM/mhuEyo5b87rYglV8A6D7\n" +
			   "Luaw4AtAfdexZ+gJkSSaz7/UhWKsTY7F/ywRnqtAxDTQXJCVJzk5U3LD8OO0LH8G\n" +
			   "qxFmqShwZPNZI1WHhnMuUAwxne3JqnuJKFSd8R9gOzjwWFDY/KtyzmlOCiacXGkj\n" +
			   "0gkjYEs2NeUcM0/PO8/T8CV9GHsieTGtHaLbv5dgwZe6fMqCpxVJ039McDoe4XcJ\n" +
			   "9wMR5AECgYEA5jU+ol/Q/HRdjEcP9FoSr0epEWbZRBoSGFaJScx0YygeG4xlet7N\n" +
			   "um4WLwPxZEJd8r4zaLz4HpIwbDz0fn2Cg4lmBkcp/VZOZeGazyC5g9SexzD342q3\n" +
			   "b00d8nx74baHh2i8zeSz57k55crNF2Uy4uyNIjwzR1MriBol518x70ECgYEA3xbd\n" +
			   "3sUi0PcBocTwBSjytA0CSfVNJ/IzCFlUXrYKp02/uAN1J4f9wlG1303hfBdTq2Ap\n" +
			   "pU+FgP6SitSj4VNC9WfBXVdtAusjbzO1E+yb+7ynTFIpcSxWK5daAIZlFm2RKMMV\n" +
			   "629dyCD7rvs3h+oRctEx8lFx4OY9ok9XHQ5iWxECgYEA1yRrpNRnO4iE5NSE1JhZ\n" +
			   "XfZayyiTZFH5F0XIiG/LVd/oQ1aygGI5YkH/+XB16H0dz/5xjTENsYRuPLm6xXwG\n" +
			   "vWdrMfLQCzLdkdmGFI1A0zOiMAZTpPXW+O3ZTwWHwSoU/zejO8jBWY6BzX/6rCKn\n" +
			   "q+vm+lfIlJ/lzwyg39rXkAECgYAKekJ1YgDem5tCA8PaGjA78Jh47MJw69klvTxP\n" +
			   "LnGVpfspI1BvSN3MMJZAJKJAd8AAPoX2M6PriQlM5+vCe2ybpgHY5nBCQNTrNhx9\n" +
			   "ERHKYvPgi8NSg2iQp40clTNOOFsm9PKkmhhne9UWRMo44e9r2xNjdBvLGkUuyFcp\n" +
			   "EDHiYQKBgQDWqLZMDnQsMZxBYuEaaj3SKafygJMA8/cbbHp7avI+e+Ha+stNHJkG\n" +
			   "gTG73PGbDwPzBVn5yRUU1FuqIc8PCoxllFPwxu07jmA1yP++pxa0QOtRhiyKj0ps\n" +
			   "oeBCp+1d1Qw8Ev5piZZETzAa/qpg4sHX4+mBtgM3CaiRkavs2RzjPA==\n" +
			   "-----END RSA PRIVATE KEY-----";
	   private String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
			   "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyJzxpx3wDZQ3sDP/LmxV\n" +
			   "MShPvniAFjS9OU7QnACbB09vlKhYgV4BGRE1Sgh/YAyEaSGI4SZrpW1SWwS/X+A0\n" +
			   "7wzpqB3kEJDpgEAQUzuN94JmKwmU5ZEj7XbKas3eQKi796VOhV4K9ECgD72O13Ko\n" +
			   "jkQLNNcI3Sw/dBDBsMOA39Sq1+OsvOUUAwZTFcTUtgWJ1bU78rXzZ9wQAcXjpghA\n" +
			   "GsG6gEYWhu5j3fRpRNEvnpw0NlO13hTE0OomvDaYMKZgqnvqb34ktnRtis2OoZhG\n" +
			   "Nyp3z90qfeRfExxsMzVKg50nBYyJMqVt1UpKPNK7y7s78HZa1/qe1jc3AyDENz7+\n" +
			   "UQIDAQAB\n" +
			   "-----END PUBLIC KEY-----";
	   
	   @Autowired
	   @Qualifier("authenticationManagerBean")
	   private AuthenticationManager authenticationManager;
	   
	   @Autowired
	   PasswordEncoder passwordEncoder;
	   
	   @Bean
	   public JwtAccessTokenConverter tokenEnhancer() {
	      JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	      converter.setSigningKey(privateKey);
	      converter.setVerifierKey(publicKey);
	      return converter;
	   }
	   
	   @Bean
	   public JwtTokenStore tokenStore() {
	      return new JwtTokenStore(tokenEnhancer());
	   }
	   
	   @Override
	   public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	      endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
	      .accessTokenConverter(tokenEnhancer());
	   }
	   @Override
	   public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
	      security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	   }
	   @Override
	   public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
	      clients.inMemory().withClient(clientId).secret(passwordEncoder.encode(clientSecret)).scopes("read", "write")
	         .authorizedGrantTypes("password", "refresh_token").accessTokenValiditySeconds(20000)
	         .refreshTokenValiditySeconds(20000);

	   }

}
