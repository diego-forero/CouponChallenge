package com.meli.models;

import java.util.List;

public class CouponItems {
     
    private List<String> item_ids;
    private Float amount;

    public CouponItems() {
    }
    
    public List<String> getItem_ids() {
        return item_ids;
    }

    public void setItem_ids(List<String> item_ids) {
        this.item_ids = item_ids;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }    
}