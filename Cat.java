public class Cat extends Pet {
    private String color;
    
    public Cat(String name, int age, String color) {
        super(name, age);
        this.color = color;
    }
    
    public String getColor() {
        return this.color;
    }
    
    public void play() {
        if(this.happiness < 10) {
            this.happiness += 3;
        }
        if(this.energy > 0) {
            this.energy -= 2;
        }
        if(this.hunger < 10) {
            this.hunger += 2;
        }
    }
    
    public void sleep() {
        if(this.energy < 10) {
            this.hunger += 2;
        }
        if(this.hunger < 10) {
            this.hunger ++2;
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
        return super.showStatus() + ", Color: " + this.color;
    }
} 
    }
}
