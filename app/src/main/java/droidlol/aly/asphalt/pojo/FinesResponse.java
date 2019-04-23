package droidlol.aly.asphalt.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FinesResponse {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("first_letter")
    @Expose
    private String firstLetter;
    @SerializedName("second_letter")
    @Expose
    private String secondLetter;
    @SerializedName("third_letter")
    @Expose
    private String thirdLetter;
    @SerializedName("numbers")
    @Expose
    private String numbers;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getSecondLetter() {
        return secondLetter;
    }

    public void setSecondLetter(String secondLetter) {
        this.secondLetter = secondLetter;
    }

    public String getThirdLetter() {
        return thirdLetter;
    }

    public void setThirdLetter(String thirdLetter) {
        this.thirdLetter = thirdLetter;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public String getPlateData(){
        return getFirstLetter()+" "+getSecondLetter()+" "+getThirdLetter()+" "+getNumbers();
    }

}