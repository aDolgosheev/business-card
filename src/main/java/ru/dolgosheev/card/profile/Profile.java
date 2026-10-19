package ru.dolgosheev.card.profile;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Содержимое визитки. Заполняется из profile.yml, чтобы правка текста не требовала правки кода.
 */
@ConfigurationProperties(prefix = "profile")
public record Profile(
        String name,
        String title,
        String location,
        String about,
        List<Contact> contacts,
        List<Experience> experience,
        List<SkillGroup> skills,
        List<Project> projects,
        List<Education> education
) {

    public record Contact(String type, String label, String url) {
    }

    public record Experience(String company, String role, String period, List<String> highlights) {
    }

    public record SkillGroup(String group, List<String> items) {
    }

    public record Project(String name, String description, List<String> stack, String url, String status) {
    }

    public record Education(String title, String organization, String period, String url) {
    }
}
