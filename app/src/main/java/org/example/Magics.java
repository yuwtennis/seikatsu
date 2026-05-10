package org.example;

/**
 * Magics.
 *
 */
public enum Magics {
  /** 人が単位分あたりに移動する距離. 不動産サイト ホームズ を参考 “徒歩◯分”は正しい？ 駅までの実際の所要時間を調べるには */
  DISTANCE_MOVED_PER_MINUTE("80");

  /** Value. */
  private final String value;

  Magics(final String v) {
    this.value = v;
  }

  /**
   * @return String
   */
  public String getValue() {
    return value;
  }
}
