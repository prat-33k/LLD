package org.example.service;

import org.example.model.Meeting;
import org.example.model.User;

import java.time.LocalDateTime;
import java.util.*;

public class MeetingService {
    private final Map<String, List<Meeting>> userMeetings = new HashMap<>();

    public boolean scheduleMeeting(String title, LocalDateTime start, LocalDateTime end, List<User> users) {
        Meeting newMeeting = new Meeting(title, start, end, users);

        for (User user : users) {
            List<Meeting> meetings = userMeetings.getOrDefault(user.getUserId(), new ArrayList<>());
            for (Meeting meeting : meetings) {
                if (meeting.overlaps(newMeeting)) {
                    System.out.println("Conflict for user: " + user.getName());
                    return false;
                }
            }
        }

        for (User user : users) {
            userMeetings.putIfAbsent(user.getUserId(), new ArrayList<>());
            userMeetings.get(user.getUserId()).add(newMeeting);
        }

        return true;
    }

    public List<Meeting> getMeetingsForUser(String userId) {
        return userMeetings.getOrDefault(userId, new ArrayList<>());
    }

    public boolean cancelMeeting(String meetingId) {
        boolean cancelled = false;
        for (List<Meeting> meetings : userMeetings.values()) {
            cancelled |= meetings.removeIf(m -> m.getMeetingId().equals(meetingId));
        }
        return cancelled;
    }
}

