public class User {
    private String userName;
    private Pet[] pets;
    private int petsCount;

    public User(String username, Pet[] pets) {
        this.userName = username;
        this.pets = new Pet[pets.length];
        for (int i = 0; i < pets.length; i++) {
            this.pets[i] = new Pet(pets[i].getName(), pets[i].getAge());
        }
    }

    public String getUserName() {
        return userName;
    }

    public void addPet(Pet pet) {
        Pet[]  newPets = new Pet[pets.length + 1];
        for (int i = 0; i < pets.length; i++) {
            newPets[i] = pets[i];
        }
        newPets[pets.length] = new Pet(pet.getName(), pet.getAge());;
        pets = newPets;
    }

    public String showAllPets() {
        String allPets = "";
        for (int i = 0; i < pets.length; i++) {
            allPets = allPets + pets[i].showStatus() + "; ";
        }
        return allPets;
    }

    public Pet getPet(String name) {
        for(int i = 0; i < pets.length; i++) {
            if(pets[i].getName().equals(name)) {
                return pets[i];
            }
        } return null;
    }
}
