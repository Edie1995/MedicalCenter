
package pl.kruko.PracaInz.config;


import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private UserDetailsService userDetailsService;
		
	@Autowired
	public AppSecurityConfig(UserDetailsService userDetailsService) {
		super();
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/patientHome.html","/searchDoctor*","/addVisit*","/calendar*","/patientMedicament*","/homePatientMedicaments*",
						"/patientDiagnosis*","/homePatientDiagnosis.html","/patientsSymptoms*",
						"/symptoms*","/homeSymptoms.html","/profilePatient*").hasRole("PATIENT")
				.antMatchers("/doctorHome*","/addSchedule*","/chosenPatient*","/doctorProfile*","/homeDoctorSchedule*","/doctorSchedule*",
						"/registeredPatients*","/homeRegisteredPatient.html").hasRole("DOCTOR")
				.antMatchers("/registerAccount*","/registerAccount/patient*","/registerAccount/doctor*").permitAll()
				.antMatchers("/anonymous*").anonymous()
				.antMatchers("/login*","/logout*").permitAll()
				.antMatchers("/css/**").permitAll().anyRequest()
				.authenticated()
				.and()
				.formLogin().loginPage("/login.html")
				.defaultSuccessUrl("/home.html", true)
				.failureUrl("/login-error.html")
				.and()
				.logout()
				.logoutUrl("/logout")
				.deleteCookies("JSESSIONID");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	RestTemplate restTemplate() throws Exception {
		char[] password = "123456".toCharArray();
	    KeyStore truststore = KeyStore.getInstance("PKCS12");
	    truststore.load(new ClassPathResource("baeldung.p12").getInputStream(), password);
	    SSLContext sslContext = new SSLContextBuilder()
	      .loadTrustMaterial(truststore, new TrustSelfSignedStrategy())
	      .build();
	    SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
	    HttpClient httpClient = HttpClients.custom()
	      .setSSLSocketFactory(socketFactory)
	      .build();
	    HttpComponentsClientHttpRequestFactory factory = 
	      new HttpComponentsClientHttpRequestFactory(httpClient);
	    return new RestTemplate(factory);
	}
}
