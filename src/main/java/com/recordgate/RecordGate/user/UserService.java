package com.recordgate.RecordGate.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> allUsersList(){
        return userRepository.findAll();
    }

    public void saveUser(User user) {
         userRepository.save(user);
    }
    public User get(Long id) throws userNotFoundExeption {
       Optional<User> result =  userRepository.findById(id);
       if(result.isPresent()){
           return result.get();
       }else {
           throw new userNotFoundExeption("Could not find any user with ID "+ id);
        }
    }

    public void delete(Long id) throws userNotFoundExeption {
       Long count =  userRepository.countById(id);
        if(count == null || count == 0){
            throw new userNotFoundExeption("Could not find any user with ID "+ id);
        }
        userRepository.deleteById(id);
    }
}
