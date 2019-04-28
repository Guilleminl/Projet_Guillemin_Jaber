public class Date {
    private int year;
    private int month;
    private int day;

    //Constructor
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
    //Getters and setters

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    //Hashcode, equals, toString

    @Override
    public int hashCode() {
        int result = 1;
        result = result * 11 + this.year;
        result = result * 11 + this.month;
        result = result * 11 + this.day;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (this.getClass()!=obj.getClass()) return false;
        Date other = (Date)obj;
        return this.year == other.getYear() && this.month == other.getMonth()
                && this.day == other.getDay();
    }

    @Override
    public String toString() {
        return "(" + this.day + '/' + this.month + '/' + this.year + ')';
    }
}
