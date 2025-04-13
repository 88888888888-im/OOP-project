public class Pet {
    private String name;
    private int age;
    private int hunger;
    private int energy;
    private int happiness;

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

    public void play(){
        if(this.happiness <  10) {
            this.happiness += 1;
        }
        if(this.energy > 0){
            this.energy -= 1;
        }
        if(this.hunger < 10) {
            this.hunger += 1;
        }
    }

    public void sleep(){
        if(this.energy <  10) {
            this.energy += 1;
        }
        if(this.hunger <  10) {
            this.hunger += 1;
        }
    }

    public void tick(){
        if(this.hunger <  10) {
            this.hunger ++;
        }
        if(this.energy > 0) {
            this.energy --;
        }
       if(this.happiness > 0) {
           this.happiness--;
       }
    }

    public String showStatus(){
        return "Name: " + this.name + ", Age: " + this.age + ", Hunger: " + this.hunger + ", Energy: " + this.energy + ", Happiness: " + this.happiness;
    }



}
