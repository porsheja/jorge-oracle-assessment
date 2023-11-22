package com.assessment.jorgeoracleassessment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for our test view.
 * 
 * @author Jorge Gonzalez
 */
@Controller
public class ViewController {
    /**
     * Method which returns the name for our test_page.html
     * page which will work as our only view.
     * @return the name of our html file (view).
     */
    @GetMapping("/")
    public String test_page() {
        return "test_page";
    }
}
