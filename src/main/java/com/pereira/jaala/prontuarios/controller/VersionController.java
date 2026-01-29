package com.pereira.jaala.prontuarios.controller;

import com.pereira.jaala.prontuarios.dto.*;
import com.pereira.jaala.prontuarios.model.*;
import com.pereira.jaala.prontuarios.service.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/version")
public class VersionController {

    @GetMapping
    public String getVersion(){
        return "1.4.2";
    }
}
