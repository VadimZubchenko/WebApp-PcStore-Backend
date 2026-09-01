package com.vadimzu.webpcstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author vadimzubchenko
 */
@Controller
public class SpaController {
    @RequestMapping({
            "/customers",
            "/orders",
            "/parts"
            //,"/products",
            //"/staff"
    })
    public String forward() {
        return "forward:/index.html";
    }
}
