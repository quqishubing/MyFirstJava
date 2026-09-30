import java.util.ArrayList;  //import是导入、引进；java.util意思是“java自带的工具包”；ArrayList是动态数组，一个“可以无限变长的魔法盒子”，专门用来装很多个学生对象
import java.util.Collections;//Collections集合工具类，里面有很多现成的算法，比如排序
import java.util.Comparator;//Comparator比较器，专门用来规定“两个东西谁大谁小”的规则
import java.util.Scanner;//Scanner扫描器，接收你在键盘上敲进去的文字和数字

public class StudentManager {//public公开的，这个类谁都可以用；class类；StudentMannger类名，必须和文件名一模一样
    public static void main(String[] args) {
        //1.准备阶段
        Scanner sc = new Scanner(System.in);//造一个专门读取键盘输入的扫描器，名字叫sc
        ArrayList<Student> students = new ArrayList<>();//声明一个“魔法盒子”，里面只能装Student类型的数据，起名叫students
        boolean running = true;//设置一个叫“运行中”的开关，初始状态为“真',表示程序开始跑了
       //主循环与菜单
        while (running) { //循环：只要running是true，就无限循环，这就是为什么你能反复看到菜单的原因
            //"控制台程序"写法：先展示菜单-->接收指令-->执行指令-->再次展示菜单
            //2. 打印菜单
            System.out.println("1.录入成绩");
            System.out.println("2.显示全部");
            System.out.println("3.按分数排序");
            System.out.println("4.退出");
            System.out.println("请输入选项（1-4）");
           //3接收指令与防崩处理
            String input = readLine(sc);//String input声明一个字符串变量，叫input
            if (input == null) {//如果你直接关掉了控制台，程序会拿到null（空）
                break;//这时候为了防止程序崩溃，用break直接跳出循环结束程序
            }

            int choice;//声明一个整数变量，叫choice
            try { //try...catch是安全气囊；try{...}尝试做括号里的事
                choice = Integer.parseInt(input.trim());//Integer是整数工具类，parseInt是“解析为整数”；input是你输入的字；trim()是去掉前后的空格；合起来是尝试把你输入的文字变成真正的数字
            } catch (NumberFormatException e) {//如果输入了字母“abc”，转换数字就会失败，抛出数字格式异常；catch抓住这个错误，防止程序崩溃
                System.out.println("【请输入数字】");
                continue;//继续下一次循环
            }
            //4.分发任务switch
            switch (choice) { //根据你输入的数字choice，决定走哪条路
                case 1: //如果输入1，就执行下面的代码
                    addStudent(sc, students);//调用名叫addStudent的方法，把扫描器和盒子传进去，让它去处理录入
                    break;//打断；干完case1后跳出switch，不要让代码继续往下跑
                case 2:
                    showStudents(students);
                    break;
                case 3:
                    Collections.sort(students,//让集合工具帮我们排序
                            Comparator.comparingDouble(Student::getScore).reversed());//按照学生的分数降序进行比较
                    //comparingDouble按double类型的数值比较；Student::getScore调用Student对象里的getScore（）方法来获取分数；reversed()反转，默认是从小到大排，加上这个就变成从大到小排
                    System.out.println("已按分数从高到低排序：");
                    showStudents(students);
                    break;
                case 4:
                    System.out.println("感谢使用，再见！");
                    running = false;//把开关关掉，下次循环时，while (running)发现是false，循环就结束了，程序退出
                    break;
                default://如果你输入的不是1、2、3、4，就走这里，提示“选项无效”
                    System.out.println("选项无效，请重新输入（1-4）");
            }
        }
    }
      //5.具体干活的工具方法
    //addStudent方法（录入成绩）
    private static void addStudent(Scanner sc, ArrayList<Student> students) { //private私有的，表示这个方法只能在StudentManager这个类里面用，外面不能调用
        //...获取姓名...
        System.out.println("请输入姓名：");
        String name = readLine(sc);//调用我们自己的readLine工具，读取用户输入的姓名，存入name变量
        if (name == null) {//如果没有输入，name就是空的
            return;//如果没名字，直接结束这个方法，回到主菜单
        }

        while (true) { //循环是为了如果分数输错了，可以让你重新输分数
            System.out.println("请输入分数：");
            String input = readLine(sc);//用String接收是因为用户可能输入字母
            if (input == null) {
                return;
            }

            try {
                double score = Double.parseDouble(input.trim());
                students.add(new Student(name, score));//把这个新造的学生塞进“大盒子”里
                System.out.println("录入成功");
                return; //成功录入后，结束这个方法
            } catch (NumberFormatException e) {
                System.out.println("【请输入数字】");
            }
        }
    }
        //showStudents方法(显示全部）
    private static void showStudents(ArrayList<Student> students) {//与addStudent类似，但是不需要Scanner，因为它只是展示，不需要接收键盘输入
        if (students.isEmpty()) { //调用ArrayList的isEmpty方法（Empty是空的意思），如果盒子里没有数据，返回true
            System.out.println("暂无学生成绩");
            return; //列表为空，直接结束，不用往下走了
        }
//把大盒子里的每个学生，依次拿出来，叫它student，然后执行syudent.introdent()打印信息
        for (Student student : students) {  //增强for循环（或者叫for-each循环
            student.introduce();
        }
    }
//readLine工具方法
    //sc.hasNextLine()问问控制台“还有下一行输入吗？”
    //?三元运算符，如果有，就返回你敲进去的那行字（sc.nextLine());如果没有（比如程序被强制结束），就返回null
    private static String readLine(Scanner sc) {
        return sc.hasNextLine() ? sc.nextLine() : null;
    }
}
