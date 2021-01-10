package com.javamentor.developer.social.platform.security.util;

import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.stereotype.Component;

@Component
public class PassayRandomPasswordGenerator {

    public String generatePassword() {
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        CharacterData lowerCase = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCase);
        lowerCaseRule.setNumberOfCharacters(2);

        CharacterData upperCase = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCase);
        upperCaseRule.setNumberOfCharacters(2);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitsRule = new CharacterRule(digitChars);
        digitsRule.setNumberOfCharacters(2);

        String password = passwordGenerator
                .generatePassword(10 , lowerCaseRule , upperCaseRule , digitsRule);

        return password;

    }
}
