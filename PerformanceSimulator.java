/**
 * ETFと投資信託のパフォーマンス比較シミュレータ
 *
 * @author hidee
 */
public class PerformanceSimulator {

    /** 年間利回り */
    private static final double ANNUAL_YIELD = 1.0700;
    /** 年間配当利回り */
    private static final double ANNUAL_DIVIDEND_YIELD = 0.0250;
    /** 年間値上がり利益 */
    private static final double ANNUAL_CAPITAL_GAINS_YIELD = ANNUAL_YIELD - ANNUAL_DIVIDEND_YIELD;

    /** 信託報酬：ETF */
    private static final double ETF_TRUST_FEE = 0.0027;
    /** 信託報酬：投資信託 */
    private static final double MF_TRUST_FEE = 0.0050;

    /**
     * 取引手数料（率）
     * 値はSBI証券の実績値（2014/05/28時点）
     */
    private static final double SALES_COMISSION = 199.0000 / 150000;

    /** 初期投資額 */
    private static final int INIT_INVESTMENT = 200000;

    /** シミュレーション年数 */
    private static final int NUMBER_OF_YEARS = 20;

    /** 譲渡益・配当の税金 */
    private static final double TAX = 0.2000;

    /**
     * メイン関数
     * @param args 使用しない
     */
    public static void main(String[] args) {

        // Mutual Funds
        System.out.println("---------Mutual Fund---------");
        for (int i = 0; i <= NUMBER_OF_YEARS; i++) {
            System.out.println(i + "年目：" + calcNetValueByMF(INIT_INVESTMENT, i));
        }

        // ETF
        System.out.println("---------ETF---------");
        for (int i = 0; i <= NUMBER_OF_YEARS; i++) {
            System.out.println(i + "年目：" + calcNetValueByEtf(INIT_INVESTMENT, i));
        }
    }

    /**
     * ETFに投資時の正味価格を計算する。
     *
     * @param initInvestment 初期投資額
     * @param numberOfYears シミュレーション年数
     * @return 正味価格
     */
    private static int calcNetValueByEtf(int initInvestment, int numberOfYears) {

        // 運用期間が0の場合、手数料は引かずに原価を返却する。
        if (numberOfYears == 0) {
            return initInvestment;
        }

        // 購入後評価額
        double marketValue = subtractFee(initInvestment);

        double sumOfReinvest = 0;
        for (int i = 1; i <= numberOfYears; i++) {

            // インカム再投資後の利益合計（正味価格の合計）
            double incomeGain = marketValue * ANNUAL_DIVIDEND_YIELD * (1 - TAX);
            sumOfReinvest += calcNetValueByEtf((int) incomeGain, numberOfYears - i);

            // 現在評価額
            marketValue = marketValue * (ANNUAL_CAPITAL_GAINS_YIELD - ETF_TRUST_FEE);
        }

        double netValue = subtractFee(marketValue) - (subtractFee(marketValue) - initInvestment) * TAX;
        return (int) (netValue + sumOfReinvest);
    }

    /**
     * 投資信託に投資時の正味価格を計算する。
     *
     * @param initInvestment 初期投資額
     * @param numberOfYears シミュレーション年数
     * @return 正味価格
     */
    private static int calcNetValueByMF(int initInvestment, int numberOfYears) {

        double current = initInvestment;
        for (int i = 1; i <= numberOfYears; i++) {
            current = current * (ANNUAL_YIELD - MF_TRUST_FEE);
        }

        double capitalGain = current - initInvestment;
        return (int) (current - (capitalGain * TAX));
    }

    /**
     * 売買手数料を減算する。
     *
     * @param amount 金額
     * @return 売買手数料を引いた後の金額
     */
    private static double subtractFee(double amount) {
        return amount * (1 - SALES_COMISSION);
    }

}
