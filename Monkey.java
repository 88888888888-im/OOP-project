public class Monkey extends Pet {
    private String favoriteFruit;
    
    public Monkey(String name, int age, String breed) {
        super(name, age);
        this.favoriteFruit = favoriteFruit;
    }
    
    public String getFavoriteFruit() {
        return this.favoriteFruit;
    }
    
    public void play() {
        if(this.happiness < 10) {
            this.happiness += 1;
        }
        if(this.energy > 0) {
            this.energy -= 2;
        }
        if(this.hunger < 10) {
            this.hunger += 3;
        }
    }
    
    public void sleep() {
        if(this.energy < 10) {
            this.hunger ++;
        }
        if(this.hunger < 10) {
            this.hunger ++1;
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
        return super.showStatus() + ", FavoriteFruit: " + this.favoriteFruit;
    
    }
}

