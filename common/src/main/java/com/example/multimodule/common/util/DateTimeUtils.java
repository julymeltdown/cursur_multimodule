package com.example.multimodule.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    private DateTimeUtils() {
        // 유틸리티 클래스는 인스턴스화를 방지합니다.
    }
    
    /**
     * LocalDateTime을 기본 포맷(yyyy-MM-dd HH:mm:ss)의 문자열로 변환합니다.
     *
     * @param dateTime 변환할 LocalDateTime
     * @return 포맷된 문자열
     */
    public static String format(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(DEFAULT_FORMATTER);
    }
    
    /**
     * LocalDateTime을 지정된 포맷의 문자열로 변환합니다.
     *
     * @param dateTime 변환할 LocalDateTime
     * @param pattern 날짜 포맷 패턴
     * @return 포맷된 문자열
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
    
    /**
     * 문자열을 LocalDateTime으로 파싱합니다.
     *
     * @param dateTimeStr 파싱할 문자열
     * @return 파싱된 LocalDateTime
     */
    public static LocalDateTime parse(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(dateTimeStr, DEFAULT_FORMATTER);
    }
    
    /**
     * 문자열을 지정된 포맷으로 LocalDateTime으로 파싱합니다.
     *
     * @param dateTimeStr 파싱할 문자열
     * @param pattern 날짜 포맷 패턴
     * @return 파싱된 LocalDateTime
     */
    public static LocalDateTime parse(String dateTimeStr, String pattern) {
        if (dateTimeStr == null || dateTimeStr.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
    }
} 