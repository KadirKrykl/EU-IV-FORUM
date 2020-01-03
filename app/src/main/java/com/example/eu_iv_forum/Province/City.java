package com.example.eu_iv_forum.Province;

public class City extends CityId{

    String name;
    String tradeGood;
    String tradeNode;
    String continent;
    String region;
    String area;
    String religion;
    String owner;
    String culture;
    String cultureGroup;
    int development;
    int bt;
    int bp;
    int bm;

    public City(){}

    public City(String name, String tradeGood, String tradeNode, String continent, String region, String area, String religion, String owner, String culture, String cultureGroup, int development, int bt, int bp, int bm) {
        this.name = name;
        this.tradeGood = tradeGood;
        this.tradeNode = tradeNode;
        this.continent = continent;
        this.region = region;
        this.area = area;
        this.religion = religion;
        this.owner = owner;
        this.culture = culture;
        this.cultureGroup = cultureGroup;
        this.development = development;
        this.bt = bt;
        this.bp = bp;
        this.bm = bm;
    }

    public  CharSequence getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  String getTradeGood() {
        return tradeGood;
    }

    public void setTradeGood(String tradeGood) {
        this.tradeGood = tradeGood;
    }

    public  String getTradeNode() {
        return tradeNode;
    }

    public void setTradeNode(String tradeNode) {
        this.tradeNode = tradeNode;
    }

    public  String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public  String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public  String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public  String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public  String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public  String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public  String getCultureGroup() {
        return cultureGroup;
    }

    public void setCultureGroup(String cultureGroup) {
        this.cultureGroup = cultureGroup;
    }

    public  int getDevelopment() {
        return development;
    }

    public void setDevelopment(int development) {
        this.development = development;
    }

    public  int getBt() {
        return bt;
    }

    public void setBt(int bt) {
        this.bt = bt;
    }

    public  int getBp() {
        return bp;
    }

    public void setBp(int bp) {
        this.bp = bp;
    }

    public  int getBm() {
        return bm;
    }

    public void setBm(int bm) {
        this.bm = bm;
    }

}
