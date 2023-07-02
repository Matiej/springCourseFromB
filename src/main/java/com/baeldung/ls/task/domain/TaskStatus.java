package com.baeldung.ls.task.domain;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

public enum TaskStatus {
    NEW, IN_PROGRESS, FINISHED, CANCELED;

    public static Optional<TaskStatus> paraseStringToOrder(String taskStatus) {
        return Arrays.stream(values())
                .filter(status -> StringUtils.equalsIgnoreCase(status.name(), taskStatus))
                .findAny();
    }
}
