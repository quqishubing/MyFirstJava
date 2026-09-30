public class Student {
    private final String name;
    private final double score;

    public Student(String name, double score) {
        this.name = name;
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    public void introduce() {
        System.out.println("我是" + name + "，成绩" + score);
    }
}
