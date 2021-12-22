package exercise;

public class Case {
    private int number;
    private int value;

    public Case(int number, int value){
        this.number = number;
        this.value = value;
    }

    @Override
    public String toString() {
        return "[" + (number+1) +
                "]";
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getValue(){
        return value;
    }
}
