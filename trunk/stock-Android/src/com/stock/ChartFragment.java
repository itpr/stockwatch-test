

package com.stock;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.stock.shared.QuoteProxy;
import com.stock.shared.charts.TimePeriod;

public class ChartFragment extends Fragment {
	
	private RelativeLayout view = null;

	private Long chartId;
	
	public ChartFragment(){

	}
	
	public ChartFragment(Long chartId){
		this.chartId = chartId;
	}
	
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        AsyncFetchQuotes asyncFetchQuotes = new AsyncFetchQuotes(getActivity(), this);
        asyncFetchQuotes.execute(chartId);
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = (RelativeLayout) inflater.inflate(R.layout.chartlayout, null);
		return view;
	}

	public Long getChartId() {
		return chartId;
	}

	public void setChartId(Long chartId) {
		this.chartId = chartId;
	}

	public void setQuotes(List<QuoteProxy> quotes){
		CandleStickChart candleChart = new CandleStickChart(getActivity());
		YAxis yAxis = candleChart.getAxisLeft();
		yAxis.setStartAtZero(false);
		XAxis xAxis = candleChart.getXAxis();
		xAxis.setAvoidFirstLastClipping(true);
		
        ArrayList<CandleEntry> yVals = new ArrayList<CandleEntry>();
        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < TimePeriod.ONE_MINUTE.getTicks(); i++) {
        	QuoteProxy quote = quotes.get(i);
            yVals.add(new CandleEntry(i, quote.getHigh().floatValue(), quote.getLow().floatValue(), 
            		quote.getOpen().floatValue(), quote.getClose().floatValue()));
            xVals.add(quote.getDate().toLocaleString());
        }


        CandleDataSet set = new CandleDataSet(yVals, "Data Set");
        set.setAxisDependency(AxisDependency.LEFT);
        set.setColor(Color.rgb(30, 45, 200));

        CandleData data = new CandleData(xVals, set);
        candleChart.setData(data);
		view.addView(candleChart);
	}

}
