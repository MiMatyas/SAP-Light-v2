package cz.matyas.SAP.Light.v1.controller;

import cz.matyas.SAP.Light.v1.dto.UserDTO;
import cz.matyas.SAP.Light.v1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    List<UserDTO> getAll() {

        return userService.getAll();
    }
    @GetMapping("/{userId}")
    UserDTO getUserById(@PathVariable("userId")Long id){

        return userService.getUserById(id);
    }
    @PostMapping("/create")
    UserDTO createUser(@RequestBody UserDTO userDTO){

        return userService.createUser(userDTO);
    }
    @PutMapping("edit/{userId}")
    UserDTO editUserById(@PathVariable("userId")Long id, @RequestBody UserDTO userDTO){

        return userService.editUserById(id, userDTO);
    }
    @DeleteMapping("/delete/{userId}")
    UserDTO deleteUserById(@PathVariable("userId")Long id){

        return userService.deleteUserById(id);
    }
}

