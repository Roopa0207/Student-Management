package model;

public class Student {
    private int id;
    private String name;
    private int age;
    private String gender;
    private String course;

    public Student() {}

    public Student(String name, int age, String gender, String course) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.course = course;
    }

    public Student(int id, String name, int age, String gender, String course) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.course = course;
    }

    // getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    @Override
    public String toString() {
        return id + ": " + name + " (" + age + ", " + gender + ") - " + course;
    }
}
