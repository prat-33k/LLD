package org.example;

import org.example.model.Meeting;
import org.example.model.User;
import org.example.service.MeetingService;
import org.example.service.UserService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        MeetingService meetingService = new MeetingService();

        User alice = userService.addUser("u1", "Alice");
        User bob = userService.addUser("u2", "Bob");

        LocalDateTime start1 = LocalDateTime.of(2025, 7, 1, 10, 0);
        LocalDateTime end1 = LocalDateTime.of(2025, 7, 1, 11, 0);

        List<User> attendees = Arrays.asList(alice, bob);
        boolean scheduled = meetingService.scheduleMeeting("Team Sync", start1, end1, attendees);
        System.out.println("Meeting scheduled? " + scheduled);

        List<Meeting> aliceMeetings = meetingService.getMeetingsForUser(alice.getUserId());
        System.out.println("Alice's meetings:");
        for (Meeting m : aliceMeetings) {
            System.out.println(" - " + m.getTitle() + " [" + m.getStartTime() + "]");
        }    }
}