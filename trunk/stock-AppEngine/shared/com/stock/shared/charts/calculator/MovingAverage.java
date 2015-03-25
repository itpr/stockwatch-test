package com.stock.shared.charts.calculator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.Queue;

public class MovingAverage implements Serializable {

	private static final long serialVersionUID = -2657623024954852877L;

    private final Queue<BigDecimal> queue = new LinkedList<BigDecimal>();
    private final int period;
    private BigDecimal sum = BigDecimal.ZERO;

    public MovingAverage(int period) {
        this.period = period;
    }

    public void add(BigDecimal num) {
        sum = sum.add(num);
        queue.add(num);
        if (queue.size() > period) {
            sum = sum.subtract(queue.remove());
        }
    }

    public BigDecimal getAverage() {
        if (queue.isEmpty()) return BigDecimal.ZERO;
        BigDecimal divisor = BigDecimal.valueOf(queue.size());
        return sum.divide(divisor, 2, RoundingMode.HALF_UP);
    }
}
