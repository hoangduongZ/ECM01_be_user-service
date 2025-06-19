package com.ecomerce.user_service.kafka.event;

import java.time.ZonedDateTime;

public class GenericEventWrapper<T> {
    private String eventId;
    private String eventType;
    private int eventVersion;
    private String source;
    private ZonedDateTime timestamp;
    private T data;
    private String traceId;

    public GenericEventWrapper(String eventId, String eventType, int eventVersion, String source, ZonedDateTime timestamp, T data, String traceId) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.eventVersion = eventVersion;
        this.source = source;
        this.timestamp = timestamp;
        this.data = data;
        this.traceId = traceId;
    }

    public GenericEventWrapper() {
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getEventVersion() {
        return eventVersion;
    }

    public void setEventVersion(int eventVersion) {
        this.eventVersion = eventVersion;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
}