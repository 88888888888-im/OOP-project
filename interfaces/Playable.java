package interfaces;

import exceptions.NoEnergyException;
import exceptions.NotHappyException;

public interface Playable {
    void play() throws NoEnergyException, NotHappyException;
}

