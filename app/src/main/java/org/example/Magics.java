package org.example;

public enum Magics {
    /**
     * 人が単位分あたりに移動する距離. 不動産サイト ホームズ を参考
     * “徒歩◯分”は正しい？ 駅までの実際の所要時間を調べるには
     */
    DISTANCE_MOVED_PER_MINUTE("80"),
    /**
     *
     */
    NUM_0("0"),
    /**
     *
     */
    NUM_1("1"),
    /**
     *
     */
    NUM_2("2"),
    /**
     *
     */
    NUM_3("3"),
    /**
     *
     */
    NUM_4("4"),
    /**
     *
     */
    NUM_5("5"),
    /**
     *
     */
    NUM_6("6"),
    /**
     *
     */
    NUM_7("7"),
    /**
     *
     */
    NUM_8("8"),
    /**
     *
     */
    NUM_9("9"),
    /**
     *
     */
    NUM_10("10"),
    /**
     *
     */
    NUM_11("11"),
    /**
     *
     */
    NUM_12("12"),
    /**
     *
     */
    NUM_13("13"),
    /**
     *
     */
    NUM_14("14"),
    /**
     *
     */
    NUM_15("15"),
    /**
     *
     */
    NUM_16("16"),
    /**
     *
     */
    NUM_17("17"),
    /**
     *
     */
    NUM_18("18"),
    /**
     *
     */
    NUM_19("19"),
    /**
     *
     */
    NUM_20("20"),
    /**
     *
     */
    NUM_21("21"),
    /**
     *
     */
    NUM_22("22"),
    /**
     *
     */
    NUM_23("23"),
    /**
     *
     */
    NUM_24("24"),
    /**
     *
     */
    NUM_25("25"),
    /**
     *
     */
    NUM_26("26"),
    /**
     *
     */
    NUM_27("27"),
    /**
     *
     */
    IS_UNEXPECTED_NUMERIC_VAL("-1"),

    /**
     *
     */
    IS_EMPTY("EMPTY");

    /**
     *
     */
    private final String value;

    Magics(final String v) {
        this.value = v;
    }

    /**
     *
     * @return String
     */
    public String getValue() {
        return value;
    }
}
