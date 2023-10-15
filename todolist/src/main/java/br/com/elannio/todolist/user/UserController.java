package br.com.elannio.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;
/**
 * Modificador
 * public
 * private
 * protected
 */

 @RestController
 @RequestMapping("/users")
public class UserController {

    /**
     * String
     * Interger
     * Double
     * Float
     * char
     * Date
     * void
     */
    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel){
        var user = this.userRepository.findByUsername(userModel.getUsername());

        if (user != null){
            // Mensagem de erro
            // Status Code
            return ResponseEntity.status(400).body("Usuário Já Existe");
        }

        var passwordHashed = BCrypt.withDefaults()
                .hashToString(12,userModel.getPassword().toCharArray());

        userModel.setPassword(passwordHashed);
        var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userCreated);
    }
}
    
}
