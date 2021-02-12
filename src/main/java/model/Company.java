package model;

public class Company extends User{

    private String companyName;
    private String country;
    private int CVR;

    public Company(String username, int phonenumber, double balance, String name, String country, int CVR) {
        super(username, phonenumber, balance);
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