package com.example.android.guessthecar;

public class Answer {
    public Answer(int carImageResId, int brandImageResId, String message) {
        this.brandImageResId = brandImageResId;
        this.carImageResId = carImageResId;
        this.message = message;
    }

    private int carImageResId;

    public int getCarImageResId() {
        return carImageResId;
    }

    private int brandImageResId;

    public int getBrandImageResId() {
        return brandImageResId;
    }

    private String message;

    public String getMessage() {
        return message;
    }
}
