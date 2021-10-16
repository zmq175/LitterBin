package info.chengzhi.chengzhi_litter_bin.app.domain.utils;

import com.github.rxyor.common.util.lang2.RadixUtil;
import org.apache.commons.lang3.RandomUtils;

import java.time.LocalDate;

public class TicketCodeUtils {
  /**
   * 表示字符，0~9,A~Z，去除0、O、I、L 4个易混淆的字符还是32位
   */
  public final static char[] DIGITS = {
      '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
      'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M',
      'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
      'Y', 'Z'};

  /**
   * 开始年份，用户计算年份偏移量
   */
  public final static int BEGIN_YEAR = 2019;

  /**
   * 随机位由5位字符表示
   */
  public final static int RANDOM_LEN = 5;

  /**
   * 5为32进制能表示的最大无符号整数为32^5-1
   */
  private final static int MAX_RANDOM_INT = (int) (Math.pow(DIGITS.length, RANDOM_LEN) - 1);

  private static final RadixUtil INSTANCE = RadixUtil.builder().digits(DIGITS).build();

  /**
   *<p>
   *生成一个核销码
   *</p>
   *
   * @author
   * @date 2019-12-10 周二 10:35:16
   * @return
   */
  public static String genTicketCode() {
    LocalDate localDate = LocalDate.now();

    //年份偏移量字符表示
    char yearChar = DIGITS[Math.abs(localDate.getYear() - BEGIN_YEAR)];
    //一年的第几天32进制字符表示
    String dayOfYearString = INSTANCE.convert2String(localDate.getDayOfYear());
    //32*32=1024>366, 2位即可表示天的偏移量, 不够2位补32进制第0位字符
    if (dayOfYearString.length() == 1) {
      dayOfYearString = DIGITS[0] + dayOfYearString;
    }

    String randomCode = genRandomCode();

    StringBuilder verifyCode = new StringBuilder(randomCode);

    //sb式混淆
    //下标1位置插入年份偏移标识
    verifyCode.insert(1, yearChar);
    //下标3位置插入天偏移标识第1位
    verifyCode.insert(3, dayOfYearString.charAt(0));
    //下标5位置插入天偏移标识第2位
    verifyCode.insert(5, dayOfYearString.charAt(1));

    return verifyCode.toString();
  }

  /**
   *生成（0~32^5-1）之间的随机整数，并转换为指定表示字符的32进制
   * @return
   */
  private static String genRandomCode() {
    final int random = RandomUtils.nextInt(0, MAX_RANDOM_INT);
    String randomCode = INSTANCE.convert2String(random);
    StringBuilder sb = new StringBuilder();
    //不足5位，补齐5位
    for (int i = randomCode.length(); i < RANDOM_LEN; i++) {
      sb.append(DIGITS[0]);
    }
    return sb.toString() + randomCode;
  }

}
