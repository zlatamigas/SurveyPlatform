package epam.zlatamigas.surveyplatform.util.keygenerator.impl;

import epam.zlatamigas.surveyplatform.util.keygenerator.ChangePasswordKeyGenerator;

import java.util.Random;

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

    @Override
    public int generateKey(){

        return MIN_KEY + random.nextInt(MAX_KEY - MIN_KEY);
    }
}
