public class Dog extends Pet {
    private String breed;
    
    public Dog(String name, int age, String breed) {
        super(name, age);
        this.breed = breed;
    }
    
    public String getBreed() {
        return this.breed;
    }
    
    public void play() {
        if(this.happiness < 10) {
            this.happiness += 2;
        }
        if(this.energy > 0) {
            this.energy -= 1;
        }
        if(this.hunger < 10) {
            this.hunger += 1;
        }
    }
    
    public void sleep() {
        if(this.energy < 10) {
            this.hunger ++;
        }
        if(this.hunger < 10) {
            this.hunger += 2;
        }
    }
    
    public void tick() {
        if(this.hunger < 10) {
            this.hunger ++;
        }
    }
        if(this.energy > 0) {
            this.energy --;
        }
        if (this.happiness > 0) {
            this.happiness --;
        }
            
    }
    public String showStatus() {
        return super.showStatus() + ", Breed: " + this.breed;
    
    }
}

