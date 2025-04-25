public class entry {

    private int period;
    private String Birth_Death;
    private String region;
    private int count;

    public entry (int period,String Birth_Death, String region,int count){
        this.period=period;
        this.Birth_Death=Birth_Death;
        this.region=region;
        this.count=count;
    }

    public int getPeriod() {
        return period;
    }

    public String getBirth_Death() {
        return Birth_Death;
    }

    public String getRegion() {
        return region;
    }

    public int getCount() {
        return count;
    }

}
