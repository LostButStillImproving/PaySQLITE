package model;

public class Company extends User{

    private final String companyName;
    private final String country;
    private final int CVR;

    public Company(String username, int phonenumber, double balance, String name, String country, int CVR, int cardNumber) {
        super(username, phonenumber, balance, cardNumber);
        this.companyName = name;
        this.country = country;
        this.CVR = CVR;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + companyName+ '\'' +
                ", country='" + country + '\'' +
                ", CVR=" + CVR +
                '}';
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCountry() {
        return country;
    }

    public int getCVR() {
        return CVR;
    }
}
