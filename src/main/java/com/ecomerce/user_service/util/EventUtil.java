package com.ecomerce.user_service.util;

import com.ecomerce.user_service.kafka.event.GenericEventWrapper;

import java.time.ZonedDateTime;
import java.util.UUID;

public abstract class EventUtil {
    private EventUtil() {
    }

    public static <T> GenericEventWrapper<T> createEvent(
            String eventType,
            T data,
            String source,
            String traceId
    ) {
        GenericEventWrapper<T> eventWrapper = new GenericEventWrapper<>();
        eventWrapper.setEventId(UUID.randomUUID().toString());
        eventWrapper.setEventType(eventType);
        eventWrapper.setEventVersion(1);
        eventWrapper.setSource(source);
        eventWrapper.setTimestamp(ZonedDateTime.now());
        eventWrapper.setData(data);
        eventWrapper.setTraceId(traceId != null ? traceId : UUID.randomUUID().toString());
        return eventWrapper;
    }
}
