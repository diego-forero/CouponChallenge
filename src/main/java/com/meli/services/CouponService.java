package com.meli.services;

import static com.meli.utils.Utilities.getKeyByValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.meli.models.CouponItems;
import com.meli.models.CouponProductValuesResult;

/**
 *
 * @author Diego Forero
 */
public class CouponService {

//    public static List<String> calculate(Map<String, Float> items, Float amount){ *** Base de código solicitada. No se usa porque se observa que no es la más óptima
    public static CouponItems calculate(Map<String, Float> items, Float amount){
        CouponItems result = new CouponItems();
        List<Float> prices = new ArrayList(items.values());
        couponAmountCheckout(prices,amount,new ArrayList<Float>(),-1f);
        List<Float> resultValues=CouponProductValuesResult.productValues;
        List<String> resultItems = new ArrayList<String>();
        for (Float value:resultValues){
            String key=getKeyByValue(items,value);
            if(key != null) resultItems.add(key);
        }
        result.setItem_ids(resultItems);
        result.setAmount(CouponProductValuesResult.totalAmount);
        return result;
    }
    static Float couponAmountCheckout(List<Float> prices, Float amount, List<Float> highestAmountPrices,Float highestAmount) {
        Float tempSum = 0f;
        for (Float price: highestAmountPrices)tempSum += price;
        if (tempSum > amount) return highestAmount;
        if (tempSum <= amount && tempSum> highestAmount){
            highestAmount=tempSum;
            CouponProductValuesResult.productValues=(ArrayList<Float>) highestAmountPrices;
            CouponProductValuesResult.totalAmount=highestAmount;
        }        
        for(int _price=0;_price<prices.size();_price++) {
            List<Float> pendingPrices = new ArrayList<Float>();
            Float _value = prices.get(_price);
            for (int pendingPrice=_price+1; pendingPrice<prices.size();pendingPrice++) pendingPrices.add(prices.get(pendingPrice));
            List<Float> highestAmountPricesTemp = new ArrayList<Float>(highestAmountPrices);
            highestAmountPricesTemp.add(_value);
            Float newhighestAmount=couponAmountCheckout(pendingPrices,amount,highestAmountPricesTemp,highestAmount);
            if(newhighestAmount>highestAmount) highestAmount=newhighestAmount;
        }
        return highestAmount;
    }
}
