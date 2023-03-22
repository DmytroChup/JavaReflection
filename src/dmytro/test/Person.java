package dmytro.test;

public class Person {
    private String name;
    private Integer age;
    private Integer kids;

    public Person(String name, int age, int kids) {
        this.name = name;
        this.age = age;
        this.kids = kids;
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getKids() {
        return kids;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setKids(Integer kids) {
        this.kids = kids;
    }

    private void testPrivate() {
        System.out.println("testing...");
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", kids=" + kids +
                '}';
    }
}
