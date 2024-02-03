package com.itykhan.watchpricecalculator.data.entity;

import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Discount {
    private int discountQuantity;
    private BigDecimal priceForQuantity;

    public Discount() {

    }

    public Discount(int discountQuantity, BigDecimal priceForQuantity) {
        this.discountQuantity = discountQuantity;
        this.priceForQuantity = priceForQuantity;
    }

    int getDiscountQuantity() {
        return discountQuantity;
    }

    BigDecimal getPriceForQuantity() {
        return priceForQuantity;
    }

    public void setDiscountQuantity(int discountQuantity) {
        this.discountQuantity = discountQuantity;
    }

    public void setPriceForQuantity(BigDecimal priceForQuantity) {
        this.priceForQuantity = priceForQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return discountQuantity == discount.discountQuantity
                && Objects.equals(priceForQuantity, discount.priceForQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discountQuantity, priceForQuantity);
    }

    @Override
    public String toString() {
        return String.format("Discount{%d for %s}", discountQuantity, priceForQuantity);
    }
}
