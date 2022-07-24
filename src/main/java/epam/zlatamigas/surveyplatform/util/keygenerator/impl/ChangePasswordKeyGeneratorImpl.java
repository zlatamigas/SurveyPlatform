package epam.zlatamigas.surveyplatform.util.keygenerator.impl;

import epam.zlatamigas.surveyplatform.util.keygenerator.ChangePasswordKeyGenerator;

import java.util.Random;

/**
 * Key generator in specified diapason.
 */
public class ChangePasswordKeyGeneratorImpl implements ChangePasswordKeyGenerator {

    private static final int MIN_KEY = 1_000_000;
    private static final int MAX_KEY = 10_000_000;

    private static Random random;

    private static ChangePasswordKeyGeneratorImpl instance;

    private ChangePasswordKeyGeneratorImpl(){
        random = new Random();
    }

    public static ChangePasswordKeyGeneratorImpl getInstance() {
        if(instance == null){
            instance = new ChangePasswordKeyGeneratorImpl();
        }
        return instance;
    }

    /**
     * Generate random key in diapason.
     *
     * @return Key in [{@link #MIN_KEY}, {@link #MAX_KEY}).
     */
    @Override
    public int generateKey(){

        return MIN_KEY + random.nextInt(MAX_KEY - MIN_KEY);
    }
}
