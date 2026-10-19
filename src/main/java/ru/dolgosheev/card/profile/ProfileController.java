package ru.dolgosheev.card.profile;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final Profile profile;

    public ProfileController(Profile profile) {
        this.profile = profile;
    }

    @GetMapping
    public Profile getProfile() {
        return profile;
    }
}
