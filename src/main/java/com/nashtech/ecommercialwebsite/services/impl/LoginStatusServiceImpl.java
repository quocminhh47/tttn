//package com.nashtech.ecommercialwebsite.services.impl;
//
//import com.nashtech.ecommercialwebsite.data.entity.Staff;
//import com.nashtech.ecommercialwebsite.data.repository.UserRepository;
//import com.nashtech.ecommercialwebsite.dto.response.LoginStatusResponse;
//import com.nashtech.ecommercialwebsite.exceptions.ResourceNotFoundException;
//import com.nashtech.ecommercialwebsite.security.jwt.JwtUtils;
//import com.nashtech.ecommercialwebsite.services.JwtService;
//import com.nashtech.ecommercialwebsite.services.LoginStatusService;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//import javax.servlet.http.HttpServletRequest;
//
//@Service
//@AllArgsConstructor
//public class LoginStatusServiceImpl implements LoginStatusService {
//
//    private final JwtService jwtService;
//
//    private final JwtUtils jwtUtils;
//
//    private final UserRepository userRepository;
//
//    @Override
//    public LoginStatusResponse getLoginStatus(HttpServletRequest request) {
//
//        String token = jwtService.parseJwt(request);
//        //token is valid
//        if (token != null && jwtUtils.validateJwtToken(token)) {
//            String username = jwtService.getUsernameFromToken(token);
//
//            Staff staff = userRepository.findAccountByUsername(username)
//                    .orElseThrow(() -> new ResourceNotFoundException(
//                            String.format("Username %s not found ", username)));
//
//            String fullName = staff.getFirstName() + ' ' + staff.getLastName();
//
//            return new LoginStatusResponse(fullName, staff.getRole().getRoleName());
//        }
//
//        return new LoginStatusResponse(null, null);
//    }
//}
