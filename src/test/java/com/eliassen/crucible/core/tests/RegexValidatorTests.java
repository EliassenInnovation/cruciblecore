package com.eliassen.crucible.core.tests;

import com.eliassen.crucible.core.helpers.RegexValidator;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class RegexValidatorTests
{
    @Test
    public void validate_shouldBeTrue_test() {
        String email = "test-email2@ThisIsATest.COM";
        Assertions.assertTrue(RegexValidator.validate(email, RegexValidator.EMAIL_PATTERN));
    }

    @Test
    public void validate_shouldBeFalse_test() {
        String email = "test-email2atThisIsTest.COM";
        Assertions.assertFalse(RegexValidator.validate(email, RegexValidator.EMAIL_PATTERN));
    }
}
