package com.caort.verify;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Reed
 * @date 2021/7/27 下午3:05
 */
public class LambdaTest {

    private List<Student> studentList = null;

    @Before
    public void before() {
        studentList = Lists.newArrayList(new Student(21, "张三", false, 89.1),
                new Student(1, "李四", true, 90.0),
                new Student(2, "赵六", true, 60.0),
                new Student(25, "张八", false, 60.0));

        List<Student> students = Arrays.asList(new Student(21, "张三", false, 89.1),
                new Student(1, "李四", true, 90.0),
                new Student(2, "赵六", true, 60.0),
                new Student(25, "张八", false, 60.0));
    }

    @Test
    public void foreachTest() {
        List<String> stringList = Lists.newArrayList("aa", "bb", "cc");
        stringList.stream().forEach(System.out::println);
        stringList.stream().forEach(s -> System.out.println(s));
    }

    @Test
    public void filterTest() {
        //过滤大于80分学生
        List<Student> students = studentList.stream().filter(student -> student.getMathScore() > 80.0).collect(Collectors.toList());
        System.out.println(students);

        //过滤身高
        List<Student> student2 = studentList.stream().filter(Student::isHigh).collect(Collectors.toList());
        System.out.println(student2);

        //匹配是不是所有名字都叫张三
        boolean allMatch = studentList.stream().allMatch(student -> Objects.equals("张三", student.getName()));
        System.out.println(allMatch);

        //匹配是不是有叫张三
        boolean anyMatch = studentList.stream().anyMatch(student -> Objects.equals("张三", student.getName()));
        System.out.println(anyMatch);

        //找到名字叫张三的学生
        Student student1 = studentList.stream().filter(student -> Objects.equals("张三", student.getName())).findAny().orElse(null);
        System.out.println(student1);
    }

    @Test
    public void mapTest() {
        /*List<Student> collect1 = studentList.stream().map(student -> {
            student.setMathScore(Double.valueOf("100.00"));
            return student;
        }).collect(Collectors.toList());
        System.out.println(collect1);
        System.out.println(studentList);*/

        /*List<Student> collect1 = studentList.stream()
                .peek(student -> student.setMathScore(Double.valueOf("100.00")))
                .collect(Collectors.toList());
        System.out.println(collect1);
        System.out.println(studentList);*/


        //获取分数列表，去重
        List<Double> scoreList = studentList.stream().map(Student::getMathScore).distinct().collect(Collectors.toList());
        System.out.println(scoreList);

        //转成map，如果key重复，取第一个
        Map<Double, Student> collectMap = studentList.stream().collect(Collectors.toMap(Student::getMathScore,
                student -> student, (stu1, stu2) -> stu1));
        System.out.println(collectMap);

        //flatMap 对map数据组合
        List<String> stringList = Lists.newArrayList("china", "good");
        List<String> collect = stringList.stream().flatMap(s -> Arrays.stream(s.split(""))).collect(Collectors.toList());
        System.out.println(collect);

        List<String[]> collect1 = stringList.stream().map(s -> s.split("")).collect(Collectors.toList());
        System.out.println(collect1);

        //reduce 对列表数据分组处理
        Double sumScore = studentList.stream().map(Student::getMathScore).reduce((score1, score2) -> score1 + score2).orElse(0.0);
        System.out.println(sumScore);
    }

    @Test
    public void sortTest() {
        //通过id排序,逆序
        List<Student> sortedList =
                studentList.stream().sorted(Comparator.comparing(Student::getId).reversed()).collect(Collectors.toList());
        System.out.println(sortedList);

        //获取前两名学生成绩
        List<Student> top2Student =
                studentList.stream().sorted(Comparator.comparing(Student::getMathScore).reversed()).limit(2).collect(Collectors.toList());
        System.out.println(top2Student);

        //取第二名学生
        List<Student> secondStudent =
                studentList.stream().sorted(Comparator.comparing(Student::getMathScore).reversed()).limit(2).skip(1).collect(Collectors.toList());
        System.out.println(secondStudent);
    }

    /**
     * 并行流测试
     * 适用于每个线程数据独立
     */
    @Test
    public void parallelTest() {
        studentList.parallelStream().forEach(student -> System.out.println(student + Thread.currentThread().getName()));
    }

    /**
     * 统计测试
     * 主要用于int、double、long类型
     */
    @Test
    public void statTest() {
        DoubleSummaryStatistics statistics = studentList.stream().mapToDouble(Student::getMathScore).summaryStatistics();
        System.out.println("最大值 " + statistics.getMax());
        System.out.println("平均值 " + statistics.getAverage());
    }

    /**
     * 分组 group by 测试
     */
    @Test
    public void groupByTest() {
        Map<Double, List<Student>> listMap = studentList.stream().collect(Collectors.groupingBy(Student::getMathScore));
        System.out.println(listMap);

        Map<Boolean, List<Student>> booleanListMap = studentList.stream().collect(Collectors.partitioningBy(Student::isHigh));
        System.out.println(booleanListMap);

    }

    /**
     * peek map
     */
    @Test
    public void peekAndMapTest() {
        //只需要访问获取内部元素，打印
        List<String> stringList1 = Lists.newArrayList("11", "22", "33");
        stringList1.stream().peek(System.out::print).collect(Collectors.toList());

        List<String> stringList2 = Lists.newArrayList("11", "22", "33");

        //支持自定义返回值，将字符串转换为数字
        List<Integer> mapResultList = stringList2.stream().map(s -> Integer.valueOf(s)).collect(Collectors.toList());
        System.out.println(mapResultList);

        //可以看到返回值还是List<String>
        List<String> peekResultList = stringList2.stream().peek(s -> Integer.valueOf(s)).collect(Collectors.toList());
        System.out.println(peekResultList);
    }

    /**
     * 收集器测试
     */
    @Test
    public void collectTest(){
        Stack<Student> collect1 = studentList.stream().collect(Collectors.toCollection(Stack::new));
        System.out.println(collect1);
    }

    static class Student implements Serializable {
        /**
         * 序列号
         */
        private static final long serialVersionUID = 7544685819102239171L;

        /**
         * 学生编号
         */
        private int id;

        /**
         * 姓名
         */
        private String name;

        /**
         * 个子是否高
         */
        private boolean isHigh;

        /**
         * 学生成绩
         */
        private Double mathScore;

        public Student(int id, String name, boolean isHigh, Double mathScore) {
            this.id = id;
            this.name = name;
            this.isHigh = isHigh;
            this.mathScore = mathScore;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isHigh() {
            return isHigh;
        }

        public void setHigh(boolean high) {
            isHigh = high;
        }

        public Double getMathScore() {
            return mathScore;
        }

        public void setMathScore(Double mathScore) {
            this.mathScore = mathScore;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", isHigh=" + isHigh +
                    ", mathScore=" + mathScore +
                    '}';
        }
    }
}
