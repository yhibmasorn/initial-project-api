package yms.api.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import yms.api.constant.CommonConstant;
import yms.api.model.Session;
import yms.api.repository.RolePermissionRepository;
import yms.api.repository.SessionRepository;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private SessionRepository sessionRepository;

	@Autowired
	private RolePermissionRepository rolePermissionRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authorizationHeader = request.getHeader("Authorization");

		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			String sessionId = authorizationHeader.substring(7);
			Date currentDate = new Date();

			Session session = new Session();
			session.setSessionID(sessionId);
			session.setIsDeleted(CommonConstant.FLAG_N);

			List<Session> sessionList = sessionRepository.find(session);

			if(sessionList == null) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("SessionID is invalid.");

				return;

			}else{
				session = sessionList.get(0);

				if(session.getExpireDate().before(currentDate)) {
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					response.getWriter().write("SessionID is expired.");

					return;

				}

				// Extend session expireDate
				Calendar expireDate = Calendar.getInstance();
				expireDate.setTime(currentDate);
				expireDate.add(Calendar.MINUTE, 10);
				
				session.setExpireDate(expireDate.getTime());
				
				sessionRepository.update(session.getSessionID(), session.getExpireDate(), "SYSTEM", currentDate);
				
				List<String> pemissionNoList = rolePermissionRepository.getPermisionNoList(session.getUserLogin().getEmployee().getRole().getRoleNo());
				
				Collection<? extends GrantedAuthority> authorities = mapRolesToAuthorities(pemissionNoList);
				
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(session.getUserLogin().getUsername(), null, authorities);

				SecurityContextHolder.getContext().setAuthentication(authenticationToken);

			}

		}
		
		filterChain.doFilter(request, response);
		
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(List<String> permissionNoList){
		return permissionNoList.stream()
				.map(role -> new SimpleGrantedAuthority(role))
				.collect(Collectors.toList());
		
	}
	
}
