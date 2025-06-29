package org.example.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Meeting {
    private final String meetingId;
    private final String title;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final List<User> participants;

    public Meeting(String title, LocalDateTime startTime, LocalDateTime endTime, List<User> participants) {
        this.meetingId = UUID.randomUUID().toString();
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.participants = participants;
    }

    public String getMeetingId() { return meetingId; }
    public String getTitle() { return title; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public List<User> getParticipants() { return participants; }

    public boolean overlaps(Meeting other) {
        return this.startTime.isBefore(other.endTime) && other.startTime.isBefore(this.endTime);
    }
}
