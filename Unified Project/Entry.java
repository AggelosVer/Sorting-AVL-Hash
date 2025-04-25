public class Entry {
    private int period;
    private String birthDeath;
    private String region;
    private int count;

    public Entry(int period, String birthDeath, String region, int count) {
        this.period = period;
        this.birthDeath = birthDeath;
        this.region = region;
        this.count = count;
    }

    public int getPeriod() {
        return period;
    }

    public String getBirthDeath() {
        return birthDeath;
    }

    public String getRegion() {
        return region;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "period=" + period +
                ", birthDeath='" + birthDeath + '\'' +
                ", region='" + region + '\'' +
                ", count=" + count +
                '}';
    }
}
