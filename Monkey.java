public class Monkey extends Pet implements Playable {
    private String favoriteFruit;

    public Monkey(String name, int age, String favoriteFruit) {
        super(name, age);
        this.favoriteFruit = favoriteFruit;
    }

    public String getFavoriteFruit() {
        return this.favoriteFruit;
    }

    public void play() throws NoEnergyException {
        if(this.happiness < 10) {
            this.happiness += 1;
        }
        if(this.energy > 0) {
            this.energy -= 2;
        }else{
            throw new NoEnergyException("Not enough energy to play!");
        }

        if(this.hunger < 10) {
            this.hunger += 3;
        }
    }

    public void sleep() {
        if(this.energy < 10) {
            this.energy++;
        }
        if(this.hunger < 10) {
            this.hunger++;
        }
    }

    public void tick() {
        if(this.hunger < 10) {
            this.hunger++;
        }
        if(this.energy > 0) {
            this.energy--;
        }
        if(this.happiness > 0) {
            this.happiness--;
        }
    }

    public String showStatus() {
        return super.showStatus() + ", FavoriteFruit: " + this.favoriteFruit;
    }
}