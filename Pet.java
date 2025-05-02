public abstract class Pet {
    private String name;
    private int age;
    protected int hunger;
    protected int energy;
    protected int happiness;

    public Pet(String name, int age) {
        this.name = name;
        this.age = age;
        this.hunger = 5;
        this.energy = 5;
        this.happiness = 5;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public void feed(){
        if(this.hunger > 0){
            this.hunger--;
        }
    }

    public abstract void sleep();
    public abstract void tick();

    public String showStatus(){
        return "Name: " + this.name + ", Age: " + this.age + ", Hunger: " + this.hunger + ", Energy: " + this.energy + ", Happiness: " + this.happiness;
    }
}
