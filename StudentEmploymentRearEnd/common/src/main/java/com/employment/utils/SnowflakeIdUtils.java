package com.employment.utils;

/**
 * @program: StudentEmploymentSystem
 * @ClassName SnowflakeIdUtils
 * @author: c9noo
 * @create: 2023-11-26 20:09
 * @Version 1.0
 * 雪花算法的工具类
 **/
public class SnowflakeIdUtils {
    // 起始时间戳，如：2021-01-01 00:00:00
    private static final long EPOCH = 1609459200000L;
    // 工作机器ID位数
    private static final long WORKER_ID_BITS = 5L;
    // 数据中心ID位数
    private static final long DATA_CENTER_ID_BITS = 5L;
    // 最大工作机器ID
    private static final long MAX_WORKER_ID = -1L ^ (-1L << WORKER_ID_BITS);
    // 最大数据中心ID
    private static final long MAX_DATA_CENTER_ID = -1L ^ (-1L << DATA_CENTER_ID_BITS);
    // 序列号位数
    private static final long SEQUENCE_BITS = 12L;
    // 工作机器ID左移位数
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    // 数据中心ID左移位数
    private static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    // 时间戳左移位数
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;
    // 序列号掩码
    private static final long SEQUENCE_MASK = -1L ^ (-1L << SEQUENCE_BITS);

    private long workerId;
    private long dataCenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    /**
     * 初始化工作机器ID和数据中心ID
     *
     * @param workerId     工作机器ID
     * @param dataCenterId 数据中心ID
     * @throws IllegalArgumentException 如果workerId或dataCenterId超出合法范围
     */
    public SnowflakeIdUtils(long workerId, long dataCenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException("工作机器ID不能大于" + MAX_WORKER_ID + "或小于0");
        }
        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
            throw new IllegalArgumentException("数据中心ID不能大于" + MAX_DATA_CENTER_ID + "或小于0");
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    /**
     * 生成唯一ID
     *
     * @return 唯一ID
     * @throws RuntimeException 如果时钟回退异常
     */
    public synchronized long generateId() {
        long timestamp = System.currentTimeMillis();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("时钟回退异常，拒绝生成ID");
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;

        return ((timestamp - EPOCH) << TIMESTAMP_LEFT_SHIFT) |
                (dataCenterId << DATA_CENTER_ID_SHIFT) |
                (workerId << WORKER_ID_SHIFT) |
                sequence;
    }

    /**
     * 获取下一个毫秒的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间戳
     * @return 下一个毫秒的时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
