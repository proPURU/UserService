package com.example.userservice.services;


import com.example.userservice.dtos.SignUpRequestDto;
import com.example.userservice.dtos.UserDto;
import com.example.userservice.exception.UserAlreadyExistsException;
import com.example.userservice.exception.UserDoesNotExistException;
import com.example.userservice.models.PayLoad;
import com.example.userservice.models.Session;
import com.example.userservice.models.SessionStatus;
import com.example.userservice.models.User;
import com.example.userservice.repositories.SessionRepository;
import com.example.userservice.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.id.uuid.StandardRandomStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.*;

@Service
public class AuthService {

   private UserRepository userRepository;
   private SessionRepository sessionRepository;
    private PasswordEncoder passwordEncoder;


   public AuthService(UserRepository userRepository, SessionRepository sessionRepository,PasswordEncoder passwordEncoder )
   {
       this.userRepository=userRepository;
       this.sessionRepository=sessionRepository;
       this.passwordEncoder=passwordEncoder;
   }
    public ResponseEntity<UserDto> login(String email,String password) throws  UserDoesNotExistException {
        Optional<User> userOptional=userRepository.findByEmail(email);
        // Need to check already there or not
        if (userOptional.isEmpty()) {
            throw new UserDoesNotExistException("User with email: " + email + " doesn't exist.");
        }

        User user=userOptional.get();
        if(!passwordEncoder.matches(password,user.getPassword()))
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        RandomStringUtils randomStringUtils = new RandomStringUtils();
        String token = RandomStringUtils.randomAscii(20);
        //HW is here -> May be need to Modify
        Map<Long,Object> payloadMap=new HashMap<>();
        PayLoad payLoad=new PayLoad();
        payLoad.setUserId(user.getId());
        payLoad.setEmail(user.getEmail());
        payloadMap.put(user.getId(),payLoad);


        // Need to implement jwt
        String jws ="imtoken";


        MultiValueMapAdapter<String, String > headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add("AUTH_TOKEN", jws);

        Session session = new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(jws);
        session.setUser(user);

        sessionRepository.save(session);


        UserDto userDto= UserDto.from(user);
        ResponseEntity<UserDto> response=new ResponseEntity<>(
                userDto,
                headers,
                HttpStatus.OK
        );
        return response;
    }


    //for new user save the id and pass to db

    public UserDto signUp( String email,String password) throws UserAlreadyExistsException {
        Optional<User> userOptional=userRepository.findByEmail(email);
        if(!userOptional.isEmpty())
        {
            throw new UserAlreadyExistsException("User with"+email+" already exists.");
        }
        User user=new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        User savedUser = userRepository.save(user);
        return UserDto.from(savedUser);
    }

    public SessionStatus validate(String token, Long userId) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

        if (sessionOptional.isEmpty()) {
            return SessionStatus.INVALID;
        }

        Session session = sessionOptional.get();

        if (!session.getSessionStatus().equals(SessionStatus.ACTIVE)) {
            return SessionStatus.EXPIRED;
        }

//        if (!session.getExpiringAt() > new Date()) {
//            return SessionStatus.EXPIRED;
//        }

        return SessionStatus.ACTIVE;
    }







}
